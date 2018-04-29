package com.rueggerllc.flink.stream;


import java.util.Properties;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer08;



public class SensorConsumer {

	public static void main(String[] args) throws Exception {
		
		try {
			
			Properties properties = new Properties();
			properties.setProperty("bootstrap.servers", "captain:9092");
			properties.setProperty("zookeeper.connect", "captain:2181");
			properties.setProperty("group.id", "flinkConsumerGroup");
			
			StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
			DataStream<String> messageStream = env.addSource(new FlinkKafkaConsumer08<>("sensors", new SimpleStringSchema(), properties));
			
			// Execute program
			messageStream.rebalance().map(new MapFunction<String, String>() {
				private static final long serialVersionUID = -6867736771747690202L;

				@Override
				public String map(String value) throws Exception {
					return "Stream Data:" + value;
				}
			}).print();
			
			
			// messageStream.print();
			env.execute("Flink Sensor KafkaConsumer");
			System.out.println("HERE WE GO FLINK AND KAFKA");
			
		} catch (Exception e) {
			System.out.println("ERROR:\n" + e);
		}
	}
}
