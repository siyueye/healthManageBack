package com.health.controller;

import com.health.util.JwtUtil;
import com.health.util.ResponseData;
import com.health.util.Result;
import com.health.entity.User;
import com.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private UserService userService;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    @CrossOrigin(allowCredentials = "true")
    public Result<?> login(@RequestBody User user) throws Exception {

        User loginUser = userService.getUser(user.getUsername(), user.getPassword());
        if (loginUser != null) {
            Map<String, String> payload = new HashMap<>();
            payload.put("userid", loginUser.getId().toString());
            payload.put("username", loginUser.getUsername().toString());
            payload.put("height", String.valueOf(loginUser.getHeight()));
            payload.put("gender",String.valueOf(loginUser.getGender()));
            payload.put("nickname",String.valueOf(loginUser.getNickname()));
            payload.put("email",String.valueOf(loginUser.getEmail()));
            String token = JwtUtil.generateToken(payload);
            HashMap tokenMap = new HashMap();
            tokenMap.put("token",token);
            return ResponseData.success(tokenMap);
        }
        return ResponseData.authError();

    }

    @RequestMapping(path = "/reg", method = RequestMethod.POST)
    @CrossOrigin(allowCredentials = "true")
    public Result<?>  register(@RequestBody User user) throws SQLException {
        int result = userService.addUser(user);
        return ResponseData.success(result);

    }
}
