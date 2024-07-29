package com.health.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Configuration
public class JdbcUtils {

//    private static String driverClass;
    @Value("${spring.datasource.url}")
    private  String url;

    @Value("${spring.datasource.username}")
    private  String username;

    @Value("${spring.datasource.password}")
    private  String password;
    public static Connection conn = null;
    public static PreparedStatement ps = null;
    /**
     * 数据库列与实体对象映射处理
     * @param sql
     * @param clazz
     * @param parameters
     * @param <T>
     * @return
     * @throws Exception
     */
    public  <T> List<T> executeQuery(String sql, Class clazz, Object...parameters) throws Exception {
        // 获取连接
        conn = getConnection();
        // 执行SQL获取结果集
        ResultSet rs = executeQuery(sql, parameters);
        // 获取表的元数据
        ResultSetMetaData metaData = ps.getMetaData();

        List<T> resultList = new ArrayList<>();

        // 结果集映射成对象实体
        while(rs.next()) {
            // 获取表字段总数
            int columnCount = metaData.getColumnCount();
            // 通过反射实例化对象
            Object obj = clazz.newInstance();
            // 遍历表字段
            for (int i = 1; i <= columnCount; i++) {
                // 列名
                String columnName = metaData.getColumnName(i);
                // 列值
                Object columnValue = rs.getObject(i);
                // 数据库字段与数据库值的映射
                setFieldValueForColumn(obj, columnName, columnValue);
            }
            resultList.add((T) obj);
        }
        // 关闭资源
        JdbcUtils.close(JdbcUtils.conn, JdbcUtils.ps, rs);

        return resultList;
    }

    /**
     * 根据字段名称设置对象属性
     * @param o
     * @param columnName
     * @param columnValue
     */
    private static void setFieldValueForColumn(Object o, String columnName, Object columnValue) {
        Class<?> aClass = o.getClass();
        try {
            Field field = aClass.getDeclaredField(columnName);
            field.setAccessible(true);
            field.set(o, columnValue);
            field.setAccessible(false);
        } catch (Exception e) {
            // 驼峰模式的处理
            if (columnName.contains("_")) {
                // \w -> 元字符，相当于 [a-zA-Z0-9]
                Pattern pattern = Pattern.compile("_(\\w)");
                columnName = columnName.toLowerCase();
                Matcher matcher = pattern.matcher(columnName);
                StringBuffer sb = new StringBuffer();

                if (matcher.find()) {
                    // matcher.group(1) 指的是第一个括号里的东西 \w
                    // 替换掉_，并将_的相邻下一个字母转为大写
                    matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
                }
                matcher.appendTail(sb);
                // 再次调用复制操作
                setFieldValueForColumn(o,sb.toString(),columnValue);
            }
        }
    }

    /**
     * 执行查询
     * @param sql
     * @param parameters
     * @return
     */
    public  ResultSet executeQuery(String sql, Object...parameters) throws SQLException {
        ResultSet rs = null;
        // 获取数据库库连接
        conn = getConnection();
        // 创建Statement对象
        ps = conn.prepareStatement(sql);
        // 设置sql中的参数
        if (Objects.nonNull(parameters) && parameters.length > 0) {
            for (int i = 0; i < parameters.length; i++) {
                ps.setObject(i+1, parameters[i]);
            }
        }

        // 返回结果集
        return ps.executeQuery();
    }
    /*
     * UPdate方法, 支持增,删,改
     */
    public  int update(String sql, Object[] params){

        int res = 0;

        try {
            // 获取数据库库连接
            conn = getConnection();
            // 创建Statement对象
            ps = conn.prepareStatement(sql);

            if (params != null){
                for (int i=0; i<params.length; i++){
                    ps.setObject(i+1, params[i]);
                }
            }
            res = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, ps,null);
        }
        return res;
    }


    /**
     * 数据库连接
     * @return
     * @throws SQLException
     */
    public  Connection getConnection() throws SQLException {
        if (conn == null) {
             try {
                  conn = DriverManager.getConnection(url, username, password);
             } catch (SQLException e) {
                  e.printStackTrace();
             }
           }
        return conn;
    }

    public static  void close(Connection conn, Statement sta, ResultSet rs){
        try{
            // 关闭ResultSet
            if(Objects.nonNull(rs)){
                rs.close();
            }
            // 关闭Statement
            if (Objects.nonNull(sta)) {
                sta.close();
            }
            // 关闭Connection
            if (Objects.nonNull(conn)) {
                sta.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

}
