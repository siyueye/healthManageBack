package com.health.controller;

import com.health.entity.Diet;
import com.health.entity.withPages.DietExtend;
import com.health.service.DietService;
import com.health.util.ResponseData;
import com.health.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/diet")
public class DietController {
    @Autowired
    private DietService dietService;

    @RequestMapping(path="/add" , method= RequestMethod.POST)
    @CrossOrigin(allowCredentials = "true")
    public Result<?> addDietRecord(@RequestBody Diet diet){
        int result = dietService.addDiet(diet);
        return ResponseData.success(result);
    }

    @RequestMapping(path="/del",method = RequestMethod.DELETE)
    @CrossOrigin(allowCredentials = "true")
    public Result<?> delDietRecord(@RequestParam(name = "id") int id){
        int result = dietService.delDiet(id);
        return ResponseData.success(result);
    }


    @RequestMapping(path="/update", method= RequestMethod.PUT)
    @CrossOrigin(allowCredentials = "true")
    public  Result<?> updateDietRecord(@RequestBody Diet diet){
        int result = dietService.updateDiet(diet);
        return ResponseData.success(result);
    }

    @RequestMapping(path="/getInfo", method= RequestMethod.GET)
    @CrossOrigin(allowCredentials = "true")
    public Result<?> getDietInfo(@RequestParam(name = "id") String id) throws Exception {
        Diet diet = dietService.getDietInfo(id);
        return ResponseData.success(diet);
    }

    @RequestMapping(path ="/getList", method= RequestMethod.POST)
    @CrossOrigin(allowCredentials = "true")
    public Result<?> getDietList(@RequestBody DietExtend diet) throws Exception {
        HashMap result = dietService.getDietList(diet);
        return ResponseData.success(result);
    }
}
