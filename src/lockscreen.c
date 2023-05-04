#include "stdio.h"

extern void SACLockScreenImmediate();


void do_screen_lock_now() {
    // insert code here...
    printf("Locking Screen\n");
    return SACLockScreenImmediate();
}