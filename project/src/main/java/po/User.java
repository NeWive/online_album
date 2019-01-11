/**
 * @用户信息类
 *
 **/
package po;

public class User {
	private String userName;
	private String password;
	private String nickName;

	public User(String userName, String password, String nickName) {
		this.userName = userName;
		this.password = password;
		this.nickName = nickName;
	}
	public User(){

	}

	public String getUserName() {
		return userName;
	}
	
	public boolean isEmpty() {
		return userName==null && password == null && nickName == null;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "User{" +
				"userName='" + userName + '\'' +
				", password='" + password + '\'' +
				", nickName='" + nickName + '\'' +
				'}';
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setUserInfo(String userName, String password){
		this.userName = userName;
		this.password = password;
	}

	public void setUserInfo(String userName, String password, String nickName){
		this.userName = userName;
		this.password = password;
		this.nickName = nickName;
	}
	public boolean isNotValid() {
		return userName.length()>10 || 
				password.length()<6 ||
				nickName.length() <5
				;
	}
}
