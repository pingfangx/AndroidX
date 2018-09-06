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

# 保留类名
# -keep class **ProGuardTest

# 保留类和成员
#-keep class **ProGuardTest{*;}

# 未找到成员，仍保留类
#-keep class **ProGuardTest{
#    *** no();
#}

# 保留 keep() 方法及其类
#-keep class * {
#    *** keep();
#}

# 仅保留 keep() 方法，不保留类
#-keepclassmembers class *{
#    *** keep();
#}



# 找到成员，保留成员及类
#-keepclasseswithmembers class *{
#    *** keep();
#}

# 未找到成员，不保留类
#-keepclasseswithmembers class *{
#    *** no();
#}