package presentacion.GUIServicio;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import negocio.servicio.TServicio;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaMostrarServicios extends JFrame implements IGUI {

    private JTable tablaServicios;
    private DefaultTableModel modeloTabla;
    private JButton btnCancelar;

    public VistaMostrarServicios() {
        setTitle("Listado General de Servicios");
        initGUI();
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent e) {
                cargarServicios();
            }

            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                modeloTabla.setRowCount(0);
            }
        });
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    private void initGUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] columnas = {"ID", "NOMBRE", "DESCRIPCIÓN", "STOCK", "PRECIO", "TIPO"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaServicios = new JTable(modeloTabla);
        tablaServicios.getTableHeader().setReorderingAllowed(false);

        JScrollPane scroll = new JScrollPane(tablaServicios);
        scroll.setBorder(BorderFactory.createTitledBorder("Lista de Servicios Activos"));
        scroll.setPreferredSize(new Dimension(750, 400));
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JPanel panelSur = new JPanel();
        btnCancelar = new JButton("CANCELAR");
        panelSur.add(btnCancelar);

        btnCancelar.addActionListener(e -> {
            setVisible(false);
            dispose();
        });

        mainPanel.add(scroll, BorderLayout.CENTER);
        mainPanel.add(panelSur, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }

    private void cargarServicios() {
        Controlador.getInstance().accion(Eventos.MOSTRAR_SERVICIOS, null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void actualizar(int evento, Object datos) {
        switch (evento) {
            case Eventos.RES_MOSTRAR_SERVICIOS_OK:
                Collection<TServicio> lista = (Collection<TServicio>) datos;
                modeloTabla.setRowCount(0);

                if (lista.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No hay servicios registrados en el sistema.", "Información", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    boolean hayActivos = false;
                    for (TServicio ts : lista) {
                        if (ts.isActivo()) {
                            Object[] fila = {
                                ts.getId(),
                                ts.getNombre(),
                                ts.getDescripcion(),
                                ts.getStock(),
                                ts.getPrecioActual(),
                                ts.getTipo() != null && ts.getTipo() == 1 ? "Artículo" : "Montaje"
                            };
                            modeloTabla.addRow(fila);
                            hayActivos = true;
                        }
                    }

                    if (!hayActivos) {
                        JOptionPane.showMessageDialog(this, "No hay servicios activos para mostrar.", "Información", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                break;

            case Eventos.RES_MOSTRAR_SERVICIOS_KO:
                modeloTabla.setRowCount(0);
                JOptionPane.showMessageDialog(this, "Error al recuperar la lista de servicios.", "Error", JOptionPane.ERROR_MESSAGE);
                break;

            default:
                break;
        }
    }
}
