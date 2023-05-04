#include <jni.h>        // JNI header provided by JDK
#include <stdio.h>      // C Standard IO Header
#include "com_averycowan_screenprotector_LockMechanism.h"   // Generated

extern int do_screen_lock_now();

// Implementation of the native method nativeLock()
JNIEXPORT void JNICALL Java_com_averycowan_screenprotector_LockMechanism_nativeLock
  (JNIEnv * env, jclass cls) {
   do_screen_lock_now();
   return;
}