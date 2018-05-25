package com.org.gjt.WebSocket.work2.version2;

/**
 * @author 官江涛
 * @version 1.0
 * @Title: 客户端启动
 * @date 2018/5/25/11:18
 */
public class ClientStart {
    public static void main(String[] args) {
        Thread thread = new Thread(new UDPSClient(8888,8889,"E:\\BaiduYunDownload\\ddd1.wmv","127.0.0.1"));
        thread.start();
    }
}
