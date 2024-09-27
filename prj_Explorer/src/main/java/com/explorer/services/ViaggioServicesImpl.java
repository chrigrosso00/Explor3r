package com.explor3r.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.explor3r.entities.Viaggio;
import com.explor3r.repos.ViaggioDAO;

@Service
public class ViaggioServicesImpl implements ViaggioServices {
    @Autowired
    private ViaggioDAO dao;

    @Override
    public List<Viaggio> findAll() {
        return dao.findAll();
    }

    @Override
    public Viaggio findById(int id) {
        return dao.findById(id).orElse(null);
    }

    @Override
    public Viaggio save(Viaggio viaggio) {
        return dao.save(viaggio);
    }
}