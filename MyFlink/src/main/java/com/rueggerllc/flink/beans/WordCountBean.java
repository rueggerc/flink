package com.rueggerllc.flink.beans;

public class WordCountBean {
	
	private String word;
	private Integer count;
	
	public WordCountBean(String word, Integer count) {
		this.word = word;
		this.count = count;
	}
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	


}
