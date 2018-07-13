package com.org.gjt.WebSocket.work1.entity;

import java.io.Serializable;

/**
 * @author 官江涛
 * @version 1.0
 * @Title: 文件实体类
 * @date 2018/5/24/10:45
 */
public class FileSetting implements Serializable{
    private static final long serialVersionUID = 613659699548582156L;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件类型
     */
    private String fileType;
    /**
     * 文件大小
     */
    private long size;
    /**
     * 文件内容
     */
    private String stream;
    /**
     * 文件源地址
     */
    private String originPath;

    public String getOriginPath() {
        return originPath;
    }

    public void setOriginPath(String originPath) {
        this.originPath = originPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }
}
