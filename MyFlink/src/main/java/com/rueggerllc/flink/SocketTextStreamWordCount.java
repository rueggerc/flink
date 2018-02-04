package com.rueggerllc.flink;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * This example shows an implementation of WordCount with data from a text
 * socket. To run the example make sure that the service providing the text data
 * is already up and running.
 *
 * <p>To start an example socket text stream on your local machine run netcat from
 * a command line: <code>nc -lk 9999</code>, where the parameter specifies the
 * port number.
 *
 * <p>Usage:
 * <code>SocketTextStreamWordCount &lt;hostname&gt; &lt;port&gt;</code>
 * <br>
 *
 * <p>This example shows how to:
 * <ul>
 * <li>use StreamExecutionEnvironment.socketTextStream
 * <li>write a simple Flink program
 * <li>write and use user-defined functions
 * </ul>
 *
 * @see <a href="www.openbsd.org/cgi-bin/man.cgi?query=nc">netcat</a>
 */
public class SocketTextStreamWordCount {

	//
	//	Program
	//

	public static void main(String[] args) throws Exception {

		if (args.length != 2){
			System.err.println("USAGE:\nSocketTextStreamWordCount <hostname> <port>");
			return;
		}

		String hostName = args[0];
		Integer port = Integer.parseInt(args[1]);

		// Set up the execution environment
		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

		// Get input data
		DataStream<String> text = env.socketTextStream(hostName, port);

		// Split up the lines in pairs (2-tuples) containing: (word,1)
		// Group by the tuple field "0" and sum up tuple field "1"
		DataStream<Tuple2<String, Integer>> counts =
		text.flatMap(new LineSplitter())
				.keyBy(0)
				.timeWindow(Time.seconds(15))
				.sum(1);

		counts.print();

		// Execute
		env.execute("Java WordCount from SocketTextStream Example");
	}


	// Split line into Collection of Tuple2<word, 1>
	public static final class LineSplitter implements FlatMapFunction<String, Tuple2<String, Integer>> {

		@Override
		public void flatMap(String value, Collector<Tuple2<String, Integer>> out) {
			// Split the line into tokens
			String[] tokens = value.toLowerCase().split("\\W+");

			// Emit Tuple2:
			// <word, 1>
			for (String token : tokens) {
				if (token.length() > 0) {
					out.collect(new Tuple2<String, Integer>(token, 1));
				}
			}
		}
	}
}
