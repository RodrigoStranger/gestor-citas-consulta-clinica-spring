-- Script SQL para insertar datos de prueba de especialidades
-- Este script se puede ejecutar después de que Spring cree las tablas automáticamente

INSERT INTO especialidades (nombre, descripcion) VALUES
('Cardiología', 'Especialidad médica que se encarga del estudio, diagnóstico y tratamiento de las enfermedades del corazón y del aparato circulatorio'),
('Dermatología', 'Especialidad médica que se encarga del estudio, diagnóstico y tratamiento de las enfermedades de la piel'),
('Neurología', 'Especialidad médica que se encarga del estudio, diagnóstico y tratamiento de las enfermedades del sistema nervioso'),
('Pediatría', 'Especialidad médica que se encarga de la atención médica de bebés, niños y adolescentes'),
('Ginecología', 'Especialidad médica que se encarga de la salud del aparato reproductor femenino'),
('Traumatología', 'Especialidad médica que se encarga del estudio, diagnóstico y tratamiento de las lesiones del aparato locomotor'),
('Oftalmología', 'Especialidad médica que se encarga del estudio, diagnóstico y tratamiento de las enfermedades de los ojos'),
('Psiquiatría', 'Especialidad médica que se encarga del estudio, diagnóstico y tratamiento de los trastornos mentales');

-- Ejemplo de cómo insertar relaciones en la tabla intermedia después de tener médicos
-- INSERT INTO medico_especialidad (id_medico, id_especialidad) VALUES
-- (1, 1), -- Médico 1 con Cardiología
-- (1, 2), -- Médico 1 también con Dermatología
-- (2, 3); -- Médico 2 con Neurología
