package com.data;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class User implements Serializable {
	private int qq=0;
	private String password=null;
	private String IP=null; 
	private String nickname;
	private int status=0;
	private Information info=Information.null_op;
	private int friend_qq;
	
	/*���ڽ��շ��������ĺ����б�*/
	public ArrayList<User> FriendList=new ArrayList<>();
	public User(){}
	public User(int qq){
		this.qq=qq;
	}
	//ע��ʱ��
	public User(String nickname,String password)
	{
		this.nickname=nickname;
		this.password=password;
	}
	public User(int qq,int status)
	{
		this.qq=qq;
		this.status=status;
	}
	//��½ʱ�õĹ��캯��
	public User(int qq,String password)
	{
		this.qq=qq;
		this.password=password;
		//this.nickname=nickname;
		this.IP=new getIP().getLocalIP();
		status=0;
	}
	public void addFriend(int friend_qq)
	{
		this.friend_qq=friend_qq;
	}
	public void delFriend(int friend_qq)
	{
		this.friend_qq=friend_qq;
	}
	public String toString()
	{
		return "qq="+qq+" ����="+password+" ip="+IP+" �ǳ�="+nickname+" status="+status;
	}
	public String UserShow()
	{
		String sta;
		sta=status==1?"����":"����";
		return "["+nickname+"]("+sta+")";
	}
	public int getAccount(){return qq;} 
	public String getpassword(){return password;} 
	public String getIP(){return IP;} 
	public String getNickname(){return nickname;}
	public void setNickname(String name){this.nickname=name;}
	public void setStatus(int s){this.status=s;}
	public void setAccount(int qq){this.qq=qq;}
	public int getStatus(){return this.status;}
	public int get_Friend_qq(){return friend_qq;}
	public void setInfo(Information info){this.info=info;}
	public Information getInfo(){return info;}
}
