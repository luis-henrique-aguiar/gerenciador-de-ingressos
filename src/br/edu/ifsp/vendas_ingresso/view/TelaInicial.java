package br.edu.ifsp.vendas_ingresso.view;

import br.edu.ifsp.vendas_ingresso.model.data.IngressoDAO;
import br.edu.ifsp.vendas_ingresso.model.entity.Ingresso;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TelaInicial extends JDialog {

    private JPanel painelFundo;
    private JButton btnComprar;
    private JButton btnGerarRelatorio;
    private JButton btnListarIngressos;
    private JButton btnSair;

    IngressoDAO gerenciador = IngressoDAO.getInstance();
    ArrayList<Ingresso> ingressos = gerenciador.getIngressos();

    public TelaInicial() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Sistema de Ingressos");
        criarComponentesTela();
        setOnClickListener();
    }

    private void criarComponentesTela() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        painelFundo = new JPanel(new BorderLayout(10, 10));
        painelFundo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelFundo.setBackground(new Color(240, 240, 240));

        JLabel titulo = new JLabel("Bem-vindo!", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(new Color(30, 100, 200));
        painelFundo.add(titulo, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel(new GridBagLayout());
        painelBotoes.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        btnComprar = new JButton("Comprar Ingresso");
        estilizarBotao(btnComprar, new Color(50, 150, 50));
        gbc.gridy = 0;
        painelBotoes.add(btnComprar, gbc);

        btnGerarRelatorio = new JButton("Gerar Relatório");
        estilizarBotao(btnGerarRelatorio, new Color(200, 150, 50));
        gbc.gridy = 1;
        painelBotoes.add(btnGerarRelatorio, gbc);

        btnListarIngressos = new JButton("Listar Ingressos");
        estilizarBotao(btnListarIngressos, new Color(50, 100, 200));
        gbc.gridy = 2;
        painelBotoes.add(btnListarIngressos, gbc);

        btnSair = new JButton("Sair");
        estilizarBotao(btnSair, new Color(200, 50, 50));
        btnSair.addActionListener(e -> System.exit(0));
        gbc.gridy = 3;
        painelBotoes.add(btnSair, gbc);

        painelFundo.add(painelBotoes, BorderLayout.CENTER);
        add(painelFundo);
        setVisibleView(this);
    }

    private void estilizarBotao(JButton botao, Color corFundo) {
        botao.setFont(new Font("Arial", Font.PLAIN, 14));
        botao.setBackground(corFundo);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    private void setOnClickListener() {
        btnComprar.addActionListener((e) -> {
            JanelaCadastroIngresso janelaCadastroIngresso = new JanelaCadastroIngresso();
            setVisibleView(janelaCadastroIngresso);
        });

        btnGerarRelatorio.addActionListener((e) -> {
            JanelaRelatorio janelaGrafica = new JanelaRelatorio();
            setVisibleView(janelaGrafica);
            janelaGrafica.imprimirRelatorio(ingressos);
        });

        btnListarIngressos.addActionListener(e -> {
            JanelaListarIngressos janelaListarIngressos = new JanelaListarIngressos();
            setVisibleView(janelaListarIngressos);
            janelaListarIngressos.imprimirIngressos(ingressos);
        });
    }

    private void setVisibleView(JDialog view){
        if (!view.isVisible()){
            view.setVisible(true);
        }
    }
}
