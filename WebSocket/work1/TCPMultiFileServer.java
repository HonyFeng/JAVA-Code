package com.org.gjt.WebSocket.work1;

import com.org.gjt.WebSocket.utils.FileUtils;
import com.org.gjt.WebSocket.work1.entity.FileSetting;

import java.io.*;
import java.net.Socket;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:  多文件上传服务端
 * @date 2018/5/24/19:59
 */
public class TCPMultiFileServer implements Runnable{

    /**
     * 启动文件工具类
     */
    private static FileUtils fileUtils = new FileUtils();
    /**
     * 接受传输过来的对象
     */
    private static FileSetting fileSetting = new FileSetting();
    /**
     * 与本线程相关的socket
     */
    Socket socket = null;

    public TCPMultiFileServer(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        Object obj = null;
        OutputStream os = null;
        PrintWriter pw = null;
        InputStream is = null;
        try {
            is = socket.getInputStream();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
