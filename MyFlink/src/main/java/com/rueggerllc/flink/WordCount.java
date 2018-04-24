package com.rueggerllc.flink;

/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

import com.rueggerllc.flink.beans.WordCountBean;

public class WordCount {

	public static void main(String[] args) throws Exception {
		
		try {
			System.out.println("HERE WE GO WITH WORD COUNT");
	
			// Setup
			ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
			
			execute0(env);
			// execute1(env);
			// execute2(env);
			// execute3(env);
			// execute4(env);
			// execute5(env);
		
		} catch (Exception e) {
			System.out.println("ERROR:\n" + e);
		}
	}
	
	private static void execute0(ExecutionEnvironment env) throws Exception {
		DataSet<String> text = env.readTextFile("C://data//foo.txt");
		DataSet<Tuple2<String, Integer>> counts = text
			.flatMap(new LineSplitter())
			.groupBy(0)
			.sum(1);
		counts.print();		
	}
	
	private static void execute1(ExecutionEnvironment env) throws Exception {
		DataSet<String> text = env.readTextFile("C://data//foo.txt");
		DataSet<Tuple2<String, Integer>> counts = text.flatMap(new LineSplitter());
		DataSet<Tuple2<String, Integer>> results = counts.groupBy(0).sum(1);
		results.print();		
	}
	
	private static void execute2(ExecutionEnvironment env) throws Exception {
		DataSet<String> text = env.fromElements(
		"To be, or not to be,--that is the question:--",
		"Whether 'tis nobler in the mind to suffer",
		"The slings and arrows of outrageous fortune",
		"Or to take arms against a sea of troubles",
		"Yo Ruge here is some extra input Foo Bar Foo Foo"
		);
		DataSet<Tuple2<String, Integer>> counts = text.flatMap(new LineSplitter());
		DataSet<Tuple2<String, Integer>> results = counts.groupBy(0).sum(1);
		results.print();		
	}
	
	private static void execute3(ExecutionEnvironment env) throws Exception {
		DataSet<String> text = env.readTextFile("hdfs://captain:9000/inputs/word_count.text");
		DataSet<Tuple2<String, Integer>> counts = text.flatMap(new LineSplitter());
		DataSet<Tuple2<String, Integer>> results = counts.groupBy(0).sum(1);
		results.print();		
	}
	
	private static void execute4(ExecutionEnvironment env) throws Exception {
		DataSet<String> text = env.readTextFile("C://data//foo.txt");
		DataSet<Tuple2<String, Integer>> counts = 
			text.flatMap(new FlatMapFunction<String,Tuple2<String,Integer>>() {
				@Override 
				public void flatMap(String line,Collector<Tuple2<String,Integer>> out) {
					String[] tokens = line.toLowerCase().split("\\W+");
					for (String token : tokens) {
						if (token.length() > 0) {
							out.collect(new Tuple2<String, Integer>(token, 1));
						}
					}		
				}
			});
		DataSet<Tuple2<String, Integer>> results = counts.groupBy(0).sum(1);
		results.print();		
	}
	
	
	private static void execute5(ExecutionEnvironment env) throws Exception {
		DataSet<String> text = env.readTextFile("C://data//foo.txt");
		DataSet<Tuple2<String, Integer>> counts = 
			text.flatMap( (line, collector) -> {
				String[] tokens = line.toLowerCase().split("\\W+");
				for (String token : tokens) {
					if (token.length() > 0) {
						collector.collect(new Tuple2<String, Integer>(token, 1));
					}
				}				
			});
		DataSet<Tuple2<String, Integer>> results = counts.groupBy(0).sum(1);
		results.print();		
	}
	


	public static final class LineSplitter implements FlatMapFunction<String, Tuple2<String, Integer>> {
		@Override
		public void flatMap(String value, Collector<Tuple2<String, Integer>> out) {
			String[] tokens = value.toLowerCase().split("\\W+");
			for (String token : tokens) {
				if (token.length() > 0) {
					out.collect(new Tuple2<String, Integer>(token, 1));
				}
			}
		}
	}
}
