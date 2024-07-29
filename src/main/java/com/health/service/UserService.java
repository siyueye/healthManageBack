package com.health.service;

import com.health.entity.Sport;
import com.health.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    public List getUserList() throws Exception ;
    public User getUser(String username,String password) throws Exception;
    public User getUserInfo(String token);

    public int addUser(User user) throws SQLException;

    public int updateUserInfo(User user);
    public int updatepwd( int id ,String old_pwd,String new_pwd) throws Exception;

}
