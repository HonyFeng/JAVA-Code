package com.gjt.chatService.entity;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author 官江涛
 * @version 1.0
 * @Title: SQL实体
 * @date 2018/6/3/9:48
 */
public class SqlEntity {
    /**
     * 带where条件查询的id
     */
    private String id;
    /**
     * 带where条件查询的内容
     */
    private String idContent;
    /**
     * 中间字段查询的内容，
     * 例如 select * from tbUser
     * 这个sqlContent就是中间的*号
     * 默认为*号
     */
    private String sqlContent = "*";

    private String tableName;

    /**
     * 多条件查询ListId
     * 建议只使用二级查询
     */
    private Map<String,Object> idList;

    /**
     * 处理对象的机制
     * 这是避免对方传入一个对象我们这边无法处理
     */
    private Object object;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return idContent;
    }

    public void setContent(String idContent) {
        this.idContent = idContent;
    }

    public String getSqlContent() {
        return sqlContent;
    }

    public void setSqlContent(String sqlContent) {
        this.sqlContent = sqlContent;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Map<String, Object> getIdList() {
        return idList;
    }

    public void setIdList(Map<String, Object> idList) {
        this.idList = idList;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    /**
     * 对象处理机制,建议暂时不使用
     * @return
     * @throws Exception
     */
    public String getObjectBySQL() throws Exception {
        StringBuffer content = new StringBuffer(" ");

        /**
         * 处理对象中的数值
         */
        Class object = getObject().getClass();
        Field[] fields = object.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            content.append(fields[i].getName()+",");
            System.out.print(fields[i].getName() + ":");
            if(fields[i].getType().getName().equals("java.lang.String")){
                System.out.println(fields[i].get(getObject()));
                // 后面需要思考当 值为空的时候如何处理
            }else if(fields[i].getType().getName().equals("java.lang.Integer")){
                System.out.println(fields[i].getInt(getObject()));
            }
        }
        /**
         * 删除最后以一个","
         */
        content.deleteCharAt(content.length()-1);
        /**
         * 删除开头多余的空格
         */
        content.deleteCharAt(0);
        System.out.println("对象处理完成:" + content.toString());
        return content.toString();

    }
}
