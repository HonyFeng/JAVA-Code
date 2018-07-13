package com.org.gjt.Socket.work2.OneToOne;

import com.org.gjt.Socket.work2.entity.UDPUtilsSetting;

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
       DatagramSocket serverFileName = null;
       InetAddress ip = null;
       DatagramPacket getPacket = null;
       DatagramPacket sendPacket = null;
       FileOutputStream fos = null;
       System.out.println("服务器启动");
       System.out.println("等待接受消息");
       try {
           ip = InetAddress.getLocalHost();
           /**
            * 接收文件名称
            * start
            */
           serverFileName = new DatagramSocket(UDPUtilsSetting.sendFileNamePort,  ip);
           byte[] fileName = new byte[UDPUtilsSetting.BUFFER_SIZE];
           // 通过套接字接收文件名数据
           DatagramPacket file = new DatagramPacket(fileName, fileName.length);
           //收到发送端发送来的文件名数据报
           serverFileName.receive(file);
           byte[] tips = "success".getBytes();
           // 返回消息
           SocketAddress fileAddr = file.getSocketAddress();
           sendPacket = new DatagramPacket(tips,  tips.length, fileAddr);
           serverFileName.send(sendPacket);
           //具体名字
           String name = new String(fileName, 0, file.getLength());
           //*得到文件名
           System.out.println(name+"\n");
           fos = new FileOutputStream("E:\\"+name);
           /**
            * 接收文件名称
            * end
            */
           /**
            * 接收数据
            * start
            */
           serverSocket = new DatagramSocket(UDPUtilsSetting.sendFilePort,  ip);
           byte[] buf = new byte[UDPUtilsSetting.BUFFER_SIZE];
           int i=0;
           int no = 1;
           while(true){
               getPacket = new DatagramPacket(buf,0,buf.length);
               serverSocket.receive(getPacket);
               i = getPacket.getLength();
               fos.write(buf,0,i);
               /**
                * 回传确认消息
                */
               System.out.println("第"+no+"个包发送完成！");
               SocketAddress sendAddress = getPacket.getSocketAddress();
               byte[] backBuf = String.valueOf(no).getBytes();
               sendPacket = new DatagramPacket(backBuf,  backBuf.length, sendAddress);
               serverSocket.send(sendPacket);
               if(i<1024){
                   break;
               }
               no++;
           }
           /**
            * 接收数据
            * start
            */
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
