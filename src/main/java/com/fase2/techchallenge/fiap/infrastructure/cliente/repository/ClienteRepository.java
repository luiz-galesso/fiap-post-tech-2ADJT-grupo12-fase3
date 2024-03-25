package com.fase2.techchallenge.fiap.infrastructure.cliente.repository;

import com.fase2.techchallenge.fiap.entity.cliente.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {

}
