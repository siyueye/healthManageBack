package com.health.service.impl;

import com.health.entity.Calorie;
import com.health.entity.User;
import com.health.entity.Calorie;
import com.health.service.CalorieService;
import com.health.util.JdbcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CalorieServiceImpl implements CalorieService {
    @Autowired
    JdbcUtils jdbcUtils;
    @Override
    public List<Calorie> getCalorieAll(String itemname,String flag) throws Exception {
        List<Calorie> calorieList = new ArrayList<>();
        if(flag.equals("+")){
            if(StringUtils.isEmpty(itemname)){
                calorieList = jdbcUtils.executeQuery("select * from h_calorie where heat>0 ", Calorie.class, null);
            }else{
                Object[] params = {itemname};
                calorieList = jdbcUtils.executeQuery("select * from h_calorie where heat>0 and itemname=?", Calorie.class, params);
            }
        }else{
            if(StringUtils.isEmpty(itemname)){
                calorieList = jdbcUtils.executeQuery("select * from h_calorie where heat<0 ", Calorie.class, null);
            }else{
                Object[] params = {itemname};
                calorieList = jdbcUtils.executeQuery("select * from h_calorie where heat<0 and itemname=?", Calorie.class, params);
            }
        }

         // 结果集的处理
        return calorieList;
    }

    @Override
    public int addCalorie(Calorie calorie) {
        String sql = "insert into h_calorie (itemname, heat,unit) values(?,?,?)";
        Object[] params = {calorie.getItemname(),calorie.getHeat(),calorie.getUnit()};
        int total_rs = jdbcUtils.update(sql ,params);
        return total_rs;
    }

    @Override
    public int updateCalorie(Calorie calorie) {
        String sql = "update h_calorie set itemname = ?, heat = ?, unit=? WHERE id= ?";

        Object[] params = {calorie.getItemname(),calorie.getHeat(),calorie.getUnit(),calorie.getId()};
        int total_rs = jdbcUtils.update(sql,params);
        return total_rs;
    }

    @Override
    public int delCalorie(int id) {
        String sql = "DELETE FROM h_calorie WHERE id= ?";
        Object[] params = {id};
        int total_rs = jdbcUtils.update(sql,params);
        return total_rs;
    }
}
