package presentacion.GUIMarca;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.Collection;
import java.util.stream.Collectors;

import negocio.marca.TMarca;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaMostrarMarcas extends JFrame implements IGUI {
	
	// ATRIBUTOS

    private JTable tablaMarcas;
    private DefaultTableModel modeloTabla;
    private JButton btnCargar, btnLimpiar, btnCancelar;

    // CONSTRUCTORA

    public VistaMostrarMarcas() {
        setTitle("Listado de Marcas");
        initGUI();

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                modeloTabla.setRowCount(0);
            }
        });

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    // MÉTODOS

    private void initGUI() {

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // COLUMNAS
        String[] columnas = {"ID", "NOMBRE", "ESPECIALIDADES"};

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaMarcas = new JTable(modeloTabla);
        tablaMarcas.getTableHeader().setReorderingAllowed(false);

        // ajusto la columna de especialidades
        tablaMarcas.setRowHeight(25);

        JScrollPane scroll = new JScrollPane(tablaMarcas);
        scroll.setBorder(BorderFactory.createTitledBorder("Lista de Marcas Activas"));
        scroll.setPreferredSize(new Dimension(700, 400));
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // PANEL NORTE
        JPanel panelNorte = new JPanel();
        btnCargar = new JButton("MOSTRAR TODAS LAS MARCAS");
        panelNorte.add(btnCargar);

        // PANEL SUR
        JPanel panelSur = new JPanel();
        btnLimpiar = new JButton("LIMPIAR");
        btnCancelar = new JButton("CANCELAR");

        panelSur.add(btnLimpiar);
        panelSur.add(btnCancelar);

        // ACCIONES

        btnCargar.addActionListener(e -> {
            Controlador.getInstance().accion(Eventos.MOSTRAR_MARCAS, null);
        });

        btnLimpiar.addActionListener(e -> modeloTabla.setRowCount(0));

        btnCancelar.addActionListener(e -> {
            setVisible(false);
            dispose();
        });

        // MONTAJE

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

        switch (evento) {

            case Eventos.RES_MOSTRAR_MARCAS_OK:

                Collection<TMarca> lista = (Collection<TMarca>) datos;
                modeloTabla.setRowCount(0);

                if (lista == null || lista.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "No hay marcas registradas.",
                            "Información",
                            JOptionPane.INFORMATION_MESSAGE);
                    break;
                }

                for (TMarca tm : lista) {
                    // escribo las especialidades
                    String especialidades = "";

                    if (tm.getEspecialidades() != null && !tm.getEspecialidades().isEmpty()) {
                        especialidades = tm.getEspecialidades()
                                .stream()
                                .map(Object::toString)
                                .collect(Collectors.joining(", "));
                    }

                    Object[] fila = { tm.getId(), tm.getNombre(), especialidades };
                    modeloTabla.addRow(fila);
                }
                break;

            case Eventos.RES_MOSTRAR_MARCAS_KO:

                modeloTabla.setRowCount(0);
                JOptionPane.showMessageDialog(this,
                        "Error al recuperar las marcas.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                break;

            default:
            	System.err.println("Evento no reconocido en mostrar todos de Marca: " + evento);
                break;
        }
    }
}
