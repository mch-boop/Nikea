package presentacion.GUIEmpleado;

import javax.swing.*;
import java.awt.*;
import negocio.empleado.TEmpleado;
import negocio.empleado.TMontador;
import negocio.empleado.TVendedor;

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
        
        btnSi.addActionListener(e -> { confirmado = true; setVisible(false); /*dispose();*/ });
        btnNo.addActionListener(e -> { confirmado = false; setVisible(false); /*dispose();*/ });
        
        pBotones.add(btnSi);
        pBotones.add(btnNo);

        add(mainPanel, BorderLayout.CENTER);
        add(pBotones, BorderLayout.SOUTH);
        
        pack();
        setResizable(false);
        setLocationRelativeTo(getOwner());
    }

	private JPanel crearTarjetaEmpleado(TEmpleado te, String titulo, Color fondo) {
        JPanel p = new JPanel(new GridLayout(0, 1, 5, 5));
        p.setBackground(fondo);
        p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), titulo));
        
        p.add(new JLabel(" Nombre: " + te.getNombre() + " " + te.getApellido()));
        p.add(new JLabel(" DNI: " + te.getDNI()));
        p.add(new JLabel(" Sueldo: " + te.getSueldo() + " €"));
        
        // Comprobación de seguridad para evitar NullPointerException en el tipo
        if (te.getTipo() != null && te.getTipo() == 1) {
            // Si es Vendedor, mostramos sus ventas
            if (te instanceof TVendedor) {
                p.add(new JLabel(" Ventas: " + ((TVendedor)te).getNumeroVentas()));
            }
        }
        
        return p;
    }

	private TEmpleado fusionarDatos(TEmpleado v, TEmpleado n) {
	    TEmpleado f;
	    
	    // Instanciamos según el nuevo tipo solicitado 
	    int tipoFinal = (n != null) ? n.getTipo() : v.getTipo();

	    if (tipoFinal == 1) {
	        f = new TVendedor();
	        // Si el nuevo es vendedor, intentamos pillar sus ventas, si no las del viejo (si era vendedor)
	        int ventasNuevas = (n instanceof TVendedor) ? ((TVendedor)n).getNumeroVentas() : -1;
	        int ventasViejas = (v instanceof TVendedor) ? ((TVendedor)v).getNumeroVentas() : 0;
	        ((TVendedor)f).setNumeroVentas(ventasNuevas != -1 ? ventasNuevas : ventasViejas);
	    } else {
	        f = new TMontador();
	    }

	    // Copiamos ID y DNI del viejo (no cambian)
	    f.setId(v.getId());
	    f.setDNI(v.getDNI());
	    f.setTipo(tipoFinal); // Mantenemos el tipo nuevo

	    // Si el usuario no escribió nada en un campo, mantenemos el valor viejo
	    if (n != null) {
	        f.setNombre(n.getNombre() != null ? n.getNombre() : v.getNombre());
	        f.setApellido(n.getApellido() != null ? n.getApellido() : v.getApellido());
	        f.setSueldo(n.getSueldo() != -1.0 ? n.getSueldo() : v.getSueldo());
	    } else {
	        f.setNombre(v.getNombre());
	        f.setApellido(v.getApellido());
	        f.setSueldo(v.getSueldo());
	    }
	    return f;
	}

    public boolean isConfirmado() { return confirmado; }

}
