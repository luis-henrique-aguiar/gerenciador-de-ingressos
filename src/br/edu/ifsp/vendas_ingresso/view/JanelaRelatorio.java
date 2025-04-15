package br.edu.ifsp.vendas_ingresso.view;

import br.edu.ifsp.vendas_ingresso.model.entity.Ingresso;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

class JanelaRelatorio extends JDialog {

    private JPanel painelFundo;
    private JTable tabelaIngressos;
    private JScrollPane scroll;
    private DefaultTableModel modelo;

    public JanelaRelatorio() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        modelo = new DefaultTableModel();
        criarTabela();
        criarComponentes();
    }

    private void criarTabela() {
        tabelaIngressos = new JTable(modelo);
        tabelaIngressos.setRowHeight(28);
        tabelaIngressos.setFont(new Font("SansSerif", Font.PLAIN, 13));

        modelo.addColumn("Código");
        modelo.addColumn("Nome");
        modelo.addColumn("Setor");
        modelo.addColumn("Qtd");
        modelo.addColumn("Valor");
        modelo.addColumn("Total");
        modelo.addColumn("Data e Hora");

        tabelaIngressos.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        tabelaIngressos.getTableHeader().setBackground(new Color(200, 200, 255));
    }

    private void criarComponentes() {
        scroll = new JScrollPane(tabelaIngressos);

        painelFundo = new JPanel(new BorderLayout());
        painelFundo.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        painelFundo.setBackground(new Color(245, 245, 245));
        painelFundo.add(scroll, BorderLayout.CENTER);

        setContentPane(painelFundo);
        setTitle("Relatório de Ingressos");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
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
