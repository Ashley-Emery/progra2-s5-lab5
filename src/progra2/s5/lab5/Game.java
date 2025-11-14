/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progra2.s5.lab5;

/**
 *
 * @author ashley
 */

import java.util.Calendar;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Game extends RentItem implements MenuActions {
    
    public Calendar fechaPublicacion;
    public ArrayList<String> listaEspecificaciones;
    
    public Game(String codigoItem, String nombreItem){
        
        super(codigoItem, nombreItem, 20.0);
        this.fechaPublicacion = Calendar.getInstance();
        this.listaEspecificaciones = new ArrayList<>();
        
    }
    
    public Calendar getFechaPublicacion(){
        return fechaPublicacion;
    }
    
    public void setFechaPublicacion(int anio, int mes, int dia){
        
        Calendar fechaNueva = Calendar.getInstance();
        fechaNueva.set(anio, mes -1, dia);
        
        this.fechaPublicacion = fechaNueva;
    }
    
    public String toString(){
        
        int anio = fechaPublicacion.get(Calendar.YEAR);
        int mes = fechaPublicacion.get(Calendar.MONTH) + 1;
        int dia = fechaPublicacion.get(Calendar.DAY_OF_MONTH);
        
        return super.toString() + ", Fecha publicacion: " + dia +
                "/" + mes + "/" + anio + " - PS3 Game";
    }
    
    public double pagoRenta(int dias){
        
        if (dias <= 0) {
            return 0;
        }
        return getPrecioBaseRenta() * dias;
    }
    
    public void listarEspecificacionesRecursivo(int indiceActual, StringBuilder texto){
        
        if (indiceActual >= listaEspecificaciones.size()) {
            return;
        }
        texto.append((indiceActual + 1))
             .append(". ")
             .append(listaEspecificaciones.get(indiceActual))
             .append("\n");
        listarEspecificacionesRecursivo(indiceActual + 1, texto);
    }
    
    public void listEspecificaciones() {
        
        if (listaEspecificaciones.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "No hay especificaciones registradas.",
                    "Especificaciones",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder texto = new StringBuilder();
        listarEspecificacionesRecursivo(0, texto);

        JOptionPane.showMessageDialog(null,
                texto.toString(),
                "Especificaciones",
                JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void actualizarFechaPublicacionDesdeGUI() {
        try {
            String textoAnio = JOptionPane.showInputDialog(null,
                    "Ingrese año de publicación:",
                    "Fecha publicación",
                    JOptionPane.QUESTION_MESSAGE);
            if (textoAnio == null) return;

            String textoMes = JOptionPane.showInputDialog(null,
                    "Ingrese mes (1-12):",
                    "Fecha publicación",
                    JOptionPane.QUESTION_MESSAGE);
            if (textoMes == null) return;

            String textoDia = JOptionPane.showInputDialog(null,
                    "Ingrese día:",
                    "Fecha publicación",
                    JOptionPane.QUESTION_MESSAGE);
            if (textoDia == null) return;

            int anio = Integer.parseInt(textoAnio);
            int mes = Integer.parseInt(textoMes);
            int dia = Integer.parseInt(textoDia);

            setFechaPublicacion(anio, mes, dia);

            JOptionPane.showMessageDialog(null,
                    "Fecha de publicación actualizada.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null,
                    "Datos inválidos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void agregarEspecificacionDesdeGUI() {
        String textoEspecificacion = JOptionPane.showInputDialog(null,
                "Ingrese especificación técnica:",
                "Agregar especificación",
                JOptionPane.QUESTION_MESSAGE);

        if (textoEspecificacion != null && !textoEspecificacion.trim().isEmpty()) {
            listaEspecificaciones.add(textoEspecificacion.trim());
            JOptionPane.showMessageDialog(null,
                    "Especificación agregada.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                actualizarFechaPublicacionDesdeGUI();
                break;
            case 2:
                agregarEspecificacionDesdeGUI();
                break;
            case 3:
                listEspecificaciones();
                break;
            case 0:
                break;
            default:
                JOptionPane.showMessageDialog(null,
                        "Opción no válida.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void submenu() {
        int opcionSubmenu = -1;

        do {
            String mensajeMenu = "Submenú Game (código: " + getCodigoItem() + ")\n" +
                                 "1. Actualizar fecha de publicación\n" +
                                 "2. Agregar especificación\n" +
                                 "3. Ver especificaciones\n" +
                                 "0. Regresar\n\n" +
                                 "Ingrese opción:";

            String textoOpcion = JOptionPane.showInputDialog(null,
                    mensajeMenu,
                    "Submenú Game",
                    JOptionPane.QUESTION_MESSAGE);

            if (textoOpcion == null) {

                break;
            }

            try {
                opcionSubmenu = Integer.parseInt(textoOpcion);
                ejecutarOpcion(opcionSubmenu);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Opción inválida.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } while (opcionSubmenu != 0);
    }
    
    
    
}
