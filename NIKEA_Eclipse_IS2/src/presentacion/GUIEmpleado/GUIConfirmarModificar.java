package presentacion.GUIEmpleado;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import negocio.empleado.TEmpleado;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;

import javax.swing.JDialog;

@SuppressWarnings("serial")
public class GUIConfirmarModificar extends JDialog {

	// ATRIBUTOS
	private boolean confirmado = false; 
	
	// CONSTRUCTORA
	public GUIConfirmarModificar(JFrame owner, TEmpleado datosViejos, TEmpleado datosNuevos) {
        super(owner, "Confirmar Cambios", true);
        initGUI(datosViejos, datosNuevos);
    }
	
	// MÉTODOS
	private void initGUI(TEmpleado viejo, TEmpleado nuevo) {
		setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // TARJETA ARRIBA: DATOS ACTUALES
        mainPanel.add(crearTarjetaEmpleado(viejo, "DATOS ACTUALES (Antes)", new Color(240, 240, 240)));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // TARJETA ABAJO: DATOS MODIFICADOS
        // Si un campo en 'nuevo' es null/-1, mostramos el valor de 'viejo'
        mainPanel.add(crearTarjetaEmpleado(fusionarDatos(viejo, nuevo), "VISTA PREVIA (Después)", new Color(220, 255, 220)));

        // BOTONES
        JPanel pBotones = new JPanel();
        JButton btnSi = new JButton("CONFIRMAR CAMBIO");
        JButton btnNo = new JButton("CANCELAR");
        
        btnSi.addActionListener(e -> { confirmado = true; dispose(); });
        btnNo.addActionListener(e -> { confirmado = false; dispose(); });
        
        pBotones.add(btnSi);
        pBotones.add(btnNo);

        add(mainPanel, BorderLayout.CENTER);
        add(pBotones, BorderLayout.SOUTH);
        
        pack();
        setResizable(false);
        setLocationRelativeTo(getOwner());
    }

    private JPanel crearTarjetaEmpleado(TEmpleado te, String titulo, Color fondo) {
        JPanel p = new JPanel(new GridLayout(3, 1));
        p.setBackground(fondo);
        p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), titulo));
        
        p.add(new JLabel(" Nombre: " + te.getNombre() + " " + te.getApellido()));
        p.add(new JLabel(" DNI: " + te.getDNI()));
        p.add(new JLabel(" Sueldo: " + te.getSueldo() + " €"));
        return p;
    }

    private TEmpleado fusionarDatos(TEmpleado v, TEmpleado n) {
        TEmpleado f = new TEmpleado();
        f.setNombre(n.getNombre() != null ? n.getNombre() : v.getNombre());
        f.setApellido(n.getApellido() != null ? n.getApellido() : v.getApellido());
        f.setSueldo(n.getSueldo() != -1.0 ? n.getSueldo() : v.getSueldo());
        f.setDNI(v.getDNI()); // El DNI no suele cambiar en modificación por ID
        return f;
    }

    public boolean isConfirmado() { return confirmado; }

}
