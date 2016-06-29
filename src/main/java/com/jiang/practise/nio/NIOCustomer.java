package com.jiang.practise.nio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import org.springframework.util.StringUtils;

public class NIOCustomer {

	private Selector selector;

	public static void main(String[] args) {
		try {
			sendMessage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void sendMessage() throws IOException {
		Socket s = new Socket("127.0.0.1", 12032);
		InputStream inStram = s.getInputStream();
		OutputStream outStream = s.getOutputStream();
		// 输出
		PrintWriter out = new PrintWriter(outStream, true);
		out.print("getPublicKey你好！");
		out.flush();
		s.shutdownOutput();// 输出结束
		// 输入
		while(true){
			Scanner in = new Scanner(inStram);
			StringBuilder sb = new StringBuilder();
			while (in.hasNextLine()) {
				String line = in.nextLine();
				sb.append(line);
			}
			String response = sb.toString();
			if(null != response && !response.equals("")){
				System.out.println("response=" + response);
			}
		}
		
	}

	public NIOCustomer() {
		try {
			SocketChannel channel = SocketChannel.open();
			channel.socket().bind(new InetSocketAddress("127.0.0.1", 12032));
			channel.configureBlocking(false);
			selector = Selector.open();
			channel.register(selector, SelectionKey.OP_CONNECT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void start() {
		while (true) {
			try {
				selector.select();
				Set<SelectionKey> selectedKeys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = selectedKeys.iterator();
				if (iterator.hasNext()) {
					SelectionKey selectionKey = iterator.next();
					if (selectionKey.isConnectable()) {
						SocketChannel channel = (SocketChannel) selectionKey
								.channel();
						if (channel.isConnectionPending()) {
							channel.finishConnect();
							System.out.println("完成连接!");
							ByteBuffer buf = ByteBuffer.allocate(1024);
							buf.put("Hello,Server".getBytes());
							buf.flip();
							channel.write(buf);
						}
						channel.register(selector, SelectionKey.OP_READ);
					} else if (selectionKey.isReadable()) {
						SocketChannel channel = (SocketChannel) selectionKey
								.channel();
						ByteBuffer buf = ByteBuffer.allocate(1024);
						int count = channel.read(buf);
						if (count == -1) {
							channel.close();
							selectionKey.cancel();
							return;
						}
						String input = new String(buf.array()).trim();
						channel.register(selector, SelectionKey.OP_WRITE);
						System.out.println("接收到数据 ======" + input);
						buf.clear();
					} else if (selectionKey.isWritable()) {
						ByteBuffer buf = ByteBuffer.allocate(1024);
						SocketChannel channel = (SocketChannel) selectionKey
								.channel();
						buf.put("客户端写数据".getBytes());
						buf.flip();
						channel.write(buf);
						channel.register(selector, SelectionKey.OP_READ);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
