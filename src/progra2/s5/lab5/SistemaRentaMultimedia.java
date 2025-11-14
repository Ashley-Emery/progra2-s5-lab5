package progra2.s5.lab5;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.*;
import java.io.File;

public class SistemaRentaMultimedia extends JFrame{

    private static ArrayList<RentItem> listaItems = new ArrayList<>();

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception ex) {
            // ignorar
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


    private static void initMenuPrincipal() {
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



            String textoOpcion = JOptionPane.showInputDialog(null,
                    mensajeMenu,
                    "Menú Principal",
                    JOptionPane.QUESTION_MESSAGE);

            if (textoOpcion == null) {
                break;
            }

            try {
                opcionMenu = Integer.parseInt(textoOpcion);
            } catch (NumberFormatException ex) {
                opcionMenu = -1;
            }

            switch (opcionMenu) {
                case 1:
                    opcionAgregarItem();
                    break;
                case 2:
                    opcionRentarItem();
                    break;
                case 3:
                    opcionEjecutarSubmenu();
                    break;
                case 4:
                    opcionImprimirTodo();
                    break;
                case 0:
                    JOptionPane.showMessageDialog(null, "Saliendo del sistema.", "Salir", JOptionPane.INFORMATION_MESSAGE);
                    break;
                default:
                    JOptionPane.showMessageDialog(null,"Opción inválida.","Error",JOptionPane.ERROR_MESSAGE);
            }

        } while (opcionMenu != 0);
    }

    // --- a. Agregar Ítem ---
    private static void opcionAgregarItem() {
        String mensajeTipo = "¿Qué tipo de ítem desea agregar?\n" +
                             "1. Movie\n" +
                             "2. Game\n";
        
        String textoTipo = JOptionPane.showInputDialog(null, mensajeTipo, "Agregar Ítem", JOptionPane.QUESTION_MESSAGE);

        if (textoTipo == null) return;

        int tipoItem;
        try {
            tipoItem = Integer.parseInt(textoTipo);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Tipo inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String codigoIngresado = JOptionPane.showInputDialog(null, "Ingrese código del ítem:", "Agregar Ítem", JOptionPane.QUESTION_MESSAGE);

        if (codigoIngresado == null || codigoIngresado.trim().isEmpty()) {
            return;
        }

        if (buscarItemPorCodigo(codigoIngresado.trim()) != null) {
            JOptionPane.showMessageDialog(null,"El código ya existe. Debe ser único.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nombreIngresado = JOptionPane.showInputDialog(null, "Ingrese nombre del ítem:", "Agregar Ítem", JOptionPane.QUESTION_MESSAGE);

        if (nombreIngresado == null || nombreIngresado.trim().isEmpty()) {
            return;
        }

        RentItem nuevoItem = null;

        if (tipoItem == 1) {
            // Movie
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

            // Opcional: permitir modificar fecha de estreno
            int respuestaFecha = JOptionPane.showConfirmDialog(null, "¿Desea cambiar la fecha de estreno (por defecto es hoy)?", "Fecha de estreno",JOptionPane.YES_NO_OPTION);

            if (respuestaFecha == JOptionPane.YES_OPTION) {
                try {
                    String textoAnio = JOptionPane.showInputDialog(null, "Ingrese año de estreno:", "Fecha estreno", JOptionPane.QUESTION_MESSAGE);
                    if (textoAnio == null) return;

                    String textoMes = JOptionPane.showInputDialog(null, "Ingrese mes (1-12):", "Fecha estreno", JOptionPane.QUESTION_MESSAGE);
                    if (textoMes == null) return;

                    String textoDia = JOptionPane.showInputDialog(null, "Ingrese día:", "Fecha estreno", JOptionPane.QUESTION_MESSAGE);
                    if (textoDia == null) return;

                    int anio = Integer.parseInt(textoAnio);
                    int mes = Integer.parseInt(textoMes);
                    int dia = Integer.parseInt(textoDia);

                    nuevaPelicula.setFechaEstreno(anio, mes, dia);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Fecha inválida, se mantiene la fecha actual.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            }

            nuevoItem = nuevaPelicula;

        } else if (tipoItem == 2) {
            // Game
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

        // Cantidad de copias
        try {
            String textoCopias = JOptionPane.showInputDialog(null, "Ingrese cantidad de copias disponibles:", "Copias", JOptionPane.QUESTION_MESSAGE);
            if (textoCopias == null) return;
            int cantidadCopias = Integer.parseInt(textoCopias);
            if (cantidadCopias < 0) cantidadCopias = 0;
            nuevoItem.setCantidadCopias(cantidadCopias);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null,
                    "Cantidad inválida, se dejará en 0.",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
        }

        // Cargar imagen desde archivo
        ImageIcon iconoImagen = seleccionarImagenDesdeArchivo();
        if (iconoImagen != null) {
            iconoImagen = escalarImagen(iconoImagen, 150, 200);
            nuevoItem.setImagenItem(iconoImagen);
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

    // --- b. Rentar ---
    private static void opcionRentarItem() {
        if (listaItems.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "No hay ítems registrados.",
                    "Rentar",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
}

