package com.rueggerllc.flink.stream;


import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;


/*
 * Start nc -l 9999
 * Enter numbers and strings
 */
public class FilterStrings {

	public static void main(String[] args) throws Exception {
		
		try {
			StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
			DataStream<String> dataStream = env
					.socketTextStream("localhost", 9999)
					// .filter(new Filter());
					.filter(s -> {
						            try {
						            	Double.parseDouble(s);
						            	return true;
						            } catch(Exception e) {
						            	return false;
						            }
								 });
			dataStream.print();
			
			// Start
			env.execute("Filter Strings");
			
		} catch (Exception e) {
			System.out.println("ERROR:\n" + e);
		}
	}
	
	public static class Filter implements FilterFunction<String> {
		public boolean filter(String input) throws Exception {
			try {
				Double.parseDouble(input.trim());
				return true;
			} catch (Exception e) {
				return false;
			}
		}
	}
	
	public static class Mapper implements MapFunction<String,String> {
		public String map(String input) throws Exception {
			try {
				return null;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
}
