package com.org.gjt.WebSocket.work2.version1;

import com.org.gjt.WebSocket.work2.entity.UDPUtilsSetting;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:
 * @date 2018/5/25/11:32
 */
public class StartUDPMultFile {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(UDPUtilsSetting.port);
        DatagramPacket packet = null;
        byte[] data = null;
        int count = 0;
        System.out.println("***服务器端启动，等待发送数据***");
        while(true){
            data = new byte[1024];
            packet = new DatagramPacket(data, data.length);
            socket.receive(packet);
            Thread thread = new Thread(new UDPMultFileService(socket, packet));
            thread.start();
            count++;
            System.out.println("服务器端被连接过的次数："+count);
            InetAddress address = packet.getAddress();
            System.out.println("当前客户端的IP为："+address.getHostAddress());

        }
    }
}
