package com.jiang.practise.storm;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

public class StormMainTest {
	
	public static String filaPath = "C:/Users/jiangjialin/Desktop/新建文件夹 (2)/新建文本文档.txt";
	
	public static void main(String[] args) throws InterruptedException{
		
		//定义拓扑
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("word-reader", new StormSpoutTest());
		builder.setBolt("word-normalizer", new StormBoltTest()).shuffleGrouping("word-reader");
		builder.setBolt("word-counter", new StormBolt2Test(),2).fieldsGrouping("word-normalizer", new Fields("word"));
		
		//配置
		Config conf = new Config();
		conf.put("wordsFile", filaPath);
		conf.setDebug(true);
		
		//你要用一个LocalCluster对象运行这个拓扑
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("Getting-Started-Topologie", conf, builder.createTopology());
		Thread.sleep(2000);
	    cluster.shutdown();
	}

}
