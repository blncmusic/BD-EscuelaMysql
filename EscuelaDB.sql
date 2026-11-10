CREATE DATABASE IF NOT EXISTS Escuela
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE Escuela;

-- PRECEPTORA
CREATE TABLE IF NOT EXISTS preceptora (
    DNI INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    PRIMARY KEY (DNI)
) ENGINE=InnoDB;

-- AULA
CREATE TABLE IF NOT EXISTS aula (
    id INT NOT NULL AUTO_INCREMENT,
    anio INT NOT NULL,
    division VARCHAR(20) NOT NULL,
    DNI_Preceptor INT DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_aula_preceptora
        FOREIGN KEY (DNI_Preceptor)
        REFERENCES preceptora(DNI)
        ON DELETE SET NULL
        ON UPDATE CASCADE
) ENGINE=InnoDB;

-- DOCENTE
CREATE TABLE IF NOT EXISTS docente (
    DNI INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    id_Aula INT DEFAULT NULL,
    PRIMARY KEY (DNI),
    CONSTRAINT fk_docente_aula
        FOREIGN KEY (id_Aula)
        REFERENCES aula(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
) ENGINE=InnoDB;

-- CHOFER
CREATE TABLE IF NOT EXISTS chofer (
    DNI INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    PRIMARY KEY (DNI)
) ENGINE=InnoDB;

-- ALUMNO
CREATE TABLE IF NOT EXISTS alumno (
    DNI INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    DNI_Preceptora INT DEFAULT NULL,
    id_Aula INT DEFAULT NULL,
    fechaNacimiento DATE NOT NULL,
    curso VARCHAR(50) NOT NULL,
    PRIMARY KEY (DNI),
    CONSTRAINT fk_alumno_preceptora
        FOREIGN KEY (DNI_Preceptora)
        REFERENCES preceptora(DNI)
        ON DELETE SET NULL
        ON UPDATE CASCADE,
    CONSTRAINT fk_alumno_aula
        FOREIGN KEY (id_Aula)
        REFERENCES aula(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
) ENGINE=InnoDB;

-- FICHA MEDICA
CREATE TABLE IF NOT EXISTS fichamedica (
    id INT NOT NULL AUTO_INCREMENT,
    historial TEXT DEFAULT NULL,
    alergias VARCHAR(255) DEFAULT NULL,
    emergenciaNombre VARCHAR(150) DEFAULT NULL,
    emergenciaNro VARCHAR(30) DEFAULT NULL,
    tipoSangre VARCHAR(10) DEFAULT NULL,
    DNI_Alumno INT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY DNI_Alumno (DNI_Alumno),
    CONSTRAINT fk_ficha_alumno
        FOREIGN KEY (DNI_Alumno)
        REFERENCES alumno(DNI)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE=InnoDB;

-- SALIDA EXTRACURRICULAR
CREATE TABLE IF NOT EXISTS salidaextracurricular (
    id INT NOT NULL AUTO_INCREMENT,
    destino VARCHAR(100) NOT NULL,
    fecha DATE NOT NULL,
    DNI_Docente INT DEFAULT NULL,
    DNI_Chofer INT DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_salida_docente
        FOREIGN KEY (DNI_Docente)
        REFERENCES docente(DNI)
        ON DELETE SET NULL
        ON UPDATE CASCADE,
    CONSTRAINT fk_salida_chofer
        FOREIGN KEY (DNI_Chofer)
        REFERENCES chofer(DNI)
        ON DELETE SET NULL
        ON UPDATE CASCADE
) ENGINE=InnoDB;

-- RELACION ENTRE ALUMNOS Y SALIDAS
CREATE TABLE IF NOT EXISTS alumno_salida (
    id_Salida INT NOT NULL,
    DNI_Alumno INT NOT NULL,
    PRIMARY KEY (id_Salida, DNI_Alumno),
    CONSTRAINT fk_alumno_salida_alumno
        FOREIGN KEY (DNI_Alumno)
        REFERENCES alumno(DNI)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_alumno_salida_salida
        FOREIGN KEY (id_Salida)
        REFERENCES salidaextracurricular(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE=InnoDB;
