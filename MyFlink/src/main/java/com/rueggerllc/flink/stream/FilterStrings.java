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
			execute0(env);
		} catch (Exception e) {
			System.out.println("ERROR:\n" + e);
		}
	}
	
	private static void execute0(StreamExecutionEnvironment env) throws Exception {
		DataStream<Long> dataStream = env
				.socketTextStream("localhost", 9999)
				.filter(new Filter())
				.map(new Rounder());
		dataStream.print();
		
		// Start
		env.execute("FilterAndRound");
	}
	
	private static void execute1(StreamExecutionEnvironment env) throws Exception {
		DataStream<String> dataStream = env
				.socketTextStream("localhost", 9999)
				.filter(s -> {
					            try {
					            	Double.parseDouble(s);
					            	return true;
					            } catch(Exception e) {
					            	System.out.println("NOPE=" + s);
					            	return false;
					            }
							 });
		dataStream.print();
		
		// Start
		env.execute("Filter Strings");		
	}
	
	
	public static class Filter implements FilterFunction<String> {
		public boolean filter(String input) throws Exception {
			try {
				Double.parseDouble(input.trim());
				return true;
			} catch (Exception e) {
				System.out.println("Filter Out=" + input);
				return false;
			}
		}
	}
	
	public static class Rounder implements MapFunction<String, Long> {
		public Long map(String input) throws Exception {
			double d = Double.parseDouble(input);
			return Math.round(d);
		}
	}
	
}
