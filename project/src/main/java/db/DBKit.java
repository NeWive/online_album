package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import utility.ConfKit;
import utility.Global;

public final class DBKit {
	// JDBC
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
 

    // user and password for database
    static String DB_URL=null;
    static String USER = null;
    static String PW = null;

    static Connection conn = null;
    static Statement stmt = null;
    static boolean inited=false;
    
    static int m_row_count=0;
    static final boolean is_log=false;
    
    static protected void init() {
    	/**
    	 * load database configuration
    	 */
    	DB_URL=ConfKit.getProperty("db_url");
    	USER=ConfKit.getProperty("db_user");
    	PW=ConfKit.getProperty("db_pw");
        try{
            /**
             *  connect to JDBC Driver
             */
            Class.forName("com.mysql.cj.jdbc.Driver");
        
            if(is_log)
            System.out.println("Connecting to db "+DB_URL+USER+PW);
            inited=true;
            conn = DriverManager.getConnection(DB_URL,USER,PW);
            conn.setAutoCommit(true);
            
        }catch (SQLException | ClassNotFoundException e) {
        	e.printStackTrace();
        	inited=false;
		}
        if(conn!=null) {
        	if(is_log)
        	System.out.println("db opened ");
        }
	}
    
    /*
    static protected int getRowCount(String table_name) {
    	if(conn==null) init();
    	
    	if(m_row_count>0) return m_row_count;
    	int r=0;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery("select max(id) from "+table_name+";");
			rset.next();
			r=rset.getInt(1);
	    	m_row_count=r;
	    	rset.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
    	
    	return r;
    }
    */
    
    
    /**
     * user 
     */
    static public boolean hasUserName(String name) {
    	if(conn==null) init();
    	try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select id from user where name = '"+name+"';");
			
			if(rs.next()) 
				{
				rs.close();
				return true;
				}
	    	rs.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} 
    	return false;
    }
    
    static public void listUser() {
    	if(conn==null) init();
    	try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from user;");
			
			while(rs.next()) {
				int i=rs.getInt("id");
				String name=rs.getString("name");
				String psw=rs.getString("psw");
				System.out.println(i+name+psw);
			}
	    	rs.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} 
    }
    
    static public boolean insertUser(String name,String nick,String pw) {
    	if(conn==null) init();
    	if(conn==null) {
    		System.out.println("fail open.");
    		return false;
    	}
    	if(hasUserName(name))
    	{
    		System.err.println("this name has been taken by another user.");
    		return false;
    	}
    	String sql=" insert into user (name,psw,nickname) values ("
    	+"'"+name+"',"
    	
    	+"'"+pw+"',"
    	+"'"+nick+"'"
    	+");";
    	if(is_log)
    	System.err.println("[insert]\t"+sql);
    	try {
    		Statement stmt=conn.createStatement();
			++m_row_count;
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			m_row_count=0;
			e.printStackTrace();
			return false;
		}
    	if(is_log)
    	System.err.println("insert over.");
    	return true;
    }
    
    static public String getUserPassword(String name) {
    	if(conn==null) init();
    	try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select psw from user where name = '"+name+"';");
			
			if(rs.next()) 
			{
				String res=rs.getString("psw");
				rs.close();
				return res;
			}
	    	rs.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} 
    	
    	return null;
    }

    static public boolean deleteUser(String name) {
    	if(conn==null) init();
    	try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("delete from user where name = '"+name+"';");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		} 
    	return true;
    }
    
    
    /**
     * album 
     */
    
    public static void main(String[] args) {
    	Global.conf_path="G:\\MyProject\\Java_Eclipse_work\\online_album\\project\\src\\main\\webapp\\system.conf";
		insertUser("name4","nick" ,"testpw2");
		listUser();
		System.out.println("psw of name4"+getUserPassword("name4"));
	}
    
}
