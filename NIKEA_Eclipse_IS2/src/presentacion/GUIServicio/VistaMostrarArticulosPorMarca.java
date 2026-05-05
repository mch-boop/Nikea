package presentacion.GUIServicio;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Collection;

import javax.swing.*;

import javax.swing.table.DefaultTableModel;

import negocio.servicio.TArticulo;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaMostrarArticulosPorMarca extends JDialog implements IGUI {

	// ATRIBUTOS 
	
    private JTable tablaArticulos;
    private DefaultTableModel modeloTabla;

    private JButton btnBuscar, btnCancelar;
    private JTextField txtIdMarca;
    
    // CONSTRUCTORA

    public VistaMostrarArticulosPorMarca() {
        super(null, "Artículos por Marca", ModalityType.APPLICATION_MODAL);
        setTitle("Artículos por Marca");
        initGUI();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    
    // INIT GUI

    private void initGUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // panel norte
        JPanel panelNorte = new JPanel();

        txtIdMarca = new JTextField(10);
        btnBuscar = new JButton("BUSCAR");

        panelNorte.add(new JLabel("ID Marca (0 para los artículos sin marca):"));
        panelNorte.add(txtIdMarca);
        panelNorte.add(btnBuscar);

        // tabla
        String[] columnas = {"ID", "NOMBRE", "DESCRIPCIÓN", "STOCK", "PRECIO"};

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaArticulos = new JTable(modeloTabla);
        tablaArticulos.getTableHeader().setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(tablaArticulos);
        scroll.setBorder(BorderFactory.createTitledBorder("Artículos de la Marca"));
        scroll.setPreferredSize(new Dimension(700, 400));

        // panel sur
        JPanel panelSur = new JPanel();
        btnCancelar = new JButton("SALIR");
        panelSur.add(btnCancelar);

        // acciones

        btnBuscar.addActionListener(e -> {
            try {
                if (txtIdMarca.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "ID obligatorio", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int id = Integer.parseInt(txtIdMarca.getText().trim());
                modeloTabla.setRowCount(0); // limpia antes de buscar
                Controlador.getInstance().accion(Eventos.MOSTRAR_ARTICULOS_POR_MARCA, id);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "ID inválido. Inserte un número positivo con el id de la marca, o cero, si quiere buscar los artículos sin marca de Nikea", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dispose());

        // montaje

        mainPanel.add(panelNorte, BorderLayout.NORTH);
        mainPanel.add(scroll, BorderLayout.CENTER);
        mainPanel.add(panelSur, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void actualizar(int evento, Object datos) {

        SwingUtilities.invokeLater(() -> {

            switch (evento) {

                case Eventos.RES_MOSTRAR_ARTICULOS_POR_MARCA_OK:
                    Collection<TArticulo> lista = (Collection<TArticulo>) datos;
                    modeloTabla.setRowCount(0);

                    for (TArticulo ta : lista) {
                        Object[] fila = {
                                ta.getId(),
                                ta.getNombre(),
                                ta.getDescripcion(),
                                ta.getStock(),
                                ta.getPrecioActual()
                        };
                        modeloTabla.addRow(fila);
                    }

                    this.setVisible(true);
                    break;

                case Eventos.RES_MOSTRAR_ARTICULOS_POR_MARCA_KO_NO_EXISTE_MARCA:
                    JOptionPane.showMessageDialog(this,
                            "La marca no existe o no está activa",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    break;
                
                case Eventos.RES_MOSTRAR_ARTICULOS_POR_MARCA_KO_NO_HAY_ARTICULOS:
                	JOptionPane.showMessageDialog(this,
                            "No hay artículos para esta marca",
                            "Información",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;

                case Eventos.RES_MOSTRAR_ARTICULOS_POR_MARCA_KO:
                    modeloTabla.setRowCount(0);
                    JOptionPane.showMessageDialog(this,
                            "Error al recuperar los artículos",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    break;
            }
        });
    }
}