#!/bin/bash

# Start Hadoop/YARN Cluster

CLASS=com.rueggerllc.flink.WordCount
JARFILE=/home/hadoop/code/flink/MyFlink/build/libs/MyFlink.jar

# Run Program
# -m  	manager/jobmanager yarn
# -yn   yarn:number of Task Managers to Allocate 
# -yjm  yarn:jobManagerMemory   Memory for JobManager in MB
# -ytm  yarn:taskManagerMemory  Memory for TaskManager in in MB
echo Run Apache Flink Job on Yarn Cluster
$FLINK_HOME/bin/flink run -m yarn-cluster -yn 4 -yjm 1024 -ytm 2096 -c $CLASS $JARFILE 

# Output
# tail -f $FLINK_HOME/log/flink-hadoop-taskmanager-0-captain.out
