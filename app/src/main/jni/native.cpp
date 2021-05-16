//
// Created by Hyun Woo Lee on 2021/05/16.
//

#include "native.h"
#include <string>

#define SOPT_BASE_URL "http://cherishserver.com/"
#define GITHUB_BASE_URL "https://api.github.com/"

bool is_same_string(const char *lhs, const char *rhs) {
    return strcmp(lhs, rhs) == 0;
}

extern "C" {
    JNIEXPORT jstring
    JNICALL
    Java_com_l2hyunwoo_android_data_util_UrlStoreImpl_getConstant(
            JNIEnv *jEnv,
            jobject thiz,
            jstring key
    ) {
        const char *c_key = jEnv->GetStringUTFChars(key, 0);

        if (is_same_string(c_key, "SOPT_BASE_URL")) {
            return jEnv->NewStringUTF(SOPT_BASE_URL);
        }
        if (is_same_string(c_key, "GITHUB_BASE_URL")) {
            return jEnv->NewStringUTF(GITHUB_BASE_URL);
        }

        return jEnv->NewStringUTF("");
    }
}
