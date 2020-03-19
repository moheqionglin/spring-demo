#!/bin/bash
source deploy/application.conf

# check process exists
PID=`cat $PID_FILE 2>/dev/null`

line=`jps -l | grep $MAINCLASS | wc -l 2>/dev/null `

if [ $line = 0 ] ; then
    echo "starting process ... "

else
     echo "process is running,  pid is "$PID
     exit 0
fi


RUN="exec $JAVA_HOME/bin/java -server $JVMOPTS $APPOPS -cp :lib/* $MAINCLASS "
echo $RUN
nohup bash -c "eval $RUN" > console.log 2>&1 &
PID=$!
echo "start success! pid = $PID"
echo $PID > $PID_FILE 2>/dev/null
