package com.Fullstack1.Microservicio1_Libros.Service;

import com.Fullstack1.Microservicio1_Libros.Assembler.AutorAssembler;
import com.Fullstack1.Microservicio1_Libros.DTO.AutorDTO;
import com.Fullstack1.Microservicio1_Libros.DTO.AutorRequestDTO;
import com.Fullstack1.Microservicio1_Libros.Model.Autor;
import com.Fullstack1.Microservicio1_Libros.Repository.AutorRepository;
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
public class AutorService {
    
    private final AutorRepository autorRepository;
    private final AutorAssembler autorAssembler;

    @Transactional(readOnly = true)
    public List<AutorDTO> obtenerTodos() {
        log.info("Service: Obteniendo lista completa de autores");
        try {
            List<Autor> autores = autorRepository.findAll();
            log.debug("Se encontraron {} autores", autores.size());
            return autores.stream()
                .map(autorAssembler::toDTO)
                .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error al obtener autores", e);
            throw new RuntimeException("Error al obtener los autores: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public AutorDTO obtenerPorId(Integer id) {
        log.info("Service: Buscando autor con ID: {}", id);
        try {
            Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Autor no encontrado con ID: {}", id);
                    return new RuntimeException("Autor no encontrado con ID: " + id);
                });
            log.debug("Autor encontrado: {}", autor.getNombre());
            return autorAssembler.toDTO(autor);
        } catch (Exception e) {
            log.error("Error al buscar autor con ID: {}", id, e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public AutorDTO obtenerPorRut(String rut) {
        log.info("Service: Buscando autor con RUT: {}", rut);
        try {
            Autor autor = autorRepository.findByRut(rut)
                .orElseThrow(() -> {
                    log.error("Autor no encontrado con RUT: {}", rut);
                    return new RuntimeException("Autor no encontrado con RUT: " + rut);
                });
            log.debug("Autor encontrado: {}", autor.getNombre());
            return autorAssembler.toDTO(autor);
        } catch (Exception e) {
            log.error("Error al buscar autor con RUT: {}", rut, e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<AutorDTO> buscarPorNombre(String nombre) {
        log.info("Service: Buscando autores por nombre: {}", nombre);
        try {
            List<Autor> autores = autorRepository.buscarPorNombre(nombre);
            log.debug("Se encontraron {} autores con nombre: {}", autores.size(), nombre);
            return autores.stream()
                .map(autorAssembler::toDTO)
                .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error al buscar autores por nombre: {}", nombre, e);
            throw new RuntimeException("Error en búsqueda: " + e.getMessage());
        }
    }

    public AutorDTO crear(AutorRequestDTO request) {
        log.info("Service: Creando nuevo autor - RUT: {}", request.getRut());
        
        try {
            if (request.getRut() != null && autorRepository.existsByRut(request.getRut())) {
                log.warn("Intento de crear autor con RUT duplicado: {}", request.getRut());
                throw new RuntimeException("Ya existe un autor con el RUT: " + request.getRut());
            }
            
            if (request.getCorreo() != null && autorRepository.existsByCorreo(request.getCorreo())) {
                log.warn("Intento de crear autor con correo duplicado: {}", request.getCorreo());
                throw new RuntimeException("Ya existe un autor con el correo: " + request.getCorreo());
            }
            Autor autor = Autor.builder()
                    .rut(request.getRut())
                    .correo(request.getCorreo())
                    .nombre(request.getNombre())
                    .numero(request.getNumero())
                    .ubicacion(request.getUbicacion())
                    .nacionalidad(request.getNacionalidad())
                    .build();
            
            Autor autorGuardado = autorRepository.save(autor);
            log.info("Autor creado exitosamente con ID: {}", autorGuardado.getId());
            
            return autorAssembler.toDTO(autorGuardado);
        } catch (Exception e) {
            log.error("Error al crear autor", e);
            throw new RuntimeException("Error al crear autor: " + e.getMessage());
        }
    }

    public AutorDTO actualizar(Integer id, AutorRequestDTO request) {
        log.info("Service: Actualizando autor con ID: {}", id);
        
        try {
            Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Autor no encontrado para actualizar - ID: {}", id);
                    return new RuntimeException("Autor no encontrado con ID: " + id);
                });
            if (request.getRut() != null && !request.getRut().equals(autor.getRut()) 
                    && autorRepository.existsByRut(request.getRut())) {
                log.warn("Intento de actualizar autor con RUT duplicado: {}", request.getRut());
                throw new RuntimeException("Ya existe un autor con el RUT: " + request.getRut());
            }
            if (request.getCorreo() != null && !request.getCorreo().equals(autor.getCorreo()) 
                    && autorRepository.existsByCorreo(request.getCorreo())) {
                log.warn("Intento de actualizar autor con correo duplicado: {}", request.getCorreo());
                throw new RuntimeException("Ya existe un autor con el correo: " + request.getCorreo());
            }
            if (request.getRut() != null) {
                autor.setRut(request.getRut());
            }
            if (request.getCorreo() != null) {
                autor.setCorreo(request.getCorreo());
            }
            if (request.getNombre() != null) {
                autor.setNombre(request.getNombre());
            }
            if (request.getNumero() != null) {
                autor.setNumero(request.getNumero());
            }
            if (request.getUbicacion() != null) {
                autor.setUbicacion(request.getUbicacion());
            }
            if (request.getNacionalidad() != null) {
                autor.setNacionalidad(request.getNacionalidad());
            }
            
            Autor autorActualizado = autorRepository.save(autor);
            log.info("Autor actualizado exitosamente - ID: {}", id);
            
            return autorAssembler.toDTO(autorActualizado);
        } catch (Exception e) {
            log.error("Error al actualizar autor con ID: {}", id, e);
            throw new RuntimeException("Error al actualizar autor: " + e.getMessage());
        }
    }

    public void eliminar(Integer id) {
        log.info("Service: Eliminando autor con ID: {}", id);
        
        try {
            if (!autorRepository.existsById(id)) {
                log.error("Autor no encontrado para eliminar - ID: {}", id);
                throw new RuntimeException("Autor no encontrado con ID: " + id);
            }
            autorRepository.deleteById(id);
            log.info("Autor eliminado exitosamente - ID: {}", id);
        } catch (Exception e) {
            log.error("Error al eliminar autor con ID: {}", id, e);
            throw new RuntimeException("Error al eliminar autor: " + e.getMessage());
        }
    }
}
