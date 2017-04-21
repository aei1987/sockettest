package com.lyf.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.lyf.entity.File;
import com.lyf.entity.User;
import com.lyf.service.FileService;
import com.lyf.service.UserService;
import com.lyf.util.CommandTransfer;

/*
 *多线程服务端
 */
public class ServerThread extends Thread {
	
	private Socket socket = null;
	private ObjectInputStream ois = null;
	private ObjectOutputStream oos = null;
	private UserService us = new UserService();
	private FileService fs = new FileService();
	
	public ServerThread(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
			CommandTransfer transfer = (CommandTransfer) ois.readObject();
			transfer = execute(transfer);
			oos.writeObject(transfer);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public CommandTransfer execute(CommandTransfer transfer) {
		String cmd = transfer.getCmd();
		if(cmd.equals("login")) {
			User user = (User) transfer.getData();
			boolean flag = us.login(user);
			transfer.setFlag(flag);
			if(flag) {
				transfer.setResult("登陆成功！");
			} else {
				transfer.setResult("用户名或密码不正确，请重新登陆！");
			}
		} else if(cmd.equals("register")) {
			User user = (User) transfer.getData();
			us.register(user);
			transfer.setFlag(true);
			transfer.setResult("注册成功！");
		} else if(cmd.equals("uploadFile")) {
			File file = (File) transfer.getData();
			fs.save(file);
			transfer.setFlag(true);
			transfer.setResult("文件上传成功！");
		}
		return transfer;
	}

}
