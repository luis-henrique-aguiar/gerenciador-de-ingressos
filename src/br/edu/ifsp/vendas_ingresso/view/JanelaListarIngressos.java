package br.edu.ifsp.vendas_ingresso.view;

import br.edu.ifsp.vendas_ingresso.model.data.IngressoDAO;
import br.edu.ifsp.vendas_ingresso.model.entity.Ingresso;
import br.edu.ifsp.vendas_ingresso.view.ExcluirIngressoAction;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;

public class JanelaListarIngressos extends JDialog {

    private JTable tabelaIngressos;
    private DefaultTableModel modelo;
    private JScrollPane scroll;

    public JanelaListarIngressos() {
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
    }

    private void criarTabela() {
        tabelaIngressos = new JTable(modelo);
        tabelaIngressos.setRowHeight(30);
        tabelaIngressos.setFont(new Font("SansSerif", Font.PLAIN, 13));

        modelo.addColumn("Código");
        modelo.addColumn("Nome");
        modelo.addColumn("Tipo");
        modelo.addColumn("Qtd");
        modelo.addColumn("Setor");
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
        scroll = new JScrollPane(tabelaIngressos);
        JPanel painelFundo = new JPanel(new BorderLayout());
        painelFundo.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        painelFundo.setBackground(new Color(250, 250, 250));
        painelFundo.add(scroll, BorderLayout.CENTER);

        setContentPane(painelFundo);
        setTitle("Lista de Ingressos");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
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

            if ("Excluir".equals(label)) {
                int row = tabelaIngressos.getSelectedRow();
                if (row != -1) {
                    int codigo = (int) tabelaIngressos.getValueAt(row, 0);
                    ExcluirIngressoAction excluirAction = new ExcluirIngressoAction(JanelaListarIngressos.this);
                    boolean foiExcluido = excluirAction.executarExclusao(codigo);

                    if (foiExcluido) {
                        imprimirIngressos(IngressoDAO.getInstance().getIngressos());
                    }
                }
            }
        }
    }
}
