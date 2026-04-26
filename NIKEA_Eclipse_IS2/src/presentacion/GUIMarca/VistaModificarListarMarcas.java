package presentacion.GUIMarca;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

import negocio.marca.TMarca;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaModificarListarMarcas extends JFrame implements IGUI {

    // ATRIBUTOS

    private DefaultListModel<TMarca> modeloActivas;
    private DefaultListModel<TMarca> modeloInactivas;

    private JList<TMarca> listaActivas;
    private JList<TMarca> listaInactivas;

    private JButton btnModificar;
    private JButton btnReactivar;
    private JButton btnCancelar;

    // CONSTRUCTOR

    public VistaModificarListarMarcas() {
        setTitle("Seleccionar Marca");
        initGUI();
        
        SwingUtilities.invokeLater(() -> {
            Controlador.getInstance().accion(
                Eventos.LISTAR_MARCAS_MODIFICAR,
                null
            );
        });
    }

    // INICIALIZACIÓN

    private void initGUI() {

        setLayout(new BorderLayout());

        // lista activas
        modeloActivas = new DefaultListModel<>();
        listaActivas = new JList<>(modeloActivas);
        JScrollPane scrollActivas = new JScrollPane(listaActivas);
        scrollActivas.setBorder(BorderFactory.createTitledBorder("Marcas activas"));

        // lista inactivas
        modeloInactivas = new DefaultListModel<>();
        listaInactivas = new JList<>(modeloInactivas);
        JScrollPane scrollInactivas = new JScrollPane(listaInactivas);
        scrollInactivas.setBorder(BorderFactory.createTitledBorder("Marcas inactivas"));

        // botones
        btnModificar = new JButton("Modificar");
        btnReactivar = new JButton("Reactivar");
        btnCancelar = new JButton("Cancelar");

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnModificar);
        panelBotones.add(btnReactivar);
        panelBotones.add(btnCancelar);

        // panel central
        JPanel center = new JPanel(new GridLayout(2, 1));
        center.add(scrollActivas);
        center.add(scrollInactivas);

        add(center, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // ACCIONES

        btnModificar.addActionListener(e -> {
            TMarca seleccionada = listaActivas.getSelectedValue();
            if (seleccionada != null) {
                Controlador.getInstance().accion(
                        Eventos.ABRIR_MODIFICAR_MARCA,
                        seleccionada
                );
            }
        });

        btnReactivar.addActionListener(e -> {
            TMarca seleccionada = listaInactivas.getSelectedValue();
            if (seleccionada != null) {
                Controlador.getInstance().accion(
                        Eventos.REACTIVAR_MARCA,
                        seleccionada
                );
            }
        });

        btnCancelar.addActionListener(e -> dispose());

        setSize(600, 500);
        setLocationRelativeTo(null);
    }

    // ACTUALIZAR

    @Override
    public void actualizar(int evento, Object datos) {

        switch (evento) {

            case Eventos.RES_LISTAR_MARCAS_OK:
                cargarListas((Collection<TMarca>) datos);
                break;
        }
    }

    // METODOS AUXILIARES

    private void cargarListas(Collection<TMarca> lista) {

        modeloActivas.clear();
        modeloInactivas.clear();

        for (TMarca m : lista) {
            if (m.isActivo()) {
                modeloActivas.addElement(m);
            } else {
                modeloInactivas.addElement(m);
            }
        }
    }
}
