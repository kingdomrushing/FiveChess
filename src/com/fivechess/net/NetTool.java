package com.fivechess.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * 采用UDP进行网络通信
 * @author admin
 *
 */
public class NetTool {
	/**
	 * 发送信息
	 * @param ip 要发送对方的IP
	 * @param msg 发送的信息
	 */
	public static void sendUDPBroadCast(String ip,String msg)
	{
		try {
			DatagramSocket ds = new DatagramSocket();
			InetAddress ia=InetAddress.getByName(ip);
			DatagramPacket dp=new DatagramPacket(msg.getBytes(),0,msg.getBytes().length,ia,10086);
			ds.send(dp);
			ds.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
