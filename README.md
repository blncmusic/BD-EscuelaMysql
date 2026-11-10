# Sistema de Gestión Escolar

## Descripción del proyecto

Este proyecto consiste en el desarrollo de un sistema de gestión escolar realizado en Java, conectado a una base de datos MySQL. Su objetivo es facilitar la administración de la información de alumnos, docentes, choferes y salidas extracurriculares, permitiendo registrar, consultar, modificar y eliminar datos de manera persistente.

El sistema utiliza una interfaz de consola para que el usuario pueda interactuar con las distintas funcionalidades mediante menús.

## Tecnologías utilizadas

- **Java:** lenguaje utilizado para desarrollar la lógica del sistema.
- **MySQL:** sistema gestor de bases de datos.
- **JDBC:** tecnología utilizada para establecer la conexión entre Java y MySQL.
- **XAMPP:** entorno utilizado para ejecutar el servidor MySQL.
- **Visual Studio Code:** entorno de desarrollo.

## Funcionalidades principales

### Administración de alumnos
- Registrar alumnos.
- Consultar alumnos por DNI.
- Listar alumnos.
- Modificar información.
- Eliminar registros.

### Administración de docentes
- Registrar docentes.
- Consultar docentes por DNI.
- Listar docentes.
- Modificar información.
- Eliminar docentes.

### Administración de choferes
- Registrar choferes.
- Consultar choferes por DNI.
- Listar choferes.
- Modificar información.
- Eliminar choferes.

### Gestión de salidas extracurriculares
- Crear salidas indicando destino y fecha.
- Asignar un docente y un chofer.
- Incorporar alumnos a una salida.
- Consultar las salidas registradas.
- Modificar información de las salidas.
- Eliminar salidas.
- Consultar los alumnos asociados a cada salida.

## Arquitectura y diseño

El proyecto utiliza una organización basada en el patrón DAO (Data Access Object), que permite separar las operaciones de acceso a datos de la lógica de la aplicación.

También incorpora una estructura inspirada en MVC (Modelo-Vista-Controlador) en el módulo de alumnos, separando las responsabilidades de representación, control y acceso a datos.

Se aplican conceptos fundamentales de la programación orientada a objetos, como el encapsulamiento, la herencia y la abstracción. El encapsulamiento se utiliza para proteger los atributos de las clases y controlar su acceso mediante métodos getters y setters. La herencia se implementa mediante la clase abstracta Persona, de la cual derivan Chofer y Docente, compartiendo atributos y comportamientos comunes. La abstracción permite representar características generales de las personas y reutilizarlas en las clases específicas del sistema.

## Base de datos

La base de datos utilizada se denomina `Escuela` y está desarrollada en MySQL.

Incluye las siguientes tablas:

- alumno
- docente
- chofer
- preceptora
- aula
- fichamedica
- salidaextracurricular
- alumno_salida

La relación entre alumnos y salidas extracurriculares se implementa mediante una tabla intermedia, permitiendo asociar varios alumnos a una misma salida.

## Requisitos para ejecutar el proyecto

- Java JDK.
- MySQL.
- XAMPP o un servidor MySQL equivalente.
- Visual Studio Code u otro entorno compatible con Java.
- MySQL Connector/J.

## Instalación y ejecución

1. Iniciar el servicio MySQL desde XAMPP.
2. Crear la base de datos importando el archivo `EscuelaDB.sql` desde phpMyAdmin, si se trata de una instalación nueva.
3. Verificar los parámetros de conexión configurados en `DatabaseConnection.java`.
4. Incorporar el controlador MySQL Connector/J al classpath del proyecto.
5. Compilar los archivos Java.
6. Ejecutar la clase principal `Seminario.java`.

**Importante:** si ya existe una base de datos con información, no es necesario volver a importarla para ejecutar el programa. El script de creación utiliza `IF NOT EXISTS` y no reemplaza la estructura de las tablas existentes.

## Autor

Proyecto académico desarrollado como parte de la asignatura Seminario, utilizando Java y MySQL.
