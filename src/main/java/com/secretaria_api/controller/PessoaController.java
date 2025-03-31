package com.secretaria_api.controller;

import com.secretaria_api.dto.PessoaDTO;
import com.secretaria_api.model.ServidorEfetivo;
import com.secretaria_api.model.Pessoa;
import com.secretaria_api.service.PessoaService;
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

@Tag(name = "Pessoa", description = "Operações com pessoas")
@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    @Autowired
    PessoaService pessoaService;

    @Operation(
            summary = "Listar pessoas paginado",
            description = "Retorna uma lista paginada de pessoas",
            parameters = {
                    @Parameter(name = "page", description = "Número da página (0-based)", example = "0", in = ParameterIn.QUERY),
                    @Parameter(name = "size", description = "Quantidade de itens por página", example = "10", in = ParameterIn.QUERY),
                    @Parameter(name = "sort", description = "Critério de ordenação (ex: id,desc)", example = "id,desc", in = ParameterIn.QUERY)
            }
    )
    @GetMapping
    public ResponseEntity<Page<Pessoa>> listarPessoas(
            @Parameter(hidden = true) @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        Page<Pessoa> pessoas = pessoaService.listarTodos(pageable);
        return ResponseEntity.ok(pessoas);
    }

    @Operation(
            summary = "Deleta uma pessoa, somente se não for servidor efetivo ou temporario e nao tiver lotação",
            description = "Deleta uma pessoa, somente se não for servidor efetivo ou temporario e nao tiver lotação")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePessoa(@Parameter(
            description = "ID ",
            required = true,
            example = "8"
    )@PathVariable Long id) {
        pessoaService.deletePessoa(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> getPessoaById(@Parameter(
            description = "ID ",
            required = true,
            example = "2"
    )@PathVariable Long id) {
        Pessoa unidade = pessoaService.buscaPorId(id);
        return unidade != null ? ResponseEntity.ok(unidade) : ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Cadastrar uma pessoa",
            description = "Endpoint para cadastrar uma pessoa",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados da pessoa a ser cadastrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ServidorEfetivo.class),
                            examples = {@ExampleObject(name = "Exemplo 1 - Pessoa X", value = """
                        { 
                        "nome": "Roberto",
                         "dataNascimento": "01/01/1995",
                         "sexo": "Masculino",
                         "mae": "Maria de souza",
                         "pai": "Roberto Pai",
                         "enderecos":[{
                               "tipoLogradouro": "Rua",
                               "logradouro": "av rubens",
                               "numero": 789,
                               "bairro": "CPA2",
                               "cidadeId": 2
                         }]
                         }""")})
            ))
    @PostMapping
    public ResponseEntity<Pessoa> savePessoa(@RequestBody PessoaDTO unidade) {
        Pessoa unidadedb = pessoaService.salvarPessoa(unidade);
        return ResponseEntity.ok(unidadedb);
    }

    @Operation(
            summary = "Alterar uma pessoa",
            description = "Endpoint para alterar uma pessoa",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados da pessoa a ser alterada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ServidorEfetivo.class),
                            examples = {@ExampleObject(name = "Exemplo 1 - Pessoa X", value = """
                        { 
                        "id": 8,
                        "nome": "Roberto",
                         "dataNascimento": "01/01/1995",
                         "sexo": "Masculino",
                         "mae": "Maria de souza",
                         "pai": "Roberto Pai",
                         "enderecos":[{
                               "tipoLogradouro": "Rua",
                               "logradouro": "av rubens",
                               "numero": 789,
                               "bairro": "CPA2",
                               "cidadeId": 2
                         }]
                         }""")})
            ))
    @PutMapping
    public ResponseEntity<Pessoa> alterarPessoa(@RequestBody PessoaDTO unidade) {
        Pessoa unidadedb = pessoaService.alterarPessoa(unidade);
        return ResponseEntity.ok(unidadedb);
    }
}
