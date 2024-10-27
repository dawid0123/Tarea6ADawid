package Tarea6Dawid;

import java.io.*;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class GestionAlumnos {
    private static File fichero;
    private static ArrayList<Alumno> alumnos = new ArrayList<>();

    private static void generarFichero() {
        fichero = new File("alumnos.dat");
        if (fichero.exists()) {
            System.out.println("Sobreescribiendo");
        }
        guardarAlumnos();
    }

    private static void seleccionarFichero(Scanner sc) {
        System.out.print("Dime el nombre");
        String nombreFichero = sc.nextLine();
        fichero = new File(nombreFichero);
        if (fichero.exists()) {
            cargarAlumnos();
        } else {
            System.out.println("No hay fichero");
        }
    }

    private static void cargarAlumno(Scanner sc) {
        if (fichero == null) {
            System.out.println("Dime un fichero");
            return;
        }
        try {
            System.out.print("NIA: ");
            int nia = sc.nextInt();
            sc.nextLine();
            System.out.print("Nombre: ");
            String nombre = sc.nextLine();
            System.out.print("Apellidos: ");
            String apellidos = sc.nextLine();
            System.out.print("Genero (M/F): ");
            char genero = sc.nextLine().charAt(0);
            System.out.print("Fecha de Nacimiento (dd/MM/yyyy): ");
            String fechaStr = sc.nextLine();
            Date fechaNacimiento = new SimpleDateFormat("dd/MM/yyyy").parse(fechaStr);
            System.out.print("Ciclo: ");
            String ciclo = sc.nextLine();
            System.out.print("Curso: ");
            String curso = sc.nextLine();
            System.out.print("Grupo: ");
            String grupo = sc.nextLine();

            Alumno alumno = new Alumno(nia, nombre, apellidos, genero, fechaNacimiento, ciclo, curso, grupo);
            alumnos.add(alumno);
            guardarAlumnos();
            
        } catch (ParseException e) {
            System.out.println("Fecha mal puesta");
        }
    }

    private static void mostrarAlumnos() {
        if (fichero == null || alumnos.isEmpty()) {
            System.out.println("No hay alumnos");
            return;
        }
        System.out.println("Lista de alumnos:");
        for (Alumno alumno : alumnos) {
            System.out.println(alumno);
        }
    }

    private static void guardarAlumnos() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fichero))) {
            out.writeObject(alumnos);
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    private static void cargarAlumnos() {
        if (fichero != null && fichero.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fichero))) {
                alumnos = (ArrayList<Alumno>) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error");
            }
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;
        
        while (!salir) {
            System.out.println("\nMenú:");
            System.out.println("1. Generar fichero vacío");
            System.out.println("2. Seleccionar fichero existente");
            System.out.println("3. Cargar alumno");
            System.out.println("4. Mostrar todos los alumnos");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = Integer.parseInt(scanner.nextLine());
            
            switch (opcion) {
                case 1:
                    generarFichero();
                    break;
                case 2:
                    seleccionarFichero(scanner);
                    break;
                case 3:
                    cargarAlumno(scanner);
                    break;
                case 4:
                    mostrarAlumnos();
                    break;
                case 5:
                    salir = true;
                    break;
                default:
                    System.out.println("No valido");
            }
        }
        scanner.close();
    }
}

class Alumno implements Serializable {
    private int nia;
    private String nombre;
    private String apellidos;
    private char genero;
    private Date fechaNacimiento;
    private String ciclo;
    private String curso;
    private String grupo;
    
    public Alumno(int nia, String nombre, String apellidos, char genero, Date fechaNacimiento, String ciclo, String curso, String grupo) {
        this.nia = nia;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.genero = genero;
        this.fechaNacimiento = fechaNacimiento;
        this.ciclo = ciclo;
        this.curso = curso;
        this.grupo = grupo;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return "NIA: " + nia + ", Nombre: " + nombre + ", Apellidos: " + apellidos + 
               ", Genero: " + genero + ", Fecha de Nacimiento: " + sdf.format(fechaNacimiento) + 
               ", Ciclo: " + ciclo + ", Curso: " + curso + ", Grupo: " + grupo;
    }
}