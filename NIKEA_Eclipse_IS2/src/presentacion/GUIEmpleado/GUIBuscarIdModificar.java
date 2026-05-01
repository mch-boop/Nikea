package presentacion.GUIEmpleado;

import javax.swing.*;
import java.awt.*;

import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class GUIBuscarIdModificar extends JDialog implements IGUI {

	// ATRRIBUTOS
	private JTextField txtId;
    private JButton btnBuscar, btnCancelar;

    // CONSTRUCTORA
    public GUIBuscarIdModificar() {
    	super(null, "Buscar ID Modificar", ModalityType.APPLICATION_MODAL);
        setTitle("Seleccionar Empleado");

        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        initGUI();
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                limpiarCampos();
            }
        });
    }

    // MÉTODOS
    
    @Override
    public void setVisible(boolean b) {
        if (b) limpiarCampos();
        super.setVisible(b);
    }
    
    private void limpiarCampos() {
		txtId.setText("");
	}
    
    private void initGUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Introduzca el ID del Empleado a modificar:");
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel pBusqueda = new JPanel(new FlowLayout(FlowLayout.CENTER));
        txtId = new JTextField(15);
        pBusqueda.add(new JLabel("ID:"));
        pBusqueda.add(txtId);

        JPanel pBotones = new JPanel();
        btnBuscar = new JButton("BUSCAR");
        btnCancelar = new JButton("CANCELAR");
        pBotones.add(btnBuscar);
        pBotones.add(btnCancelar);

        // Lógica Buscar
        btnBuscar.addActionListener(e -> {
            try {
                String textoId = txtId.getText().trim();
                if (textoId.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El ID es obligatorio.");
                } else {
                    int id = Integer.parseInt(textoId);
                    // Usamos el evento de búsqueda para modificar
                    Controlador.getInstance().accion(Eventos.BUSCAR_EMPLEADO_PARA_MODIFICAR, id);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID debe ser un número.");
            }
        });

        // Lógica Cancelar
        btnCancelar.addActionListener(e ->{
        	limpiarCampos(); 
        	setVisible(false);
        });

        mainPanel.add(lblTitulo);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(pBusqueda);
        mainPanel.add(pBotones);

        getContentPane().add(mainPanel);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    @Override
    public void actualizar(int evento, Object datos) {
        switch (evento) {
            case Eventos.RES_BUSCAR_EMPLEADO_PARA_MODIFICAR_OK:
                limpiarCampos(); 
                break;
            case Eventos.RES_BUSCAR_EMPLEADO_PARA_MODIFICAR_KO:
                JOptionPane.showMessageDialog(this, "No existe el empleado con ID: " + datos, "Error", JOptionPane.ERROR_MESSAGE);
                limpiarCampos(); 
                break;
        }
    }

}
