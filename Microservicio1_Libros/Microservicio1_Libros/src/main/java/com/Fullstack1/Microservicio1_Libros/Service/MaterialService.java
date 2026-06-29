package com.Fullstack1.Microservicio1_Libros.Service;

import com.Fullstack1.Microservicio1_Libros.DTO.MaterialDTO;
import com.Fullstack1.Microservicio1_Libros.DTO.MaterialRequestDTO;
import com.Fullstack1.Microservicio1_Libros.Model.Material;
import com.Fullstack1.Microservicio1_Libros.Repository.MaterialRepository;
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
public class MaterialService {
    
    private final MaterialRepository materialRepository;
    private final MaterialAssembler materialAssembler;
    
    @Transactional(readOnly = true)
    public List<MaterialDTO> obtenerTodos() {
        log.info("Service: Obteniendo lista completa de materiales");
        try {
            List<Material> materiales = materialRepository.findAll();
            log.debug("Se encontraron {} materiales", materiales.size());
            return materiales.stream()
                    .map(materialAssembler::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error al obtener materiales", e);
            throw new RuntimeException("Error al obtener materiales: " + e.getMessage());
        }
    }
    
    @Transactional(readOnly = true)
    public MaterialDTO obtenerPorId(Integer id) {
        log.info("Service: Buscando material con ID: {}", id);
        try {
            Material material = materialRepository.findById(id)
                    .orElseThrow(() -> {
                        log.error("Material no encontrado con ID: {}", id);
                        return new RuntimeException("Material no encontrado con ID: " + id);
                    });
            log.debug("Material encontrado: {}", material.getNombreMaterial());
            return materialAssembler.toDTO(material);
        } catch (Exception e) {
            log.error("Error al buscar material con ID: {}", id, e);
            throw e;
        }
    }
    
    @Transactional(readOnly = true)
    public List<MaterialDTO> obtenerPorLibro(Integer libroId) {
        log.info("Service: Obteniendo materiales del libro ID: {}", libroId);
        try {
            List<Material> materiales = materialRepository.findByLibroId(libroId);
            log.debug("Se encontraron {} materiales del libro", materiales.size());
            return materiales.stream()
                    .map(materialAssembler::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error al obtener materiales del libro: {}", libroId, e);
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
    
    @Transactional(readOnly = true)
    public List<MaterialDTO> obtenerPorEstado(String estado) {
        log.info("Service: Obteniendo materiales por estado: {}", estado);
        try {
            List<Material> materiales = materialRepository.findByEstado(estado);
            log.debug("Se encontraron {} materiales con estado: {}", materiales.size(), estado);
            return materiales.stream()
                    .map(materialAssembler::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error al obtener materiales por estado: {}", estado, e);
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
    
    public MaterialDTO crear(MaterialRequestDTO request) {
        log.info("Service: Creando nuevo material - Nombre: {}", request.getNombreMaterial());
        
        try {
            Material material = Material.builder()
                    .nombreMaterial(request.getNombreMaterial())
                    .tipoMaterial(request.getTipoMaterial())
                    .estado(request.getEstado())
                    .libroId(request.getLibroId())
                    .build();
            
            Material materialGuardado = materialRepository.save(material);
            log.info("Material creado exitosamente con ID: {}", materialGuardado.getId());
            
            return materialAssembler.toDTO(materialGuardado);
        } catch (Exception e) {
            log.error("Error al crear material", e);
            throw new RuntimeException("Error al crear material: " + e.getMessage());
        }
    }
    
    public MaterialDTO actualizar(Integer id, MaterialRequestDTO request) {
        log.info("Service: Actualizando material con ID: {}", id);
        
        try {
            Material material = materialRepository.findById(id)
                    .orElseThrow(() -> {
                        log.error("Material no encontrado para actualizar - ID: {}", id);
                        return new RuntimeException("Material no encontrado con ID: " + id);
                    });
            
            if (request.getNombreMaterial() != null) material.setNombreMaterial(request.getNombreMaterial());
            if (request.getTipoMaterial() != null) material.setTipoMaterial(request.getTipoMaterial());
            if (request.getEstado() != null) material.setEstado(request.getEstado());
            if (request.getLibroId() != null) material.setLibroId(request.getLibroId());
            
            Material materialActualizado = materialRepository.save(material);
            log.info("Material actualizado exitosamente - ID: {}", id);
            
            return materialAssembler.toDTO(materialActualizado);
        } catch (Exception e) {
            log.error("Error al actualizar material con ID: {}", id, e);
            throw new RuntimeException("Error al actualizar material: " + e.getMessage());
        }
    }
    
    public void eliminar(Integer id) {
        log.info("Service: Eliminando material con ID: {}", id);
        
        try {
            if (!materialRepository.existsById(id)) {
                log.error("Material no encontrado para eliminar - ID: {}", id);
                throw new RuntimeException("Material no encontrado con ID: " + id);
            }
            
            materialRepository.deleteById(id);
            log.info("Material eliminado exitosamente - ID: {}", id);
        } catch (Exception e) {
            log.error("Error al eliminar material con ID: {}", id, e);
            throw new RuntimeException("Error al eliminar material: " + e.getMessage());
        }
    }
}
