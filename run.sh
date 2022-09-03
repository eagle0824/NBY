#!/bin/bash
./gradlew assembleDebug

adb install -t ./app/build/outputs/apk/debug/app-debug.apk
