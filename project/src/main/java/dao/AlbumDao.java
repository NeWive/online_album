package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DBUtil;
import po.Album;
import po.Comment;
import po.Photo;

public class AlbumDao {

	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private ResultSet resultSet_temp = null;
	String sql = null;
	Connection connection = null;


	/**
	 *    (  resultset)
	 */
	public void closeParaResources() {
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
	 * c  ر	  Դ(δʹ	resultset)
	 */
	public void closeResources() {
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
	 * 
	 */
	public Album findByAlbumName(String album_name) {

		sql = "select name,userid from album where name = ?";
		Album album = new Album();
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, album_name);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				album.setName(resultSet.getString("name"));
				album.setUserid(resultSet.getInt("userid"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeParaResources();
		return album;
	}


	/**
	 * 
	 */
	public void addAlbum(String userName, Album album_t) {
		try {
			connection = DBUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql = "select id from user where name = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, userName);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				album_t.setUserid(resultSet.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		sql = "insert into album (name,userid) values(?,?)";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, album_t.getName());
			preparedStatement.setInt(2, album_t.getUserid());
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeResources();
	}
	
	public void deleteAlbum(int albumid) {
		try {
			connection = DBUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql = "delete from album where id = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, albumid);
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeResources();
	}

	/**
	 *	   id  ѯ
	 */
	public Album findByAlbumId(String id) throws SQLException {
		connection = DBUtil.getConnection();
		Album album = new Album();
		sql = "select name,userid from album where id = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, Integer.parseInt(id));
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				album.setName(resultSet.getString("1"));
				album.setUserid(resultSet.getInt("2"));
				album.setId(Integer.parseInt(id));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return album;
	}
	/**
	 *	
	 * 
	 */
	public ArrayList<Album> findAllAlbums(int location) {
		ArrayList<Album> albumArrayList = new ArrayList<>();
		try {
			connection = DBUtil.getConnection();
			sql = "select * from album";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()){
				Album album = new Album();
				album.setId(resultSet.getInt(1));
				album.setUserid(resultSet.getInt(2));
				album.setName(resultSet.getString(3));

				/**
				 *	 ѯͼƬ
				 */
				sql = "select albumid,photopath from photo where albumid = ?";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, (album.getId()));
				resultSet_temp = preparedStatement.executeQuery();
				ArrayList<Photo> photoArrayList = new ArrayList<>();
				while(resultSet_temp.next()){
					Photo photo = new Photo(resultSet_temp.getInt(1), resultSet_temp.getString(2));
					photoArrayList.add(photo);
				}
				album.setPhotos(photoArrayList);

				/**
				 *	 ѯ û ID
				 */
				sql = "select name from user where id = ?";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, album.getUserid());
				ResultSet resultSet_temp = preparedStatement.executeQuery();
				if(resultSet_temp.next()){
					album.setUserName(resultSet_temp.getString(1));
				}

				/**
				 *	 ѯ		 
				 */
				sql = "select username,comment from comment where albumid = ?";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, album.getId());
				ResultSet resultSet_comment = preparedStatement.executeQuery();
				ArrayList<Comment> commentArrayList = new ArrayList<>();
				while(resultSet_comment.next()){
					Comment comment = new Comment(album.getId(),resultSet_comment.getString(2),resultSet_comment.getString(1));
					commentArrayList.add(comment);
				}
				album.setComments(commentArrayList);

				albumArrayList.add(album);
			}


		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeParaResources();
		try {
			resultSet_temp.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return albumArrayList;
	}
	/**
	 * ͨ   û	 ѯ	 
	 */
	public ArrayList<Album> findAllAlbumsByUserName(String userName){
		ArrayList<Album> albumArrayList = new ArrayList<>();
		int userId;
		try {
			connection = DBUtil.getConnection();
			/**
			 *	 ѯ û id
			 */
			sql = "select id from user where name = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, userName);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				userId = resultSet.getInt(1);
				System.out.println(userId);
			}else {
				userId = 0;
			}
				sql = "select * from album where userid = ?";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, userId);
				resultSet_temp = preparedStatement.executeQuery();
				while (resultSet_temp.next()) {
					Album album = new Album();
					album.setId(resultSet_temp.getInt(1));
					album.setUserid(resultSet_temp.getInt(2));
					album.setName(resultSet_temp.getString(3));
					album.setUserName(userName);
					System.out.println(album);

					/**
					 *	 ѯͼƬ
					 */
					sql = "select albumid,photopath from photo where albumid = ?";
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setInt(1, (album.getId()));
					ResultSet resultSet_temp_temp = preparedStatement.executeQuery();
					ArrayList<Photo> photoArrayList = new ArrayList<>();
					while (resultSet_temp_temp.next()) {
						Photo photo = new Photo(resultSet_temp_temp.getInt(1), resultSet_temp_temp.getString(2));
						photoArrayList.add(photo);
					}
					album.setPhotos(photoArrayList);

					/**
					 *	 ѯ		 
					 */
					sql = "select username,comment from comment where albumid = ?";
					preparedStatement = connection.prepareStatement(sql);
					preparedStatement.setInt(1, album.getId());
					ResultSet resultSet_comment = preparedStatement.executeQuery();
					ArrayList<Comment> commentArrayList = new ArrayList<>();
					while (resultSet_comment.next()) {
						Comment comment = new Comment(album.getId(), resultSet_comment.getString(2), resultSet_comment.getString(1));
						commentArrayList.add(comment);
					}
					album.setComments(commentArrayList);

					albumArrayList.add(album);
				}



		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeParaResources();
		return albumArrayList;
	}
	/**
	 *		    
	 */
	public void deleteAlbumsByAlbumId(int albumId){
		try {
			connection = DBUtil.getConnection();
			sql = "delete from album where id = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, albumId);
			preparedStatement.execute();
			sql = "delete from photo where albumid = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, albumId);
			preparedStatement.execute();
			sql = "delete from comment where albumid = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, albumId);
			preparedStatement.execute();
			closeResources();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
