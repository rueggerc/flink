package com.rueggerllc.flink;

import java.util.ArrayList;
import java.util.List;

import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;


public class CountOddEven {

	private static int maxCount = 10;
	
	public static void main(String[] args) throws Exception {
		
		// Set up the execution environment
		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		
		List<Tuple2<String,Integer>> data = new ArrayList<Tuple2<String,Integer>>();	
//		for (int i = 0; i < maxCount; i++) {
//			if ((i%2) != 0) {
//				data.add(new Tuple2<>("odd", i));
//			} else {
//				data.add(new Tuple2<>("even", i));
//			}
//		}
		
		
		data.add(new Tuple2<>("odd", 1));
		data.add(new Tuple2<>("odd", 3));
		data.add(new Tuple2<>("odd", 5));
		data.add(new Tuple2<>("odd", 7));
		data.add(new Tuple2<>("even", 2));
		data.add(new Tuple2<>("even", 4));
		data.add(new Tuple2<>("even", 6));
		data.add(new Tuple2<>("even", 8));
		
		DataStream<Tuple2<String,Integer>> tuples = env.fromCollection(data);
		
		// KeyedStream<T, Key>
		// Tuple
		KeyedStream<Tuple2<String,Integer>, Tuple> oddsAndEvens = tuples.keyBy(0);
		
		DataStream<Tuple2<String,Integer>> sums =
			oddsAndEvens.reduce(new ReduceFunction<Tuple2<String,Integer>>() {
				@Override
				public Tuple2<String,Integer> reduce(Tuple2<String,Integer> t1, Tuple2<String,Integer> t2) throws Exception {
				  return new Tuple2<>(t1.f0, t1.f1 + t2.f1);
				}
		    });
		
//		DataStream<Tuple2<String,Integer>> sums =
//				oddsAndEvens.reduce( (t1,t2) -> {
//					return new Tuple2<>(t1.f0, t1.f1 + t2.f1);
//				});

		
		sums.print();
		
		
		// hdfs dfs -mkdir /outputs/OddEvenCount
		sums.writeAsText("hdfs://captain:9000/outputs/OddEvenCount/oddEvenCount.txt");
		
		// Execute
		env.execute();
		
		System.out.println("WE ARE DONE COUNTING ODD EVEN");
		
		
	}


}
