package com.data;

import java.io.Serializable;


/**
 * Э���ʽ:���ͷ�qq ���շ�qq ���շ����ǳ� ��Ϣ
 * @author xyy
 *
 */
public class UdpMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static UdpMessage msg;
	private User user;
	private String data;
	private User friend;
	public UdpMessage(User user,User friend,String data)
	{
		this.data=data;
		this.user=user;
		this.friend=friend;
	}
	public String getData()
	{
		return data;
	}
	public User getUser()
	{
		return user;
	}
	public String toString()
	{
		return user.getAccount()+" "+friend.getAccount()+" "+friend.getNickname()+" "+data;
	}
}
