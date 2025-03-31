package com.secretaria_api.controller;

import com.secretaria_api.model.FotoPessoa;
import com.secretaria_api.service.FotoPessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Foto", description = "Operações com fotos de pessoas")
@RestController
@RequestMapping("/pessoa/foto")
public class FotoPessoaController {

    @Autowired
    FotoPessoaService fotoPessoaService;

    @Operation(summary = "Upload de múltiplas fotos para uma pessoa")
    @PostMapping(value = "/varias/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<FotoPessoa>> uploadFoto(
            @Parameter(description = "ID da pessoa", required = true)
            @RequestParam(value = "pessoaId", required = true) Long pessoaId,
            @Parameter(description = "Lista de arquivos de imagem", required = true)
            @RequestParam(value = "arquivos" , required = true) MultipartFile[] arquivos) {

        try {
            List<FotoPessoa> fotoPessoa = fotoPessoaService.create(pessoaId, arquivos);

            return ResponseEntity.ok(fotoPessoa);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Upload de uma foto para uma pessoa")
    @PostMapping(value = "/uma/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<FotoPessoa>> uploadFoto(
            @Parameter(description = "ID da pessoa", required = true, example = "2")
            @RequestParam(value = "pessoaId", required = true) Long pessoaId,
            @Parameter(description = "Lista de arquivos de imagem", required = true)
            @RequestParam(value = "arquivos" , required = true) MultipartFile arquivo) {

        try {
            List<FotoPessoa> fotoPessoa = fotoPessoaService.createUma(pessoaId, arquivo);

            return ResponseEntity.ok(fotoPessoa);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Buscar foto pelo ID",
            description = "Retorna os detalhes da foto, incluindo o link para download.",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Foto encontrada",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = FotoPessoa.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Foto não encontrada")
            })
    @GetMapping("/{fotoId}")
    public FotoPessoa buscarFoto(@PathVariable Long fotoId) {
        return fotoPessoaService.buscaFoto(fotoId);
    }
}
