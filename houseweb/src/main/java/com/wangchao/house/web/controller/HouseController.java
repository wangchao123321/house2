package com.wangchao.house.web.controller;

import com.wangchao.house.biz.service.HouseService;
import com.wangchao.house.common.model.House;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HouseController {

    @Autowired
    private HouseService houseService;

    @RequestMapping("/house/list")
    public String houseList(Integer pageSize, Integer pageNum, House query, ModelMap modelMap){
        return null;
    }
}
