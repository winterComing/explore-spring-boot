package com.dengh.explore.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author dengH
 * @title: TController
 * @description: TODO
 * @date 2019/9/12 15:35
 *
 */
@Controller
public class TController {

    @RequestMapping("/t123")
    @ResponseBody
    public void t(){
        System.out.println("t123");
    }
}
