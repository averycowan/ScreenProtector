#!/usr/bin/env bash

export JAVA_HOME=$(/usr/libexec/java_home)

# Build the static library for the call
clang -c -fPIC lockscreen.c -o lockscreen.o
ar -rc liblockscreen.a lockscreen.o

# Build the native interface object
clang -c -fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/darwin com_averycowan_screenprotector_LockMechanism.c -o com_averycowan_screenprotector_LockMechanism.o

# Link into a library
clang -dynamiclib -o libnativelock.dylib com_averycowan_screenprotector_LockMechanism.o -lc -L. -llockscreen -F /System/Library/PrivateFrameworks -framework login

# Cleanup
rm lockscreen.o
rm liblockscreen.a
rm com_averycowan_screenprotector_LockMechanism.o
cp libnativelock.dylib com/averycowan/screenprotector/