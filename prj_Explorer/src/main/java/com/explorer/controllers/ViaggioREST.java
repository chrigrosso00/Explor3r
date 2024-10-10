package com.explorer.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.explorer.entities.Viaggio;
import com.explorer.services.ViaggioServices;

@RestController
@RequestMapping("api")
public class ViaggioREST {
    
    @Autowired
    private ViaggioServices viaggioServices;
    
    @GetMapping("viaggi")
    public List<Viaggio> getOrdine() {
        return viaggioServices.findAll();
    }

    @GetMapping("viaggi/{id}")
    public Viaggio findViaggioById(@PathVariable int id) {
        return viaggioServices.findById(id);
    }
}
