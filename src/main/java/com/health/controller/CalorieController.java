package com.health.controller;

import com.health.entity.Calorie;
import com.health.service.CalorieService;
import com.health.util.ResponseData;
import com.health.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 卡路里
 */
@RestController
@RequestMapping("/calorie")
public class CalorieController {
    @Autowired
    private CalorieService calorieService;

    @RequestMapping(path="/getCalorieAll", method= RequestMethod.GET)
    @CrossOrigin(allowCredentials = "true")
    public Result<?> getCalorieAll(@RequestParam("itemname") String itemname,@RequestParam("flag") String flag) throws Exception {
        List<Calorie> result = calorieService.getCalorieAll(itemname,flag);
        return ResponseData.success(result);
    }

    @RequestMapping(path="/add" , method= RequestMethod.POST)
    @CrossOrigin(allowCredentials = "true")
    public Result<?> addCalorieRecord(@RequestBody Calorie calorie){
        int result = calorieService.addCalorie(calorie);
        return ResponseData.success(result);
    }

    @RequestMapping(path="/del",method = RequestMethod.DELETE)
    @CrossOrigin(allowCredentials = "true")
    public Result<?> delCalorieRecord(@RequestParam(name = "id") int id){
        int result = calorieService.delCalorie(id);
        return ResponseData.success(result);
    }


    @RequestMapping(path="/update", method= RequestMethod.PUT)
    @CrossOrigin(allowCredentials = "true")
    public  Result<?> updateCalorieRecord(@RequestBody Calorie calorie){
        int result = calorieService.updateCalorie(calorie);
        return ResponseData.success(result);
    }

}
