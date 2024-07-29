package com.health.controller;

import com.health.util.ResponseData;
import com.health.util.Result;
import com.health.entity.User;
import com.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(path="/getAll", method= RequestMethod.GET)
    public List<User> getAll() throws Exception {
        return userService.getUserList();
    }

    @RequestMapping(path="/userinfo", method= RequestMethod.GET)
    @CrossOrigin(allowCredentials = "true")
    public Result<?> getUserInfo(@RequestHeader("Authorization") String token) throws Exception {

        User user = userService.getUserInfo(token);
        return ResponseData.success(user);
    }

    @RequestMapping(path="/update", method= RequestMethod.PUT)
    @CrossOrigin(allowCredentials = "true")
    public Result<?> updateUserInfo(@RequestBody User user) throws Exception {

        int result = userService.updateUserInfo(user);
        return ResponseData.success(result);
    }

    @RequestMapping(path="/updatepwd", method= RequestMethod.GET)
    @CrossOrigin(allowCredentials = "true")
    public Result<?> updatepwd(@RequestParam(name = "id") int id,@RequestParam(name = "old_pwd") String old_pwd, @RequestParam(name = "new_pwd") String new_pwd) throws Exception {

        int result = userService.updatepwd(id,old_pwd,new_pwd);
        if (result==1){
            return ResponseData.success(result);
        }
        return ResponseData.error("更新失败");
    }

}
