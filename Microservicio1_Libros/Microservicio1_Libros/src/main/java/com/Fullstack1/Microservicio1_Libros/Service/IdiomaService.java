package com.Fullstack1.Microservicio1_Libros.Service;

import com.Fullstack1.Microservicio1_Libros.DTO.IdiomaDTO;
import com.Fullstack1.Microservicio1_Libros.DTO.IdiomaRequestDTO;
import com.Fullstack1.Microservicio1_Libros.Model.Idioma;
import com.Fullstack1.Microservicio1_Libros.Repository.IdiomaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class IdiomaService {
    
    private final IdiomaRepository idiomaRepository;
    private final IdiomaAssembler idiomaAssembler;
    
    @Transactional(readOnly = true)
    public List<IdiomaDTO> obtenerTodos() {
        log.info("Service: Obteniendo lista completa de idiomas");
        try {
            List<Idioma> idiomas = idiomaRepository.findAll();
            log.debug("Se encontraron {} idiomas", idiomas.size());
            return idiomas.stream()
                    .map(idiomaAssembler::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error al obtener idiomas", e);
            throw new RuntimeException("Error al obtener idiomas: " + e.getMessage());
        }
    }
    
    @Transactional(readOnly = true)
    public IdiomaDTO obtenerPorId(Integer id) {
        log.info("Service: Buscando idioma con ID: {}", id);
        try {
            Idioma idioma = idiomaRepository.findById(id)
                    .orElseThrow(() -> {
                        log.error("Idioma no encontrado con ID: {}", id);
                        return new RuntimeException("Idioma no encontrado con ID: " + id);
                    });
            log.debug("Idioma encontrado: {}", idioma.getNombreIdioma());
            return idiomaAssembler.toDTO(idioma);
        } catch (Exception e) {
            log.error("Error al buscar idioma con ID: {}", id, e);
            throw e;
        }
    }
    
    @Transactional(readOnly = true)
    public IdiomaDTO obtenerPorNombre(String nombre) {
        log.info("Service: Buscando idioma por nombre: {}", nombre);
        try {
            Idioma idioma = idiomaRepository.findByNombreIdioma(nombre)
                    .orElseThrow(() -> {
                        log.error("Idioma no encontrado con nombre: {}", nombre);
                        return new RuntimeException("Idioma no encontrado con nombre: " + nombre);
                    });
            log.debug("Idioma encontrado: {}", idioma.getNombreIdioma());
            return idiomaAssembler.toDTO(idioma);
        } catch (Exception e) {
            log.error("Error al buscar idioma por nombre: {}", nombre, e);
            throw e;
        }
    }
    
    public IdiomaDTO crear(IdiomaRequestDTO request) {
        log.info("Service: Creando nuevo idioma - Nombre: {}", request.getNombreIdioma());
        
        try {
            if (idiomaRepository.existsByNombreIdioma(request.getNombreIdioma())) {
                log.warn("Intento de crear idioma con nombre duplicado: {}", request.getNombreIdioma());
                throw new RuntimeException("Ya existe un idioma con el nombre: " + request.getNombreIdioma());
            }
            
            Idioma idioma = Idioma.builder()
                    .nombreIdioma(request.getNombreIdioma())
                    .build();
            
            Idioma idiomaGuardado = idiomaRepository.save(idioma);
            log.info("Idioma creado exitosamente con ID: {}", idiomaGuardado.getId());
            
            return idiomaAssembler.toDTO(idiomaGuardado);
        } catch (Exception e) {
            log.error("Error al crear idioma", e);
            throw new RuntimeException("Error al crear idioma: " + e.getMessage());
        }
    }
    
    public IdiomaDTO actualizar(Integer id, IdiomaRequestDTO request) {
        log.info("Service: Actualizando idioma con ID: {}", id);
        
        try {
            Idioma idioma = idiomaRepository.findById(id)
                    .orElseThrow(() -> {
                        log.error("Idioma no encontrado para actualizar - ID: {}", id);
                        return new RuntimeException("Idioma no encontrado con ID: " + id);
                    });
            
            if (request.getNombreIdioma() != null && !request.getNombreIdioma().equals(idioma.getNombreIdioma()) 
                    && idiomaRepository.existsByNombreIdioma(request.getNombreIdioma())) {
                log.warn("Intento de actualizar idioma con nombre duplicado: {}", request.getNombreIdioma());
                throw new RuntimeException("Ya existe un idioma con el nombre: " + request.getNombreIdioma());
            }
            
            if (request.getNombreIdioma() != null) {
                idioma.setNombreIdioma(request.getNombreIdioma());
            }
            
            Idioma idiomaActualizado = idiomaRepository.save(idioma);
            log.info("Idioma actualizado exitosamente - ID: {}", id);
            
            return idiomaAssembler.toDTO(idiomaActualizado);
        } catch (Exception e) {
            log.error("Error al actualizar idioma con ID: {}", id, e);
            throw new RuntimeException("Error al actualizar idioma: " + e.getMessage());
        }
    }
    
    public void eliminar(Integer id) {
        log.info("Service: Eliminando idioma con ID: {}", id);
        
        try {
            if (!idiomaRepository.existsById(id)) {
                log.error("Idioma no encontrado para eliminar - ID: {}", id);
                throw new RuntimeException("Idioma no encontrado con ID: " + id);
            }
            
            idiomaRepository.deleteById(id);
            log.info("Idioma eliminado exitosamente - ID: {}", id);
        } catch (Exception e) {
            log.error("Error al eliminar idioma con ID: {}", id, e);
            throw new RuntimeException("Error al eliminar idioma: " + e.getMessage());
        }
    }
}
