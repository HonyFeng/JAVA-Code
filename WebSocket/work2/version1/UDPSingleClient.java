package com.org.gjt.Socket.work2.OneToOne;

import com.org.gjt.Socket.work2.entity.UDPUtilsSetting;
import com.org.gjt.Socket.work2.utils.FileSendTools;

import java.io.File;
import java.io.FileInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.DecimalFormat;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:  UDP一对一文件上传：客户端
 * @date 2018/5/24/20:22
 */
public class UDPSingleClient {

    private static void SendFile(String path) throws Exception {

        FileInputStream fis = null;
        DatagramSocket sendSocket= null;
        DatagramPacket sendPacket =null;
        DatagramPacket getPacket = null;
        InetAddress ip = null;
        /**
         * 设置double类型的小数点格式
         */
        DecimalFormat df = new DecimalFormat( "0.00");
        try {
            sendSocket = new DatagramSocket();
            ip = InetAddress.getLocalHost();
            // 读出文件
            fis = new FileInputStream(path);
            byte[] buf =new byte[UDPUtilsSetting.BUFFER_SIZE];
            int i;
            /**
             * 获取文件长度
             */
            File file = new File(path);
            System.out.println("文件大小为："+file.length());
            long len = file.length();
            double totalLen = 0;
            int no = 1;
            /**
             * 发送文件名称
             */
            FileSendTools fileSendTools = new FileSendTools();
            byte[] fileName =  fileSendTools.getNameByBytes(path);
            DatagramPacket sendPacketFileName = new DatagramPacket(fileName, fileName.length, ip,  UDPUtilsSetting.sendFileNamePort);
            sendSocket.send(sendPacketFileName);
            byte[] res = new byte[1024];
            getPacket = new DatagramPacket(res, res.length);
            sendSocket.receive(getPacket);
            String titleRes = new String(res, 0, getPacket.getLength());
            System.out.println("回馈信息:"+titleRes);
            // 开始发送文件
            if(titleRes.equals("success")){
                /**
                 * 程序开始时间
                 */
                long startTime = System.currentTimeMillis();
                while((i = fis.read(buf, 0, UDPUtilsSetting.BUFFER_SIZE) )==UDPUtilsSetting.BUFFER_SIZE){
                    totalLen+=i;
                    System.out.println("发送进度："+df.format((double)((totalLen/len)*100))+"%");
                    sendPacket = new DatagramPacket(buf, buf.length, ip, UDPUtilsSetting.sendFilePort);
                    sendSocket.send(sendPacket);
                    byte[] getBuf = new byte[1024];
                    getPacket = new DatagramPacket(getBuf, getBuf.length);
                    sendSocket.receive(getPacket);
                    String backMes = new String(getBuf, 0, getPacket.getLength());
                    System.out.println("回馈信息:"+backMes);
                    if(Integer.parseInt(backMes) == no){
                        no++;
                        System.out.println("接受成功");
                        continue;
                    }else {
                        System.out.println("resend ...");
                        sendSocket.send(sendPacket);
                    }
                }
                totalLen+=i;
                System.out.println("发送进度："+df.format((double)((totalLen/len)*100))+"%");
                sendPacket = new DatagramPacket(buf, 0,i, ip,  UDPUtilsSetting.sendFilePort);
                sendSocket.send(sendPacket);
                Thread.sleep(1);

                System.out.println("客户端传输结束.");
                /**
                 * 程序结束时间
                 */
                long endTime = System.currentTimeMillis();
                /**
                 * 总花费
                 */
                System.out.println("程序运行时间：" + df.format((double)((endTime - startTime)/1000)) + "s");
            }

            /**
             *
             */
            byte[] getBuf = new byte[1024];
            getPacket = new DatagramPacket(getBuf, getBuf.length);
            sendSocket.receive(getPacket);
            String backMes = new String(getBuf, 0, getPacket.getLength());
            System.out.println("回馈信息:"+backMes);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if(fis !=null){
                    fis.close();
                }
                if(sendSocket != null){
                    sendSocket.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String path = "E:\\hack\\17.5.25msf会话玩法录屏.mp4";

        SendFile(path);
    }

}
