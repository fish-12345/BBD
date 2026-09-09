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

    data class SiteCheckResult(
        val domain: String,
        val successCount: Int,
        val avgPing: Long
    )

    suspend fun checkSitesAsync(
        sites: List<String>,
        requestsCount: Int,
        requestTimeout: Long,
        concurrentRequests: Int = 20,
        onSiteChecked: ((String, Int, Int) -> Unit)? = null
    ): List<SiteCheckResult> {
        val semaphore = Semaphore(concurrentRequests)
        return withContext(Dispatchers.IO) {
            sites.map { site ->
                async {
                    semaphore.withPermit {
                        val result = checkSiteAccess(site, requestsCount, requestTimeout)
                        onSiteChecked?.invoke(site, result.first, requestsCount)
                        SiteCheckResult(site, result.first, result.second)
                    }
                }
            }.awaitAll()
        }
    }

    private suspend fun checkSiteAccess(
        site: String,
        requestsCount: Int,
        timeout: Long
    ): Pair<Int, Long> = withContext(Dispatchers.IO) {
        var responseCount = 0
        var totalPing = 0L

        val formattedUrl = if (site.startsWith("http://") || site.startsWith("https://")) site
        else "https://$site"

        val url = try {
            URL(formattedUrl)
        } catch (_: Exception) {
            Log.e("SiteChecker", "Invalid URL: $formattedUrl")
            return@withContext 0 to 0L
        }

        val proxy = Proxy(Proxy.Type.SOCKS, InetSocketAddress(proxyIp, proxyPort))

        repeat(requestsCount) { attempt ->
            Log.i("SiteChecker", "Attempt ${attempt + 1}/$requestsCount for $site")

            var connection: HttpURLConnection? = null
            val startTime = System.currentTimeMillis()
            try {
                connection = url.openConnection(proxy) as HttpURLConnection
                connection.connectTimeout = (timeout * 1000).toInt()
                connection.readTimeout = (timeout * 1000).toInt()
                connection.instanceFollowRedirects = true
                connection.setRequestProperty("Connection", "close")

                val responseCode = connection.responseCode
                val endTime = System.currentTimeMillis()
                
                val declaredLength = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    connection.contentLengthLong
                } else {
                    connection.contentLength.toLong()
                }

                var actualLength = 0L
                try {
                    val inputStream = if (responseCode in 200..399) connection.inputStream else connection.errorStream
                    if (inputStream != null) {
                        val buffer = ByteArray(8192)
                        var bytesRead: Int

                        val limit = if (declaredLength > 0) declaredLength else 1024L * 1024

                        while (actualLength < limit) {
                            val remaining = limit - actualLength
                            val toRead = if (remaining > buffer.size) buffer.size else remaining.toInt()
                            bytesRead = inputStream.read(buffer, 0, toRead)
                            if (bytesRead == -1) break
                            actualLength += bytesRead
                        }
                    }
                } catch (_: IOException) {
                    // Stream reading failed
                }

                if (declaredLength <= 0L || actualLength >= declaredLength) {
                    Log.i("SiteChecker", "Response for $site: $responseCode, Declared: $declaredLength, Actual: $actualLength")
                    responseCount++
                    totalPing += (endTime - startTime)
                } else {
                    Log.w("SiteChecker", "Block detected for $site, Declared: $declaredLength, Actual: $actualLength")
                }

            } catch (e: Exception) {
                Log.e("SiteChecker", "Error accessing $site: ${e.message}")
            } finally {
                connection?.disconnect()
            }
        }

        val avgPing = if (responseCount > 0) totalPing / responseCount else 0L
        responseCount to avgPing
    }
}