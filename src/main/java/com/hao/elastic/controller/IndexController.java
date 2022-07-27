package com.hao.elastic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @description:
 * @author: Hao
 * @create: 2022-02-11 09:56
 **/
@Controller
public class IndexController
{
    @GetMapping({"/","/index"})
    public String index(){
        return "index";
    }
}