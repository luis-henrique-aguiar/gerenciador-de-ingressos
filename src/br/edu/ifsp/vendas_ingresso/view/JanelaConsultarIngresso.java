package br.edu.ifsp.vendas_ingresso.view;

import br.edu.ifsp.vendas_ingresso.model.data.IngressoDAO;
import br.edu.ifsp.vendas_ingresso.model.entity.Ingresso;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;


public class JanelaConsultarIngresso extends JDialog{

    private JTable tabelaIngressos;
    private DefaultTableModel modelo;
    private JScrollPane scroll;

    private JTextField txtFiltroCodigo;
    private JButton btnBuscar;
    private JComboBox<String> comboCor;

    public JanelaConsultarIngresso() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5 || column == 6;
            }
        };

        criarTabela();
        criarComponentes();
        imprimirIngressos(IngressoDAO.getInstance().getIngressos());
    }

    private void criarTabela() {
        tabelaIngressos = new JTable(modelo);
        tabelaIngressos.setRowHeight(30);
        tabelaIngressos.setFont(new Font("SansSerif", Font.PLAIN, 13));

        modelo.addColumn("Código");
        modelo.addColumn("Nome");
        modelo.addColumn("Tipo");
        modelo.addColumn("Qtd");
        modelo.addColumn("Setor"); // Considerando o setor como representante da cor
        modelo.addColumn("Editar");
        modelo.addColumn("Excluir");

        tabelaIngressos.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        tabelaIngressos.getTableHeader().setBackground(new Color(220, 220, 255));

        tabelaIngressos.getColumn("Editar").setCellRenderer(new ButtonRenderer("Editar"));
        tabelaIngressos.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox(), "Editar"));

        tabelaIngressos.getColumn("Excluir").setCellRenderer(new ButtonRenderer("Excluir"));
        tabelaIngressos.getColumn("Excluir").setCellEditor(new ButtonEditor(new JCheckBox(), "Excluir"));
    }

    private void criarComponentes() {

        JPanel painelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        JLabel lblCodigo = new JLabel("Código:");
        txtFiltroCodigo = new JTextField(10);
        btnBuscar = new JButton("Buscar");

        txtFiltroCodigo.addActionListener(e -> filtrarIngressos());
        btnBuscar.addActionListener(e -> filtrarIngressos());

        JLabel lblCor = new JLabel("Cor:");
        comboCor = new JComboBox<>(new String[]{"Todos", "Amarelo", "Azul", "Branco", "Verde"});
        comboCor.addActionListener(e -> filtrarIngressos());

        painelFiltros.add(lblCodigo);
        painelFiltros.add(txtFiltroCodigo);
        painelFiltros.add(btnBuscar);
        painelFiltros.add(Box.createHorizontalStrut(20));  // Espaço entre os filtros
        painelFiltros.add(lblCor);
        painelFiltros.add(comboCor);

        scroll = new JScrollPane(tabelaIngressos);
        JPanel painelFundo = new JPanel(new BorderLayout());
        painelFundo.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        painelFundo.setBackground(new Color(250, 250, 250));

        painelFundo.add(painelFiltros, BorderLayout.NORTH);
        painelFundo.add(scroll, BorderLayout.CENTER);

        setContentPane(painelFundo);
        setTitle("Lista de Ingressos");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void filtrarIngressos() {
        String codigoText = txtFiltroCodigo.getText().trim();
        String corSelecionada = ((String) comboCor.getSelectedItem()).trim();

        ArrayList<Ingresso> todosIngressos = IngressoDAO.getInstance().getIngressos();
        ArrayList<Ingresso> filtrados = new ArrayList<>();

        for (Ingresso ingresso : todosIngressos) {
            boolean atendeCodigo = true;
            boolean atendeCor = true;

            if (!codigoText.isEmpty()) {
                try {
                    int codigoFiltro = Integer.parseInt(codigoText);
                    atendeCodigo = (ingresso.getCodigo() == codigoFiltro);
                } catch (NumberFormatException e) {
                    atendeCodigo = false;
                }
            }

            if (!corSelecionada.equalsIgnoreCase("Todos")) {
                if (ingresso.getSetor() == null ||
                        !ingresso.getSetor().toString().equalsIgnoreCase(corSelecionada)) {
                    atendeCor = false;
                }
            }

            if (atendeCodigo && atendeCor) {
                filtrados.add(ingresso);
            }
        }

        if (filtrados.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nenhum ingresso encontrado.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }

        imprimirIngressos(filtrados);
    }

    public void imprimirIngressos(ArrayList<Ingresso> ingressos) {
        modelo.setRowCount(0);
        for (Ingresso i : ingressos) {
            modelo.addRow(new Object[]{
                    i.getCodigo(),
                    i.getNome(),
                    i.getTipoIngresso(),
                    i.getQuantidade(),
                    i.getSetor(),
                    "Editar",
                    "Excluir"
            });
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer(String label) {
            setText(label);
            setFont(new Font("Arial", Font.BOLD, 12));
            setBackground(new Color(100, 150, 255));
            setForeground(Color.WHITE);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            setText((value != null) ? value.toString() : "");
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean clicked;

        public ButtonEditor(JCheckBox checkBox, String actionType) {
            super(checkBox);
            button = new JButton();
            button.setFont(new Font("Arial", Font.BOLD, 12));
            button.setBackground(new Color(100, 100, 200));
            button.setForeground(Color.WHITE);
            button.setOpaque(true);

            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            label = (value != null) ? value.toString() : "";
            button.setText(label);
            clicked = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            clicked = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }
}
