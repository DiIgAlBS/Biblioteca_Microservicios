-- Creación de la tabla de Préstamos
CREATE TABLE prestamos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    libro_id INT NOT NULL,
    fecha_prestamo DATE NOT NULL,
    fecha_devolucion DATE,
    estado VARCHAR(50) NOT NULL
);

-- Creación de la tabla de Multas (Actualizada con TUS variables)
CREATE TABLE multas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    prestamo_id INT NOT NULL,
    usuario_id INT NOT NULL,
    monto DOUBLE NOT NULL,
    motivo VARCHAR(255) NOT NULL,
    fecha_generada DATE NOT NULL,
    fecha_pago DATE,
    estado VARCHAR(50) NOT NULL,
    CONSTRAINT fk_multa_prestamo FOREIGN KEY (prestamo_id) REFERENCES prestamos(id) ON DELETE CASCADE
);