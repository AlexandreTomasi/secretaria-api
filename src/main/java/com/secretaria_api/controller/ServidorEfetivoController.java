package com.secretaria_api.controller;

import com.secretaria_api.model.ServidorEfetivo;
import com.secretaria_api.service.ServidorEfetivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/servidores-efetivos")
public class ServidorEfetivoController {

    @Autowired
    private ServidorEfetivoService servidorEfetivoService;

    // Endpoint para obter todos os servidores efetivos com paginação
    @GetMapping
    public Page<ServidorEfetivo> getAllServidoresEfetivos(Pageable pageable) {
        return servidorEfetivoService.getAllServidoresEfetivos(pageable);
    }

    // Endpoint para obter um servidor efetivo por ID
    @GetMapping("/{id}")
    public ResponseEntity<ServidorEfetivo> getServidorEfetivoById(@PathVariable Long id) {
        ServidorEfetivo servidorEfetivo = servidorEfetivoService.getServidorEfetivoById(id);
        return servidorEfetivo != null ? ResponseEntity.ok(servidorEfetivo) : ResponseEntity.notFound().build();
    }

    // Endpoint para salvar ou atualizar um servidor efetivo
    @PostMapping
    public ResponseEntity<ServidorEfetivo> saveServidorEfetivo(@RequestBody ServidorEfetivo servidorEfetivo) {
        ServidorEfetivo savedServidor = servidorEfetivoService.saveServidorEfetivo(servidorEfetivo);
        return ResponseEntity.ok(savedServidor);
    }

    // Endpoint para deletar um servidor efetivo por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServidorEfetivo(@PathVariable Long id) {
        servidorEfetivoService.deleteServidorEfetivo(id);
        return ResponseEntity.noContent().build();
    }
}
