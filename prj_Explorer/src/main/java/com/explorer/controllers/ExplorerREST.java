package com.explorer.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.explorer.entities.Utente;
import com.explorer.entities.Viaggio;
import com.explorer.services.UtenteServices;
import com.explorer.services.ViaggioServices;

@RestController
@RequestMapping("api")
public class ExplorerREST {
    @Autowired
    private UtenteServices utenteServices;
    
    @Autowired
    private ViaggioServices viaggioServices;
    

    @GetMapping("utenti")
    public List<Utente> getUtente() {
        return utenteServices.findAll();
    }

    @GetMapping("utenti/{id}")
    public Utente findUtentiById(@PathVariable int id) {
        return utenteServices.findById(id);
    }
    
    @GetMapping("viaggi")
    public List<Viaggio> getOrdine() {
        return viaggioServices.findAll();
    }

    @GetMapping("viaggi/{id}")
    public Utente findViaggioById(@PathVariable int id) {
        return utenteServices.findById(id);
    }
}
