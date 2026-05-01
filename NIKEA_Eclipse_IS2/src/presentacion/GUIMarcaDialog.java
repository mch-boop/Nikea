package presentacion;

import javax.swing.*;
import java.awt.*;

import presentacion.controlador.Controlador;
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
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        initGUI();
    }
    
    // MÉTODOS
    
    private void initGUI() {
        
        JPanel panel = new JPanel(new GridLayout(2, 3, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JButton btnAlta          = new JButton("Alta Marca");
        JButton btnBaja          = new JButton("Baja Marca");
        JButton btnMostrarId     = new JButton("Buscar Marca");
        JButton btnMostrarTodos  = new JButton("Listar Marcas");
        JButton btnModificar     = new JButton("Modificar Marca");
        JButton btnRanking       = new JButton("Mostrar Ranking de Marcas");

        JButton[] botones = {btnAlta, btnBaja, btnModificar, btnMostrarId, btnMostrarTodos, btnRanking};
        for (JButton b : botones) {
            b.setFocusPainted(false);
            panel.add(b);
        }

        add(panel, BorderLayout.CENTER);

        // Listeners
        
        btnAlta.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.ALTA_MARCA);
            abrirVistaBloqueante((JDialog) vista);
        });

        btnBaja.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BAJA_MARCA);
            abrirVistaBloqueante((JDialog) vista);
        });

        btnMostrarId.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BUSCAR_MARCA);
            abrirVistaBloqueante((JDialog) vista);
        });

        btnMostrarTodos.addActionListener(e -> {
        	Controlador.getInstance().accion(Eventos.MOSTRAR_MARCAS, null);
        });

        btnModificar.addActionListener(e -> { 
        	IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MODIFICAR_MARCA);
        	abrirVistaBloqueante((JDialog) vista);
        });
        
        btnRanking.addActionListener(e -> { 
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MOSTRAR_RANKING_MARCA);
            abrirVistaBloqueante((JDialog) vista);
        });
    }

    private void abrirVistaBloqueante(JDialog vista) {
    	vista.setModal(true);
    	vista.setVisible(true);
    }
}