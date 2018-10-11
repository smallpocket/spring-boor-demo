package com.demo.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author : Hyper
 * @Time : 2018/10/10 23:59
 */
@RestController
@RequestMapping("test")
public class BaseController {

    /**
     * 写一个请求
     *
     * @return
     */
    @RequestMapping(value = "get", method = RequestMethod.GET)
    public Map getMap() {
        Map map = new HashMap(1);
        map.put("test", "test");
        return map;
    }
}
