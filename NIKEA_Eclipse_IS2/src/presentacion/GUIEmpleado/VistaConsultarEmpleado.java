package presentacion.GUIEmpleado;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import negocio.empleado.TEmpleado;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

public class VistaConsultarEmpleado extends JFrame implements IGUI {

	// ATRIBUTOS
	
    private JTextField txtId;
    private JTextArea areaDetalles;
    private JButton btnConsultar, btnLimpiar, btnCancelar;

    // CONSTRUCTORA
    
    public VistaConsultarEmpleado() {
        setTitle("Consultar Empleado por ID");
        initGUI();
    }

    
    // MÉTODOS
    
    private void initGUI() {
    	
    	// Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtId = new JTextField(10);
        btnConsultar = new JButton("CONSULTAR");
        panelBusqueda.add(new JLabel("ID Empleado:"));
        panelBusqueda.add(txtId);
        panelBusqueda.add(btnConsultar);

        // Área de visualización (no editable)
        areaDetalles = new JTextArea(10, 30);
        areaDetalles.setEditable(false);
        areaDetalles.setBorder(BorderFactory.createTitledBorder("Detalles del Empleado"));
        JScrollPane scroll = new JScrollPane(areaDetalles);

        // Panel de botones inferiores
        JPanel panelBotones = new JPanel();
        btnLimpiar = new JButton("LIMPIAR");
        btnCancelar = new JButton("CANCELAR");
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnCancelar);

        // Lógica de Consulta
        btnConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String textoId = txtId.getText();
                    if (textoId.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Debe introducir un ID.");
                    } else {
                        int id = Integer.parseInt(textoId);
                        Controlador.getInstance().accion(Eventos.BUSCAR_EMPLEADO, id);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "El ID debe ser un número entero.");
                    txtId.requestFocus();
                }
            }
        });

        // Botón Limpiar
        btnLimpiar.addActionListener(e -> {
            txtId.setText("");
            areaDetalles.setText("");
        });

        // Botón Cancelar
        btnCancelar.addActionListener(e -> {
            setVisible(false);
            dispose();
        });

        // Añadir componentes al panel principal
        mainPanel.add(panelBusqueda);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(scroll);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(panelBotones);

        getContentPane().add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public void actualizar(int evento, Object datos) {
        switch (evento) {

            case Eventos.RES_BUSCAR_EMPLEADO_OK:
                TEmpleado te = (TEmpleado) datos;
                StringBuilder sb = new StringBuilder();
                sb.append("ID: ").append(te.getId()).append("\n");
                sb.append("DNI: ").append(te.getDNI()).append("\n");
                sb.append("Nombre: ").append(te.getNombre()).append("\n");
                sb.append("Apellido: ").append(te.getApellido()).append("\n");
                sb.append("Sueldo: ").append(te.getSueldo()).append(" €\n");
                sb.append("Estado: ").append(te.isActivo() ? "ACTIVO" : "INACTIVO (Baja)");
                
                areaDetalles.setText(sb.toString());
                break;

            case Eventos.RES_BUSCAR_EMPLEADO_KO:
                areaDetalles.setText("");
                JOptionPane.showMessageDialog(this, "No se ha encontrado ningún empleado con el ID: " + datos, "Error", JOptionPane.ERROR_MESSAGE);
                txtId.requestFocus();
                break;

            default:
                JOptionPane.showMessageDialog(this, "Error inesperado al consultar.");
                break;
        }
    }
}
