package com.example.diningreviewapp.controller;

import org.springframework.web.bind.annotation.*;

@RequestMapping("/test")
@RestController
public class TestController {
    @GetMapping()
    public String test() {
        return "Testadooo";
    }
}
