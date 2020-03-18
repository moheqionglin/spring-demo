#!/bin/bash
source deploy/application.conf

RUN="exec $JAVA_HOME/bin/java -server $JVMOPTS $APPOPS -cp :lib/* $MAINCLASS "
echo $RUN
nohup bash -c "eval $RUN" > console.log 2>&1 &
PID=$!
echo "start success! pid = $PID"
