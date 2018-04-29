package com.rueggerllc.flink.stream;


import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;


public class Words {

	public static void main(String[] args) throws Exception {
		
		try {
			
			final ParameterTool parms = ParameterTool.fromArgs(args);
			
			StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
			
		
			
		} catch (Exception e) {
			System.out.println("ERROR:\n" + e);
		}
	}
	
	
}
