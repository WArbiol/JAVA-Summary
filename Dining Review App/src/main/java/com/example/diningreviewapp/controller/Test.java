package com.example.diningreviewapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("")
@RestController
public class Test {
    @GetMapping()
    public String test() {
        return "Testadooo";
    }
}
