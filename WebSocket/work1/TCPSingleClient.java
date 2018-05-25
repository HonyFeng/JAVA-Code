package com.org.gjt.WebSocket.work1;

import com.org.gjt.WebSocket.utils.FileUtils;
import com.org.gjt.WebSocket.work1.entity.FileSetting;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author 官江涛
 * @version 1.0
 * @Title: tcp一对一文件上传：客户端
 * @date 2018/5/24/9:50
 */
public class TCPSingleClient {

    /**
     * 建立客户端socket连接
     */
    private static TcpSocketClient conn  = new TcpSocketClient();

    /**
     * 设置文件信息
     */
    private static FileSetting file = new FileSetting();

    /**
     * 启动文件工具类
     */
    private static FileUtils fileUtils = new FileUtils();

    /**
     * 发送文件
     * @param path
     * @throws Exception
     */
    private static void SendFile(String path) throws Exception {
        Socket connect = conn.ConnectTcpClient();
        OutputStream os = connect.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(file);
        oos.writeObject(null);
        oos.flush();
        connect.shutdownOutput();
        String infoString=null;
        InputStream is = connect.getInputStream();
        BufferedReader br=new BufferedReader(new InputStreamReader(is));
        String info=null;
        while((info=br.readLine())!=null){
            System.out.println("服务器端的信息：" + info);
        }
        Scanner sc = new Scanner(System.in);
        sc.nextInt();
        connect.shutdownInput();
        oos.close();
        os.close();
        is.close();
        br.close();
        conn.Close(connect);
    }

    /**
     * 初始化文件并组装
     * @param path
     */
    private static void initFiles(String path) {
        File selectFile = new File(path);
        String name = selectFile.getName();
        String fileOriginPath = selectFile.getPath();
        String type = name.substring(name.lastIndexOf(".") + 1);
        String content = fileUtils.encryptToBase64(path);
        long size = selectFile.length();
        System.out.println("***************文件基本信息***************");
        System.out.println("name:"+name);
        System.out.println("fileOriginPath:"+fileOriginPath);
        System.out.println("type:"+type);
        System.out.println("content:"+content);
        System.out.println("size:"+size);
        System.out.println("***************文件基本信息***************");
        file.setFileName(name);
        file.setSize(size);
        file.setStream(content);
        file.setFileType(type);
        file.setOriginPath(fileOriginPath);
    }
    public static void main(String[] args) {
        String path = "E:\\webstromProject\\eduProject\\classWork\\test.jpg";
        initFiles(path);
        try {
            SendFile(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
