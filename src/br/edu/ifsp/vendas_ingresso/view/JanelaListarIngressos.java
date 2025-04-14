package br.edu.ifsp.vendas_ingresso.view;

import br.edu.ifsp.vendas_ingresso.model.entity.Ingresso;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;

public class JanelaListarIngressos extends JDialog {
    private JTable tabelaIngressos;
    private DefaultTableModel modelo;
    private JScrollPane scroll;

    public JanelaListarIngressos() {
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Permite edição apenas nas colunas dos botões
                return column == 5 || column == 6;
            }
        };

        criarTabela();
        criarComponentes();
    }

    private void criarTabela() {
        tabelaIngressos = new JTable(modelo);
        tabelaIngressos.setRowHeight(30);

        modelo.addColumn("Código");
        modelo.addColumn("Nome");
        modelo.addColumn("Tipo");
        modelo.addColumn("Qtd");
        modelo.addColumn("Setor");
        modelo.addColumn("Editar");
        modelo.addColumn("Excluir");

        tabelaIngressos.getColumn("Editar").setCellRenderer(new ButtonRenderer("Editar"));
        tabelaIngressos.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox(), "Editar"));

        tabelaIngressos.getColumn("Excluir").setCellRenderer(new ButtonRenderer("Excluir"));
        tabelaIngressos.getColumn("Excluir").setCellEditor(new ButtonEditor(new JCheckBox(), "Excluir"));
    }

    private void criarComponentes() {
        scroll = new JScrollPane(tabelaIngressos);

        JPanel painelFundo = new JPanel(new BorderLayout());
        painelFundo.add(scroll, BorderLayout.CENTER);

        setContentPane(painelFundo);
        setTitle("Ingressos");
        setSize(800, 600);
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

    // Renderizador de botão
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer(String label) {
            setText(label);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            setText((value != null) ? value.toString() : "");
            return this;
        }
    }

    // Editor de botão (sem ação por enquanto)
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean clicked;

        public ButtonEditor(JCheckBox checkBox, String actionType) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);

            button.addActionListener(e -> fireEditingStopped()); // Só para encerrar edição
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
