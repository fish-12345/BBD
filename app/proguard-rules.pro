# Keep JNI methods
-keepclasseswithmembernames class * {
    native <methods>;
}

-keep class io.github.romanvht.byedpi.core.ByeDpiProxy { *; }
-keep class io.github.romanvht.byedpi.data.Command { *; }
-keep class io.github.romanvht.byedpi.data.AppSettings { *; }

-keep,allowoptimization class io.github.romanvht.byedpi.core.TProxyService { *; }
-keep,allowoptimization class io.github.romanvht.byedpi.activities.** { *; }
-keep,allowoptimization class io.github.romanvht.byedpi.services.** { *; }
-keep,allowoptimization class io.github.romanvht.byedpi.receiver.** { *; }

-repackageclasses 'ru.gdlbo'
-renamesourcefileattribute ''
-keepattributes SourceFile,InnerClasses,EnclosingMethod,Signature,RuntimeVisibleAnnotations,*Annotation*,*Parcelable*
-allowaccessmodification
-verbose
-adaptclassstrings