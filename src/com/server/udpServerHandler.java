package com.server;

import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.sql.ResultSet;

import com.mysql.Data;
import com.mysql.Modify_login_info;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
public class udpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
		ByteBuf buf = (ByteBuf) packet.copy().content();
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String msg = new String(req);
        String[] ls=msg.split(" ",4);
        
        int friendid=Integer.parseInt(ls[1]);
        String data=ls[3];
        
        //�ж����������Ƿ����ߣ�������ת��������ת����
        ResultSet result=new Data(new Modify_login_info()).Select(friendid, "*");
        /*����,ת����Ϣ*/
        if(result.next()){
        	//Ѱ�ҶԷ�ip;
        	String ip=result.getString(2);
            // �������ͷ����׽���
            DatagramSocket sendSocket = new DatagramSocket();  
            InetSocketAddress ipaddr = new InetSocketAddress(ip, 8882);
            // �����������͵����ݱ���  
            java.net.DatagramPacket sendPacket 
            		= new java.net.DatagramPacket(req, req.length, ipaddr);
            // ͨ���׽��ַ������ݣ�  
            sendSocket.send(sendPacket);
            sendSocket.close();
            
            System.out.println(ls[0]+"==>"+ls[1]+":"+data);
        }
        else{
        	 System.out.println(ls[0]+"==>"+ls[1]+":"+ls[1]+" �����޷�ת����Ϣ");
        }
		
		
	}

}
