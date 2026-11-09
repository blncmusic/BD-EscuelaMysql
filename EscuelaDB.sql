CREATE DATABASE Escuela;
USE Escuela;

CREATE TABLE SalidaExtraurricular (
    id INT AUTO_INCREMENT PRIMARY KEY,
    destino VARCHAR(100),
    fecha DATE
);

CREATE TABLE Docente (
    DNI INT PRIMARY KEY,
    nombre VARCHAR(100),
    apellido VARCHAR(100),
    id_SalidaExtraurricular INT,
    FOREIGN KEY (id_SalidaExtraurricular) REFERENCES SalidaExtraurricular(id)
);

CREATE TABLE Chofer (
    DNI INT PRIMARY KEY,
    nombre VARCHAR(100),
    apellido VARCHAR(100),
    id_SalidaExtraurricular INT,
    FOREIGN KEY (id_SalidaExtraurricular) REFERENCES SalidaExtraurricular(id)
);

CREATE TABLE Preceptora (
    DNI INT PRIMARY KEY,
    nombre VARCHAR(100),
    apellido VARCHAR(100)
);

CREATE TABLE Aula (
    id INT PRIMARY KEY,
    año INT,
    grado VARCHAR(100),
    DNI_Preceptor INT,
    FOREIGN KEY (DNI_Preceptor) REFERENCES Preceptora(DNI)
);

CREATE TABLE Alumno (
    DNI INT PRIMARY KEY,
    nombre VARCHAR(100),
    apellido VARCHAR(100),
    DNI_Preceptora INT,
    id_Aula INT,
    fechaNacimiento DATE,
    curso VARCHAR(100),  -- Agregar columna curso
    FOREIGN KEY (DNI_Preceptora) REFERENCES Preceptora(DNI),
    FOREIGN KEY (id_Aula) REFERENCES Aula(id)
);

CREATE TABLE FichaMedica (
    id INT AUTO_INCREMENT PRIMARY KEY,
    alergia VARCHAR(255),
    tiposangre VARCHAR(50),
    contacto_emergencia VARCHAR(255),
    DNI_Alumno INT,
    FOREIGN KEY (DNI_Alumno) REFERENCES Alumno(DNI)
);

ALTER TABLE Docente
ADD COLUMN id_Aula INT,
ADD FOREIGN KEY (id_Aula) REFERENCES Aula(id);

INSERT INTO Preceptora (DNI, nombre, apellido) 
    VALUES (1, 'Maria', 'Gomez'), (2, 'Juan', 'Perez');
INSERT INTO Aula (id, año, grado, DNI_Preceptor) 
    VALUES (1, 2024, '1A', 1), (2, 2024, '2B', 2);
INSERT INTO Alumno (DNI, nombre, apellido, DNI_Preceptora, id_Aula, fechaNacimiento, curso) 
    VALUES (40165312, 'Pedro', 'Lopez', 1, 1, '2010-01-01', '1A'), (50215659, 'Ana', 'Martinez', 2, 2, '2011-02-02', '2B');
INSERT INTO FichaMedica (id, alergia, tiposangre, contacto_emergencia, DNI_Alumno) 
    VALUES (1, 'Polen', 'A+', '123456789', 40165312), (2, 'Ninguna', 'O-', '987654321', 50215659);
INSERT INTO SalidaExtraurricular (id, destino, fecha) 
    VALUES (1, 'Museo', '2024-05-01'), (2, 'Zoo', '2024-05-02');
INSERT INTO Docente (DNI, nombre, apellido, id_Aula) 
    VALUES (1, 'Carlos', 'Rodriguez', 1), (2, 'Laura', 'Gonzalez', 2);
INSERT INTO Chofer (DNI, nombre, apellido) 
    VALUES (1, 'Roberto', 'Sanchez'), (2, 'Carmen', 'Diaz');

SELECT * FROM Alumno WHERE DNI_Preceptora = 1;
SELECT * FROM Docente WHERE id_Aula = 1;
SELECT * FROM FichaMedica WHERE DNI_Alumno = 1;
SELECT * FROM Alumno;
