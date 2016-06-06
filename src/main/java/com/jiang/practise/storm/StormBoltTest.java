package com.jiang.practise.storm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class StormBoltTest implements IRichBolt{

	private OutputCollector collector;
	
	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute(Tuple arg0) {
		// TODO Auto-generated method stub
		String sentence = arg0.getString(0);
		String[] words = sentence.split(" ");
		 for(String word : words){
             word = word.trim();
             if(!word.isEmpty()){
                 word=word.toLowerCase();
                 //发布这个单词
                 List a = new ArrayList();
                 a.add(arg0);
                 collector.emit(a,new Values(word));
             }
         }
		 collector.ack(arg0);
	}

	@Override
	public void prepare(Map arg0, TopologyContext arg1, OutputCollector arg2) {
		// TODO Auto-generated method stub
		this.collector = arg2;
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		// TODO Auto-generated method stub
		arg0.declare(new Fields("word"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	public OutputCollector getCollector() {
		return collector;
	}

	public void setCollector(OutputCollector collector) {
		this.collector = collector;
	}

}
