package com.ihl95.nuclear.reactor.application.service;

import org.springframework.stereotype.Service;

import com.ihl95.nuclear.reactor.domain.Reactor;

@Service
public class ReactorServiceImpl implements ReactorService {
    @Override
    public Reactor getReactorById(Long id) {
        // Implementación mínima para pruebas
        return new Reactor();
    }
}
