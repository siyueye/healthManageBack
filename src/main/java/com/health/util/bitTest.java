package com.health.util;
import org.postgresql.util.PGobject;

import java.sql.*;

public class bitTest {
        public static void main(String[] args) {
            // 数据库连接信息
            String url = "jdbc:postgresql://localhost:5432/postgres";
            //加 tinyInt1isBit=false 参数也是报错
//            String url = "jdbc:postgresql://localhost:5432/postgres?tinyInt1isBit=false";
            String username = "postgres";
            String password = "postgres";

            // SQL语句
            String sql = "insert into h_user (username, password, gender,height) values(?,?,?,?)";
            try {
                // 加载并注册JDBC驱动
                Class.forName("org.postgresql.Driver");
                // 建立数据库连接
                try (Connection conn = DriverManager.getConnection(url, username, password);
                     // 创建PreparedStatement
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, "ztt");
                    pstmt.setString(2, "123456");
                    //用PGobject解决
                    PGobject pGobject = new PGobject();
                    pGobject.setType("BIT");
                    pGobject.setValue("1");
                    pstmt.setObject(3,pGobject);
                    pstmt.setFloat(4, 170);
                    // 执行SQL语句
                    pstmt.executeUpdate();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

}
