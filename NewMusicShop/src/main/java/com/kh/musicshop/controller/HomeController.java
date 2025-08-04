package com.kh.musicshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String main() {
        return "main"; // templates/main.html 렌더링됨
    }

}