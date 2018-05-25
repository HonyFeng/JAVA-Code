package com.org.gjt.WebSocket.work2.version2;

/**
 * @author 官江涛
 * @version 1.0
 * @Title: 服务端启动
 * @date 2018/5/25/11:16
 */
public class ServiceStart {
    public static void main(String[] args) {
          Thread thread = new Thread(new UDPSService("127.0.0.1",8888,8889,"E:\\ddd1.wmv"));
          thread.start();
    }
}
