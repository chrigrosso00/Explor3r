package com.explorer.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.explorer.entities.Viaggio;
import com.explorer.repos.ViaggioDAO;

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

	@Override
	public List<Viaggio> getViaggiByContinente(String continente) {
		return dao.findByContinente(continente);
	}

	@Override
	public List<Viaggio> getViaggioByStato(String stato) {
		return dao.findByStato(stato);
	}

	@Override
	public List<Viaggio> getViaggiBydifficolta(String difficolta) {
		return dao.findByDifficolta(difficolta);
	}
}