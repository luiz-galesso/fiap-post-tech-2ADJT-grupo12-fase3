package com.fase3.techchallenge.fiap.infrastructure.restaurante.repository;

import com.fase3.techchallenge.fiap.entity.restaurante.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long>
{
    List<Restaurante> findByNomeContainingIgnoreCase(String nome);

    List<Restaurante> findByTipoCulinariaContainingIgnoreCase(String tipoCulinaria);
    @Query("SELECT r FROM Restaurante r WHERE r.endereco.logradouro = :logradouro AND r.endereco.cidade = :cidade AND r.endereco.cep = :cep AND r.endereco.numero = :numero")
    List<Restaurante> findByEndereco(@Param("logradouro") String logradouro,
                                     @Param("cidade") String cidade,
                                     @Param("cep") Long cep,
                                     @Param("numero") String numero);
}
