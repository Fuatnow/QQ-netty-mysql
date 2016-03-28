package com.server;

import static com.data.Information.*;

import java.sql.ResultSet;

import com.data.TcpMessage;
import com.data.User;
import com.mysql.Data;
import com.mysql.Modify_friends_info;
import com.mysql.Modify_login_info;
import com.mysql.Modify_user_info;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
public class tcpServerHandler extends ChannelInboundHandlerAdapter{
	private int boundid;
	private volatile boolean quitIsNormal=false;
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		TcpMessage tcpmsg=(TcpMessage)msg;
		User user=tcpmsg.getUser();
		boundid=user.getAccount();
		Data data;
		switch(tcpmsg.getMode())
		{
		case registe:
			 data=new Data( new Modify_user_info());
			//�ͻ��˸�server�ǳƺ����룬server�����˺š� 
			user.setAccount(data.getOneAccount());
			user.setInfo(success);
			System.out.println("�û�ע��\n"+user);
			data.Insert(user);
			ctx.writeAndFlush(user);//���ظ��ͻ���һ��qq�˺š�
			break;
		case login:
			 data=new Data( new Modify_user_info());
			 /*��֤��¼*/
			 boolean ret=data.Verify_login(user);
			 ctx.writeAndFlush(user);//���������ɹ����
			 /*�ɹ�����µ�½�б�*/
			 if(ret){
			     user.setStatus(1);
			     new Data(new Modify_login_info()).Insert(user);
			     new Data(new Modify_friends_info()).Update(user);
			 }
			 //��ʾ������Ϣ
			 System.out.println("�ͻ���["+user.getAccount()+"] ��¼"+user.getInfo()+"  ip="+user.getIP());
			
			break;
		case addfriend:
			ResultSet result=new Data(new Modify_friends_info())
				.getRrsult("select * from friends_info where user_id="
							+user.getAccount()+" and friend_id="+user.get_Friend_qq());
			if(!result.next())
			{
				new Data(new Modify_friends_info()).Insert(user);
				System.out.println("���"+user.get_Friend_qq()+"��Ϊ"+user.getAccount()+"�ĺ���");
			}
			break;
		case delfriend:
			 result=new Data(new Modify_friends_info())
			.getRrsult("select * from friends_info where user_id="
						+user.getAccount()+" and friend_id="+user.get_Friend_qq());
			if(result.next())
			{
				new Data(new Modify_friends_info()).Delete(user);
				System.out.println("ɾ��"+user.getAccount()+"�ĺ���"+user.get_Friend_qq());
			}
			
			break;
		case showfriends:
			new Data(new Modify_friends_info()).SearchFriend(user);
			/*���ظ��ͻ��˺����б�*/
			ctx.writeAndFlush(user);
			break;
		case quit:
			//��������״̬�ɿͻ���ά��
			new Data(new Modify_login_info()).Delete(user);
			new Data(new Modify_friends_info()).Update(user);
			quitIsNormal=true;
			System.out.println(user.getAccount()+":����");
			break;
		default:
			break;
		}
	}
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		if(!quitIsNormal)
		{
			new Data(new Modify_login_info()).Delete(new User(boundid,0));
			new Data(new Modify_friends_info()).Update(new User(boundid,0));
			System.out.println(boundid+":�쳣����!");
		}
		else
			quitIsNormal=false;
	}
}
