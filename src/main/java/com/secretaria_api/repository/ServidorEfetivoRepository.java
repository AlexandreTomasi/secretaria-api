package com.secretaria_api.repository;

import com.secretaria_api.model.ServidorEfetivo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServidorEfetivoRepository extends CrudRepository<ServidorEfetivo, Long> {
    Page<ServidorEfetivo> findAll(Pageable pageable);


    @Query(nativeQuery = true, value = """
        SELECT 
            p.pes_nome AS nome_servidor,
            se.se_matricula AS matricula,
            u.unid_nome AS unidade_lotacao,
            u.unid_sigla AS sigla_unidade,
            e.end_tipo_logradouro AS tipo_logradouro,
            e.end_logradouro AS logradouro,
            e.end_numero AS numero,
            e.end_bairro AS bairro,
            c.cid_nome AS cidade,
            c.cid_uf AS uf
        FROM 
            pessoa p
        JOIN 
            servidor_efetivo se ON p.pes_id = se.pes_id
        JOIN 
            lotacao l ON p.pes_id = l.pes_id
        JOIN 
            unidade u ON l.unid_id = u.unid_id
        JOIN 
            unidade_endereco ue ON u.unid_id = ue.unid_id
        JOIN 
            endereco e ON ue.end_id = e.end_id
        JOIN 
            cidade c ON e.cid_id = c.cid_id
        WHERE 
            p.pes_nome ILIKE '%' || :parteNome || '%'
            AND (l.lot_data_remocao IS NULL OR l.lot_data_remocao > CURRENT_DATE)
        ORDER BY 
            p.pes_nome
    """)
    List<Object[]> findEnderecoFuncionalByParteNome(@Param("parteNome") String parteNome);


    @Query("SELECT se FROM ServidorEfetivo se JOIN FETCH se.pessoa WHERE se.pesId = :pesId")
    Optional<ServidorEfetivo> findByIdWithPessoa(@Param("pesId") Long pesId);

    @Query(nativeQuery = true, value = """
        SELECT 
            p.pes_nome AS nome,
            EXTRACT(YEAR FROM AGE(p.pes_data_nascimento)) AS idade,
            u.unid_nome AS unidade_lotacao,
            p.pes_id AS id_pessoa
        FROM 
            pessoa p
        JOIN 
            servidor_efetivo se ON p.pes_id = se.pes_id
        JOIN 
            lotacao l ON p.pes_id = l.pes_id
        JOIN 
            unidade u ON l.unid_id = u.unid_id
        WHERE 
            u.unid_id = :unidId
            AND (l.lot_data_remocao IS NULL OR l.lot_data_remocao > CURRENT_DATE)
        ORDER BY 
            p.pes_nome
    """)
    List<Object[]> findServidoresByUnidadeId(@Param("unidId") Long unidId);
}