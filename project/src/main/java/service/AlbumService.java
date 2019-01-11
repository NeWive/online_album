package service;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.AlbumDao;
import po.Album;

public class AlbumService {
    /**
     * 根据相册id查询
     */
    private AlbumDao albumDao = new AlbumDao();
    public Album[] findTenAlbum(){
        return null;
    }
    public ArrayList<Album> findAllAlbums(int location){
        return albumDao.findAllAlbums(location);
    }
    public ArrayList<Album> findAllAlbumsByUserName(String userName){
        return albumDao.findAllAlbumsByUserName(userName);
    }
    public void deleteAlbumByAlbumId(int id){
        albumDao.deleteAlbumsByAlbumId(id);
    }
    public void addAlbumService(String userName, Album album){
        albumDao.addAlbum(userName, album);
    }
}
