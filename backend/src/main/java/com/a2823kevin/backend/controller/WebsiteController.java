package com.a2823kevin.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebsiteController {
    @GetMapping("/")
    public String site() {
        return "forward:/index.html";
    }
}
