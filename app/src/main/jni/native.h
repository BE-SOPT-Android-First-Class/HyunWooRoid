//
// Created by Hyun Woo Lee on 2021/05/16.
//

#include <jni.h>
#include "android/log.h"

#ifndef HYUNWOOROID_NATIVE_H
#define HYUNWOOROID_NATIVE_H

#define LOGTAG "Native"
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,LOGTAG,__VA_ARGS__)

extern "C" {
    JNIEXPORT jstring
    JNICALL
    Java_com_l2hyunwoo_android_utils_NativeLibImpl_getConstant(
            JNIEnv *jEnv,
            jobject thiz,
            jstring key
    );
}

#endif //HYUNWOOROID_NATIVE_H
