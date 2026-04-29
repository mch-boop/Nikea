package presentacion.GUIDescuento;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import negocio.descuento.TDescuento;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaMostrarDescuentos extends JFrame implements IGUI {

    private JTable tabla;
    private DefaultTableModel modelo;

    public VistaMostrarDescuentos() {
        setTitle("Lista de Descuentos Activos");
        initGUI();
    }

    private void initGUI() {
        setLayout(new BorderLayout(10, 10));

        String[] columnas = {"ID", "Código", "Nombre/Desc.", "Porcentaje (%)", "Tipo", "Mínimo/Cant."};
        
        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        tabla = new JTable(modelo);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabla.getTableHeader().setReorderingAllowed(false);

        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoton.add(btnCerrar);
        add(panelBoton, BorderLayout.SOUTH);

        setSize(700, 350); 
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void cargarTabla(Collection<TDescuento> lista) {
        modelo.setRowCount(0); 
        for (TDescuento td : lista) {
            if (td.isActivo()) {
                String condicion = td.isTipo() 
                    ? td.getCantidad() + "€" 
                    : (int)td.getCantidad() + " uds";

                modelo.addRow(new Object[]{
                    td.getId(),
                    td.getCodigo(),
                    td.getNombre(),
                    td.getPorcentaje() + "%",
                    td.isTipo() ? "Por importe" : "Por cantidad",
                    condicion 
                });
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void actualizar(int evento, Object datos) {
        SwingUtilities.invokeLater(() -> {
            switch (evento) {
                case Eventos.RES_MOSTRAR_DESCUENTOS:
                    cargarTabla((Collection<TDescuento>) datos);
                    setVisible(true);
                    break;
                case Eventos.RES_MOSTRAR_DESCUENTOS_KO:
                    JOptionPane.showMessageDialog(this, "No se pudieron cargar los descuentos.", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
                default:
                    break;
            }
        });
    }
}