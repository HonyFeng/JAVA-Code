package com.org.gjt.WebSocket.work1;

import com.org.gjt.WebSocket.utils.FileUtils;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:
 * @Package
 * @Description:
 * @date 2018/5/24/9:42
 */
public class test {

    /**
     * 文件压缩测试
     * @param args
     */
    public static void main(String[] args) {
        String fileName = "E:\\webstromProject\\eduProject\\classWork\\PersonTeacher.java";
        FileUtils file = new FileUtils();
        String str = file.encryptToBase64(fileName);
        String conPath = "E:\\webstromProject\\eduProject\\classWork\\PersonTeacher1.java";
        System.out.println("保存成功");
        file.decryptByBase64(str,conPath);
        System.out.println(str);
    }
}
