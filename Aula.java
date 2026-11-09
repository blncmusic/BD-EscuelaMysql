/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package seminario;


public class Aula {
    protected int año;
    protected String division;
    protected Alumno[] alumnos;

    public Aula() {
    
    }

    public Aula(int año, String division, Alumno[] alumnos) {
        this.año = año;
        this.division = division;
        this.alumnos = alumnos;
    }

    public void agregarAlumno(Alumno alumno) {
        for (int i = 0; i < alumnos.length; i++) {
            if (alumnos[i] == null) {
                alumnos[i] = alumno;
                System.out.println("Alumno agregado correctamente");
                return;
            }
        }
        System.out.println("El aula está llena");
    }
}

         

         
