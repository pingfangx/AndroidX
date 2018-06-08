#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring

JNICALL
Java_com_pingfangx_demo_ndkdemo_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jboolean JNICALL
Java_com_pingfangx_demo_ndkdemo_MainActivity_isEquals(JNIEnv *env, jobject instance,
                                                      jstring text_) {
    const char *text = env->GetStringUTFChars(text_, 0);
    int len = strlen(text);
    char *dest = (char *) malloc(len);
    strcpy(dest, text);

    int result = strcmp(text, "abcd");

    env->ReleaseStringUTFChars(text_, text);

    if (result == 0) {
        return 1;
    } else {
        return 0;
    }
}