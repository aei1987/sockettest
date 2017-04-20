package com.lyf.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.lyf.entity.User;
import com.lyf.util.DBUtil;

public class UserService {
	
	private Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	/*
	 * 用户登陆
	 */
	public boolean login(User user) {
		String sql = "select * from tb_user where username=? and passwd=?";
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getPasswd());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(rs, pstmt, conn);
		}
		return false;
	}
	
	/*
	 * 用户注册
	 */
	public void register(User user) {
		String sql = "insert into tb_user(username, passwd) values (?, ?)";
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getPasswd());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeAll(rs, pstmt, conn);
		}
	}

}
