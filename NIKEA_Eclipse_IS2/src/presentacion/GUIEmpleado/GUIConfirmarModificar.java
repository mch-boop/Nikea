package presentacion.GUIEmpleado;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import negocio.empleado.TEmpleado;
import negocio.empleado.TMontador;
import negocio.empleado.TVendedor;

@SuppressWarnings("serial")
public class GUIConfirmarModificar extends JDialog {

    private boolean confirmado = false; 
    
    // Colores
    private final Color COLOR_FONDO_VIEJO = new Color(245, 245, 245);
    private final Color COLOR_FONDO_NUEVO = new Color(235, 250, 235);
    private final Color COLOR_BORDE_VIEJO = new Color(200, 200, 200);
    private final Color COLOR_BORDE_NUEVO = new Color(160, 210, 160);

    public GUIConfirmarModificar(JFrame owner, TEmpleado datosViejos, TEmpleado datosNuevos) {
        super(owner, "Confirmación de Cambios", true);
        initGUI(datosViejos, datosNuevos);
    }

    private void initGUI(TEmpleado viejo, TEmpleado nuevo) {
        setLayout(new BorderLayout());
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 20, 10, 20)); // Espaciado exterior

        // CABECERA INFORMATIVA
        JLabel lblIntro = new JLabel("Revise los cambios antes de confirmar:");
        lblIntro.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblIntro.setBorder(new EmptyBorder(0, 0, 15, 0));
        mainPanel.add(lblIntro);

        // TARJETAS
        mainPanel.add(crearTarjetaEmpleado(viejo, "DATOS ACTUALES", COLOR_FONDO_VIEJO, COLOR_BORDE_VIEJO));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(crearTarjetaEmpleado(fusionarDatos(viejo, nuevo), "VISTA PREVIA", COLOR_FONDO_NUEVO, COLOR_BORDE_NUEVO));

        // PANEL DE BOTONES
        JPanel pBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pBotones.setBorder(new EmptyBorder(10, 10, 15, 20));
        
        JButton btnSi = new JButton("Confirmar Cambios");
        btnSi.setPreferredSize(new Dimension(160, 35));
        JButton btnNo = new JButton("Cancelar");
        btnNo.setPreferredSize(new Dimension(100, 35));
        
        btnSi.addActionListener(e -> { confirmado = true; setVisible(false); });
        btnNo.addActionListener(e -> { confirmado = false; setVisible(false); });
        
        pBotones.add(btnSi);
        pBotones.add(btnNo);
       
        add(mainPanel, BorderLayout.CENTER);
        add(pBotones, BorderLayout.SOUTH);
        
        pack();
        setResizable(false);
        setLocationRelativeTo(getOwner());
    }

    private JPanel crearTarjetaEmpleado(TEmpleado te, String titulo, Color fondo, Color colorBorde) {

        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(fondo);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        Border lineB = BorderFactory.createLineBorder(colorBorde, 1);
        TitledBorder titledB = BorderFactory.createTitledBorder(lineB, " " + titulo + " ");
        titledB.setTitleFont(titledB.getTitleFont().deriveFont(Font.BOLD));
        p.setBorder(new CompoundBorder(titledB, new EmptyBorder(10, 15, 10, 15)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(3, 0, 3, 10);
        gbc.gridx = 0;

        // Añadir filas de datos
        añadirFila(p, "Nombre:", te.getNombre() + " " + te.getApellido(), 0);
        añadirFila(p, "DNI:", te.getDNI(), 1);
        añadirFila(p, "Sueldo:", String.format("%.2f €", te.getSueldo()), 2);
        
        if (te.getTipo() != null && te.getTipo() == 1) {
            if (te instanceof TVendedor) {
                añadirFila(p, "Ventas:", String.valueOf(((TVendedor)te).getNumeroVentas()), 3);
            }
        }
        
        return p;
    }

    private void añadirFila(JPanel p, String label, String valor, int fila) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(2, 5, 2, 20);
        gbc.gridy = fila;

        gbc.gridx = 0;
        JLabel lHeader = new JLabel(label);
        lHeader.setForeground(Color.DARK_GRAY);
        p.add(lHeader, gbc);

        gbc.gridx = 1;
        JLabel lValor = new JLabel(valor);
        lValor.setFont(lValor.getFont().deriveFont(Font.BOLD));
        p.add(lValor, gbc);
    }

    // El método fusionarDatos se mantiene igual que en tu código original
    private TEmpleado fusionarDatos(TEmpleado v, TEmpleado n) {
        TEmpleado f;
        int tipoFinal = (n != null) ? n.getTipo() : v.getTipo();

        if (tipoFinal == 1) {
            f = new TVendedor();
            int ventasNuevas = (n instanceof TVendedor) ? ((TVendedor)n).getNumeroVentas() : -1;
            int ventasViejas = (v instanceof TVendedor) ? ((TVendedor)v).getNumeroVentas() : 0;
            ((TVendedor)f).setNumeroVentas(ventasNuevas != -1 ? ventasNuevas : ventasViejas);
        } else {
            f = new TMontador();
        }

        f.setId(v.getId());
        f.setDNI(v.getDNI());
        f.setTipo(tipoFinal);

        if (n != null) {
            f.setNombre(n.getNombre() != null && !n.getNombre().isEmpty() ? n.getNombre() : v.getNombre());
            f.setApellido(n.getApellido() != null && !n.getApellido().isEmpty() ? n.getApellido() : v.getApellido());
            f.setSueldo(n.getSueldo() > 0 ? n.getSueldo() : v.getSueldo());
        } else {
            f.setNombre(v.getNombre());
            f.setApellido(v.getApellido());
            f.setSueldo(v.getSueldo());
        }
        return f;
    }

    public boolean isConfirmado() { return confirmado; }
}