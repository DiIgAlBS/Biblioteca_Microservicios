package com.Fullstack1.Microservicio1_Libros.Service;

import com.Fullstack1.Microservicio1_Libros.DTO.InventarioDTO;
import com.Fullstack1.Microservicio1_Libros.DTO.InventarioRequestDTO;
import com.Fullstack1.Microservicio1_Libros.Model.Inventario;
import com.Fullstack1.Microservicio1_Libros.Repository.InventarioRepository;
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
public class InventarioService {
    
    private final InventarioRepository inventarioRepository;
    private final InventarioAssembler inventarioAssembler;
    
    @Transactional(readOnly = true)
    public List<InventarioDTO> obtenerTodos() {
        log.info("Service: Obteniendo lista completa de inventarios");
        try {
            List<Inventario> inventarios = inventarioRepository.findAll();
            log.debug("Se encontraron {} inventarios", inventarios.size());
            return inventarios.stream()
                    .map(inventarioAssembler::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error al obtener inventarios", e);
            throw new RuntimeException("Error al obtener inventarios: " + e.getMessage());
        }
    }
    
    @Transactional(readOnly = true)
    public InventarioDTO obtenerPorId(Integer id) {
        log.info("Service: Buscando inventario con ID: {}", id);
        try {
            Inventario inventario = inventarioRepository.findById(id)
                    .orElseThrow(() -> {
                        log.error("Inventario no encontrado con ID: {}", id);
                        return new RuntimeException("Inventario no encontrado con ID: " + id);
                    });
            log.debug("Inventario encontrado: {}", inventario.getId());
            return inventarioAssembler.toDTO(inventario);
        } catch (Exception e) {
            log.error("Error al buscar inventario con ID: {}", id, e);
            throw e;
        }
    }
    
    public InventarioDTO crear(InventarioRequestDTO request) {
        log.info("Service: Creando nuevo inventario");
        
        try {
            Inventario inventario = Inventario.builder()
                    .servicioControlProductos(request.getServicioControlProductos())
                    .servicioComprasProveedores(request.getServicioComprasProveedores())
                    .servicioVentasPedidos(request.getServicioVentasPedidos())
                    .servicioStockAlmacen(request.getServicioStockAlmacen())
                    .build();
            
            Inventario inventarioGuardado = inventarioRepository.save(inventario);
            log.info("Inventario creado exitosamente con ID: {}", inventarioGuardado.getId());
            
            return inventarioAssembler.toDTO(inventarioGuardado);
        } catch (Exception e) {
            log.error("Error al crear inventario", e);
            throw new RuntimeException("Error al crear inventario: " + e.getMessage());
        }
    }
    
    public InventarioDTO actualizar(Integer id, InventarioRequestDTO request) {
        log.info("Service: Actualizando inventario con ID: {}", id);
        
        try {
            Inventario inventario = inventarioRepository.findById(id)
                    .orElseThrow(() -> {
                        log.error("Inventario no encontrado para actualizar - ID: {}", id);
                        return new RuntimeException("Inventario no encontrado con ID: " + id);
                    });
            
            if (request.getServicioControlProductos() != null) 
                inventario.setServicioControlProductos(request.getServicioControlProductos());
            if (request.getServicioComprasProveedores() != null) 
                inventario.setServicioComprasProveedores(request.getServicioComprasProveedores());
            if (request.getServicioVentasPedidos() != null) 
                inventario.setServicioVentasPedidos(request.getServicioVentasPedidos());
            if (request.getServicioStockAlmacen() != null) 
                inventario.setServicioStockAlmacen(request.getServicioStockAlmacen());
            
            Inventario inventarioActualizado = inventarioRepository.save(inventario);
            log.info("Inventario actualizado exitosamente - ID: {}", id);
            
            return inventarioAssembler.toDTO(inventarioActualizado);
        } catch (Exception e) {
            log.error("Error al actualizar inventario con ID: {}", id, e);
            throw new RuntimeException("Error al actualizar inventario: " + e.getMessage());
        }
    }
    
    public void eliminar(Integer id) {
        log.info("Service: Eliminando inventario con ID: {}", id);
        
        try {
            if (!inventarioRepository.existsById(id)) {
                log.error("Inventario no encontrado para eliminar - ID: {}", id);
                throw new RuntimeException("Inventario no encontrado con ID: " + id);
            }
            
            inventarioRepository.deleteById(id);
            log.info("Inventario eliminado exitosamente - ID: {}", id);
        } catch (Exception e) {
            log.error("Error al eliminar inventario con ID: {}", id, e);
            throw new RuntimeException("Error al eliminar inventario: " + e.getMessage());
        }
    }
}
