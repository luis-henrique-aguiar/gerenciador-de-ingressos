package br.edu.ifsp.vendas_ingresso.view;

import br.edu.ifsp.vendas_ingresso.model.entity.Ingresso;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class JanelaRelatorio extends JDialog {

    private JPanel painelFundo;
    private JTable tabelaIngressos;
    private JScrollPane scroll;
    private DefaultTableModel modelo;
    private JLabel lblTitulo;
    private JButton btnVoltar;
    private final TelaInicial telaInicial;

    public JanelaRelatorio(TelaInicial telaInicial) {
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

        modelo = new DefaultTableModel();
        criarTabela();
        criarComponentes();
        setOnClickListener();
    }

    private void criarTabela() {
        tabelaIngressos = new JTable(modelo);
        tabelaIngressos.setRowHeight(28);
        tabelaIngressos.setFont(new Font("Arial", Font.PLAIN, 13));
        tabelaIngressos.setSelectionBackground(new Color(200, 220, 255));
        tabelaIngressos.setGridColor(new Color(220, 220, 220));

        modelo.addColumn("Código");
        modelo.addColumn("Nome");
        modelo.addColumn("Setor");
        modelo.addColumn("Qtd");
        modelo.addColumn("Valor");
        modelo.addColumn("Total");
        modelo.addColumn("Data e Hora");

        tabelaIngressos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabelaIngressos.getTableHeader().setBackground(new Color(200, 200, 255));

        // Ajustar larguras das colunas
        tabelaIngressos.getColumnModel().getColumn(0).setPreferredWidth(50);  // Código
        tabelaIngressos.getColumnModel().getColumn(1).setPreferredWidth(150); // Nome
        tabelaIngressos.getColumnModel().getColumn(2).setPreferredWidth(80);  // Setor
        tabelaIngressos.getColumnModel().getColumn(3).setPreferredWidth(40);  // Qtd
        tabelaIngressos.getColumnModel().getColumn(4).setPreferredWidth(80);  // Valor
        tabelaIngressos.getColumnModel().getColumn(5).setPreferredWidth(80);  // Total
        tabelaIngressos.getColumnModel().getColumn(6).setPreferredWidth(120); // Data e Hora
    }

    private void criarComponentes() {
        setTitle("Relatório de Ingressos");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(800, 500));
        setLocationRelativeTo(null);

        painelFundo = new JPanel(new BorderLayout(10, 10));
        painelFundo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelFundo.setBackground(new Color(240, 240, 240));

        lblTitulo = new JLabel("Relatório de Ingressos", SwingConstants.CENTER);
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

    public void imprimirRelatorio(ArrayList<Ingresso> ingressos) {
        modelo.setRowCount(0);
        if (ingressos != null && !ingressos.isEmpty()) {
            for (Ingresso c : ingressos) {
                modelo.addRow(new Object[]{
                        c.getCodigo(),
                        c.getNome(),
                        c.getSetor(),
                        c.getQuantidade(),
                        String.format("R$ %.2f", c.getValor()),
                        String.format("R$ %.2f", c.getValorTotal()),
                        c.getDataHora()
                });
            }
        }
    }
}