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

-keep class com.demo.converter.view.model.CurrencyExchangeUiState{*;}
-keep class com.demo.converter.domain.entity.** {*;}
-keep class com.demo.converter.data.network.response** {*;}


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

# This is a configuration file for ProGuard.
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose
-dontpreverify


# Enable Optimization. # Optimization is turned off by default.
-optimizations   code/simplification/arithmetic,!code/simplification/cast,!field
-optimizationpasses 5
-allowaccessmodification


#Disable Optimization
#-dontoptimize
#-dontpreverify

# Remove Log command from code
-assumenosideeffects class android.util.Log{
 public static *** d(...);
 public static *** i(...);
 public static *** v(...);
}

# -------------------------------------------------
-keep public class kotlin.**
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.app.backup.BackupAgent
-keep public class * extends android.preference.Preference

-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.support.v4.app.DialogFragment
-keep public class * extends com.actionbarsherlock.app.SherlockListFragment
-keep public class * extends com.actionbarsherlock.app.SherlockFragment
-keep public class * extends android.app.Fragment
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * extends java.lang.Exception

# -------------------------------------------------
#injars libs
-keepattributes *Annotation*, Signature, Exception, EnclosingMethod, InnerClasses
-keepattributes JavascriptInterface

# Keep source file name and line number
-keepattributes SourceFile,LineNumberTable

-keep class okhttp3.** {*;}
-keep interface okhttp3.** {*;}
-dontwarn okhttp3.**

-keep class okio.** { *; }
-keep interface okio.** { *; }
-dontwarn okio.**

-keep class org.apache.http.** { *; }
-keep class org.apache.james.mime4j.** { *; }
-dontwarn org.apache.**

-keep class com.activeandroid.** { *; }
-keep class com.activeandroid.**.** { *; }
-dontwarn com.activeandroid.**

-keep class * extends com.activeandroid.Model
-keep class * extends com.activeandroid.serializer.TypeSerializer

-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }
-dontwarn android.support.v7.**

# Dontwarn-----------------------------------------
-dontwarn javax.**
-dontwarn java.lang.management.**
-dontwarn org.apache.log4j.**
-dontwarn org.apache.commons.logging.**
-dontwarn android.support.**
-dontwarn com.google.ads.**
-dontwarn org.slf4j.**
-dontwarn org.json.**


-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}


# -------------------------------------------------
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembernames class * {
    native <methods>;
}


-keepclassmembers class * extends android.content.Context {
   public void *(android.view.View);
   public void *(android.view.MenuItem);
}

-keepclassmembers class * implements android.os.Parcelable {
    static ** CREATOR;
}

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}


# Support Library
-keep class android.support.** {*;}
-keep interface android.support.** {*;}


# Needed when building against Marshmallow SDK.
  -dontwarn android.app.Notification


# Retrofit and GSON
-keep class com.squareup.okhttp3.** { *; }
-keep interface com.squareup.okhttp3.** { *; }
-dontwarn com.squareup.okhttp3.**

-keep class retrofit2.** { *; }
-dontwarn retrofit2.**

-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**

-keep class sun.misc.Unsafe.** { *; }
-dontwarn sun.misc.Unsafe.**

-keep public class com.google.gson.** {*;}
-keep class * implements com.google.gson.** {*;}
-keep class com.google.gson.stream.** { *; }
-dontwarn com.google.gson.**

#-keep class * implements com.google.gson.TypeAdapterFactory
#-keep class * implements com.google.gson.JsonSerializer
#-keep class * implements com.google.gson.JsonDeserializer

-keepclasseswithmembers class * {@retrofit2.http.* <methods>;}
-keepclasseswithmembers interface * { @retrofit2.* <methods>;}
-dontwarn com.google.appengine.**
-dontwarn java.nio.file.**
-dontwarn org.codehaus.**
-dontwarn org.codehaus.mojo.**
-dontwarn retrofit2.Platform$Java8
-dontnote retrofit2.Platform
-dontnote retrofit2.Platform$IOS$MainThreadExecutor


# EventBus
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}


# OkHttp and Picasso
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**
-dontwarn com.jakewharton.picasso.**

# Otto
-keepclassmembers class ** {
    @com.squareup.otto.Subscribe public *;
    @com.squareup.otto.Produce public *;
}

# Slidingmenu
-keep class com.jeremyfeinstein.** { *; }

# Pulltorefresh
-keep class uk.co.senab.actionbarpulltorefresh.library.** { *; }

# OrmLite
-keep class com.j256.**
-keepclassmembers class com.j256.** { *; }
-keep enum com.j256.**
-keepclassmembers enum com.j256.** { *; }
-keep interface com.j256.**
-keepclassmembers interface com.j256.** { *; }
-dontwarn com.j256.**

# Keep line number
-keepattributes SourceFile,LineNumberTable


-keepattributes Signature



-keepnames class com.shaded.fasterxml.** { *; }
-dontwarn org.shaded.apache.**

-keep class org.apache.** { *; }
-dontwarn org.apache.**


-keepnames class org.ietf.jgss.** { *; }
-dontwarn org.ietf.jgss.**

-keepnames class com.fasterxml.jackson.** { *; }
-dontwarn com.fasterxml.**

-keepnames class javax.servlet.** { *; }

-dontwarn org.w3c.dom.**
-dontwarn org.joda.time.**
-dontnote com.firebase.client.core.GaePlatform

-dontwarn android.databinding.**
-keep class android.databinding.** { *; }

#---------------------------------------------------------------------------------------
# My Personal
#-keep public class shishirtstudio.com.proguardtest.MyPack.** {
#  private protected public *;
#}

# CirlceImageView- No need pro guard rules..
# --------------------------------------------------
# Here include the POJO's that have you have created for mapping JSON response to POJO in Retrofit/Application classes that will be serialized/deserialized over Gson
-keep class shishirtstudio.com.proguardtest.data.network.apiResponse.** { *; }


# Stripe Rules
-keep class android.support.design.widget.TextInputLayout { *; }
-keep class android.support.design.widget.CollapsingTextHelper { *; }
-keep class org.bouncycastle.jcajce.provider.** { *; }
-keep class org.bouncycastle.jce.provider.** { *; }


## Retrofit Rules
#-dontwarn retrofit.**
#-keep class retrofit.** { *; }
#-keepattributes Signature
#-keepattributes Exceptions
#
## Okhttp Rules
#-keepattributes Signature
#-keepattributes *Annotation*
#-keep class okhttp3.** { *; }
#-keep interface okhttp3.** { *; }
#-dontwarn okhttp3.**
#-dontnote okhttp3.**

## Okio
#-keep class sun.misc.Unsafe { *; }
#-dontwarn java.nio.file.*
#-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
#

#Gson
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }

# Parceler library
-keep interface org.parceler.Parcel
-keep @org.parceler.Parcel class * { *; }
-keep class **$$Parcelable { *; }

# Retro Lmbada Rules
-dontwarn java.lang.invoke.*




# Android x Rules
-dontwarn com.google.android.material.**
-keep class com.google.android.material.** { *; }
-dontwarn androidx.**
-keep class androidx.** { *; }
-keep interface androidx.** { *; }

# Android Support Rules
-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-dontwarn android.support.v7.**
-keep class android.support.v7.** { *; }

-dontwarn com.google.devtools.ksp.processing.SymbolProcessorProvider

# JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**

-keepclasseswithmembers class * {
    @com.squareup.moshi.* <methods>;
}

-keep @com.squareup.moshi.JsonQualifier @interface *

# Enum field names are used by the integrated EnumJsonAdapter.
# values() is synthesized by the Kotlin compiler and is used by EnumJsonAdapter indirectly
# Annotate enums with @JsonClass(generateAdapter = false) to use them with Moshi.
-keepclassmembers @com.squareup.moshi.JsonClass class * extends java.lang.Enum {
    <fields>;
    **[] values();
}

# Keep helper method to avoid R8 optimisation that would keep all Kotlin Metadata when unwanted
-keepclassmembers class com.squareup.moshi.internal.Util {
    private static java.lang.String getKotlinMetadataClassName();
}

# Keep ToJson/FromJson-annotated methods
-keepclassmembers class * {
  @com.squareup.moshi.FromJson <methods>;
  @com.squareup.moshi.ToJson <methods>;
}

