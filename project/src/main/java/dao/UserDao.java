package dao;

import db.DBUtil;
import po.User;
import java.sql.*;

/***
 * 处理用户表的查询等操作
 */

public class UserDao {
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    User user = new User();
    String sql = null;
    Connection connection = null;
    /**
     * 关闭各种资源(使用resultset)
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
     * 关闭资源(未使用resultset)
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
    /**
     * 通过用户名查询
     * */
    public User findByUserName(String username){
        connection = DBUtil.getConnection();
        sql = "select username,password,nickname from user where username = ?";
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
     * 注册服务
     */
    public void addUser(User user){
        connection = DBUtil.getConnection();
        sql = "insert into user (username,password,nickname) values(?,?,?)";
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