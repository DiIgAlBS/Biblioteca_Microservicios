package com.Fullstack1.Microservicio1_Libros.Service;

import com.Fullstack1.Microservicio1_Libros.DTO.EditorialDTO;
import com.Fullstack1.Microservicio1_Libros.DTO.EditorialRequestDTO;
import com.Fullstack1.Microservicio1_Libros.Model.Editorial;
import com.Fullstack1.Microservicio1_Libros.Repository.EditorialRepository;
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
public class EditorialService {
    
    private final EditorialRepository editorialRepository;
    private final EditorialAssembler editorialAssembler;
    
    @Transactional(readOnly = true)
    public List<EditorialDTO> obtenerTodos() {
        log.info("Service: Obteniendo lista completa de editoriales");
        try {
            List<Editorial> editoriales = editorialRepository.findAll();
            log.debug("Se encontraron {} editoriales", editoriales.size());
            return editoriales.stream()
                    .map(editorialAssembler::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error al obtener editoriales", e);
            throw new RuntimeException("Error al obtener editoriales: " + e.getMessage());
        }
    }
    
    @Transactional(readOnly = true)
    public EditorialDTO obtenerPorId(Integer id) {
        log.info("Service: Buscando editorial con ID: {}", id);
        try {
            Editorial editorial = editorialRepository.findById(id)
                    .orElseThrow(() -> {
                        log.error("Editorial no encontrada con ID: {}", id);
                        return new RuntimeException("Editorial no encontrada con ID: " + id);
                    });
            log.debug("Editorial encontrada: {}", editorial.getNombre());
            return editorialAssembler.toDTO(editorial);
        } catch (Exception e) {
            log.error("Error al buscar editorial con ID: {}", id, e);
            throw e;
        }
    }
    
    @Transactional(readOnly = true)
    public EditorialDTO obtenerPorNombre(String nombre) {
        log.info("Service: Buscando editorial por nombre: {}", nombre);
        try {
            Editorial editorial = editorialRepository.findByNombre(nombre)
                    .orElseThrow(() -> {
                        log.error("Editorial no encontrada con nombre: {}", nombre);
                        return new RuntimeException("Editorial no encontrada con nombre: " + nombre);
                    });
            log.debug("Editorial encontrada: {}", editorial.getNombre());
            return editorialAssembler.toDTO(editorial);
        } catch (Exception e) {
            log.error("Error al buscar editorial por nombre: {}", nombre, e);
            throw e;
        }
    }
    
    public EditorialDTO crear(EditorialRequestDTO request) {
        log.info("Service: Creando nueva editorial - Nombre: {}", request.getNombre());
        
        try {
            if (editorialRepository.existsByNombre(request.getNombre())) {
                log.warn("Intento de crear editorial con nombre duplicado: {}", request.getNombre());
                throw new RuntimeException("Ya existe una editorial con el nombre: " + request.getNombre());
            }
            
            if (request.getCorreoContacto() != null && editorialRepository.existsByCorreoContacto(request.getCorreoContacto())) {
                log.warn("Intento de crear editorial con correo duplicado: {}", request.getCorreoContacto());
                throw new RuntimeException("Ya existe una editorial con el correo: " + request.getCorreoContacto());
            }
            
            Editorial editorial = Editorial.builder()
                    .nombre(request.getNombre())
                    .direccion(request.getDireccion())
                    .telefono(request.getTelefono())
                    .correoContacto(request.getCorreoContacto())
                    .build();
            
            Editorial editorialGuardada = editorialRepository.save(editorial);
            log.info("Editorial creada exitosamente con ID: {}", editorialGuardada.getId());
            
            return editorialAssembler.toDTO(editorialGuardada);
        } catch (Exception e) {
            log.error("Error al crear editorial", e);
            throw new RuntimeException("Error al crear editorial: " + e.getMessage());
        }
    }
    
    public EditorialDTO actualizar(Integer id, EditorialRequestDTO request) {
        log.info("Service: Actualizando editorial con ID: {}", id);
        
        try {
            Editorial editorial = editorialRepository.findById(id)
                    .orElseThrow(() -> {
                        log.error("Editorial no encontrada para actualizar - ID: {}", id);
                        return new RuntimeException("Editorial no encontrada con ID: " + id);
                    });
            
            if (request.getNombre() != null && !request.getNombre().equals(editorial.getNombre()) 
                    && editorialRepository.existsByNombre(request.getNombre())) {
                log.warn("Intento de actualizar editorial con nombre duplicado: {}", request.getNombre());
                throw new RuntimeException("Ya existe una editorial con el nombre: " + request.getNombre());
            }
            
            if (request.getCorreoContacto() != null && !request.getCorreoContacto().equals(editorial.getCorreoContacto()) 
                    && editorialRepository.existsByCorreoContacto(request.getCorreoContacto())) {
                log.warn("Intento de actualizar editorial con correo duplicado: {}", request.getCorreoContacto());
                throw new RuntimeException("Ya existe una editorial con el correo: " + request.getCorreoContacto());
            }
            
            if (request.getNombre() != null) editorial.setNombre(request.getNombre());
            if (request.getDireccion() != null) editorial.setDireccion(request.getDireccion());
            if (request.getTelefono() != null) editorial.setTelefono(request.getTelefono());
            if (request.getCorreoContacto() != null) editorial.setCorreoContacto(request.getCorreoContacto());
            
            Editorial editorialActualizada = editorialRepository.save(editorial);
            log.info("Editorial actualizada exitosamente - ID: {}", id);
            
            return editorialAssembler.toDTO(editorialActualizada);
        } catch (Exception e) {
            log.error("Error al actualizar editorial con ID: {}", id, e);
            throw new RuntimeException("Error al actualizar editorial: " + e.getMessage());
        }
    }
    
    public void eliminar(Integer id) {
        log.info("Service: Eliminando editorial con ID: {}", id);
        
        try {
            if (!editorialRepository.existsById(id)) {
                log.error("Editorial no encontrada para eliminar - ID: {}", id);
                throw new RuntimeException("Editorial no encontrada con ID: " + id);
            }
            
            editorialRepository.deleteById(id);
            log.info("Editorial eliminada exitosamente - ID: {}", id);
        } catch (Exception e) {
            log.error("Error al eliminar editorial con ID: {}", id, e);
            throw new RuntimeException("Error al eliminar editorial: " + e.getMessage());
        }
    }
}
