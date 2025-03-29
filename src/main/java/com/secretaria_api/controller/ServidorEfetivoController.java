package com.secretaria_api.controller;

import com.secretaria_api.dto.EnderecoFuncionalDTO;
import com.secretaria_api.dto.ServidorEfetivoDTO;
import com.secretaria_api.dto.ServidorUnidadeDTO;
import com.secretaria_api.exception.NotFoundException;
import com.secretaria_api.model.ServidorEfetivo;
import com.secretaria_api.service.ServidorEfetivoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Servidor Efetivos", description = "Operações de CRUD para servidores efetivos")
@RestController
@RequestMapping("/servidor/efetivo")
public class ServidorEfetivoController {

    @Autowired
    private ServidorEfetivoService servidorEfetivoService;

    @Operation(
            summary = "Consultar o endereço funcional (da unidade\n" +
                    "onde o servidor é lotado) a partir de uma parte do nome do servidor efetivo",
            description = "Consultar o endereço funcional (da unidade\n" +
                    "onde o servidor é lotado) a partir de uma parte do nome do servidor efetivo.",
            parameters = {
            @Parameter(
                    name = "nome",
                    description = "Parte do nome do servidor efetivo para busca",
                    examples = {
                            @ExampleObject(
                                    name = "Exemplo com nome 'Ana'",
                                    summary = "Busca por Ana",
                                    value = "Ana",
                                    description = "Retorna endereço da unidade pelo nome do servidor com 'Ana' no nome"
                            )
                    }
            )}
            ,
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Endereço funcional encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EnderecoFuncionalDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Acesso não autorizado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                            {
                                "error": "Unauthorized",
                                "status": 401,
                                "message": "Credenciais de autenticação inválidas ou ausentes"
                            }
                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Nenhum servidor encontrado com o nome informado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                            {
                                "error": "Not Found",
                                "status": 404,
                                "message": "Nenhum servidor encontrado com o nome informado"
                            }
                            """
                                    )
                            )
                    )}
    )
    @GetMapping("/endereco-funcional")
    public ResponseEntity<List<EnderecoFuncionalDTO>> consultarEnderecoFuncional(
            @RequestParam String nome) {

        List<EnderecoFuncionalDTO> results = servidorEfetivoService.consultarEnderecoFuncional(nome);
        if (results.isEmpty()) {
            throw new NotFoundException("Nenhum servidor efetivo encontrado com o nome informado: " + nome);
        }
        return ResponseEntity.ok(results);
    }


    @Operation(
            summary = "consultar os servidores efetivos lotados\n" +
                    "em determinada unidade parametrizando a consulta pelo atributo unid_id",
            description = "Consultar os servidores efetivos lotados\n" +
                    "em determinada unidade parametrizando a consulta pelo atributo unid_id;\n" +
                    "Retornar os seguintes campos: Nome, idade, unidade de lotação e fotografia",
            parameters = {
                    @Parameter(
                            name = "unidadeId",
                            description = "Unidade id efetivo para busca",
                            examples = {
                                    @ExampleObject(
                                            name = "Exemplo 1",
                                            summary = "Busca por id 1",
                                            value = "1",
                                            description = "consultar os servidores efetivos lotados\n" +
                                                    "em determinada unidade parametrizando a consulta pelo atributo unid_id;\n" +
                                                    "Retornar os seguintes campos: Nome, idade, unidade de lotação e\n" +
                                                    "fotografia"
                                    )
                            }
                    )}
            ,
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Servidores encontrados",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EnderecoFuncionalDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Acesso não autorizado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                            {
                                "error": "Unauthorized",
                                "status": 401,
                                "message": "Credenciais de autenticação inválidas ou ausentes"
                            }
                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Nenhum servidor encontrado na unidade informada",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                            {
                                "error": "Not Found",
                                "status": 404,
                                "message": "Nenhum servidor encontrado na unidade informada"
                            }
                            """
                                    )
                            )
                    )}
    )
    @GetMapping("/unidade")
    public ResponseEntity<List<ServidorUnidadeDTO>> consultarServidoresPorUnidade(
            @RequestParam Long unidadeId) {

        List<ServidorUnidadeDTO> results = servidorEfetivoService.consultarServidorEfetivoPorUni(unidadeId);
        if (results == null || results.isEmpty()) {
            throw new NotFoundException("Nenhum servidor encontrado na unidade informada: " + unidadeId);
        }
        return ResponseEntity.ok(results);
    }



    @Operation(
            summary = "Listar servidore efetivos paginado",
            description = "Retorna uma lista paginada de servidores efetivos",
            parameters = {
                    @Parameter(name = "page", description = "Número da página (0-based)", example = "0", in = ParameterIn.QUERY),
                    @Parameter(name = "size", description = "Quantidade de itens por página", example = "10", in = ParameterIn.QUERY),
                    @Parameter(name = "sort", description = "Critério de ordenação (ex: pesId,asc)", example = "pesId,asc", in = ParameterIn.QUERY)
            }
    )
    @GetMapping
    public ResponseEntity<Page<ServidorEfetivo>> listarUsuarios(
            @Parameter(hidden = true) @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        Page<ServidorEfetivo> servidorEfetivos = servidorEfetivoService.getAllServidoresEfetivos(pageable);
        return ResponseEntity.ok(servidorEfetivos);
    }

    // Endpoint para obter um servidor efetivo por ID
    @GetMapping("/{id}")
    public ResponseEntity<ServidorEfetivo> getServidorEfetivoById(@PathVariable Long id) {
        ServidorEfetivo servidorEfetivo = servidorEfetivoService.getServidorEfetivoById(id);
        return servidorEfetivo != null ? ResponseEntity.ok(servidorEfetivo) : ResponseEntity.notFound().build();
    }


    // Endpoint para deletar um servidor efetivo por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServidorEfetivo(@PathVariable Long id) {
        servidorEfetivoService.deleteServidorEfetivo(id);
        return ResponseEntity.noContent().build();
    }


    @Operation(
            summary = "Cadastrar um servidor efetivo",
            description = "Endpoint para cadastrar uma servidor efetivo",
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
                                      "matricula": "20000329"
                         }""")})
            ))
    @PostMapping("")
    public ResponseEntity<ServidorEfetivo> saveServidorTemporario(@RequestBody ServidorEfetivoDTO servidorEfetivoDTO) {
        ServidorEfetivo servidorTemporariodb = servidorEfetivoService.salvarServidorTemporario(servidorEfetivoDTO);
        return ResponseEntity.ok(servidorTemporariodb);
    }

    @Operation(
            summary = "Alterar uma servidor efetivo",
            description = "Endpoint para alterar uma servidor efetivo",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do Servidor Efetivo a ser cadastrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ServidorEfetivo.class),
                            examples = {@ExampleObject(name = "Exemplo 1 - Servidor efetivo Pedro", value = """
                        { 
                            "pesId": 3,
                           "nome": "Atualizado Pedro",
                           "dataNascimento": "2025-03-29",
                           "sexo": "Masculino",
                           "mae": "Atualizado Teresa Oliveira",
                           "pai": "Atualizado Antonio Oliveira",
                            "matricula": "1234567"
                         }""")})
            ))
    @PutMapping("")
    public ResponseEntity<ServidorEfetivo> alterarServidorTemporario(@RequestBody ServidorEfetivoDTO servidorEfetivoDTO) {
        ServidorEfetivo servidorTemporariodb = servidorEfetivoService.atualizarServidorTemporario(servidorEfetivoDTO);
        return ResponseEntity.ok(servidorTemporariodb);
    }
}
