package com.ihl95.nuclear.service;

import org.springframework.stereotype.Service;

import com.ihl95.nuclear.model.Reactor;

@Service
public class ReactorServiceImpl implements ReactorService {
    @Override
    public Reactor getReactorById(Long id) {
        // Implementación mínima para pruebas
        return new Reactor();
    }
}
