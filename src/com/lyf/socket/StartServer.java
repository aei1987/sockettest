package com.lyf.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class StartServer {
	
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(8800);
			Socket socket = null;
			System.out.println("**服务器即将启动，等待客户端的连接**");
			while(true) {
				socket = serverSocket.accept();
				ServerThread thread = new ServerThread(socket);
				thread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
