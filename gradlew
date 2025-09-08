#!/usr/bin/env sh
# Gradle wrapper start (POSIX)
APP_BASE_NAME=${0##*/}
DIRNAME=$(cd "$(dirname "$0")" && pwd)
CLASSPATH="$DIRNAME/gradle/wrapper/gradle-wrapper.jar"
JAVA_OPTS="${JAVA_OPTS:-}"
exec "${JAVA_HOME:-}/bin/java" -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"