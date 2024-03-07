package com.fase2.techchallenge.fiap.infrastructure.restaurante.repository;

import com.fase2.techchallenge.fiap.entity.endereco.model.Endereco;
import com.fase2.techchallenge.fiap.entity.restaurante.model.Restaurante;
import org.springframework.data.domain.Example;
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
//    List<Restaurante> findByEndereco_LogradouroOrEndereco_NumeroOrEndereco_CepOrEndereco_CidadeOrEndereco_Estado(
//      String logradouro, String numero, Long cep, String cidade, String estado
//    );

//    @Query("SELECT r FROM Restaurante r WHERE (r.endereco.logradouro, r.endereco.cidade, r.endereco.cep, r.endereco.numero) = (:logradouro, :cidade, :cep, :numero)")

    @Query("SELECT r FROM Restaurante r WHERE r.endereco.logradouro = :logradouro AND r.endereco.cidade = :cidade AND r.endereco.cep = :cep AND r.endereco.numero = :numero")
    List<Restaurante> findByEndereco(@Param("logradouro") String logradouro,
                                     @Param("cidade") String cidade,
                                     @Param("cep") Long cep,
                                     @Param("numero") String numero);
}
