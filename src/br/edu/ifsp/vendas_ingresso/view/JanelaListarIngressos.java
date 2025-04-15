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
    private JPanel painelFundo;
    private JLabel lblTitulo;
    private JButton btnVoltar;
    private final TelaInicial telaInicial;
    private final IngressoDAO gerenciador = IngressoDAO.getInstance();

    public JanelaListarIngressos(TelaInicial telaInicial) {
        this.telaInicial = telaInicial;
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5 || column == 6;
            }
        };

        criarTabela();
        criarComponentes();
        setOnClickListener();
    }

    private void criarTabela() {
        tabelaIngressos = new JTable(modelo);
        tabelaIngressos.setRowHeight(30);
        tabelaIngressos.setFont(new Font("Arial", Font.PLAIN, 12));
        tabelaIngressos.setSelectionBackground(new Color(200, 220, 255));
        tabelaIngressos.setGridColor(new Color(220, 220, 220));

        modelo.addColumn("Código");
        modelo.addColumn("Nome");
        modelo.addColumn("Tipo");
        modelo.addColumn("Qtd");
        modelo.addColumn("Setor");
        modelo.addColumn("Editar");
        modelo.addColumn("Excluir");

        tabelaIngressos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabelaIngressos.getTableHeader().setBackground(new Color(220, 220, 255));

        tabelaIngressos.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabelaIngressos.getColumnModel().getColumn(1).setPreferredWidth(150);
        tabelaIngressos.getColumnModel().getColumn(2).setPreferredWidth(80);
        tabelaIngressos.getColumnModel().getColumn(3).setPreferredWidth(40);
        tabelaIngressos.getColumnModel().getColumn(4).setPreferredWidth(80);
        tabelaIngressos.getColumnModel().getColumn(5).setPreferredWidth(60);
        tabelaIngressos.getColumnModel().getColumn(6).setPreferredWidth(60);

        tabelaIngressos.getColumn("Editar").setCellRenderer(new ButtonRenderer("Editar", new Color(50, 100, 200)));
        tabelaIngressos.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox(), "Editar", this));

        tabelaIngressos.getColumn("Excluir").setCellRenderer(new ButtonRenderer("Excluir", new Color(200, 50, 50)));
        tabelaIngressos.getColumn("Excluir").setCellEditor(new ButtonEditor(new JCheckBox(), "Excluir", this));
    }

    private void criarComponentes() {
        setTitle("Lista de Ingressos");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(700, 500));
        setLocationRelativeTo(null);

        painelFundo = new JPanel(new BorderLayout(10, 10));
        painelFundo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelFundo.setBackground(new Color(240, 240, 240));

        lblTitulo = new JLabel("Lista de Ingressos Cadastrados", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(30, 100, 200));
        painelFundo.add(lblTitulo, BorderLayout.NORTH);

        scroll = new JScrollPane(tabelaIngressos);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        painelFundo.add(scroll, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.setOpaque(false);
        btnVoltar = new JButton("Voltar");
        estilizarBotao(btnVoltar, new Color(200, 50, 50));
        btnVoltar.setToolTipText("Retornar à tela inicial");
        painelBotoes.add(btnVoltar);
        painelFundo.add(painelBotoes, BorderLayout.SOUTH);

        setContentPane(painelFundo);
        pack();
        setVisible(true);
    }

    private void estilizarBotao(JButton botao, Color corFundo) {
        botao.setFont(new Font("Arial", Font.PLAIN, 14));
        botao.setBackground(corFundo);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setOpaque(true);
        botao.setContentAreaFilled(true);
    }

    private void setOnClickListener() {
        btnVoltar.addActionListener(e -> {
            telaInicial.setVisible(true);
            dispose();
        });
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
        public ButtonRenderer(String label, Color corFundo) {
            setText(label);
            estilizarBotao(this, corFundo);
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
        private int row;
        private final JanelaListarIngressos janela;

        public ButtonEditor(JCheckBox checkBox, String actionType, JanelaListarIngressos janela) {
            super(checkBox);
            this.janela = janela;
            button = new JButton();
            estilizarBotao(button, actionType.equals("Editar") ? new Color(50, 100, 200) : new Color(200, 50, 50));
            button.setOpaque(true);

            button.addActionListener(e -> {
                if (clicked) {
                    int codigo = (int) tabelaIngressos.getValueAt(row, 0);
                    if (actionType.equals("Editar")) {
                        Ingresso ingresso = gerenciador.buscarPorCodigo(codigo);
                        if (ingresso != null) {
                            new JanelaCadastroIngresso(telaInicial, ingresso).setVisible(true);
                            janela.dispose();
                        } else {
                            JOptionPane.showMessageDialog(janela, "Ingresso não encontrado!");
                        }
                    } else if (actionType.equals("Excluir")) {
                        ExcluirIngressoAction excluirAction = new ExcluirIngressoAction(janela);
                        boolean foiExcluido = excluirAction.executarExclusao(codigo);
                        if (foiExcluido) {
                            janela.imprimirIngressos(gerenciador.getIngressos());
                        }
                    }
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            this.row = row;
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
    }
}