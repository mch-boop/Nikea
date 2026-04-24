package presentacion.GUIEmpleado;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import negocio.empleado.TEmpleado;
import negocio.empleado.TVendedor;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaModificarEmpleado extends JFrame implements IGUI {

	// ATRIBUTOS
	
    private JTextField txtId, txtNombre, txtApellido, txtSueldo, txtVentas;
    private JButton btnModificar, btnCancelar;
    private JLabel lblVentas;
    private static TEmpleado datosNuevosParaConfirmar;

    // CONSTRUCTORA
    
    public VistaModificarEmpleado() {
        setTitle("Modificar Empleado");
        initGUI();
    }

    
    // MÉTODOS
    
    private void initGUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // El ID es obligatorio para saber a quién modificar
        txtId = new JTextField(20);
        
        // Campos opcionales
        txtNombre = new JTextField(20);
        txtApellido = new JTextField(20);
        txtSueldo = new JTextField(20);

        txtNombre.setToolTipText("Deje este campo vacío para conservar el nombre actual");
        txtApellido.setToolTipText("Deje este campo vacío para conservar el apellido actual");
        txtSueldo.setToolTipText("Deje este campo vacío para conservar el sueldo actual");
        
        lblVentas = new JLabel("Número de Ventas:");
        txtVentas = new JTextField(20);
        txtSueldo.setToolTipText("Deje este campo vacío para conservar el número de ventas actual");

        // Por defecto no sabemos qué es el empleado, así que ocultamos
        lblVentas.setVisible(false);
        txtVentas.setVisible(false);

        btnModificar = new JButton("GUARDAR CAMBIOS");
        btnCancelar = new JButton("CANCELAR");

        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (txtId.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(VistaModificarEmpleado.this, "El ID es obligatorio.");
                        return;
                    }

                    datosNuevosParaConfirmar = new TEmpleado();
                    datosNuevosParaConfirmar.setId(Integer.parseInt(txtId.getText().trim()));
                    datosNuevosParaConfirmar.setNombre(!txtNombre.getText().trim().isEmpty() ? txtNombre.getText().trim() : null);
                    datosNuevosParaConfirmar.setApellido(!txtApellido.getText().trim().isEmpty() ? txtApellido.getText().trim() : null);
                    datosNuevosParaConfirmar.setSueldo(!txtSueldo.getText().trim().isEmpty() ? Double.parseDouble(txtSueldo.getText().trim()) : -1.0);

                    if (txtVentas.isVisible() && !txtVentas.getText().trim().isEmpty()) {
                        // Hacemos el cast a TVendedor para poder acceder a sus métodos específicos
                        ((TVendedor) datosNuevosParaConfirmar).setNumeroVentas(Integer.parseInt(txtVentas.getText().trim()));
                        datosNuevosParaConfirmar.setTipo(1); 
                    }
                    
                    
                    Controlador.getInstance().accion(Eventos.BUSCAR_EMPLEADO_PARA_MODIFICAR, datosNuevosParaConfirmar.getId());
                    
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(VistaModificarEmpleado.this, "ID o Sueldo no válidos.");
                }
            }
        });

        // Lógica de Cancelar
        btnCancelar.addActionListener(e -> {
            setVisible(false);
            dispose();
        });

        // ALINEACIÓN
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Fila 0: ID
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("ID Empleado (Obligatorio):"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtId, gbc);

        // Fila 1: Separador (dentro del GridBag)
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        formPanel.add(new JSeparator(), gbc);
        gbc.gridwidth = 1; // Reset

        // Fila 2: Nombre
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Nuevo Nombre:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtNombre, gbc);

        // Fila 3: Apellido
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Nuevo Apellido:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtApellido, gbc);

        // Fila 4: Sueldo
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Nuevo Sueldo:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtSueldo, gbc);
        
        // Fila 5: Número de ventas
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(lblVentas, gbc);
        gbc.gridx = 1;
        formPanel.add(txtVentas, gbc);

        // Añadir componentes al panel principal
        mainPanel.add(formPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Panel de botones
        JPanel pBotones = new JPanel();
        pBotones.add(btnModificar); pBotones.add(btnCancelar);
        mainPanel.add(pBotones);

        getContentPane().add(mainPanel);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    @Override
    public void actualizar(int evento, Object datos) {
        SwingUtilities.invokeLater(() -> {
            switch (evento) {
                // Recibimos los datos del empleado que queremos modificar
                case Eventos.RES_BUSCAR_EMPLEADO_PARA_MODIFICAR_OK:
                    TEmpleado datosViejos = (TEmpleado) datos;
                    
                    // Lanzamos el diálogo de las tarjetas
                    GUIConfirmarModificar dialog = new GUIConfirmarModificar(this, datosViejos, datosNuevosParaConfirmar);
                    dialog.setVisible(true);
                    
                    if (dialog.isConfirmado()) {
                        // Si el usuario acepta en las tarjetas, se lanza la modificación real
                        Controlador.getInstance().accion(Eventos.MODIFICAR_EMPLEADO, datosNuevosParaConfirmar);
                    }
                    break;

                case Eventos.RES_BUSCAR_EMPLEADO_PARA_MODIFICAR_KO:
                    JOptionPane.showMessageDialog(this, "Error: No se encontró el empleado con ID: " + datos, "ID no encontrado", JOptionPane.ERROR_MESSAGE);
                    txtId.requestFocus();
                    break;

                case Eventos.RES_MODIFICAR_EMPLEADO_OK:
                    JOptionPane.showMessageDialog(this, "Empleado actualizado correctamente.");
                    this.dispose(); // Cerramos al terminar
                    break;

                case Eventos.RES_MODIFICAR_EMPLEADO_KO_NO_EXISTE:
                    JOptionPane.showMessageDialog(this, "El empleado ha dejado de existir durante la operación.", "Error", JOptionPane.ERROR_MESSAGE);
                    break;

                case Eventos.RES_MODIFICAR_EMPLEADO_KO_DATOS_INVALIDOS:
                    JOptionPane.showMessageDialog(this, "Datos no válidos para la actualización.", "Error de validación", JOptionPane.WARNING_MESSAGE);
                    break;
            }
        });
    }


	private void limpiarCampos() {
		txtId.setText("");
		txtNombre.setText("");
        txtApellido.setText("");
        txtSueldo.setText("");
	}
}