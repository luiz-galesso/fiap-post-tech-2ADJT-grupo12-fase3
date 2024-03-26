package com.fase3.techchallenge.fiap.infrastructure.cliente.repository;

import com.fase3.techchallenge.fiap.entity.cliente.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {

}
