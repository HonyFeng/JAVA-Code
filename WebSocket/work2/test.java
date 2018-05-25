package com.org.gjt.WebSocket.work2;

import java.io.File;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:
 * @Package
 * @Description:
 * @date 2018/5/25/9:31
 */
public class test {
    public static void main(String[] args) {
        String path = "E:\\音乐\\曾经艺也 - 关云长.mp3";
        File file = new File(path);
        System.out.println("文件大小："+file.length()/1024/1024);
    }
}
