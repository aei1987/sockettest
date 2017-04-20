package com.lyf.socket;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.lyf.entity.File;
import com.lyf.entity.User;
import com.lyf.util.CommandTransfer;

public class SocketClient {
	
	Scanner input = new Scanner(System.in);
	private Socket socket = null;
	
	public void showMainMenu() {
		System.out.println("***欢迎使用lyf文件上传器***");
		System.out.println("1.登陆\n2.注册\n3.退出");
		System.out.println("*********************");
		System.out.print("请选择：");
		int choice = input.nextInt();
		switch(choice) {
		case 1:
			showLogin();
			break;
		case 2:
			showRegister();
			break;
		case 3:
			System.out.println("再见，感谢您对本系统的支持！");
			System.exit(0);
		default:
			System.out.println("输入有误！");
			System.exit(0);
		}
	}
	
	private void showLogin() {
		User user = new User();
		CommandTransfer transfer = new CommandTransfer();
		int count = 0;
		while(true) {
			count++;
			if(count > 3) {
				System.out.println("您已连续三次登陆失败，程序退出");
				System.exit(0);
			}
			System.out.println("请输入用户名：");
			user.setUserName(input.next());
			System.out.println("请输入密码：");
			user.setPasswd(input.next());
			transfer.setCmd("login");
			transfer.setData(user);
			try {
				socket = new Socket("127.0.0.1", 8800);
				sendData(transfer);
				transfer = getData();
				System.out.println(transfer.getResult());
				System.out.println("*******************");
				if (transfer.isFlag()) {
					break;
				} 
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				closeAll();
			}
		}
		showUploadFile();
	}
	
	private void showRegister() {
		User user = new User();
		CommandTransfer transfer = new CommandTransfer();
		while(true) {
			System.out.println("请输入用户名:");
			user.setUserName(input.next());
			System.out.println("请输入密码：");
			user.setPasswd(input.next());
			System.out.println("请再次输入密码：");
			String rePasswd = input.next();
			if(!user.getPasswd().equals(rePasswd)) {
				System.out.println("两次输入的密码不一致");
				System.out.println("****************");
				continue;
			}
			transfer.setCmd("register");
			transfer.setData(user);
			try {
				socket = new Socket("127.0.0.1", 8800);
				sendData(transfer);
				transfer = getData();
				System.out.println(transfer.getResult());
				System.out.println("*******************");
				if(transfer.isFlag()) {
					break;
				}
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				closeAll();
			}
		}
		showLogin();
	}

	public void showUploadFile() {
		System.out.println("请输入上传文件的绝对路径(如：e:/lyf/dog.jpg)");
		String path = input.next();
		File file = null;
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		String fileName = path.substring(path.lastIndexOf("/") + 1);
		try {
			fis = new FileInputStream(new java.io.File(path));
			byte[] fileContent = new byte[fis.available()];
			bis = new BufferedInputStream(fis);
			bis.read(fileContent);
			file = new File(fileName, fileContent);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(bis != null) {
					bis.close();
				}
				if(fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		CommandTransfer transfer = new CommandTransfer();
		transfer.setCmd("uploadFile");
		transfer.setData(file);
		try {
			socket = new Socket("127.0.0.1", 8800);
			sendData(transfer);
			transfer = getData();
			System.out.println(transfer.getResult());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}
		
	}

	private void closeAll() {
		try {
			if(socket != null) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendData(CommandTransfer transfer) {
		try {
			OutputStream os = socket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(transfer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private CommandTransfer getData() {
		CommandTransfer transfer = null;
		try {
			InputStream is = socket.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			transfer = (CommandTransfer) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return transfer;
	}
	
}
