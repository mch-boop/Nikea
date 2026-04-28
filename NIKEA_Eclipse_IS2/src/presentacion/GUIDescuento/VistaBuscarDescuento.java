package presentacion.GUIDescuento;

import java.awt.*;
import javax.swing.*;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import negocio.descuento.TDescuento;

@SuppressWarnings("serial")
public class VistaBuscarDescuento extends JFrame implements IGUI {

    private JPanel panelBusqueda;
    private JTextField txtId;
    private JButton btnBuscar, btnCancelar;

    private JPanel panelResultado;
    private JLabel lblNombreVal, lblCodigoVal, lblPorcentajeVal,
                   lblTipoVal, lblCantidadVal, lblActivoVal;
    private JButton btnVolver;

    private JPanel cardPanel;
    private CardLayout cardLayout;

    private static final String CARD_BUSQUEDA  = "busqueda";
    private static final String CARD_RESULTADO = "resultado";

    public VistaBuscarDescuento() {
        setTitle("Buscar Descuento");
        initGUI();
    }

    private void initGUI() {
        cardLayout = new CardLayout();
        cardPanel  = new JPanel(cardLayout);

        cardPanel.add(buildPanelBusqueda(),  CARD_BUSQUEDA);
        cardPanel.add(buildPanelResultado(), CARD_RESULTADO);

        add(cardPanel);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    //Introducir id
    private JPanel buildPanelBusqueda() {
        panelBusqueda = new JPanel();
        panelBusqueda.setLayout(new BoxLayout(panelBusqueda, BoxLayout.Y_AXIS));
        panelBusqueda.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblInfo = new JLabel("Introduce el ID del descuento a consultar:");
        lblInfo.setAlignmentX(CENTER_ALIGNMENT);

        txtId = new JTextField(10);
        txtId.setMaximumSize(new Dimension(200, 30));
        txtId.setAlignmentX(CENTER_ALIGNMENT);

        btnBuscar  = new JButton("BUSCAR");
        btnCancelar = new JButton("CANCELAR");

        btnCancelar.addActionListener(e -> {
            txtId.setText("");
            setVisible(false);
        });

        btnBuscar.addActionListener(e -> {
            String idStr = txtId.getText().trim();
            if (idStr.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Por favor, introduce un ID válido.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                int id = Integer.parseInt(idStr);
                Controlador.getInstance().accion(Eventos.BUSCAR_DESCUENTO, id);
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this,
                    "El ID debe ser un número entero.", "Error de formato",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.add(btnBuscar);
        panelBotones.add(btnCancelar);

        panelBusqueda.add(lblInfo);
        panelBusqueda.add(Box.createRigidArea(new Dimension(0, 15)));
        panelBusqueda.add(txtId);
        panelBusqueda.add(Box.createRigidArea(new Dimension(0, 15)));
        panelBusqueda.add(panelBotones);

        return panelBusqueda;
    }

    //Mostrar datos
    private JPanel buildPanelResultado() {
        panelResultado = new JPanel();
        panelResultado.setLayout(new BoxLayout(panelResultado, BoxLayout.Y_AXIS));
        panelResultado.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel lblTitulo = new JLabel("Datos del Descuento");
        lblTitulo.setFont(lblTitulo.getFont().deriveFont(Font.BOLD, 14f));
        lblTitulo.setAlignmentX(CENTER_ALIGNMENT);

        JPanel grid = new JPanel(new GridLayout(0, 2, 10, 8));
        grid.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        lblNombreVal    = new JLabel();
        lblCodigoVal    = new JLabel();
        lblPorcentajeVal = new JLabel();
        lblTipoVal      = new JLabel();
        lblCantidadVal  = new JLabel();
        lblActivoVal    = new JLabel();

        grid.add(new JLabel("Nombre:"));      grid.add(lblNombreVal);
        grid.add(new JLabel("Código:"));      grid.add(lblCodigoVal);
        grid.add(new JLabel("Porcentaje:"));  grid.add(lblPorcentajeVal);
        grid.add(new JLabel("Tipo:"));        grid.add(lblTipoVal);
        grid.add(new JLabel("Cantidad mín:")); grid.add(lblCantidadVal);
        grid.add(new JLabel("Activo:"));      grid.add(lblActivoVal);

        btnVolver = new JButton("VOLVER");
        btnVolver.setAlignmentX(CENTER_ALIGNMENT);
        btnVolver.addActionListener(e -> {
            txtId.setText("");
            cardLayout.show(cardPanel, CARD_BUSQUEDA);
            pack();
        });

        panelResultado.add(lblTitulo);
        panelResultado.add(grid);
        panelResultado.add(btnVolver);

        return panelResultado;
    }

    private void mostrarDescuento(TDescuento td) {
        lblNombreVal.setText(td.getNombre());
        lblCodigoVal.setText(td.getCodigo());
        lblPorcentajeVal.setText(td.getPorcentaje() + " %");
        lblTipoVal.setText(td.isTipo() ? "Por importe" : "Por cantidad");
        lblCantidadVal.setText(String.valueOf(td.getCantidad()));
        lblActivoVal.setText(td.isActivo() ? "Sí" : "No");

        cardLayout.show(cardPanel, CARD_RESULTADO);
        pack();
    }

    @Override
    public void actualizar(int evento, Object datos) {
        SwingUtilities.invokeLater(() -> {
            switch (evento) {
                case Eventos.RES_BUSCAR_DESCUENTO_OK:
                    mostrarDescuento((TDescuento) datos);
                    break;

                case Eventos.RES_BUSCAR_DESCUENTO_KO:
                    JOptionPane.showMessageDialog(this,
                        "No se encontró ningún descuento con ese ID.",
                        "No encontrado", JOptionPane.WARNING_MESSAGE);
                    break;

                default:
                    break;
            }
        });
    }
}