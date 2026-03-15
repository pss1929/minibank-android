# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#############################################
# General
#############################################

-keepattributes Signature
-keepattributes *Annotation*
-dontwarn javax.annotation.**

#############################################
# Retrofit / Gson
#############################################

-keep class retrofit2.** { *; }
-dontwarn retrofit2.**

-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**

# Keep API models
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

#############################################
# Hilt / Dagger
#############################################

-keep class dagger.** { *; }
-keep class javax.inject.** { *; }
-dontwarn dagger.hilt.**

#############################################
# Room Database
#############################################

-keep class androidx.room.** { *; }

# Keep Entities
-keep class * extends androidx.room.RoomDatabase

# Keep DAO
-keep interface *Dao

#############################################
# ViewBinding
#############################################

-keep class **Binding { *; }

#############################################
# Kotlin Coroutines
#############################################

-dontwarn kotlinx.coroutines.**

#############################################
# Navigation SafeArgs
#############################################

-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

#############################################
# Logging
#############################################

-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
}