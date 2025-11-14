/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progra2.s5.lab5;

/**
 *
 * @author ashley
 */

import javax.swing.ImageIcon;

public class RentItem {
    
    public String codigoItem;
    public String nombreItem;
    public double precioBaseRenta;
    public int cantidadCopias;
    public ImageIcon imagenItem;
    
    public RentItem(String codigoItem, String nombreItem, double precioBaseRenta){
        this.codigoItem = codigoItem;
        this.nombreItem = nombreItem;
        this.precioBaseRenta = precioBaseRenta;
        this.cantidadCopias = 0;
    }
    
    public String getCodigoItem(){
        return codigoItem; 
    }
    
    public String getNombreItem(){
        return nombreItem;
    }
    
    public double getPrecioBaseRenta(){
        return precioBaseRenta;
    }
    
    public int getCantidadCopias(){
        return cantidadCopias;
    }
    
    public ImageIcon getImagenItem(){
        return imagenItem;
    }
    
    
}
