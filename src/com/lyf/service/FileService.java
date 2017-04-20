package com.lyf.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.lyf.entity.File;
import com.lyf.util.DBUtil;

public class FileService {
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	/*
	 * 将文件保存到数据库中
	 */
	public void save(File file) {
		String sql = "insert into tb_file(filename, filecontent) values (? , ?)";
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, file.getFileName());
			pstmt.setBytes(2, file.getFileContent());;
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(rs, pstmt, conn);
		}
	}

}
