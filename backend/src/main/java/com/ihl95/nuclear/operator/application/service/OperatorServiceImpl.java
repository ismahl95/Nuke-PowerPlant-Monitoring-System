package com.ihl95.nuclear.operator.application.service;

import org.springframework.stereotype.Service;

import com.ihl95.nuclear.operator.domain.Operator;

@Service
public class OperatorServiceImpl implements OperatorService {

    @Override
    public Operator getOperatorById(Long id) {
        // Implementación mínima para pruebas; ajusta según tus necesidades
        return new Operator(); // Devuelve un operador vacío o simulado
    }
}
