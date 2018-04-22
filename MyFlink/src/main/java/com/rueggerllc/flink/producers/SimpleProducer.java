package com.rueggerllc.flink.producers;

import java.util.Scanner;

import org.apache.log4j.Logger;

public class SimpleProducer {
	
	private static final Logger logger = Logger.getLogger(SimpleProducer.class);
	
	public static void main(String args[]) {
		Scanner in = null;
		try {
	        in = new Scanner(System.in);
	        logger.info("Enter message(type exit to quit)");
	        String line = in.nextLine();
	        while(!line.equals("exit")) {
	        	logger.info("SEND=" + line);
	            line = in.nextLine();
	        }
	        in.close();
		} catch (Exception e) {
			logger.error("ERROR", e);
		} finally {
			close(in);
		}
	}
	
	private static void close(Scanner scanner) {
		if (scanner != null) {
			scanner.close();
		}
	}

}
