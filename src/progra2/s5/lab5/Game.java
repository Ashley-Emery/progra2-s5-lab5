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
    
    
}
