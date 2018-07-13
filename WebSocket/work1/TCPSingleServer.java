package com.org.gjt.WebSocket.work1;

import com.org.gjt.WebSocket.utils.FileUtils;
import com.org.gjt.WebSocket.work1.entity.FileSetting;
import com.org.gjt.WebSocket.work1.entity.TCPsetting;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author 官江涛
 * @version 1.0
 * @Title: 一对一上次：服务端
 * @date 2018/5/24/16:55
 */
public class TCPSingleServer {

    /**
     * 启动文件工具类
     */
    private static FileUtils fileUtils = new FileUtils();
    /**
     * 接受传输过来的对象
     */
    private static FileSetting fileSetting = new FileSetting();

    private static void StartService() throws Exception {
        Object obj = null;
        ServerSocket serverSocket = new ServerSocket(TCPsetting.port);
        System.out.println("服务器启动，等待客户端的连接。。。");
        Socket socket = serverSocket.accept();
        OutputStream os = null;
        PrintWriter pw = null;
        InputStream is = socket.getInputStream();
        ObjectInputStream ois=new ObjectInputStream(is);
        obj = ois.readObject();
        if (obj != null){
            System.out.println("接收到来自["+socket.getInetAddress().getHostAddress()+"]的文件");
            fileSetting = (FileSetting) obj;
            /**
             * 开始存储工作
             */
            File dir = new File("E:\\"+socket.getInetAddress().getHostAddress());
            if(!dir.exists()){
                dir.mkdir();
            }
            File file = new File(dir, socket.getInetAddress().getHostAddress()+fileSetting.getFileName());
            /**
             * 获取base64流转为指定文件
             */
            String stream = fileSetting.getStream();
            fileUtils.decryptByBase64(stream,file.getPath());
            System.out.println("保存成功");
        }
        os=socket.getOutputStream();
        pw=new PrintWriter(os);
        pw.println("传输完成！");
        pw.flush();
        socket.shutdownOutput();
        if(pw!=null){
            pw.close();
        }
        if(os!=null){
            os.close();
        }
        if(socket!=null){
            socket.close();
        }
    }

    public static void main(String[] args) {
        try {
            StartService();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
