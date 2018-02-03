#!/bin/bash

# Start Flink Cluster

JOB_MANAGER=captain:6123
CLASS=com.rueggerllc.flink.WordCount
JARFILE=/home/hadoop/code/flink/MyFlink/build/libs/MyFlink.jar

# Run Program
# -m  	manager/jobmanager
$FLINK_HOME/bin/flink run -m $JOB_MANAGER -c $CLASS $JARFILE 

# Output
# tail -f $FLINK_HOME/log/flink-hadoop-taskmanager-0-captain.out
