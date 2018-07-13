package com.org.gjt.WebSocket.work2.version1;

import java.io.FileOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;

/**
 * @author 官江涛
 * @version 1.0
 * @Title: 多文件上传UDP版本
 * @date 2018/5/25/9:10
 */
public class UDPMultFileService implements Runnable{
    DatagramSocket socket = null;
    DatagramPacket packet = null;


    public UDPMultFileService(DatagramSocket socket,DatagramPacket packet) {
        this.socket = socket;
        this.packet = packet;
    }
    @Override
    public void run() {
        DatagramPacket getPacket = null;
        DatagramPacket sendPacket = null;
        FileOutputStream fos = null;
        System.out.println("服务器启动");
        System.out.println("等待接受消息");
        try {

            fos = new FileOutputStream("E:\\ddd1.wmv");
            byte[] buf = new byte[1024];
            int i=0;
            while(true){
                getPacket = new DatagramPacket(buf,0,buf.length);
                socket.receive(getPacket);
                i = getPacket.getLength();
                fos.write(buf,0,i);
                if(i<1024){
                    break;
                }
            }
            System.out.println("发送完成");
            SocketAddress sendAddress = getPacket.getSocketAddress();
            String feedback = "接收成功！";
            byte[] backBuf = feedback.getBytes();
            sendPacket = new DatagramPacket(backBuf,  backBuf.length, sendAddress);
            socket.send(sendPacket);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                socket.close();
                fos.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }


}
