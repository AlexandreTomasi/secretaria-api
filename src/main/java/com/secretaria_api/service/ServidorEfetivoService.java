package com.secretaria_api.service;

import com.secretaria_api.model.ServidorEfetivo;
import com.secretaria_api.repository.ServidorEfetivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ServidorEfetivoService {
    @Autowired
    private ServidorEfetivoRepository servidorEfetivoRepository;

    // Método para buscar todos os servidores efetivos com paginação
    public Page<ServidorEfetivo> getAllServidoresEfetivos(Pageable pageable) {
        return servidorEfetivoRepository.findAll(pageable);
    }

    // Método para buscar um servidor efetivo por ID
    public ServidorEfetivo getServidorEfetivoById(Long id) {
        return servidorEfetivoRepository.findById(id).orElse(null); // Retorna null se não encontrar
    }

    // Método para salvar ou atualizar um servidor efetivo
    public ServidorEfetivo saveServidorEfetivo(ServidorEfetivo servidorEfetivo) {
        return servidorEfetivoRepository.save(servidorEfetivo);
    }

    // Método para deletar um servidor efetivo por ID
    public void deleteServidorEfetivo(Long id) {
        servidorEfetivoRepository.deleteById(id);
    }
}
