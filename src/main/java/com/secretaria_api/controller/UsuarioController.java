package com.secretaria_api.controller;

import com.secretaria_api.dto.UsuarioDTO;
import com.secretaria_api.model.Usuario;
import com.secretaria_api.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Tag(name = "Usuarios", description = "Inserir usuario do sistema")
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;


    @Operation(
            summary = "Criar um novo usuário",
            description = "Endpoint para cadastrar um usuário no sistema",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class),
                            examples = @ExampleObject(
                                    name = "Exemplo de payload",
                                    value = """
                        {
                            "nome": "Exbom",
                            "login": "teste",
                            "senha": "teste",
                            "email": "teste.silva@example.com"
                        }
                        """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuário criado com sucesso",
                            content = @Content(schema = @Schema(implementation = Usuario.class)))
                            }
                    )
    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        Usuario savedUsuario = usuarioService.salvarUsuario(usuario);
        savedUsuario.setSenha(null);
        return ResponseEntity.ok(savedUsuario);
    }


    @Operation(
            summary = "Listar usuários paginados",
            description = "Retorna uma lista paginada de usuários",
            parameters = {
                    @Parameter(name = "page", description = "Número da página (0-based)", example = "0", in = ParameterIn.QUERY),
                    @Parameter(name = "size", description = "Quantidade de itens por página", example = "10", in = ParameterIn.QUERY),
                    @Parameter(name = "sort", description = "Critério de ordenação (ex: nome,asc)", example = "nome,asc", in = ParameterIn.QUERY)
            }
    )
    @GetMapping
    public ResponseEntity<Page<Usuario>> listarUsuarios(
            @Parameter(hidden = true) @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        Page<Usuario> usuarios = usuarioService.listarTodos(pageable);
        Page<UsuarioDTO> usuariosDTO = usuarios.map(Usuario::toDTO);
        return ResponseEntity.ok(usuarios);
    }
}
