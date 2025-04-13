package br.edu.ifsp.vendas_ingresso.view;

import br.edu.ifsp.vendas_ingresso.model.entity.Ingresso;

import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

class JanelaRelatorio extends JDialog {

    private JPanel painelFundo;
    private JPanel painelBotoes;
    private JTable tabelaIngressos;
    private JScrollPane scroll;
    private DefaultTableModel modelo;//Modelo da tabela

    public JanelaRelatorio() {
        modelo = new DefaultTableModel();
        criarTabela();
        criarComponentes();
    }

    private void criarTabela() {
        tabelaIngressos = new JTable(modelo);
        tabelaIngressos.setSize(700, 800);

        modelo.addColumn("Código");
        modelo.addColumn("Nome");
        modelo.addColumn("Setor");
        modelo.addColumn("Qtd");
        modelo.addColumn("Valor");
        modelo.addColumn("Total");
        modelo.addColumn("Data e Hora");

        tabelaIngressos.getColumnModel().getColumn(0)
                .setPreferredWidth(5);
        tabelaIngressos.getColumnModel().getColumn(1)
                .setPreferredWidth(70);
        tabelaIngressos.getColumnModel().getColumn(2)
                .setPreferredWidth(20);
        tabelaIngressos.getColumnModel().getColumn(3)
                .setPreferredWidth(1);
        tabelaIngressos.getColumnModel().getColumn(4)
                .setPreferredWidth(15);
        tabelaIngressos.getColumnModel().getColumn(5)
                .setPreferredWidth(15);
        tabelaIngressos.getColumnModel().getColumn(6)
                .setPreferredWidth(70);
    }

    private void criarComponentes() {
        painelBotoes = new JPanel();
        scroll = new JScrollPane(tabelaIngressos);
        painelFundo = new JPanel();
        painelFundo.add(scroll);
        painelFundo.add(painelBotoes);
        add(painelFundo);

        setTitle("Ingressos");
        setVisible(true);
        pack();
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void carregarDados(DefaultTableModel modelo, ArrayList<Ingresso> ingressos) {

        modelo.setNumRows(0);
        if (ingressos != null && !ingressos.isEmpty()) {
            ingressos.forEach(c -> modelo.addRow(new Object[]{
                    c.getCodigo(),
                    c.getNome(),
                    c.getSetor(),
                    c.getQuantidade(),
                    c.getValor(),
                    c.getValorTotal(),
                    c.getDataHora()
            }));
        }
    }

    public void imprimirRelatorio(ArrayList<Ingresso> ingressos) {
        carregarDados(modelo, ingressos);
    }

}
