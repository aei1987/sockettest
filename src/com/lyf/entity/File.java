package com.lyf.entity;

import java.io.Serializable;

/*
 *文件实体类
 */
public class File implements Serializable {

	private static final long serialVersionUID = 7688406831289658196L;
	private int fileId;
	private String fileName;
	private byte[] fileContent;
	
	public File() {
		super();
	}
	
	public File(String fileName, byte[] fileCountent) {
		super();
		this.fileName = fileName;
		this.fileContent = fileCountent;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getFileContent() {
		return fileContent;
	}

	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

}
