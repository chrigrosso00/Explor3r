package com.explorer.controllers;

import com.explorer.entities.Paese;
import com.explorer.services.PaeseServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class PaeseController {

    @Autowired
    private PaeseServices paeseService;

    @GetMapping("/paesi")
    public List<Paese> getPaesi() {
        return paeseService.findAllPaesi();
    }
    
    @GetMapping("/suggestions")
    public List<String> getSuggestions(@RequestParam String query) {
        // Ottieni tutti i Paesi dal servizio
        List<Paese> paesi = paeseService.findAllPaesi();

        // Filtra i Paesi in base alla query, cercando match parziali (case-insensitive)
        return paesi.stream()
                .map(Paese::getStato)  // Supponendo che Paese abbia un metodo getNome() per ottenere il nome
                .filter(nome -> nome.toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}
