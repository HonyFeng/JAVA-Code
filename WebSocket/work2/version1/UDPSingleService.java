package com.org.gjt.WebSocket.work2.version1;

import com.org.gjt.WebSocket.work2.entity.UDPUtilsSetting;

import java.io.FileOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;

/**
 * @author 官江涛
 * @version 1.0
 * @Title: UDP一对一文件上传：服务端
 * @date 2018/5/24/22:03
 */
public class UDPSingleService {
    /**
     * 开启UDP服务
     */
   private static void UDPServer(){
       DatagramSocket serverSocket = null;
       InetAddress ip = null;
       DatagramPacket getPacket = null;
       DatagramPacket sendPacket = null;
       FileOutputStream fos = null;
       System.out.println("服务器启动");
       System.out.println("等待接受消息");
       try {
           ip = InetAddress.getLocalHost();
           serverSocket = new DatagramSocket(UDPUtilsSetting.port,  ip);
           fos = new FileOutputStream("E:\\ddd1.mp4");
           byte[] buf = new byte[4096];
           int i=0;
           while(true){
               getPacket = new DatagramPacket(buf,0,buf.length);
               serverSocket.receive(getPacket);
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
           serverSocket.send(sendPacket);
       } catch (Exception e) {
           e.printStackTrace();
       }finally{
           try {
               serverSocket.close();
               fos.close();
           } catch (Exception e2) {
               e2.printStackTrace();
           }
       }
   }

    public static void main(String[] args) {
        UDPServer();
    }
}
