package com.lyf.entity;

import java.io.Serializable;

/*
 *用户实体类
 */
public class User implements Serializable {

	private static final long serialVersionUID = 3123389002590039929L;
	private int id;
	private String userName;
	private String passwd;
	
	public User() {
		super();
	}
	
	public User(String userName, String passwd) {
		super();
		this.userName = userName;
		this.passwd = passwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

}
