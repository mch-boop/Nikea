package presentacion;

import javax.swing.*;
import java.awt.*;
import presentacion.controlador.Eventos;
import presentacion.factoria.FactoriaAbstractaPresentacion;

@SuppressWarnings("serial")
public class GUIMarcaDialog extends JDialog {

    // CONSTRUCTORA

    public GUIMarcaDialog(JFrame owner) {
    	super(owner, "Gestión de Marca", false);
        setResizable(false);
        setSize(800, 150);
        setLocationRelativeTo(owner);
        
        initGUI();
    }
    
    // MÉTODOS
    
    private void initGUI() {
        
    	JPanel panel = new JPanel(new GridLayout(2, 3, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JButton btnAlta          = new JButton("Alta Marca");
        JButton btnBaja          = new JButton("Baja Marca");
        JButton btnMostrarId     = new JButton("Mostrar info por ID");
        JButton btnMostrarTodos  = new JButton("Mostrar todos");
        JButton btnModificar     = new JButton("Modificar");
        JButton btnRanking       = new JButton("Mostrar Ranking");

        JButton[] botones = {btnAlta, btnBaja, btnMostrarId, btnMostrarTodos, btnModificar, btnRanking};
        for (JButton b : botones) {
            b.setFocusPainted(false);
            panel.add(b);
        }

        add(panel, BorderLayout.CENTER);

        // Listeners
        
        btnAlta.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.ALTA_MARCA);
            ((JFrame) vista).setVisible(true);
        });

        btnBaja.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BAJA_MARCA);
            ((JFrame) vista).setVisible(true);
        });

        btnMostrarId.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BUSCAR_MARCA);
            ((JFrame) vista).setVisible(true);
        });

        btnMostrarTodos.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MOSTRAR_MARCAS);
            ((JFrame) vista).setVisible(true);
        });

        btnModificar.addActionListener(e -> { 
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MODIFICAR_MARCA);
            ((JFrame) vista).setVisible(true);
        });
        
        btnRanking.addActionListener(e -> { 
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MOSTRAR_RANKING_MARCA);
            ((JFrame) vista).setVisible(true);
        });
    }
}