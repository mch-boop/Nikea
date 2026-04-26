package presentacion.GUIMarca;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import negocio.marca.TMarca;
import negocio.marca.TMarca.Especialidad;

@SuppressWarnings("serial")
public class VistaModificarConfirmarMarca extends JDialog {

    private boolean confirmado = false;

    // CONSTRUCTORA
    
    public VistaModificarConfirmarMarca(JFrame owner, TMarca viejo, TMarca nuevo) {
        super(owner, "Confirmar modificación", true);
        init(viejo, nuevo);
    }
    
    // MÉTODOS

    private void init(TMarca v, TMarca n) {

        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        mainPanel.add(crearTarjeta(v, "DATOS ACTUALES (Antes)", new Color(240, 240, 240)));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(crearTarjeta(fusionarDatos(v, n), "VISTA PREVIA (Después)", new Color(220, 255, 220)));

        // botones
        JPanel pBotones = new JPanel();
        JButton ok = new JButton("CONFIRMAR CAMBIO");
        JButton cancel = new JButton("CANCELAR");

        ok.addActionListener(e -> {
            confirmado = true;
            dispose();
        });
        cancel.addActionListener(e -> {
            confirmado = false;
            dispose();
        });
        
        pBotones.add(ok);
        pBotones.add(cancel);

        add(mainPanel, BorderLayout.CENTER);
        add(pBotones, BorderLayout.SOUTH);

        pack();
        setResizable(false);
        setLocationRelativeTo(getOwner());
    }
    
    private TMarca fusionarDatos(TMarca v, TMarca n) {
    	TMarca fusion = new TMarca();
    	
    	fusion.setId(v.getId());
    	
    	if (n != null) {
    		fusion.setNombre(n.getNombre() != null ? 
    				n.getNombre() : v.getNombre());
    		fusion.setEspecialidades(n.getEspecialidades() != null ? 
    				n.getEspecialidades() : v.getEspecialidades());
    	} else {
    		fusion.setNombre(v.getNombre());
    		fusion.setEspecialidades(v.getEspecialidades());
    	}
    	
    	return fusion;
    }

    private JPanel crearTarjeta(TMarca m, String titulo, Color c) {

    	JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(c);
        p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), titulo));
        
        JLabel lblNombre = new JLabel("Nombre: " + safe(m.getNombre()));
        JLabel lblEsp = new JLabel("Especialidades: ");
        
        lblNombre.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblEsp.setAlignmentX(Component.LEFT_ALIGNMENT);

        p.add(lblNombre);
        p.add(Box.createVerticalStrut(5)); // 👈 separación controlada
        p.add(lblEsp);

        JPanel lista = crearListaEspecialidades((List<Especialidad>) m.getEspecialidades());
        lista.setBackground(new Color(0,0,0,0));
        lista.setAlignmentX(Component.LEFT_ALIGNMENT);

        p.add(Box.createVerticalStrut(5)); // 👈 separación controlada
        p.add(lista);
        
        return p;
    }

    // - A
    // - B
    // - C
    private JPanel crearListaEspecialidades(List<Especialidad> lista) {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(0,0,0,0));

        if (lista == null || lista.isEmpty()) {
            panel.add(new JLabel("(sin cambios)"));
            return panel;
        }

        for (Especialidad e : lista) {
            panel.add(new JLabel(" - " + e.toString()));
        }

        return panel;
    }
    
    private String safe(String s) {
        return (s == null || s.isEmpty()) ? "(sin cambios)" : s;
    }
    
    public boolean isConfirmado() {
        return confirmado;
    }
}