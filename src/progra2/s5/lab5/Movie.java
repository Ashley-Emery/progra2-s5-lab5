package progra2.s5.lab5;

/**
 *
 * @author gaat1
 */
import java.util.Calendar;

public class Movie extends RentItem {

    private Calendar fechaEstreno;

    public Movie(String codigoItem, String nombreItem, double precioBaseRenta) {
        super(codigoItem, nombreItem, precioBaseRenta);
        this.fechaEstreno = Calendar.getInstance(); 
    }

    public Calendar getFechaEstreno() {
        return fechaEstreno;
    }

    public void setFechaEstreno(int anio, int mes, int dia) {
        Calendar fechaNueva = Calendar.getInstance();
        fechaNueva.set(anio, mes - 1, dia);
        this.fechaEstreno = fechaNueva;
    }

    public String getEstado() {
        Calendar fechaActual = Calendar.getInstance();

        int anioActual = fechaActual.get(Calendar.YEAR);
        int mesActual = fechaActual.get(Calendar.MONTH);

        int anioEstreno = fechaEstreno.get(Calendar.YEAR);
        int mesEstreno = fechaEstreno.get(Calendar.MONTH);

        int diferenciaAnios = anioActual - anioEstreno;
        int diferenciaMeses = diferenciaAnios * 12 + (mesActual - mesEstreno);

        if (diferenciaMeses <= 3 && diferenciaMeses >= 0) {
            return "ESTRENO";
        } else {
            return "NORMAL";
        }
    }

    @Override
    public String toString() {
        return super.toString() +
               ", Estado: " + getEstado() +
               " - Movie";
    }

    @Override
    public double pagoRenta(int dias) {
        if (dias <= 0) {
            return 0;
        }

        double montoBase = getPrecioBaseRenta() * dias;
        double recargo = 0;

        String estado = getEstado();

        if ("ESTRENO".equals(estado)) {
            if (dias > 2) {
                int diasAdicionales = dias - 2;
                recargo = diasAdicionales * 50;
            }
        } else { 
            if (dias > 5) {
                int diasAdicionales = dias - 5;
                recargo = diasAdicionales * 30;
            }
        }

        return montoBase + recargo;
    }
}
