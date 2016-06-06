package com.jiang.practise.nio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioChannel {
	
	/**
	 * 这些是Java NIO中最重要的通道的实现：
	FileChannel
	DatagramChannel
	SocketChannel
	ServerSocketChannel
	 * @param fileName
	 */
	
	public static void baseChannel(String fileName){
	
		RandomAccessFile file;
		try {
			file = new RandomAccessFile(fileName, "rw");
			FileChannel channel = file.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			channel.read(buffer);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
