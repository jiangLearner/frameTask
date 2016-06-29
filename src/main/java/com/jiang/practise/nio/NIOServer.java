package com.jiang.practise.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {

	private Selector selector;
	
	public static void main(String[] args){
		NIOServer nioServer = new NIOServer(12032);
		nioServer.listen();
	}
	
	public NIOServer (int port){
		try {
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.socket().bind(new InetSocketAddress("127.0.0.1", port));
			serverSocketChannel.configureBlocking(false);
			selector = Selector.open();
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void listen(){
		while(true){
			try {
				int readyChannels = selector.select();
				if(readyChannels == 0) continue;
				Set<SelectionKey> selectedKeys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = selectedKeys.iterator();
				while(iterator.hasNext()){
					SelectionKey selectionKey = iterator.next();
					iterator.remove();
					if(selectionKey.isAcceptable()){
						ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
						SocketChannel acceptChannel = channel.accept();
						acceptChannel.configureBlocking(false);
						acceptChannel.register(selector, SelectionKey.OP_READ);
					}else if(selectionKey.isReadable()){
						SocketChannel channel = (SocketChannel)selectionKey.channel();
						ByteBuffer buf = ByteBuffer.allocate(1024);
						int count = channel.read(buf);
						if(count == -1){
							channel.close();  
					        continue;  
						}
						String input = new String(buf.array()).trim();
						channel.register(selector, SelectionKey.OP_WRITE  | SelectionKey.OP_READ);
						System.out.println("接收到数据 ======"+input);
						buf.clear();
					}else if(selectionKey.isWritable()){
						ByteBuffer buf = ByteBuffer.allocate(1024);
						SocketChannel channel = (SocketChannel)selectionKey.channel();
						buf.put("服务端写数据".getBytes());
						buf.flip();
						channel.write(buf);
						channel.register(selector, SelectionKey.OP_READ  | SelectionKey.OP_WRITE);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class sendMessageThread extends Thread{
	
	SelectionKey selectionKey;
	Selector selector;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		ByteBuffer buf = ByteBuffer.allocate(1024);
		SocketChannel channel = (SocketChannel)selectionKey.channel();
		buf.put("服务端写数据".getBytes());
		buf.flip();
		try {
			channel.write(buf);
			channel.register(selector, SelectionKey.OP_READ  | SelectionKey.OP_WRITE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
