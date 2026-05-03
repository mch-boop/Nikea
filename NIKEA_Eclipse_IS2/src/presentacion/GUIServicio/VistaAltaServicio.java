package presentacion.GUIServicio;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JScrollPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import negocio.servicio.TArticulo;
import negocio.servicio.TMontaje;
import negocio.servicio.TServicio;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaAltaServicio extends JDialog implements IGUI {

    private JTextField txtNombre;
    private JTextArea txtDescripcion;
    private JSpinner spStock;
    private JSpinner spPrecio;
    private JRadioButton rbArticulo;
    private JRadioButton rbMontaje;
    private JComboBox<String> comboMarcas;
    private Map<String, Integer> marcasMap;
    private JButton btnAceptar;
    private JButton btnCancelar;

    public VistaAltaServicio() {
    	super(null, "Alta Servicio", ModalityType.APPLICATION_MODAL);
        setTitle("Alta Servicio");
        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initGUI();
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtDescripcion.setText("");
        spStock.setValue(0);
        spPrecio.setValue(0);
        rbArticulo.setSelected(true);
        comboMarcas.setSelectedIndex(-1);
        txtNombre.requestFocus();
        repaint();
        revalidate();
    }

    private void initGUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtNombre = new JTextField(20);
        txtDescripcion = new JTextArea(4, 20);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        spStock = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        SpinnerNumberModel precioModel = new SpinnerNumberModel(0.0, 0.0, 1000000.0, 1);
        spPrecio = new JSpinner(precioModel);
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spPrecio, "0.00");
        spPrecio.setEditor(editor);
        editor.getTextField().setColumns(10);

        rbArticulo = new JRadioButton("Artículo", true);
        rbMontaje = new JRadioButton("Montaje");
        ButtonGroup grupoTipo = new ButtonGroup();
        grupoTipo.add(rbArticulo);
        grupoTipo.add(rbMontaje);

        rbArticulo.addActionListener(e -> {
            // ComboBox siempre está habilitado
        });
        rbMontaje.addActionListener(e -> {
            comboMarcas.setSelectedIndex(-1);
        });

        marcasMap = new HashMap<>();
        comboMarcas = new JComboBox<>();
        // El comboBox siempre está habilitado para permitir seleccionar marcas

        JPanel panelTipo = new JPanel();
        panelTipo.add(rbArticulo);
        panelTipo.add(rbMontaje);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1.0;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(txtNombre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
        scrollDescripcion.setPreferredSize(new Dimension(220, 90));
        formPanel.add(scrollDescripcion, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Stock:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(spStock, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Precio actual:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(spPrecio, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Tipo de servicio:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(panelTipo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Marca (solo para Artículos):"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        formPanel.add(comboMarcas, gbc);

        JPanel panelBotones = new JPanel();
        btnAceptar = new JButton("ACEPTAR");
        btnCancelar = new JButton("CANCELAR");
        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);

        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (txtNombre.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Error: El nombre es un campo obligatorio.", "Faltan datos", JOptionPane.WARNING_MESSAGE);
                        txtNombre.requestFocus();
                        return;
                    }

                    if (txtDescripcion.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Error: La descripción es un campo obligatorio.", "Faltan datos", JOptionPane.WARNING_MESSAGE);
                        txtDescripcion.requestFocus();
                        return;
                    }

                    TServicio servicio;
                    if (rbArticulo.isSelected()) {
                        servicio = new TArticulo();
                        servicio.setTipo(1);

                        if (comboMarcas.getSelectedIndex() == -1) {
                            JOptionPane.showMessageDialog(null, "Error: Debe seleccionar una marca para los artículos.", "Faltan datos", JOptionPane.WARNING_MESSAGE);
                            comboMarcas.requestFocus();
                            return;
                        }

                        String marcaNombreSeleccionada = (String) comboMarcas.getSelectedItem();
                        Integer marcaId = marcasMap.get(marcaNombreSeleccionada);
                        ((TArticulo) servicio).setMarcaId(marcaId);
                        servicio.setMarca(marcaNombreSeleccionada);  // Almacenar también el nombre
                    } else {
                        servicio = new TMontaje();
                        servicio.setTipo(2);
                    }

                    servicio.setNombre(txtNombre.getText().trim());
                    servicio.setDescripcion(txtDescripcion.getText().trim());

                    int stock = ((Number) spStock.getValue()).intValue();
                    if (stock == 0) {
                        JOptionPane.showMessageDialog(null, "Error: El stock debe ser mayor que 0.", "Faltan datos", JOptionPane.WARNING_MESSAGE);
                        spStock.requestFocus();
                        return;
                    }

                    int precio = ((Number) spPrecio.getValue()).intValue();
                    if (precio == 0) {
                        JOptionPane.showMessageDialog(null, "Error: El precio actual debe ser mayor que 0.", "Faltan datos", JOptionPane.WARNING_MESSAGE);
                        spPrecio.requestFocus();
                        return;
                    }

                    servicio.setStock(stock);
                    servicio.setPrecioActual(precio);
                    servicio.setActivo(true);

                    Controlador.getInstance().accion(Eventos.ALTA_SERVICIO, servicio);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error: El stock y el precio deben ser números válidos.");
                }
            }
        });

        btnCancelar.addActionListener(e -> {
            limpiarCampos();
            setVisible(false);
            dispose();
        });

        mainPanel.add(formPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(panelBotones);

        getContentPane().add(mainPanel, BorderLayout.CENTER);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    @Override
    public void actualizar(int evento, Object datos) {
        SwingUtilities.invokeLater(() -> {
            switch (evento) {
                case Eventos.RES_CARGAR_MARCAS_PARA_SERVICIO_OK:
                    @SuppressWarnings("unchecked")
                    Collection<Object> listaMarcas = (Collection<Object>) datos;
                    marcasMap.clear();
                    comboMarcas.removeAllItems();
                    for (Object marcaObj : listaMarcas) {
                        try {
                            // Usar reflection para obtener nombre e id sin importar TMarca
                            String nombre = (String) marcaObj.getClass().getMethod("getNombre").invoke(marcaObj);
                            Integer id = (Integer) marcaObj.getClass().getMethod("getId").invoke(marcaObj);
                            marcasMap.put(nombre, id);
                            comboMarcas.addItem(nombre);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (!marcasMap.isEmpty()) {
                        comboMarcas.setSelectedIndex(0);
                    }
                    break;

                case Eventos.RES_CARGAR_MARCAS_PARA_SERVICIO_KO:
                    JOptionPane.showMessageDialog(this, "No se pudieron cargar las marcas disponibles.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    break;

                case Eventos.RES_MOSTRAR_MARCAS_OK:
                    @SuppressWarnings("unchecked")
                    Collection<Object> listaMarcas2 = (Collection<Object>) datos;
                    marcasMap.clear();
                    comboMarcas.removeAllItems();
                    for (Object marcaObj : listaMarcas2) {
                        try {
                            // Usar reflection para obtener nombre e id sin importar TMarca
                            String nombre = (String) marcaObj.getClass().getMethod("getNombre").invoke(marcaObj);
                            Integer id = (Integer) marcaObj.getClass().getMethod("getId").invoke(marcaObj);
                            marcasMap.put(nombre, id);
                            comboMarcas.addItem(nombre);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (!marcasMap.isEmpty()) {
                        comboMarcas.setSelectedIndex(0);
                    }
                    break;

                case Eventos.RES_MOSTRAR_MARCAS_KO:
                    JOptionPane.showMessageDialog(this, "No se pudieron cargar las marcas disponibles.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    break;

                case Eventos.RES_ALTA_SERVICIO_OK:
                    limpiarCampos();
                    JOptionPane.showMessageDialog(this, "Servicio creado con ID: " + datos, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case Eventos.RES_ALTA_SERVICIO_YA_EXISTE:
                    if (datos instanceof TServicio) {
                        TServicio duplicado = (TServicio) datos;
                        JOptionPane.showMessageDialog(this,
                                "Ya existe un servicio con ese nombre: " + duplicado.getNombre(),
                                "Aviso",
                                JOptionPane.WARNING_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "El servicio ya existe.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    }
                    break;
                case Eventos.RES_ALTA_SERVICIO_KO:
                    JOptionPane.showMessageDialog(this, "No se ha podido dar de alta el servicio.", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
                default:
                    break;
            }
        });
    }
    
    
    // reset
    
    @Override
    public void setVisible(boolean b) {
        if (b) {
            limpiarCampos();
            Controlador.getInstance().accion(Eventos.CARGAR_MARCAS_PARA_SERVICIO, this);
        }
        super.setVisible(b);
    }
}
