package com.explorer.controllers;

import com.explorer.entities.Paese;
import com.explorer.services.PaeseServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PaeseController {

    @Autowired
    private PaeseServices paeseService;

    @GetMapping("/paesi")
    public List<Paese> getPaesi() {
        return paeseService.findAllPaesi();
    }
}
