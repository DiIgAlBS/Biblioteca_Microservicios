package com.Fullstack1.Microservicio1_Libros.Service;

import com.Fullstack1.Microservicio1_Libros.DTO.LibroDTO;
import com.Fullstack1.Microservicio1_Libros.DTO.LibroRequestDTO;
import com.Fullstack1.Microservicio1_Libros.Model.Libro;
import com.Fullstack1.Microservicio1_Libros.Repository.LibroRepository;
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
public class LibroService {
    
    private final LibroRepository libroRepository;
    private final LibroAssembler libroAssembler;
    
    @Transactional(readOnly = true)
    public List<LibroDTO> obtenerTodos() {
        log.info("Service: Obteniendo lista completa de libros");
        try {
            List<Libro> libros = libroRepository.findAll();
            log.debug("Se encontraron {} libros", libros.size());
            return libros.stream()
                    .map(libroAssembler::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error al obtener libros", e);
            throw new RuntimeException("Error al obtener libros: " + e.getMessage());
        }
    }
    
    @Transactional(readOnly = true)
    public LibroDTO obtenerPorId(Integer id) {
        log.info("Service: Buscando libro con ID: {}", id);
        try {
            Libro libro = libroRepository.findById(id)
                    .orElseThrow(() -> {
                        log.error("Libro no encontrado con ID: {}", id);
                        return new RuntimeException("Libro no encontrado con ID: " + id);
                    });
            log.debug("Libro encontrado: {}", libro.getNombre());
            return libroAssembler.toDTO(libro);
        } catch (Exception e) {
            log.error("Error al buscar libro con ID: {}", id, e);
            throw e;
        }
    }
    
    @Transactional(readOnly = true)
    public List<LibroDTO> buscarPorNombre(String nombre) {
        log.info("Service: Buscando libros por nombre: {}", nombre);
        try {
            List<Libro> libros = libroRepository.buscarPorNombre(nombre);
            log.debug("Se encontraron {} libros con nombre: {}", libros.size(), nombre);
            return libros.stream()
                    .map(libroAssembler::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error al buscar libros por nombre: {}", nombre, e);
            throw new RuntimeException("Error en búsqueda: " + e.getMessage());
        }
    }
    
    @Transactional(readOnly = true)
    public List<LibroDTO> obtenerPorAutor(Integer autorId) {
        log.info("Service: Obteniendo libros del autor ID: {}", autorId);
        try {
            List<Libro> libros = libroRepository.findByAutorId(autorId);
            log.debug("Se encontraron {} libros del autor", libros.size());
            return libros.stream()
                    .map(libroAssembler::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error al obtener libros del autor: {}", autorId, e);
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
    
    @Transactional(readOnly = true)
    public List<LibroDTO> obtenerPorEditorial(Integer editorialId) {
        log.info("Service: Obteniendo libros de la editorial ID: {}", editorialId);
        try {
            List<Libro> libros = libroRepository.findByEditorialId(editorialId);
            log.debug("Se encontraron {} libros de la editorial", libros.size());
            return libros.stream()
                    .map(libroAssembler::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error al obtener libros de la editorial: {}", editorialId, e);
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
    
    public LibroDTO crear(LibroRequestDTO request) {
        log.info("Service: Creando nuevo libro - Nombre: {}", request.getNombre());
        
        try {
            Libro libro = Libro.builder()
                    .nombre(request.getNombre())
                    .fechaPublicacion(request.getFechaPublicacion())
                    .idiomaId(request.getIdiomaId())
                    .autorId(request.getAutorId())
                    .editorialId(request.getEditorialId())
                    .categoriaId(request.getCategoriaId())
                    .build();
            
            Libro libroGuardado = libroRepository.save(libro);
            log.info("Libro creado exitosamente con ID: {}", libroGuardado.getId());
            
            return libroAssembler.toDTO(libroGuardado);
        } catch (Exception e) {
            log.error("Error al crear libro", e);
            throw new RuntimeException("Error al crear libro: " + e.getMessage());
        }
    }
    
    public LibroDTO actualizar(Integer id, LibroRequestDTO request) {
        log.info("Service: Actualizando libro con ID: {}", id);
        
        try {
            Libro libro = libroRepository.findById(id)
                    .orElseThrow(() -> {
                        log.error("Libro no encontrado para actualizar - ID: {}", id);
                        return new RuntimeException("Libro no encontrado con ID: " + id);
                    });
            
            if (request.getNombre() != null) libro.setNombre(request.getNombre());
            if (request.getFechaPublicacion() != null) libro.setFechaPublicacion(request.getFechaPublicacion());
            if (request.getIdiomaId() != null) libro.setIdiomaId(request.getIdiomaId());
            if (request.getAutorId() != null) libro.setAutorId(request.getAutorId());
            if (request.getEditorialId() != null) libro.setEditorialId(request.getEditorialId());
            if (request.getCategoriaId() != null) libro.setCategoriaId(request.getCategoriaId());
            
            Libro libroActualizado = libroRepository.save(libro);
            log.info("Libro actualizado exitosamente - ID: {}", id);
            
            return libroAssembler.toDTO(libroActualizado);
        } catch (Exception e) {
            log.error("Error al actualizar libro con ID: {}", id, e);
            throw new RuntimeException("Error al actualizar libro: " + e.getMessage());
        }
    }
    
    public void eliminar(Integer id) {
        log.info("Service: Eliminando libro con ID: {}", id);
        
        try {
            if (!libroRepository.existsById(id)) {
                log.error("Libro no encontrado para eliminar - ID: {}", id);
                throw new RuntimeException("Libro no encontrado con ID: " + id);
            }
            
            libroRepository.deleteById(id);
            log.info("Libro eliminado exitosamente - ID: {}", id);
        } catch (Exception e) {
            log.error("Error al eliminar libro con ID: {}", id, e);
            throw new RuntimeException("Error al eliminar libro: " + e.getMessage());
        }
    }
}
