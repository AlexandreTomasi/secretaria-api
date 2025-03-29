package com.secretaria_api.controller;

import com.secretaria_api.dto.ServidorTemporarioDTO;
import com.secretaria_api.model.ServidorEfetivo;
import com.secretaria_api.model.ServidorTemporario;
import com.secretaria_api.service.ServidorTemporarioService;
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

@Tag(name = "Servidor Temporario", description = "Operações com Servidor Temporario")
@RestController
@RequestMapping("/servidorTemporario")
public class ServidorTemporarioController {

    @Autowired
    ServidorTemporarioService servidorTemporarioService;

    @Operation(
            summary = "Listar servidores temporario paginado",
            description = "Retorna uma lista paginada de servidores temporarios",
            parameters = {
                    @Parameter(name = "page", description = "Número da página (0-based)", example = "0", in = ParameterIn.QUERY),
                    @Parameter(name = "size", description = "Quantidade de itens por página", example = "10", in = ParameterIn.QUERY),
                    @Parameter(name = "sort", description = "Critério de ordenação (ex: dataAdmissao,desc)", example = "dataAdmissao,desc", in = ParameterIn.QUERY)
            }
    )
    @GetMapping
    public ResponseEntity<Page<ServidorTemporario>> listarUsuarios(
            @Parameter(hidden = true) @PageableDefault(size = 10, sort = "dataAdmissao") Pageable pageable) {
        Page<ServidorTemporario> servidor = servidorTemporarioService.listarTodos(pageable);
        return ResponseEntity.ok(servidor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServidorTemporario(@PathVariable Long id) {
        servidorTemporarioService.deleteServidorTemporario(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<ServidorTemporario> getServidorTemporarioById(@PathVariable Long id) {
        ServidorTemporario servidorTemporario = servidorTemporarioService.buscaPorId(id);
        return servidorTemporario != null ? ResponseEntity.ok(servidorTemporario) : ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Cadastrar uma servidorTemporario",
            description = "Endpoint para cadastrar uma servidorTemporario",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do Servidor Efetivo a ser cadastrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ServidorEfetivo.class),
                            examples = {@ExampleObject(name = "Exemplo 1 - ServidorTemporario X", value = """
                        { 
                                      "nome": "Luiz da sousa",
                                      "dataNascimento": "1980-03-29",
                                      "sexo": "Masculino",
                                      "mae": "Maria",
                                      "pai": "Joaquim",
                                      "dataAdmissao": "2000-03-29",
                                      "dataDemissao": "2025-03-29"
                         }""")})
            ))
    @PostMapping
    public ResponseEntity<ServidorTemporario> saveServidorTemporario(@RequestBody ServidorTemporarioDTO servidorTemporario) {
        ServidorTemporario servidorTemporariodb = servidorTemporarioService.salvarServidorTemporario(servidorTemporario);
        return ResponseEntity.ok(servidorTemporariodb);
    }

    @Operation(
            summary = "Alterar uma servidorTemporario",
            description = "Endpoint para alterar uma servidorTemporario",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do Servidor Efetivo a ser cadastrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ServidorEfetivo.class),
                            examples = {@ExampleObject(name = "Exemplo 1 - ServidorTemporario X", value = """
                        { 
                            "pesId": 3,
                           "nome": "Atualizado Pedro",
                           "dataNascimento": "2025-03-29",
                           "sexo": "Masculino",
                           "mae": "Atualizado Teresa Oliveira",
                           "pai": "Atualizado Antonio Oliveira",
                           "dataAdmissao": "2001-01-01",
                           "dataDemissao": "2024-12-01"
                         }""")})
            ))
    @PutMapping
    public ResponseEntity<ServidorTemporario> alterarServidorTemporario(@RequestBody ServidorTemporarioDTO servidorTemporario) {
        ServidorTemporario servidorTemporariodb = servidorTemporarioService.atualizarServidorTemporario(servidorTemporario);
        return ResponseEntity.ok(servidorTemporariodb);
    }
}
