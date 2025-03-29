package com.secretaria_api.controller;

import com.secretaria_api.model.Cidade;
import com.secretaria_api.service.CidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Cidades", description = "Listar cidades")
@RestController
@RequestMapping("/cidade")
public class CidadeController {

    @Autowired
    CidadeService cidadeService;

    @Operation(
            summary = "Listar cidades paginada",
            description = "Retorna uma lista paginada de cidades",
            parameters = {
                    @Parameter(name = "page", description = "Número da página (0-based)", example = "0", in = ParameterIn.QUERY),
                    @Parameter(name = "size", description = "Quantidade de itens por página", example = "10", in = ParameterIn.QUERY),
                    @Parameter(name = "sort", description = "Critério de ordenação (ex: nome,asc)", example = "nome,asc", in = ParameterIn.QUERY)
            }
    )
    @GetMapping
    public ResponseEntity<Page<Cidade>> listarCidades(
            @Parameter(hidden = true) @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        Page<Cidade> cidades = cidadeService.listarTodos(pageable);
        return ResponseEntity.ok(cidades);
    }
}
