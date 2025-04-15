package br.edu.ifsp.vendas_ingresso.view;

import br.edu.ifsp.vendas_ingresso.model.data.IngressoDAO;
import br.edu.ifsp.vendas_ingresso.model.entity.Ingresso;
import br.edu.ifsp.vendas_ingresso.view.enums.Setores;
import br.edu.ifsp.vendas_ingresso.view.enums.TipoIngresso;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class JanelaCadastroIngresso extends JDialog {

    private JPanel painelFundo;
    private JButton btnSalvar;
    private JButton btnVoltar;
    private JLabel lblTitulo;
    private JLabel lblNome;
    private JLabel lblQtde;
    private JLabel lblSetor;
    private JLabel lblTipoIngresso;
    private JTextField txtNome;
    private JTextField txtQtde;
    private JComboBox<Setores> cbxSetores;
    private JComboBox<TipoIngresso> cbxTipoIngresso;
    private final IngressoDAO gerenciador = IngressoDAO.getInstance();
    private Ingresso ingressoEdicao;
    private final TelaInicial telaInicial;

    public JanelaCadastroIngresso(TelaInicial telaInicial) {
        this(telaInicial, null); // Novo ingresso
    }

    public JanelaCadastroIngresso(TelaInicial telaInicial, Ingresso ingresso) {
        this.telaInicial = telaInicial;
        this.ingressoEdicao = ingresso;
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

        criarComponentes();
        setOnClickListener();
        if (ingressoEdicao != null) {
            preencherCampos();
        }
    }

    private void criarComponentes() {
        setTitle(ingressoEdicao == null ? "Cadastro de Ingresso" : "Editar Ingresso");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(400, 400));
        setLocationRelativeTo(null);

        painelFundo = new JPanel(new BorderLayout(10, 10));
        painelFundo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painelFundo.setBackground(new Color(240, 240, 240));

        lblTitulo = new JLabel(ingressoEdicao == null ? "Cadastro de Novo Ingresso" : "Editar Ingresso", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(30, 100, 200));
        painelFundo.add(lblTitulo, BorderLayout.NORTH);

        JPanel painelFormulario = new JPanel(new GridBagLayout());
        painelFormulario.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        lblNome = new JLabel("Nome:");
        lblNome.setFont(new Font("Arial", Font.PLAIN, 14));
        painelFormulario.add(lblNome, gbc);

        gbc.gridx = 1;
        txtNome = new JTextField(15);
        txtNome.setFont(new Font("Arial", Font.PLAIN, 14));
        painelFormulario.add(txtNome, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        lblTipoIngresso = new JLabel("Tipo de Ingresso:");
        lblTipoIngresso.setFont(new Font("Arial", Font.PLAIN, 14));
        painelFormulario.add(lblTipoIngresso, gbc);

        gbc.gridx = 1;
        cbxTipoIngresso = new JComboBox<>(TipoIngresso.values());
        cbxTipoIngresso.setFont(new Font("Arial", Font.PLAIN, 14));
        painelFormulario.add(cbxTipoIngresso, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        lblQtde = new JLabel("Quantidade:");
        lblQtde.setFont(new Font("Arial", Font.PLAIN, 14));
        painelFormulario.add(lblQtde, gbc);

        gbc.gridx = 1;
        txtQtde = new JTextField(5);
        txtQtde.setFont(new Font("Arial", Font.PLAIN, 14));
        painelFormulario.add(txtQtde, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        lblSetor = new JLabel("Setor:");
        lblSetor.setFont(new Font("Arial", Font.PLAIN, 14));
        painelFormulario.add(lblSetor, gbc);

        gbc.gridx = 1;
        cbxSetores = new JComboBox<>(Setores.values());
        cbxSetores.setFont(new Font("Arial", Font.PLAIN, 14));
        painelFormulario.add(cbxSetores, gbc);

        painelFundo.add(painelFormulario, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.setOpaque(false);

        btnSalvar = new JButton("Salvar");
        estilizarBotao(btnSalvar, new Color(50, 150, 50));
        btnSalvar.setToolTipText("Salvar o ingresso");
        painelBotoes.add(btnSalvar);

        btnVoltar = new JButton("Voltar");
        estilizarBotao(btnVoltar, new Color(200, 50, 50));
        btnVoltar.setToolTipText("Retornar à tela inicial");
        painelBotoes.add(btnVoltar);

        painelFundo.add(painelBotoes, BorderLayout.SOUTH);

        add(painelFundo);
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

    private void preencherCampos() {
        txtNome.setText(ingressoEdicao.getNome());
        txtQtde.setText(String.valueOf(ingressoEdicao.getQuantidade()));
        cbxSetores.setSelectedItem(ingressoEdicao.getSetor());
        cbxTipoIngresso.setSelectedItem(ingressoEdicao.getTipoIngresso());
    }

    private void salvarIngresso() {
        Ingresso ingresso;
        if (ingressoEdicao != null) {
            ingresso = ingressoEdicao; // Usar o objeto existente
        } else {
            ingresso = new Ingresso(); // Criar novo objeto
        }

        // Validações
        String nome = txtNome.getText().trim();
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, insira o nome!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        ingresso.setNome(nome);

        String qtde = txtQtde.getText().trim();
        if (!qtde.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Insira uma quantidade válida de ingressos!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int quantidade = Integer.parseInt(qtde);
        if (quantidade <= 0) {
            JOptionPane.showMessageDialog(this, "A quantidade deve ser maior que zero!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        ingresso.setQuantidade(quantidade);

        Setores setor = (Setores) cbxSetores.getSelectedItem();
        ingresso.setSetor(setor);

        TipoIngresso tipoIngresso = (TipoIngresso) cbxTipoIngresso.getSelectedItem();
        ingresso.setTipoIngresso(tipoIngresso);

        double valorIngresso = getValorIngresso(setor, tipoIngresso);
        ingresso.setValor(valorIngresso);
        ingresso.setValorTotal(valorIngresso * ingresso.getQuantidade());
        ingresso.setDataHora(LocalDateTime.now());

        boolean sucesso;
        if (ingressoEdicao == null) {
            sucesso = gerenciador.comprarIngresso(ingresso);
        } else {
            if (ingresso.getCodigo() <= 0) {
                JOptionPane.showMessageDialog(this, "Código do ingresso inválido para edição!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            sucesso = gerenciador.atualizarIngresso(ingresso);
        }

        if (sucesso) {
            JOptionPane.showMessageDialog(this,
                    ingressoEdicao == null ? "Ingresso comprado com sucesso!" : "Ingresso atualizado com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
            limpar();
            telaInicial.setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    ingressoEdicao == null ? "Ingressos esgotados! Por favor, selecione outro setor." : "Erro ao atualizar o ingresso.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private double getValorIngresso(Setores setor, TipoIngresso tipoIngresso) {
        double valorIngresso = switch (setor) {
            case AMARELO -> 180.00;
            case AZUL -> 100.00;
            case BRANCO -> 60.00;
            case VERDE -> 350.00;
        };
        if (tipoIngresso == TipoIngresso.MEIA) {
            valorIngresso /= 2;
        }
        return valorIngresso;
    }

    private void limpar() {
        txtNome.setText("");
        txtQtde.setText("");
        cbxSetores.setSelectedIndex(0);
        cbxTipoIngresso.setSelectedIndex(0);
    }

    private void setOnClickListener() {
        btnSalvar.addActionListener(e -> salvarIngresso());

        btnVoltar.addActionListener(e -> {
            telaInicial.setVisible(true);
            dispose();
        });
    }
}