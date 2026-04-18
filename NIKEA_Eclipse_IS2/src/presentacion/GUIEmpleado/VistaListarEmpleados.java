package presentacion.GUIEmpleado;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import negocio.empleado.TEmpleado;
import presentacion.IGUI;
import presentacion.Controlador.Controlador;
import presentacion.Controlador.Evento;

public class VistaListarEmpleados extends JFrame implements IGUI {

	// Atributos
    private JTextArea areaListado;
    private JButton btnCargar, btnLimpiar, btnCancelar;

    // Constructora
    public VistaListarEmpleados() {
        setTitle("Listado General de Empleados");
        initGUI();
    }

    // Métodos
    private void initGUI() {
    	
    	// Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Área de texto para el listado
        areaListado = new JTextArea(20, 50);
        areaListado.setEditable(false);
        areaListado.setFont(new Font("Monospaced", Font.PLAIN, 12)); 
        JScrollPane scroll = new JScrollPane(areaListado);
        scroll.setBorder(BorderFactory.createTitledBorder("Lista de Empleados en el Sistema"));

        // Panel superior con el botón de carga
        JPanel panelNorte = new JPanel();
        btnCargar = new JButton("MOSTRAR TODOS LOS EMPLEADOS");
        panelNorte.add(btnCargar);

        // Panel inferior con botones 
        JPanel panelSur = new JPanel();
        btnLimpiar = new JButton("LIMPIAR");
        btnCancelar = new JButton("CANCELAR");
        panelSur.add(btnLimpiar);
        panelSur.add(btnCancelar);

        // Lógica de Carga
        btnCargar.addActionListener(e -> {
            // No necesitamos enviar datos para listar, el SA ya sabe qué hacer
            Controlador.getInstance().accion(Evento.LISTAR_EMPLEADOS, null);
        });

        // Lógica de Limpiar
        btnLimpiar.addActionListener(e -> areaListado.setText(""));

        // Lógica de Cancelar
        btnCancelar.addActionListener(e -> {
            setVisible(false);
            dispose();
        });

        // Añadir componentes al panel principal
        mainPanel.add(panelNorte, BorderLayout.NORTH);
        mainPanel.add(scroll, BorderLayout.CENTER);
        mainPanel.add(panelSur, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void actualizar(int evento, Object datos) {
        switch (evento) {

            case Evento.RES_LISTAR_EMPLEADOS_OK:
                Collection<TEmpleado> lista = (Collection<TEmpleado>) datos;
                
                if (lista.isEmpty()) {
                    areaListado.setText("No hay empleados registrados en el sistema.");
                } else {
                    StringBuilder sb = new StringBuilder();
                    // Cabecera simple
                    sb.append(String.format("%-5s | %-12s | %-25s | %-10s | %-10s\n", "ID", "DNI", "NOMBRE COMPLETO", "SUELDO", "ESTADO"));
                    sb.append("--------------------------------------------------------------------------------\n");
                    
                    for (TEmpleado te : lista) {
                        String nombreCompleto = te.getNombre() + " " + te.getApellido();
                        String estado = te.isActivo() ? "Activo" : "Inactivo";
                        
                        sb.append(String.format("%-5d | %-12s | %-25s | %-10.2f | %-10s\n", 
                                te.getId(), te.getDni(), nombreCompleto, te.getSueldo(), estado));
                    }
                    areaListado.setText(sb.toString());
                }
                break;

            case Evento.RES_LISTAR_EMPLEADOS_KO:
                areaListado.setText("");
                JOptionPane.showMessageDialog(this, "Error al recuperar la lista de empleados.", "Error", JOptionPane.ERROR_MESSAGE);
                break;

            default:
                break;
        }
    }
}
