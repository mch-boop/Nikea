package presentacion.GUIMarca;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.List;

import negocio.marca.TMarca;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaMostrarMejorMarca extends JFrame implements IGUI {

	// ATRIBUTOS

    private JTable tabla;
    private DefaultTableModel modelo;
    private JButton btnCargar, btnCancelar;
    private JTextField txtTitulo;
    private JTextArea txtDescripcion;

    // CONSTRUCTORA

    public VistaMostrarMejorMarca() {
        setTitle("Ranking de Marcas");
        initGUI();
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                limpiar();
            }
        });
    }

    // MÉTODOS

    private void initGUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(10,10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        // titulo
        txtTitulo = new JTextField("RANKING DE MARCAS");
        txtTitulo.setEditable(false);
        txtTitulo.setHorizontalAlignment(JTextField.CENTER);
        txtTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        
        // descripción del top
        txtDescripcion = new JTextArea(
                "\nCRITERIOS PARA EL RANKING:\n\n" + 
                "     - En esta ventana puedes consultar el ranking de las marcas con mayores ventas.\n" +
                "     - Pulsa el botón para cargar las 5 marcas con mejor rendimiento.\n"
            );
        txtDescripcion.setEditable(false);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setBackground(getBackground());
        txtDescripcion.setFont(new Font("Arial", Font.PLAIN, 12));

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(txtTitulo, BorderLayout.NORTH);
        panelSuperior.add(txtDescripcion, BorderLayout.CENTER);
        
        // tabla
        String[] columnas = {"POSICIÓN", "ID", "NOMBRE"};
        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(500, 300));

        // botones
        JPanel panelBotones = new JPanel();

        btnCargar = new JButton("MOSTRAR TOP 5");
        btnCancelar = new JButton("CANCELAR");

        panelBotones.add(btnCargar);
        panelBotones.add(btnCancelar);

        // acciones
        btnCargar.addActionListener(e ->
            Controlador.getInstance().accion(Eventos.MOSTRAR_RANKING_MARCA, null)
        );
        btnCancelar.addActionListener(e -> {
        	limpiar();
            setVisible(false);
        });

        // formato
        mainPanel.add(panelSuperior, BorderLayout.NORTH);
        mainPanel.add(scroll, BorderLayout.CENTER);
        mainPanel.add(panelBotones, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }

    // ACTUALIZAR

    @SuppressWarnings("unchecked")
    @Override
    public void actualizar(int evento, Object datos) {

        SwingUtilities.invokeLater(() -> {
            switch (evento) {
                case Eventos.RES_MOSTRAR_RANKING_MARCA_OK:
                    List<TMarca> lista = (List<TMarca>) datos;
                    modelo.setRowCount(0);

                    if (lista == null || lista.isEmpty()) {
                        JOptionPane.showMessageDialog(this,
                                "No hay marcas registradas en el sistema.",
                                "Información",
                                JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    
                    // MENSAJE SEGÚN CASO
                    if (lista.size() == 5) {
                    	txtTitulo.setText("TOP 5 MARCAS CON MEJORES VENTAS DE NIKEA");
                    } else if (lista.size() == 0) {
                    	txtTitulo.setText("Ninguna Marca activa ha tenido ventas en NIKEA");
                    } else {
                    	txtTitulo.setText("TOP " + lista.size() + " MARCAS CON MEJORES VENTAS DE NIKEA\n (no hay suficientes marcas como para hacer un top 5)");
                    }

                    // RELLENAR TABLA
                    for (int i = 0; i < lista.size(); i++) {
                        TMarca m = lista.get(i);
                        Object[] fila = {
                            (i + 1) + " PUESTO",
                            m.getId(),
                            m.getNombre()
                        };
                        modelo.addRow(fila);
                    }
                    break;

                case Eventos.RES_MOSTRAR_RANKING_MARCA_KO:
                    modelo.setRowCount(0);
                    txtTitulo.setText("ERROR AL CARGAR RANKING");
                    
                    JOptionPane.showMessageDialog(this,
                            "Error al obtener el ranking de marcas.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    break;
            }
        });
    }

    private void limpiar() {
        modelo.setRowCount(0);
        txtTitulo.setText("Ranking de marcas (sin cargar)");
    }
}
