package com.secretaria_api.controller;

import com.secretaria_api.dto.UnidadeDTO;
import com.secretaria_api.model.ServidorEfetivo;
import com.secretaria_api.model.Unidade;
import com.secretaria_api.service.UnidadeService;
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

@Tag(name = "Unidades", description = "Operações com unidades")
@RestController
@RequestMapping("/unidade")
public class UnidadeController {

    @Autowired
    UnidadeService unidadeService;

    @Operation(
            summary = "Listar unidades paginado",
            description = "Retorna uma lista paginada de usuários",
            parameters = {
                    @Parameter(name = "page", description = "Número da página (0-based)", example = "0", in = ParameterIn.QUERY),
                    @Parameter(name = "size", description = "Quantidade de itens por página", example = "2", in = ParameterIn.QUERY),
                    @Parameter(name = "sort", description = "Critério de ordenação (ex: id,desc)", example = "id,desc", in = ParameterIn.QUERY)
            }
    )
    @GetMapping
    public ResponseEntity<Page<Unidade>> listarUsuarios(
            @Parameter(hidden = true) @PageableDefault(size = 2, sort = "nome") Pageable pageable) {
        Page<Unidade> usuarios = unidadeService.listarTodos(pageable);
        //Page<UsuarioDTO> usuariosDTO = usuarios.map(Usuario::toDTO);
        return ResponseEntity.ok(usuarios);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUnidade(@PathVariable Long id) {
        unidadeService.deleteUnidade(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Unidade> getUnidadeById(@PathVariable Long id) {
        Unidade unidade = unidadeService.buscaPorId(id);
        return unidade != null ? ResponseEntity.ok(unidade) : ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Cadastrar uma unidade",
            description = "Endpoint para cadastrar uma unidade",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do Servidor Efetivo a ser cadastrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ServidorEfetivo.class),
                            examples = {@ExampleObject(name = "Exemplo 1 - Unidade X", value = """
                        { 
                        "nome": "Secretaria xxx",
                         "sigla": "xxx",
                         "enderecos":[{
                               "tipoLogradouro": "Rua",
                               "logradouro": "av rubens",
                               "numero": 1002,
                               "bairro": "centro",
                               "cidadeId": 2
                         }]
                         }"""),
                                    @ExampleObject(
                                            name = "Exemplo 2 - Unidade Y", value = """
                                                    { 
                                                    "nome": "Secretaria YYY",
                         "sigla": "YYY",
                         "enderecos":[{
                               "tipoLogradouro": "Rua",
                               "logradouro": "av rubens",
                               "numero": 1002,
                               "bairro": "centro",
                               "cidadeId": 2
                         },{
                               "tipoLogradouro": "Av",
                               "logradouro": "av ARANGUERA",
                               "numero": 666,
                               "bairro": "CPA",
                               "cidadeId": 3
                         }]
                         }""")})
            ))
    @PostMapping
    public ResponseEntity<Unidade> saveUnidade(@RequestBody UnidadeDTO unidade) {
        Unidade unidadedb = unidadeService.salvarUnidade(unidade);
        return ResponseEntity.ok(unidadedb);
    }

    @Operation(
            summary = "Alterar uma unidade",
            description = "Endpoint para alterar uma unidade",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do Servidor Efetivo a ser cadastrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ServidorEfetivo.class),
                            examples = {@ExampleObject(name = "Exemplo 1 - Unidade X", value = """
                        { 
                        "id": 1,
                        "nome": "Secretaria atualizada Educacao",
                         "sigla": "novoSeduc",
                         "enderecos":[{
                                "id": 1 ,
                               "tipoLogradouro": "Estrada",
                               "logradouro": "campo",
                               "numero": 23,
                               "bairro": "novo bairro",
                               "cidadeId": 2
                         }]
                         }""")})
            ))
    @PutMapping
    public ResponseEntity<Unidade> alterarUnidade(@RequestBody UnidadeDTO unidade) {
        Unidade unidadedb = unidadeService.alterarUnidade(unidade);
        return ResponseEntity.ok(unidadedb);
    }
}
