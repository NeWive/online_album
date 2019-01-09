package service;

import dao.UserDao;
import po.User;

public class UserService {
    public static void main(String[] args) {
		System.out.println(new UserService().findByUserNameService("name4").getNickName());
	}

    UserDao userDao = new UserDao();
    /**
     * 通过用户名查询
     */
    public User findByUserNameService(String username){
        return userDao.findByUserName(username);
    }
    /**
     * 登陆服务
     */
    public String logInService(User user_temp){
        User user = findByUserNameService(user_temp.getUserName());
        if(user == null || user.getUserName() == null || user.getUserName().equals("")){
            return "no_account";
        }else if(!user.getPassword().equals(user_temp.getPassword())){
            return "wrong_password";
        }else{
            return "logIn";
        }
    }
    /**
     * 注册服务
     */
    public String registorService(User user){
        User user_temp = findByUserNameService(user.getUserName());
        System.out.println(user_temp);

        return "success";
    }
}
