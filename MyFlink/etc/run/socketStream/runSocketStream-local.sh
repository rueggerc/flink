#!/bin/bash

# Start Local Flink Cluster
# $FLINK_HOME/bin/start-local.sh

# Start netcat
# nc -l 9042

CLASS=com.rueggerllc.flink.SocketTextStreamWordCount
JARFILE=/home/hadoop/code/flink/MyFlink/build/libs/MyFlink.jar

# Run Program
$FLINK_HOME/bin/flink run -c $CLASS $JARFILE captain 9042


# Output
# tail -f $FLINK_HOME/log/flink-hadoop-taskmanager-0-captain.out
