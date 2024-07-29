package com.health.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.health.util.JwtUtil;
import com.health.entity.User;
import com.health.service.UserService;
import com.health.util.JdbcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import org.postgresql.util.PGobject;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    JdbcUtils jdbcUtils;
    public List getUserList() throws Exception {
        // 查询
        List<User> userList = jdbcUtils.executeQuery("select * from h_user ", User.class, null);
        // 结果集的处理
        System.out.println("userList = " + userList);
        return userList;
    }

    @Override
    public User getUser(String username,String password) throws Exception {
        // 查询
        List<User> userList = jdbcUtils.executeQuery("select * from h_user where username=? and password =?", User.class, username,password);
        // 结果集的处理
        System.out.println("userList = " + userList);
        if(userList.size()>0){
         return  userList.get(0);
        }
        return null;
    }

    @Override
    public User getUserInfo(String token) {
        DecodedJWT decodedJWT = JwtUtil.decodeToken(token);
        String userid = decodedJWT.getClaim("userid").asString();
        String username = decodedJWT.getClaim("username").asString();
        String height = decodedJWT.getClaim("height").asString();
        String gender = decodedJWT.getClaim("gender").asString();
        String nickname = decodedJWT.getClaim("nickname").asString();
        String email = decodedJWT.getClaim("email").asString();
        String exp = decodedJWT.getExpiresAt().toString();
        User user = new User();
        user.setId(Integer.parseInt(userid,10));
        user.setUsername(username);
        user.setHeight(Float.parseFloat(height));
        user.setGender(Integer.parseInt(gender));
        user.setNickname(nickname);
        user.setEmail(email);
        return user;
    }

    @Override
    public int addUser(User user) throws SQLException {
        //注释的方法是字符串拼接
//        Connection conn = jdbcUtils.getConnection();
//        Statement stat =conn.createStatement();
//
//        StringBuilder sqlBud = new StringBuilder("insert into h_user (username, password, gender,height) values('");
//        sqlBud.append(user.getUsername()).append("','").append(user.getPassword()).append("',").append(user.getGender()).append("::bit,").append(user.getHeight()).append(")");
//        int total_rs = stat.executeUpdate(sqlBud.toString());
//        jdbcUtils.close(conn,stat,null);
//bit使用PGobject设置
        String sql = "insert into h_user (username, password, gender,height) values(?,?,?,?)";
        PGobject pGobject= new PGobject();
        pGobject.setType("BIT");
        pGobject.setValue(String.valueOf(user.getGender()));
        Object[] params =  {user.getUsername(),user.getPassword(),pGobject,user.getHeight()};
        int total_rs = jdbcUtils.update(sql,params);
        return total_rs;
    }

    @Override
    public int updateUserInfo(User user) {
        String sql = "update h_user set nickname = ?, email = ? WHERE id= ?";

        Object[] params = {user.getNickname(),user.getEmail(),user.getId()};

        int total_rs = jdbcUtils.update(sql,params);
        return total_rs;
    }

    @Override
    public int updatepwd(int id ,String old_pwd,String new_pwd) throws Exception {
        List<User> userList = jdbcUtils.executeQuery("select * from h_user where id=? and password =?", User.class, id,old_pwd);
        int total_rs =0;
        // 结果集的处理
        if(userList.size()>0){
            String querySql = "";
            jdbcUtils.getConnection();
            String sql = "update h_user set password = ? WHERE id= ?";
            Object[] params = {new_pwd,id};
            total_rs = jdbcUtils.update(sql,params);
        }

        return total_rs;
    }


}