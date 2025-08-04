package com.kh.musicshop.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AccessDeniedController {

    @RequestMapping("accessDenied")
    public String accessDeniedPage() {
        return "common/accessDenied";
    }
}