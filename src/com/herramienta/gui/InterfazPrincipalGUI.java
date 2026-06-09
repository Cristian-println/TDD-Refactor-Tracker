package com.herramienta.gui;
 
import com.herramienta.integracion.RegistradorRefactorizacion;
 
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
 
public class InterfazPrincipalGUI extends JFrame {
 
    private static final Color BG_DARK        = new Color( 18,  18,  30);
    private static final Color BG_CARD        = new Color( 28,  28,  45);
    private static final Color BG_FIELD       = new Color( 38,  38,  58);
    private static final Color ACCENT         = new Color(108,  99, 255);
    private static final Color ACCENT_HOVER   = new Color(130, 122, 255);
    private static final Color SUCCESS        = new Color( 82, 183, 136);
    private static final Color ERROR          = new Color(230,  57,  70);
    private static final Color TEXT_PRIMARY   = new Color(220, 220, 255);
    private static final Color TEXT_SECONDARY = new Color(150, 150, 200);
    private static final Color BORDER         = new Color( 55,  55,  85);
 
    private static final Font FONT_UI    = new Font("Segoe UI",      Font.PLAIN, 13);
    private static final Font FONT_BOLD  = new Font("Segoe UI",      Font.BOLD,  13);
    private static final Font FONT_TITLE = new Font("Segoe UI",      Font.BOLD,  14);
    private static final Font FONT_SMALL = new Font("Segoe UI",      Font.PLAIN, 11);
    private static final Font FONT_MONO  = new Font(Font.MONOSPACED, Font.PLAIN, 13);
 
    private final RegistradorRefactorizacion registrador = new RegistradorRefactorizacion();
 
    private JTextField               campoBuscarClase;
    private JTextField               campoBuscarMetodo;
    private DefaultListModel<String> modeloHistorial;
    private JList<String>            listaArchivos;
    private JTextField               campoNombreTest;
    private JTextField               campoRevisor;
    private JRadioButton             radioAprobado;
    private JRadioButton             radioFallido;
 
    private JTextArea                areaCode;
    private JLabel                   labelClaseDetectada;
    private JLabel                   labelMetodosCount;
    private DefaultListModel<String> modeloMetodosDetectados;
    private JList<String>            listaMetodosDetectados;
 
    private JLabel labelStatus;
 
    public void iniciarAplicacion() {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext",                "true");
 
        UIManager.put("ToolTip.background", BG_CARD);
        UIManager.put("ToolTip.foreground", TEXT_PRIMARY);
        UIManager.put("ToolTip.border",     BorderFactory.createLineBorder(ACCENT, 1));
        UIManager.put("ToolTip.font",       FONT_UI);
        ToolTipManager.sharedInstance().setInitialDelay(300);
        ToolTipManager.sharedInstance().setDismissDelay(8000);
 
        setTitle("Herramienta de Refactorizacion TDD");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1080, 720);
        setMinimumSize(new Dimension(900, 620));
        setLocationRelativeTo(null);
 
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG_DARK);
        root.setBorder(new EmptyBorder(20, 24, 16, 24));
        root.add(buildHeader(),    BorderLayout.NORTH);
        root.add(buildCenter(),    BorderLayout.CENTER);
        root.add(buildStatusBar(), BorderLayout.SOUTH);
        setContentPane(root);
        setVisible(true);
    }
 
    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_DARK);
        header.setBorder(new EmptyBorder(0, 0, 20, 0));
 
        JLabel titulo = new JLabel("Herramienta de Refactorizacion TDD");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(TEXT_PRIMARY);
 
        JLabel subtitulo = new JLabel("Registra, versiona y audita refactorizaciones con metadatos TDD");
        subtitulo.setFont(FONT_UI);
        subtitulo.setForeground(TEXT_SECONDARY);
 
        JPanel textos = new JPanel(new GridLayout(2, 1, 0, 3));
        textos.setBackground(BG_DARK);
        textos.add(titulo);
        textos.add(subtitulo);
        header.add(textos, BorderLayout.WEST);
        header.add(buildVersionBadge(), BorderLayout.EAST);
        return header;
    }
 
    private JPanel buildCenter() {
        JPanel split = new JPanel(new GridLayout(1, 2, 20, 0));
        split.setBackground(BG_DARK);
        split.add(buildPanelUS02());
        split.add(buildPanelUS01());
        return split;
    }
 
    private JPanel buildStatusBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(BG_DARK);
        bar.setBorder(new EmptyBorder(10, 0, 0, 0));
 
        JSeparator sep = new JSeparator();
        sep.setForeground(BORDER);
        bar.add(sep, BorderLayout.NORTH);
 
        labelStatus = new JLabel("  Listo");
        labelStatus.setFont(FONT_SMALL);
        labelStatus.setForeground(TEXT_SECONDARY);
        labelStatus.setBorder(new EmptyBorder(5, 0, 0, 0));
        bar.add(labelStatus, BorderLayout.CENTER);
        return bar;
    }
 
    private JPanel buildPanelUS02() {
        JPanel card = buildCard("US-02  —  Evaluacion TDD");
 
        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(BG_CARD);
        GridBagConstraints g = baseGBC();
 
        g.gridy = 0; g.insets = ins(0, 0, 8, 0);
        content.add(buildSectionLabel("Buscar historial"), g);
 
        campoBuscarClase = buildField("Clase");
        g.gridy = 1; g.insets = ins(0, 0, 6, 0);
        content.add(campoBuscarClase, g);
 
        campoBuscarMetodo = buildField("Metodo");
        g.gridy = 2; g.insets = ins(0, 0, 8, 0);
        content.add(campoBuscarMetodo, g);
 
        JButton btnConsultar = buildPrimaryButton("Consultar historial");
        btnConsultar.addActionListener(e -> onBotonConsultarClick());
        g.gridy = 3; g.insets = ins(0, 0, 12, 0);
        content.add(btnConsultar, g);
 
        g.gridy = 4; g.insets = ins(0, 0, 4, 0);
        content.add(buildSmallLabel("Archivos .md  —  un clic para ver el codigo  |  cursor para el veredicto TDD"), g);
 
        modeloHistorial = new DefaultListModel<>();
        listaArchivos = new JList<String>(modeloHistorial) {
            @Override
            public String getToolTipText(MouseEvent event) {
                int index = locationToIndex(event.getPoint());
                if (index < 0) return null;
                String archivo  = getModel().getElementAt(index);
                String clase    = campoBuscarClase.getText().trim();
                String metodo   = campoBuscarMetodo.getText().trim();
                if (clase.isEmpty() || metodo.isEmpty()) return null;
                String veredicto = registrador.leerVeredicto(clase + "/" + metodo + "/" + archivo);
                if (veredicto.startsWith("Sin metadatos")) return null;
                return "<html>" + veredicto.replace(" | ", "<br>") + "</html>";
            }
        };
        ToolTipManager.sharedInstance().registerComponent(listaArchivos);
 
        listaArchivos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = listaArchivos.locationToIndex(e.getPoint());
                if (index < 0) return;
                String archivo = modeloHistorial.getElementAt(index);
                String clase   = campoBuscarClase.getText().trim();
                String metodo  = campoBuscarMetodo.getText().trim();
                if (clase.isEmpty() || metodo.isEmpty()) {
                    setStatus("Ingresa clase y metodo antes de abrir el archivo.", ERROR);
                    return;
                }
                mostrarVisorArchivo(clase + "/" + metodo + "/" + archivo, archivo);
            }
        });
 
        listaArchivos.setBackground(BG_FIELD);
        listaArchivos.setForeground(TEXT_PRIMARY);
        listaArchivos.setFont(FONT_MONO);
        listaArchivos.setSelectionBackground(ACCENT);
        listaArchivos.setSelectionForeground(Color.WHITE);
        listaArchivos.setFixedCellHeight(28);
        listaArchivos.setBorder(new EmptyBorder(4, 10, 4, 10));
 
        JScrollPane scrollLista = new JScrollPane(listaArchivos);
        scrollLista.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        scrollLista.getViewport().setBackground(BG_FIELD);
 
        g.gridy   = 5;
        g.weighty = 1.0;
        g.fill    = GridBagConstraints.BOTH;
        g.insets  = ins(0, 0, 12, 0);
        content.add(scrollLista, g);
 
        g.gridy   = 6;
        g.weighty = 0;
        g.fill    = GridBagConstraints.HORIZONTAL;
        g.insets  = ins(0, 0, 12, 0);
        JSeparator sep = new JSeparator();
        sep.setForeground(BORDER);
        content.add(sep, g);
 
        g.gridy = 7; g.insets = ins(0, 0, 8, 0);
        content.add(buildSectionLabel("Evaluar archivo seleccionado"), g);
 
        campoNombreTest = buildField("Nombre del test");
        g.gridy = 8; g.insets = ins(0, 0, 6, 0);
        content.add(campoNombreTest, g);
 
        campoRevisor = buildField("Revisor");
        g.gridy = 9; g.insets = ins(0, 0, 8, 0);
        content.add(campoRevisor, g);
 
        g.gridy = 10; g.insets = ins(0, 0, 4, 0);
        content.add(buildSmallLabel("Estado del test"), g);
 
        g.gridy = 11; g.insets = ins(0, 0, 10, 0);
        content.add(buildRadioPanel(), g);
 
        JButton btnGuardar = buildSecondaryButton("Guardar veredicto");
        btnGuardar.addActionListener(e -> onBotonConsultarClick());
        g.gridy = 12; g.insets = ins(0, 0, 0, 0);
        content.add(btnGuardar, g);
 
        card.add(content, BorderLayout.CENTER);
        return card;
    }
 
    private JPanel buildPanelUS01() {
        JPanel card = buildCard("US-01  —  Registro de Refactorizacion");
 
        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(BG_CARD);
        GridBagConstraints g = baseGBC();
 
        g.gridy = 0; g.insets = ins(0, 0, 4, 0);
        content.add(buildSectionLabel("Codigo refactorizado"), g);
 
        g.gridy = 1; g.insets = ins(0, 0, 6, 0);
        content.add(buildSmallLabel("Pega la clase completa — todos los metodos se detectan automaticamente"), g);
 
        areaCode = new JTextArea();
        areaCode.setBackground(BG_FIELD);
        areaCode.setForeground(TEXT_PRIMARY);
        areaCode.setCaretColor(TEXT_PRIMARY);
        areaCode.setFont(FONT_MONO);
        areaCode.setBorder(new EmptyBorder(10, 10, 10, 10));
        areaCode.setLineWrap(true);
        areaCode.setWrapStyleWord(true);
        areaCode.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e)  { actualizarDeteccion(); }
            @Override public void removeUpdate(DocumentEvent e)  { actualizarDeteccion(); }
            @Override public void changedUpdate(DocumentEvent e) { actualizarDeteccion(); }
        });
 
        JScrollPane scrollCode = new JScrollPane(areaCode);
        scrollCode.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        scrollCode.getViewport().setBackground(BG_FIELD);
 
        g.gridy   = 2;
        g.weighty = 1.0;
        g.fill    = GridBagConstraints.BOTH;
        g.insets  = ins(0, 0, 14, 0);
        content.add(scrollCode, g);
 
        g.gridy   = 3;
        g.weighty = 0;
        g.fill    = GridBagConstraints.HORIZONTAL;
        g.insets  = ins(0, 0, 12, 0);
        JSeparator sep = new JSeparator();
        sep.setForeground(BORDER);
        content.add(sep, g);
 
        g.gridy = 4; g.insets = ins(0, 0, 8, 0);
        content.add(buildSectionLabel("Ubicacion estructural detectada"), g);
 
        labelClaseDetectada = buildDetectionLabel("—");
        g.gridy = 5; g.insets = ins(0, 0, 8, 0);
        content.add(buildDetectionRow("Clase", labelClaseDetectada), g);
 
        labelMetodosCount = buildSmallLabel("Metodos detectados (0)");
        g.gridy = 6; g.insets = ins(0, 0, 4, 0);
        content.add(labelMetodosCount, g);
 
        modeloMetodosDetectados = new DefaultListModel<>();
        listaMetodosDetectados  = new JList<>(modeloMetodosDetectados);
        listaMetodosDetectados.setBackground(BG_FIELD);
        listaMetodosDetectados.setForeground(SUCCESS);
        listaMetodosDetectados.setFont(FONT_MONO);
        listaMetodosDetectados.setFixedCellHeight(24);
        listaMetodosDetectados.setVisibleRowCount(3);
        listaMetodosDetectados.setBorder(new EmptyBorder(4, 10, 4, 10));
        listaMetodosDetectados.setFocusable(false);
 
        JScrollPane scrollMetodos = new JScrollPane(listaMetodosDetectados);
        scrollMetodos.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        scrollMetodos.getViewport().setBackground(BG_FIELD);
 
        g.gridy = 7; g.insets = ins(0, 0, 14, 0);
        content.add(scrollMetodos, g);
 
        JButton btnRegistrar = buildPrimaryButton("Registrar refactorizacion");
        btnRegistrar.addActionListener(e -> onBotonRegistrarClick());
        g.gridy = 8; g.insets = ins(0, 0, 0, 0);
        content.add(btnRegistrar, g);
 
        card.add(content, BorderLayout.CENTER);
        return card;
    }
 
    private void onBotonRegistrarClick() {
        String clase  = labelClaseDetectada.getText().trim();
        String codigo = areaCode.getText().trim();
 
        if (clase.equals("—") || clase.isEmpty()) {
            setStatus("No se detecto la clase. Verifica el codigo pegado.", ERROR);
            return;
        }
        if (codigo.isEmpty()) {
            setStatus("El area de codigo esta vacia.", ERROR);
            return;
        }
        if (modeloMetodosDetectados.isEmpty()) {
            setStatus("No se detectaron metodos. Verifica el codigo pegado.", ERROR);
            return;
        }
 
        int total      = modeloMetodosDetectados.getSize();
        int registrados = 0;
 
        for (int i = 0; i < total; i++) {
            String metodo       = modeloMetodosDetectados.getElementAt(i);
            String codigoMetodo = extraerMetodo(codigo, metodo);
            boolean ok          = registrador.registrar(clase, metodo, codigoMetodo, false, "pendiente");
            if (ok) registrados++;
        }
 
        if (registrados == total) {
            setStatus(registrados + " metodo(s) registrados en " + clase + "/", SUCCESS);
            limpiarFormularioRegistro();
        } else {
            setStatus("Registrados " + registrados + " de " + total + " metodos.", TEXT_SECONDARY);
        }
    }
 
    private void onBotonConsultarClick() {
        String clase   = campoBuscarClase.getText().trim();
        String metodo  = campoBuscarMetodo.getText().trim();
        String archivo = listaArchivos.getSelectedValue();
        String test    = campoNombreTest.getText().trim();
        String revisor = campoRevisor.getText().trim();
 
        if (archivo != null && !test.isBlank() && !revisor.isBlank()) {
            if (clase.isEmpty() || metodo.isEmpty()) {
                setStatus("Ingresa clase y metodo para ubicar el archivo.", ERROR);
                return;
            }
            boolean ok = registrador.evaluarArchivo(
                    clase + "/" + metodo + "/" + archivo,
                    test, radioAprobado.isSelected(), revisor);
            if (ok) {
                setStatus("Veredicto guardado: " + archivo, SUCCESS);
                limpiarFormularioVeredicto();
            } else {
                setStatus("Error al guardar el veredicto.", ERROR);
            }
            return;
        }
 
        if (clase.isEmpty() || metodo.isEmpty()) {
            setStatus("Ingresa clase y metodo para consultar el historial.", ERROR);
            return;
        }
        List<String> archivos = registrador.consultarHistorial(clase, metodo);
        modeloHistorial.clear();
        archivos.forEach(modeloHistorial::addElement);
 
        if (archivos.isEmpty()) {
            setStatus("Sin registros para " + clase + "/" + metodo, TEXT_SECONDARY);
        } else {
            setStatus("Se encontraron " + archivos.size() + " version(es) para " + clase + "/" + metodo, SUCCESS);
        }
    }
 
    private void mostrarVisorArchivo(String rutaCompleta, String nombreArchivo) {
        JDialog visor = new JDialog(this, nombreArchivo, false);
        visor.setSize(640, 520);
        visor.setLocationRelativeTo(this);
        visor.setResizable(true);
 
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG_DARK);
        root.setBorder(new EmptyBorder(16, 18, 14, 18));
 
        JLabel lblNombre = new JLabel(nombreArchivo);
        lblNombre.setFont(FONT_BOLD);
        lblNombre.setForeground(TEXT_PRIMARY);
 
        JLabel lblRuta = new JLabel(rutaCompleta);
        lblRuta.setFont(FONT_SMALL);
        lblRuta.setForeground(TEXT_SECONDARY);
 
        JPanel header = new JPanel(new GridLayout(2, 1, 0, 2));
        header.setBackground(BG_DARK);
        header.add(lblNombre);
        header.add(lblRuta);
        header.setBorder(new EmptyBorder(0, 0, 10, 0));
        root.add(header, BorderLayout.NORTH);
 
        String contenido = registrador.leerContenido(rutaCompleta);
 
        JTextArea area = new JTextArea(contenido);
        area.setEditable(false);
        area.setBackground(BG_FIELD);
        area.setForeground(TEXT_PRIMARY);
        area.setFont(FONT_MONO);
        area.setBorder(new EmptyBorder(12, 12, 12, 12));
        area.setLineWrap(false);
 
        JScrollPane scroll = new JScrollPane(area);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        scroll.getViewport().setBackground(BG_FIELD);
        root.add(scroll, BorderLayout.CENTER);
 
        String veredicto    = registrador.leerVeredicto(rutaCompleta);
        JLabel lblVeredicto = new JLabel(
                veredicto.startsWith("Sin metadatos") ? "Sin evaluacion TDD" : veredicto);
        lblVeredicto.setFont(FONT_SMALL);
        lblVeredicto.setForeground(
                veredicto.startsWith("Sin metadatos") ? TEXT_SECONDARY
              : veredicto.contains("Aprobado")        ? SUCCESS : ERROR);
 
        JButton btnCerrar = buildSecondaryButton("Cerrar");
        btnCerrar.setPreferredSize(new Dimension(100, 34));
        btnCerrar.addActionListener(e -> visor.dispose());
 
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(BG_DARK);
        footer.setBorder(new EmptyBorder(10, 0, 0, 0));
        footer.add(lblVeredicto, BorderLayout.WEST);
        footer.add(btnCerrar,   BorderLayout.EAST);
        root.add(footer, BorderLayout.SOUTH);
 
        visor.setContentPane(root);
        visor.setVisible(true);
    }
 
    private void actualizarDeteccion() {
        String       codigo  = areaCode.getText();
        String       clase   = detectarClase(codigo);
        List<String> metodos = detectarMetodos(codigo);
 
        labelClaseDetectada.setText(clase.isEmpty() ? "—" : clase);
        labelClaseDetectada.setForeground(clase.isEmpty() ? TEXT_SECONDARY : SUCCESS);
 
        modeloMetodosDetectados.clear();
        metodos.forEach(modeloMetodosDetectados::addElement);
 
        labelMetodosCount.setText("Metodos detectados (" + metodos.size() + ")");
        labelMetodosCount.setForeground(metodos.isEmpty() ? TEXT_SECONDARY : TEXT_PRIMARY);
    }
 
    private String detectarClase(String codigo) {
        for (String linea : codigo.split("\n")) {
            String t = linea.trim();
            if (t.contains("class ")) {
                int idx = t.indexOf("class ") + 6;
                int end = t.length();
                for (char c : new char[]{ ' ', '{', '<', '\t' }) {
                    int i = t.indexOf(c, idx);
                    if (i > idx && i < end) end = i;
                }
                String nombre = t.substring(idx, end).trim();
                if (!nombre.isEmpty()) return nombre;
            }
        }
        return "";
    }
 
    private List<String> detectarMetodos(String codigo) {
        List<String> metodos      = new ArrayList<>();
        String[]     modificadores = { "public ", "private ", "protected " };
 
        for (String linea : codigo.split("\n")) {
            String t = linea.trim();
            if (t.startsWith("//") || t.startsWith("*") || t.startsWith("@")) continue;
            if (!t.contains("(")) continue;
            for (String mod : modificadores) {
                if (t.contains(mod)) {
                    int paren = t.indexOf("(");
                    if (paren > 0) {
                        String[] parts = t.substring(0, paren).trim().split("\\s+");
                        if (parts.length > 0) {
                            String nombre = parts[parts.length - 1];
                            if (!nombre.isEmpty() && !metodos.contains(nombre)) {
                                metodos.add(nombre);
                            }
                        }
                    }
                    break;
                }
            }
        }
        return metodos;
    }
 
    private String extraerMetodo(String codigo, String nombreMetodo) {
        String[]      lineas               = codigo.split("\n");
        StringBuilder resultado            = new StringBuilder();
        boolean       dentroMetodo         = false;
        int           nivelLlaves          = 0;
        boolean       encontroLlaveInicial = false;
 
        for (String linea : lineas) {
            String t = linea.trim();
 
            if (!dentroMetodo) {
                if (t.contains(nombreMetodo + "(") &&
                    (t.contains("public ") || t.contains("private ") || t.contains("protected "))) {
                    dentroMetodo = true;
                }
            }
 
            if (dentroMetodo) {
                resultado.append(linea).append("\n");
                for (char c : linea.toCharArray()) {
                    if (c == '{') { nivelLlaves++; encontroLlaveInicial = true; }
                    if (c == '}') nivelLlaves--;
                }
                if (encontroLlaveInicial && nivelLlaves == 0) break;
            }
        }
 
        String extraido = resultado.toString().trim();
        return extraido.isEmpty() ? codigo : extraido;
    }
 
    private JPanel buildCard(String titulo) {
        JPanel card = new JPanel(new BorderLayout(0, 12));
        card.setBackground(BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                new EmptyBorder(18, 18, 18, 18)));
 
        JPanel cardHeader = new JPanel(new BorderLayout(0, 8));
        cardHeader.setBackground(BG_CARD);
        JLabel lbl = new JLabel(titulo);
        lbl.setFont(FONT_TITLE);
        lbl.setForeground(TEXT_PRIMARY);
        cardHeader.add(lbl, BorderLayout.CENTER);
        JSeparator sep = new JSeparator();
        sep.setForeground(BORDER);
        cardHeader.add(sep, BorderLayout.SOUTH);
        card.add(cardHeader, BorderLayout.NORTH);
        return card;
    }
 
    private JPanel buildVersionBadge() {
        JLabel badge = new JLabel("v 1.0", SwingConstants.CENTER);
        badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
        badge.setForeground(ACCENT);
        badge.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT, 1),
                new EmptyBorder(4, 12, 4, 12)));
        JPanel wrap = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 6));
        wrap.setBackground(BG_DARK);
        wrap.add(badge);
        return wrap;
    }
 
    private JLabel buildSectionLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(FONT_BOLD);
        lbl.setForeground(TEXT_PRIMARY);
        return lbl;
    }
 
    private JLabel buildSmallLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(FONT_SMALL);
        lbl.setForeground(TEXT_SECONDARY);
        return lbl;
    }
 
    private JLabel buildDetectionLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(FONT_BOLD);
        lbl.setForeground(TEXT_SECONDARY);
        return lbl;
    }
 
    private JPanel buildDetectionRow(String tag, JLabel valueLabel) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBackground(BG_CARD);
        JLabel tagLabel = new JLabel(tag + ":");
        tagLabel.setFont(FONT_SMALL);
        tagLabel.setForeground(TEXT_SECONDARY);
        tagLabel.setPreferredSize(new Dimension(55, 22));
        JPanel pill = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 4));
        pill.setBackground(BG_FIELD);
        pill.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        pill.add(valueLabel);
        row.add(tagLabel, BorderLayout.WEST);
        row.add(pill,     BorderLayout.CENTER);
        return row;
    }
 
    private JTextField buildField(String placeholder) {
        JTextField f = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty() && !isFocusOwner()) {
                    g.setColor(new Color(100, 100, 145));
                    g.setFont(getFont().deriveFont(Font.ITALIC));
                    g.drawString(placeholder,
                            getInsets().left + 2,
                            getHeight() / 2 + getFont().getSize() / 2 - 2);
                }
            }
        };
        f.setBackground(BG_FIELD);
        f.setForeground(TEXT_PRIMARY);
        f.setCaretColor(TEXT_PRIMARY);
        f.setFont(FONT_UI);
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                new EmptyBorder(8, 10, 8, 10)));
        f.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                f.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(ACCENT, 1),
                        new EmptyBorder(8, 10, 8, 10)));
            }
            @Override public void focusLost(FocusEvent e) {
                f.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER, 1),
                        new EmptyBorder(8, 10, 8, 10)));
            }
        });
        return f;
    }
 
    private JPanel buildRadioPanel() {
        radioAprobado = new JRadioButton("Aprobado");
        radioAprobado.setBackground(BG_CARD);
        radioAprobado.setForeground(SUCCESS);
        radioAprobado.setFont(FONT_BOLD);
        radioAprobado.setFocusPainted(false);
        radioAprobado.setSelected(true);
 
        radioFallido = new JRadioButton("Fallido");
        radioFallido.setBackground(BG_CARD);
        radioFallido.setForeground(ERROR);
        radioFallido.setFont(FONT_BOLD);
        radioFallido.setFocusPainted(false);
 
        ButtonGroup grp = new ButtonGroup();
        grp.add(radioAprobado);
        grp.add(radioFallido);
 
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        p.setBackground(BG_CARD);
        p.add(radioAprobado);
        p.add(Box.createHorizontalStrut(20));
        p.add(radioFallido);
        return p;
    }
 
    private JButton buildPrimaryButton(String texto) {
        return buildButtonInternal(texto, ACCENT, ACCENT_HOVER, FONT_BOLD, 42);
    }
 
    private JButton buildSecondaryButton(String texto) {
        return buildButtonInternal(texto,
                new Color(48, 48, 72), new Color(62, 62, 90), FONT_UI, 36);
    }
 
    private JButton buildButtonInternal(String texto, Color base, Color hover,
                                         Font font, int height) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isPressed()  ? base.darker()
                           : getModel().isRollover() ? hover : base);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.setColor(Color.WHITE);
                g2.setFont(font);
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth()  - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        btn.setFont(font);
        btn.setPreferredSize(new Dimension(0, height));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
 
    private GridBagConstraints baseGBC() {
        GridBagConstraints g = new GridBagConstraints();
        g.fill    = GridBagConstraints.HORIZONTAL;
        g.weightx = 1.0;
        g.gridx   = 0;
        return g;
    }
 
    private Insets ins(int top, int left, int bottom, int right) {
        return new Insets(top, left, bottom, right);
    }
 
    private void setStatus(String msg, Color color) {
        labelStatus.setText("  " + msg);
        labelStatus.setForeground(color);
    }
 
    private void limpiarFormularioRegistro() {
        areaCode.setText("");
        modeloMetodosDetectados.clear();
        labelClaseDetectada.setText("—");
        labelClaseDetectada.setForeground(TEXT_SECONDARY);
        labelMetodosCount.setText("Metodos detectados (0)");
        labelMetodosCount.setForeground(TEXT_SECONDARY);
    }
 
    private void limpiarFormularioVeredicto() {
        campoNombreTest.setText("");
        campoRevisor.setText("");
        radioAprobado.setSelected(true);
    }
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            new InterfazPrincipalGUI().iniciarAplicacion();
        });
    }
}