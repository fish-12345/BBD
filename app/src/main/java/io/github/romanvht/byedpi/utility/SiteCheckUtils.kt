package io.github.romanvht.byedpi.utility

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpURLConnection
import java.net.InetSocketAddress
import java.net.Proxy
import java.net.URL

class SiteCheckUtils(
    private val proxyIp: String,
    private val proxyPort: Int
) {

    suspend fun checkSitesAsync(
        sites: List<String>,
        requestsCount: Int,
        requestTimeout: Long,
        concurrentRequests: Int = 20,
        onSiteChecked: ((String, Int, Int) -> Unit)? = null
    ): List<Pair<String, Int>> {
        val semaphore = Semaphore(concurrentRequests)
        return withContext(Dispatchers.IO) {
            sites.map { site ->
                async {
                    semaphore.withPermit {
                        val successCount = checkSiteAccess(site, requestsCount, requestTimeout)
                        onSiteChecked?.invoke(site, successCount, requestsCount)
                        site to successCount
                    }
                }
            }.awaitAll()
        }
    }

    private suspend fun checkSiteAccess(
        site: String,
        requestsCount: Int,
        timeout: Long
    ): Int = withContext(Dispatchers.IO) {
        var responseCount = 0

        val formattedUrl = if (site.startsWith("http://") || site.startsWith("https://")) site
        else "https://$site"

        val url = try {
            URL(formattedUrl)
        } catch (_: Exception) {
            Log.e("SiteChecker", "Invalid URL: $formattedUrl")
            return@withContext 0
        }

        val proxy = Proxy(Proxy.Type.SOCKS, InetSocketAddress(proxyIp, proxyPort))

        repeat(requestsCount) { attempt ->
            Log.i("SiteChecker", "Attempt ${attempt + 1}/$requestsCount for $site")

            var connection: HttpURLConnection? = null
            try {
                connection = url.openConnection(proxy) as HttpURLConnection
                connection.connectTimeout = (timeout * 1000).toInt()
                connection.readTimeout = (timeout * 1000).toInt()
                connection.instanceFollowRedirects = false
                connection.setRequestProperty("Connection", "close")
                connection.setRequestProperty("User-Agent", "Mozilla/5.0")

                val responseCode = connection.responseCode
                val isHttpSuccess = responseCode in 200..399
                if (!isHttpSuccess) {
                    Log.w("SiteChecker", "Non-success code for $site: $responseCode")
                    return@repeat
                }
                val declaredLength = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    connection.contentLengthLong
                } else {
                    connection.contentLength.toLong()
                }

                var actualLength = 0L
                try {
                    val inputStream = connection.inputStream
                    if (inputStream != null) {
                        val buffer = ByteArray(4096)
                        val bytesRead = inputStream.read(buffer, 0, buffer.size)
                        if (bytesRead > 0) actualLength = bytesRead.toLong()
                    }
                } catch (_: IOException) {
                    Log.w("SiteChecker", "Could not read body for $site")
                    return@repeat
                }

                val bodyOk = declaredLength <= 0L || actualLength > 0

                if (bodyOk) {
                    Log.i("SiteChecker", "Success for $site: $responseCode, body: $actualLength bytes")
                    responseCount++
                } else {
                    Log.w("SiteChecker", "Empty body for $site despite code $responseCode")
                }

            } catch (e: Exception) {
                Log.e("SiteChecker", "Connection failed for $site: ${e.message}")
            } finally {
                connection?.disconnect()
            }
        }

        responseCount
    }
}