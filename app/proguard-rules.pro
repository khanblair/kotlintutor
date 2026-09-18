# Kotlin Tutor — R8/ProGuard rules.
# Minify is currently disabled for release; these rules are written ahead of
# time so flipping isMinifyEnabled = true doesn't silently break the build.

# kotlinx.serialization: generated serializers are referenced by name via the
# plugin, so the serializer classes and their descriptor fields must survive.
-keepattributes *Annotation*, InnerClasses, Signature, EnclosingMethod
-keep,includedescriptorclasses class com.khanblair.kotlintutor.**$$serializer { *; }
-keepclassmembers class com.khanblair.kotlintutor.** {
    *** Companion;
}
-keepclassmembers class com.khanblair.kotlintutor.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Coroutines
-dontwarn kotlinx.coroutines.**

# OkHttp / Okio (Ktor engine)
-dontwarn okhttp3.**
-dontwarn okio.**

# Room generated implementations are referenced by name from generated code
-keep class * extends androidx.room.RoomDatabase { *; }
