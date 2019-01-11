package dao;

import db.DBUtil;
import po.User;
import java.sql.*;

/**
 *c �����û���Ĳ�ѯ�Ȳ���
 */

public class UserDao {
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	User user = new User();
	String sql = null;
	Connection connection = null;
	DBUtil dbUtil = new DBUtil();
	/**
	 * c  �رո�����Դ(ʹ��resultset)
	 */
	public void closeParaResources(){
		try {
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 *c �ر���Դ(δʹ��resultset)
	 */
	public void closeResources(){
		try {
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean userNameTaken(String name) {
		 try {
			 connection = DBUtil.getConnection();
		 } catch (SQLException e) {
			 e.printStackTrace();
		 }
		 sql = "select name from user where name = ?";
		 try {
			 preparedStatement = connection.prepareStatement(sql);
			 preparedStatement.setString(1,name);
			 resultSet = preparedStatement.executeQuery();
			 if(resultSet.next()){
				 closeParaResources();
				 return true;
			 }
		 } catch (SQLException e) {
			 e.printStackTrace();
		 }
		 closeParaResources();
		 return false;
	}
	/**
	 *c ͨ���û�����ѯ
	 * */
	public User findByUserName(String username){
		try {
			connection = DBUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql = "select name,psw,nickname from user where name = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				user.setUserName(resultSet.getString(1));
				user.setPassword(resultSet.getString(2));
				user.setNickName(resultSet.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeParaResources();
		return user;
	}
	/**
	 * cע�����
	 */
	public void addUser(User user){
		try {
			connection = DBUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql = "insert into user (name,psw,nickname) values(?,?,?)";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, user.getUserName());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getNickName());
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeResources();
	}
	/**
	 *
	 */
}
