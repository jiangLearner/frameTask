package com.jiang.practise.storm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class StormSpoutTest implements IRichSpout{

	public static Logger logger = LoggerFactory.getLogger(StormSpoutTest.class);
	
	private FileReader fileReader;
	private SpoutOutputCollector collector;
	private boolean completed = false;
	
	@Override
	public void ack(Object arg0) {
		// TODO Auto-generated method stub
		 System.out.println("OK:"+arg0);
	}

	@Override
	public void activate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fail(Object arg0) {
		// TODO Auto-generated method stub
		  System.out.println("FAIL:"+arg0);
	}

	@Override
	public void nextTuple() {
		// TODO Auto-generated method stub
		if(completed){
			 try {
                 Thread.sleep(1000);
             } catch (InterruptedException e) {
                 //什么也不做
             }
            return;
		}
		String str;
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		try {
			while((str = bufferedReader.readLine()) != null){
				this.collector.emit(new Values(str), str);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			completed = true;
		}
	}

	@Override
	public void open(Map arg0, TopologyContext arg1, SpoutOutputCollector arg2) {
		// TODO Auto-generated method stub
		logger.info("进入Storm----------OPEN");
		System.out.println("进入Storm----------OPEN");
		try {
			this.fileReader = new FileReader(arg0.get("wordsFile").toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.collector = arg2;
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		// TODO Auto-generated method stub
		arg0.declare(new Fields("line"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	public FileReader getFileReader() {
		return fileReader;
	}

	public void setFileReader(FileReader fileReader) {
		this.fileReader = fileReader;
	}

	public SpoutOutputCollector getCollector() {
		return collector;
	}

	public void setCollector(SpoutOutputCollector collector) {
		this.collector = collector;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

}
