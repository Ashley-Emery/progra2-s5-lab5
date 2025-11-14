/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progra2.s5.lab5;

/**
 *
 * @author ashley
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.*;
import java.io.File;
import com.toedter.calendar.JCalendar;

public class SistemaRentaMultimedia extends JFrame {

    private static ArrayList<RentItem> listaItems = new ArrayList<>();

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception ex) {
        }

        SwingUtilities.invokeLater(() -> {
            new SistemaRentaMultimedia().setVisible(true);
        });
    }

    public SistemaRentaMultimedia() {
        setTitle("Menú Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        initMenuPrincipal();
    }

    private void initMenuPrincipal() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBackground(Color.WHITE);
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));

        JLabel etiquetaTitulo = new JLabel("MENU PRINCIPAL");
        etiquetaTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        etiquetaTitulo.setFont(new Font("SansSerif", Font.BOLD, 40));
        etiquetaTitulo.setForeground(new Color(0, 153, 255));

        panelPrincipal.add(Box.createVerticalStrut(40));
        panelPrincipal.add(etiquetaTitulo);
        panelPrincipal.add(Box.createVerticalStrut(60));

        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));

        JButton botonAgregar  = crearBotonMenu("AGREGAR");
        JButton botonRentar   = crearBotonMenu("RENTAR");
        JButton botonSubmenu  = crearBotonMenu("SUBMENU");
        JButton botonImprimir = crearBotonMenu("IMPRIMIR");

        botonAgregar.addActionListener(e -> opcionAgregarItem());
        botonRentar.addActionListener(e -> opcionRentarItem());
        botonSubmenu.addActionListener(e -> opcionEjecutarSubmenuGUI());
        botonImprimir.addActionListener(e -> opcionImprimirTodo());

        panelBotones.add(botonAgregar);
        panelBotones.add(Box.createVerticalStrut(20));
        panelBotones.add(botonRentar);
        panelBotones.add(Box.createVerticalStrut(20));
        panelBotones.add(botonSubmenu);
        panelBotones.add(Box.createVerticalStrut(20));
        panelBotones.add(botonImprimir);

        panelPrincipal.add(panelBotones);

        add(panelPrincipal);
    }

    private JButton crearBotonMenu(String texto) {
        JButton boton = new JButton(texto);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setPreferredSize(new Dimension(200, 50));
        boton.setMaximumSize(new Dimension(200, 50));
        boton.setFocusPainted(false);
        boton.setBackground(new Color(200, 200, 200));
        boton.setFont(new Font("SansSerif", Font.BOLD, 16));
        return boton;
    }

    private static void opcionAgregarItem() {
        String mensajeTipo = "¿Qué tipo de ítem desea agregar?\n" +
                             "1. Movie\n" +
                             "2. Game\n";
        String textoTipo = JOptionPane.showInputDialog(null,
                mensajeTipo,
                "Agregar Ítem",
                JOptionPane.QUESTION_MESSAGE);

        if (textoTipo == null) return;

        int tipoItem;
        try {
            tipoItem = Integer.parseInt(textoTipo);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null,
                    "Tipo inválido.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String codigoIngresado = JOptionPane.showInputDialog(null,
                "Ingrese código del ítem:",
                "Agregar Ítem",
                JOptionPane.QUESTION_MESSAGE);

        if (codigoIngresado == null || codigoIngresado.trim().isEmpty()) {
            return;
        }

        if (buscarItemPorCodigo(codigoIngresado.trim()) != null) {
            JOptionPane.showMessageDialog(null,
                    "El código ya existe. Debe ser único.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nombreIngresado = JOptionPane.showInputDialog(null,
                "Ingrese nombre del ítem:",
                "Agregar Ítem",
                JOptionPane.QUESTION_MESSAGE);

        if (nombreIngresado == null || nombreIngresado.trim().isEmpty()) {
            return;
        }

        RentItem nuevoItem = null;

        if (tipoItem == 1) {
            double precioBase;
            try {
                String textoPrecio = JOptionPane.showInputDialog(null,
                        "Ingrese precio base de renta (Lps por día):",
                        "Agregar Movie",
                        JOptionPane.QUESTION_MESSAGE);
                if (textoPrecio == null) return;
                precioBase = Double.parseDouble(textoPrecio);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Precio inválido.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Movie nuevaPelicula = new Movie(codigoIngresado.trim(),
                                            nombreIngresado.trim(),
                                            precioBase);

            int respuestaFecha = JOptionPane.showConfirmDialog(null,
                    "¿Desea cambiar la fecha de estreno (por defecto es hoy)?",
                    "Fecha de estreno",
                    JOptionPane.YES_NO_OPTION);

            if (respuestaFecha == JOptionPane.YES_OPTION) {
                Calendar fechaSeleccionada = pedirFechaConJCalendar();
                if (fechaSeleccionada != null) {
                    int anio = fechaSeleccionada.get(Calendar.YEAR);
                    int mes = fechaSeleccionada.get(Calendar.MONTH) + 1;
                    int dia = fechaSeleccionada.get(Calendar.DAY_OF_MONTH);
                    nuevaPelicula.setFechaEstreno(anio, mes, dia);
                }
            }

            nuevoItem = nuevaPelicula;

        } else if (tipoItem == 2) {
            Game nuevoJuego = new Game(codigoIngresado.trim(),
                                       nombreIngresado.trim());
            nuevoItem = nuevoJuego;

        } else {
            JOptionPane.showMessageDialog(null,
                    "Tipo de ítem no reconocido.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String textoCopias = JOptionPane.showInputDialog(null,
                    "Ingrese cantidad de copias disponibles:",
                    "Copias",
                    JOptionPane.QUESTION_MESSAGE);
            if (textoCopias == null) return;
            int cantidadCopias = Integer.parseInt(textoCopias);
            if (cantidadCopias < 0) cantidadCopias = 0;
            nuevoItem.cantidadCopias = cantidadCopias;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null,
                    "Cantidad inválida, se dejará en 0.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
        }

        ImageIcon iconoImagen = seleccionarImagenDesdeArchivo();
        if (iconoImagen != null) {
            iconoImagen = escalarImagen(iconoImagen, 250, 250);
            nuevoItem.setImageItem(iconoImagen);
        }

        listaItems.add(nuevoItem);

        JOptionPane.showMessageDialog(null,
                "Ítem agregado correctamente.",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private static ImageIcon seleccionarImagenDesdeArchivo() {
        JFileChooser selectorArchivos = new JFileChooser();
        int resultado = selectorArchivos.showOpenDialog(null);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = selectorArchivos.getSelectedFile();
            return new ImageIcon(archivoSeleccionado.getAbsolutePath());
        }
        return null;
    }

    private static Calendar pedirFechaConJCalendar() {
        JCalendar calendario = new JCalendar();

        int opcion = JOptionPane.showConfirmDialog(
                null,
                calendario,
                "Seleccione una fecha",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (opcion == JOptionPane.OK_OPTION) {
            Calendar fecha = Calendar.getInstance();
            fecha.setTime(calendario.getDate());
            return fecha;
        }

        return null;
    }

    private static ImageIcon escalarImagen(ImageIcon iconoOriginal, int ancho, int alto) {
        if (iconoOriginal == null) return null;
        Image imagenOriginal = iconoOriginal.getImage();
        Image imagenEscalada = imagenOriginal.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenEscalada);
    }

    private static RentItem buscarItemPorCodigo(String codigoBuscado) {
        for (RentItem itemActual : listaItems) {
            if (itemActual.getCodigoItem().equalsIgnoreCase(codigoBuscado)) {
                return itemActual;
            }
        }
        return null;
    }

    private static void opcionRentarItem() {
        if (listaItems.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "No hay ítems registrados.",
                    "Rentar",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String codigoBuscado = JOptionPane.showInputDialog(null,
                "Ingrese el código del ítem a rentar:",
                "Rentar",
                JOptionPane.QUESTION_MESSAGE);

        if (codigoBuscado == null || codigoBuscado.trim().isEmpty()) {
            return;
        }

        RentItem itemEncontrado = buscarItemPorCodigo(codigoBuscado.trim());

        if (itemEncontrado == null) {
            JOptionPane.showMessageDialog(null,
                    "Item No Existe",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        mostrarVentanaRenta(itemEncontrado);
    }

    private static void mostrarVentanaRenta(RentItem item) {
        JFrame ventanaRenta = new JFrame("Renta");
        ventanaRenta.setSize(600, 500);
        ventanaRenta.setLocationRelativeTo(null);
        ventanaRenta.setResizable(false);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBackground(Color.WHITE);
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));

        JLabel etiquetaTitulo = new JLabel("RENTA");
        etiquetaTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        etiquetaTitulo.setFont(new Font("SansSerif", Font.BOLD, 40));
        etiquetaTitulo.setForeground(new Color(0, 153, 255));

        panelPrincipal.add(Box.createVerticalStrut(30));
        panelPrincipal.add(etiquetaTitulo);
        panelPrincipal.add(Box.createVerticalStrut(30));

        JLabel etiquetaImagen = new JLabel("IMAGEN A INSERTAR", SwingConstants.CENTER);
        etiquetaImagen.setOpaque(true);
        etiquetaImagen.setBackground(new Color(200, 200, 200));
        etiquetaImagen.setPreferredSize(new Dimension(250, 250));
        etiquetaImagen.setMaximumSize(new Dimension(250, 250));

        if (item.getImagenItem() != null) {
            etiquetaImagen.setText("");
            etiquetaImagen.setIcon(escalarImagen(item.getImagenItem(), 250, 250));
        }

        JPanel panelImagen = new JPanel();
        panelImagen.setOpaque(false);
        panelImagen.add(etiquetaImagen);

        panelPrincipal.add(panelImagen);

        JPanel panelInferior = new JPanel();
        panelInferior.setOpaque(false);
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));

        JLabel etiquetaDias = new JLabel("CANTIDAD DE DIAS:");
        etiquetaDias.setAlignmentX(Component.CENTER_ALIGNMENT);
        etiquetaDias.setFont(new Font("SansSerif", Font.BOLD, 18));

        JTextField campoDias = new JTextField(5);
        campoDias.setMaximumSize(new Dimension(100, 30));
        campoDias.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton botonRentar = new JButton("RENTAR");
        botonRentar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonRentar.setPreferredSize(new Dimension(150, 40));
        botonRentar.setMaximumSize(new Dimension(150, 40));
        botonRentar.setBackground(new Color(200, 200, 200));
        botonRentar.setFont(new Font("SansSerif", Font.BOLD, 16));

        botonRentar.addActionListener(e -> {
            try {
                int dias = Integer.parseInt(campoDias.getText());
                double montoTotal = item.pagoRenta(dias);
                JOptionPane.showMessageDialog(ventanaRenta,
                        "Monto total de la renta: " + montoTotal + " Lps.",
                        "Total",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ventanaRenta,
                        "Cantidad de días inválida.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        panelInferior.add(Box.createVerticalStrut(10));
        panelInferior.add(etiquetaDias);
        panelInferior.add(Box.createVerticalStrut(5));
        panelInferior.add(campoDias);
        panelInferior.add(Box.createVerticalStrut(15));
        panelInferior.add(botonRentar);

        panelPrincipal.add(panelInferior);

        ventanaRenta.add(panelPrincipal);
        ventanaRenta.setVisible(true);
    }

    private static void opcionEjecutarSubmenuGUI() {
        if (listaItems.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "No hay ítems registrados.",
                    "Submenú",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String codigoBuscado = JOptionPane.showInputDialog(null,
                "Ingrese el código del ítem para submenú:",
                "Submenú",
                JOptionPane.QUESTION_MESSAGE);

        if (codigoBuscado == null || codigoBuscado.trim().isEmpty()) {
            return;
        }

        RentItem itemEncontrado = buscarItemPorCodigo(codigoBuscado.trim());

        if (itemEncontrado == null) {
            JOptionPane.showMessageDialog(null,
                    "Item No Existe",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!(itemEncontrado instanceof Game)) {
            JOptionPane.showMessageDialog(null,
                    "Este ítem no tiene submenú (solo Game).",
                    "Sin submenú",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Game juego = (Game) itemEncontrado;
        mostrarVentanaSubmenu(juego);
    }

    private static void mostrarVentanaSubmenu(Game juego) {
        JFrame ventanaSubmenu = new JFrame("Submenú Game");
        ventanaSubmenu.setSize(700, 450);
        ventanaSubmenu.setLocationRelativeTo(null);
        ventanaSubmenu.setResizable(false);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBackground(Color.WHITE);
        panelPrincipal.setLayout(new BorderLayout());

        JLabel etiquetaTitulo = new JLabel("SUBMENU", SwingConstants.CENTER);
        etiquetaTitulo.setFont(new Font("SansSerif", Font.BOLD, 40));
        etiquetaTitulo.setForeground(new Color(0, 153, 255));

        panelPrincipal.add(etiquetaTitulo, BorderLayout.NORTH);

        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.setOpaque(false);

        JLabel etiquetaImagen = new JLabel("IMAGEN A INSERTAR", SwingConstants.CENTER);
        etiquetaImagen.setOpaque(true);
        etiquetaImagen.setBackground(new Color(200, 200, 200));
        etiquetaImagen.setPreferredSize(new Dimension(300, 250));

        if (juego.getImagenItem() != null) {
            etiquetaImagen.setText("");
            etiquetaImagen.setIcon(escalarImagen(juego.getImagenItem(), 300, 250));
        }

        panelCentro.add(etiquetaImagen, BorderLayout.WEST);

        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.setLayout(new GridLayout(3, 1, 10, 10));

        JButton botonFecha = new JButton("ACTUALIZAR FECHA");
        JButton botonAgregarSpecs = new JButton("AGREGAR SPECS");
        JButton botonVerSpecs = new JButton("VER SPECS");

        JButton[] botones = { botonFecha, botonAgregarSpecs, botonVerSpecs };
        for (JButton b : botones) {
            b.setBackground(new Color(200, 200, 200));
            b.setFont(new Font("SansSerif", Font.BOLD, 14));
        }

        botonFecha.addActionListener(e -> juego.ejecutarOpcion(1));
        botonAgregarSpecs.addActionListener(e -> juego.ejecutarOpcion(2));
        botonVerSpecs.addActionListener(e -> juego.ejecutarOpcion(3));

        panelBotones.add(botonFecha);
        panelBotones.add(botonAgregarSpecs);
        panelBotones.add(botonVerSpecs);

        panelCentro.add(panelBotones, BorderLayout.EAST);

        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        ventanaSubmenu.add(panelPrincipal);
        ventanaSubmenu.setVisible(true);
    }

    private static void opcionImprimirTodo() {
        if (listaItems.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "No hay ítems registrados.",
                    "Imprimir Todo",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JFrame ventanaListado = new JFrame("Listado de Ítems");
        ventanaListado.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelTarjetas = new JPanel();
        panelTarjetas.setBackground(Color.WHITE);
        panelTarjetas.setLayout(new GridLayout(0, 2, 10, 10));

        for (RentItem itemActual : listaItems) {
            JPanel tarjeta = new JPanel();
            tarjeta.setLayout(new BorderLayout());
            tarjeta.setBackground(new Color(245, 245, 245));

            String textoPrincipal = "Nombre: " + itemActual.getNombreItem() + "\n" +
                                    "Precio renta: " + itemActual.getPrecioBaseRenta() + " Lps/día\n";

            if (itemActual instanceof Movie) {
                Movie peli = (Movie) itemActual;
                textoPrincipal += "Estado: " + peli.getEstado() + "\n";
            }

            JTextArea areaTexto = new JTextArea(textoPrincipal);
            areaTexto.setEditable(false);
            areaTexto.setOpaque(false);

            tarjeta.add(areaTexto, BorderLayout.CENTER);

            if (itemActual.getImagenItem() != null) {
                JLabel etiquetaImagen = new JLabel(escalarImagen(itemActual.getImagenItem(), 150, 180));
                tarjeta.add(etiquetaImagen, BorderLayout.EAST);
            }

            panelTarjetas.add(tarjeta);
        }

        JScrollPane scroll = new JScrollPane(panelTarjetas);
        ventanaListado.add(scroll);

        ventanaListado.setSize(800, 600);
        ventanaListado.setLocationRelativeTo(null);
        ventanaListado.setVisible(true);
    }
}