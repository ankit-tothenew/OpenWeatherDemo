#include <string.h>
#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_khareankit_openweather_presenters_WeatherPresenterImpl_stringFromJNI(JNIEnv *env,
                                                                              jobject instance) {
        return (*env)->NewStringUTF(env, "a2e8eb7301ae46437a7dbf3eb0bcc3e2");
}