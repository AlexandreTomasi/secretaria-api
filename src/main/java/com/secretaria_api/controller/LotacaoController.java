package com.secretaria_api.controller;

import com.secretaria_api.dto.LotacaoDTO;
import com.secretaria_api.model.Lotacao;
import com.secretaria_api.model.ServidorEfetivo;
import com.secretaria_api.service.LotacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Lotação", description = "Operações com lotações")
@RestController
@RequestMapping("/lotacao")
public class LotacaoController {

    @Autowired
    LotacaoService lotacaoService;

    @Operation(
            summary = "Listar lotacoes paginado",
            description = "Retorna uma lista paginada de usuários",
            parameters = {
                    @Parameter(name = "page", description = "Número da página (0-based)", example = "0", in = ParameterIn.QUERY),
                    @Parameter(name = "size", description = "Quantidade de itens por página", example = "10", in = ParameterIn.QUERY),
                    @Parameter(name = "sort", description = "Critério de ordenação (ex: id,desc)", example = "id,desc", in = ParameterIn.QUERY)
            }
    )
    @GetMapping
    public ResponseEntity<Page<Lotacao>> listarUsuarios(
            @Parameter(hidden = true) @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        Page<Lotacao> usuarios = lotacaoService.listarTodos(pageable);
        return ResponseEntity.ok(usuarios);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLotacao(@Parameter(
            description = "ID da lotação",
            required = true,
            example = "2"  // Exemplo numérico que aparecerá no Swagger
    )@PathVariable Long id) {
        lotacaoService.deleteLotacao(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Obter lotação por ID", description = "Retorna uma lotação específica com base no ID fornecido")
    @GetMapping("/{id}")
    public ResponseEntity<Lotacao> getLotacaoById(@Parameter(
            description = "ID da lotação",
            required = true,
            example = "1"  // Exemplo numérico que aparecerá no Swagger
    ) @PathVariable Long id) {
        Lotacao lotacao = lotacaoService.buscaPorId(id);
        return lotacao != null ? ResponseEntity.ok(lotacao) : ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Cadastrar uma lotacao",
            description = "Endpoint para cadastrar uma lotacao",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do Servidor Efetivo a ser cadastrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ServidorEfetivo.class),
                            examples = {@ExampleObject(name = "Exemplo 1 - Lotacao X", value = """
                        { 
                        "pessoaId": 2,
                         "unidadeId": 1,
                         "dataLotacao": "01/01/2025",
                         "dataRemocao": "29/03/2025",
                         "portaria": "numero 02"
                         }""")})
            ))
    @PostMapping
    public ResponseEntity<Lotacao> saveLotacao(@RequestBody LotacaoDTO lotacao) {
        Lotacao lotacaodb = lotacaoService.salvarLotacao(lotacao);
        return ResponseEntity.ok(lotacaodb);
    }

    @Operation(
            summary = "Alterar uma lotacao",
            description = "Endpoint para alterar uma lotacao",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do Servidor Efetivo a ser cadastrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ServidorEfetivo.class),
                            examples = {@ExampleObject(name = "Exemplo 1 - Lotacao X", value = """
                        { 
                         "id": 1,
                         "pessoaId": 2,
                         "unidadeId": 2,
                         "dataLotacao": "01/01/2025",
                         "dataRemocao": "29/03/2026",
                         "portaria": "numero 0700"
                         }""")})
            ))
    @PutMapping
    public ResponseEntity<Lotacao> alterarLotacao(@RequestBody LotacaoDTO lotacao) {
        Lotacao lotacaodb = lotacaoService.atualizarLotacao(lotacao);
        return ResponseEntity.ok(lotacaodb);
    }
}
