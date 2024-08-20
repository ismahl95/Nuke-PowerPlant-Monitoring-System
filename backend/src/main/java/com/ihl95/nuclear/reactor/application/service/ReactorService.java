package com.ihl95.nuclear.reactor.application.service;

import com.ihl95.nuclear.reactor.domain.Reactor;

public interface ReactorService {
  Reactor getReactorById(Long id);
}
