package presentacion.GUIEmpleado;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import negocio.empleado.TEmpleado;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

public class VistaModificarEmpleado extends JFrame implements IGUI {

	// ATRIBUTOS
	
    private JTextField txtId, txtNombre, txtApellido, txtSueldo;
    private JButton btnModificar, btnCancelar;

    // CONSTRUCTORA
    
    public VistaModificarEmpleado() {
        setTitle("Modificar Empleado (Campos Opcionales)");
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

        btnModificar = new JButton("GUARDAR CAMBIOS");
        btnCancelar = new JButton("CANCELAR");

        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (txtId.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "El ID es obligatorio para identificar al empleado.");
                        return;
                    }

                    TEmpleado te = new TEmpleado();
                    te.setId(Integer.parseInt(txtId.getText()));

                    // Solo enviamos datos si el usuario escribió algo
                    
                    // Nombre
                    if (!txtNombre.getText().trim().isEmpty()) {
                        te.setNombre(txtNombre.getText().trim());
                    } else {
                        te.setNombre(null); // Indicamos al SA que ignore este campo
                    }

                    // Apellido
                    if (!txtApellido.getText().trim().isEmpty()) {
                        te.setApellido(txtApellido.getText().trim());
                    } else {
                        te.setApellido(null);
                    }

                    // Sueldo 
                    if (!txtSueldo.getText().trim().isEmpty()) {
                        te.setSueldo(Double.parseDouble(txtSueldo.getText().trim()));
                    } else {
                        te.setSueldo(-1.0); // Valor centinela: "No modificar sueldo"
                    }

                    Controlador.getInstance().accion(Eventos.MODIFICAR_EMPLEADO, te);
                    
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Asegúrese de que el ID y el Sueldo sean números válidos.");
                }
            }
        });

        // Lógica de Cancelar
        btnCancelar.addActionListener(e -> {
            setVisible(false);
            dispose();
        });

        // Añadir componentes al panel principal
        mainPanel.add(new JLabel("ID del empleado (Obligatorio):"));
        mainPanel.add(txtId);
        mainPanel.add(new JSeparator()); 
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        mainPanel.add(new JLabel("Nuevo Nombre (vacío para mantener actual):"));
        mainPanel.add(txtNombre);
        
        mainPanel.add(new JLabel("Nuevo Apellido (vacío para mantener actual):"));
        mainPanel.add(txtApellido);
        
        mainPanel.add(new JLabel("Nuevo Sueldo (vacío para mantener actual):"));
        mainPanel.add(txtSueldo);
        
        // Panel de botones
        JPanel pBotones = new JPanel();
        pBotones.add(btnModificar); pBotones.add(btnCancelar);
        mainPanel.add(pBotones);

        getContentPane().add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public void actualizar(int evento, Object datos) {
        switch (evento) {

            case Eventos.RES_MODIFICAR_EMPLEADO_OK:
                JOptionPane.showMessageDialog(this, "Datos actualizados correctamente para el empleado ID: " + datos, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                // Opcional: limpiar campos tras éxito
                break;

            case Eventos.RES_MODIFICAR_EMPLEADO_KO_NO_EXISTE:
                JOptionPane.showMessageDialog(this, "Error: No se encontró ningún empleado con el ID especificado.", "Error", JOptionPane.ERROR_MESSAGE);
                txtId.requestFocus();
                break;

            case Eventos.RES_MODIFICAR_EMPLEADO_KO_DATOS_INVALIDOS:
                // Aquí podrías ser más específico con sub-eventos como hicimos en el Alta
                JOptionPane.showMessageDialog(this, "Error: Los datos introducidos no son válidos para la modificación.", "Validación Fallida", JOptionPane.WARNING_MESSAGE);
                break;

            default:
                JOptionPane.showMessageDialog(this, "Error inesperado al intentar modificar.");
                break;
        }
    }
}
