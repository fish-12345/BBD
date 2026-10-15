# Keep JNI methods
-keepclasseswithmembernames class * {
    native <methods>;
}

-keep class io.github.romanvht.byedpi.core.ByeDpiProxy { *; }
-keep class io.github.romanvht.byedpi.data.Command { *; }
-keep class io.github.romanvht.byedpi.data.AppSettings { *; }

-keep,allowoptimization class io.github.romanvht.byedpi.core.TProxyService { *; }

-renamesourcefileattribute ''
-keepattributes SourceFile,InnerClasses,Signature,RuntimeVisibleAnnotations,*Annotation*
-allowaccessmodification
