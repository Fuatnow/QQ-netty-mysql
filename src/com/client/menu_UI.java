package com.client;

import java.io.IOException;
import java.util.Scanner;

import com.data.Information;
import com.data.Mode;
import com.data.TcpMessage;
import com.data.UdpMessage;
import com.data.User;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

public class menu_UI {
	private Scanner in=new Scanner(System.in);
	//public static menu_UI menu=new menu_UI();
	public int Int_input(Scanner input)
	{
		int qq=0;
		try {
			qq=input.nextInt();
		} catch (Exception e) {
			System.out.println("��������");
			String string=input.next();
			return Integer.MIN_VALUE;
		}
		return qq;
	}
	public void MainUI(Channel ch) throws InterruptedException
	{
		
		 while(true)
         {
     		System.out.println("��ѡ�������\n1.ע��  \n2.��½");
     		int num;
     		while((num=Int_input(in))==Integer.MIN_VALUE)
     		{
     			System.out.println("��ѡ�������\n1.ע��  \n2.��½");
     		}
     		switch (num) {
     		case 1://ע��
     			System.out.println("----------------------------");
     			System.out.println("�������ǳ�:");
     			String nickname=in.next();
     			System.out.println("����������:");
     			String password=in.next();
     			User user=new User(nickname,password);
     			ch.writeAndFlush(new TcpMessage(Mode.registe, user));
     			synchronized (menu_UI.class) {
     				menu_UI.class.wait();
				}
     			System.out.println("���䵽���˺ţ�"+tcpClientHandler.getUser().getAccount());
     			break;
     		case 2:
     			System.out.println("�����˺�:");
				int qq=Int_input(in);
				if(qq==Integer.MIN_VALUE) break;
				
     			System.out.println("��������:");
     			password=in.next();
     			user=new User(qq,password);
     			ch.writeAndFlush(new TcpMessage(Mode.login, user));
     			synchronized (menu_UI.class) {
     				menu_UI.class.wait();
				}
     			if(tcpClientHandler.getUser().getInfo()==Information.success)
     			{
     				After_login(ch);
     			}
     			break;
     		default:
     			break;
     		}
         }
	}
	public void After_login(Channel ch) throws InterruptedException
	{
		User user;
		System.out.println("����qq����....................................");
		while(true)
		{
			System.out.println("��ѡ�����²���:\n1.��Ӻ���\n2.ɾ������\n3.��ʾ�����б�\n4.�˳���¼");
			switch (in.nextInt()) {
			case 1:
				user=new tcpClientHandler().getUser();
				System.out.print("���������ѵ�qq��:");
				int qq=Int_input(in);
				if(qq==Integer.MIN_VALUE) break;
				user.addFriend(qq);
				ch.writeAndFlush(new TcpMessage(Mode.addfriend, user));
				break;
			case 2:
				user=new tcpClientHandler().getUser();
				System.out.print("���������ѵ�qq��:");
				qq=Int_input(in);
				if(qq==Integer.MIN_VALUE) break;
				user.delFriend(qq);
				ch.writeAndFlush(new TcpMessage(Mode.delfriend, user));
				break;
			case 3:
				user=tcpClientHandler.getUser();
				ch.writeAndFlush(new TcpMessage(Mode.showfriends, user));
				synchronized (menu_UI.class) {
					menu_UI.class.wait();
				}
				/*��ʾ��ѡ�����*/
				if(user.getInfo()==Information.success)
				{
					user=tcpClientHandler.getUser();
					System.out.println("��ѡ����Ҫ����Ķ���:--------------(-1������һ��)");
					int num=user.FriendList.size();
					for(int i=0;i<num;i++)
					{
						System.out.println("<"+i+">"+user.FriendList.get(i).UserShow());
					}
				}
				if(user.FriendList.size()==0)
				{
					System.out.println("û�к���");
					break;
				}
				int index = 0;
				if((index=in.nextInt())>=user.FriendList.size())
				{
					System.out.println("ѡ�����");
					break;
				}
				if(index==-1)
					break;
				
				while(true){
					System.out.println("��������������:-----------<END is ������һ��>");
					String data=in.next();
					if(data.equals("END"))break;
					//����udp��
					UdpMessage.msg=new UdpMessage(user,user.FriendList.get(index), data);
					synchronized (udpClientSendThread.class) {
						udpClientSendThread.class.notifyAll();//����udp������������
					}
					
				}
				break;
			case 4:
				user=tcpClientHandler.getUser();
				user.setStatus(0);
     			ChannelFuture f=ch.writeAndFlush(new TcpMessage(Mode.quit, user));
				f.addListener(ChannelFutureListener.CLOSE);
				System.exit(0);
				break;

			default:
				break;
			}
		}
	}
}
