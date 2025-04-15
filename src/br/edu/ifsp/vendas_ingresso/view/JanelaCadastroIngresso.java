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
    private JTextField txtCodigo, txtNome, txtQuantidade, txtValor;
    private JComboBox<TipoIngresso> comboTipo;
    private JComboBox<Setores> comboSetor;
    private JButton btnSalvar, btnCancelar;

    public JanelaCadastroIngresso() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Cadastrar Ingresso");
        criarComponentes();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(400, 420);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void criarComponentes() {
        painelFundo = new JPanel(new GridBagLayout());
        painelFundo.setBackground(new Color(250, 250, 250));
        painelFundo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 8, 10, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel titulo = new JLabel("Cadastro de Ingresso");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(new Color(50, 80, 160));
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        painelFundo.add(titulo, gbc);

        gbc.gridwidth = 1;

        // Nome
        gbc.gridy++;
        painelFundo.add(new JLabel("Nome:"), gbc);
        txtNome = new JTextField();
        gbc.gridx = 1;
        painelFundo.add(txtNome, gbc);
        gbc.gridx = 0;

        // Tipo
        gbc.gridy++;
        painelFundo.add(new JLabel("Tipo:"), gbc);
        comboTipo = new JComboBox<>(TipoIngresso.values());
        gbc.gridx = 1;
        painelFundo.add(comboTipo, gbc);
        gbc.gridx = 0;

        // Quantidade
        gbc.gridy++;
        painelFundo.add(new JLabel("Quantidade:"), gbc);
        txtQuantidade = new JTextField();
        gbc.gridx = 1;
        painelFundo.add(txtQuantidade, gbc);
        gbc.gridx = 0;

        // Setor
        gbc.gridy++;
        painelFundo.add(new JLabel("Setor:"), gbc);
        comboSetor = new JComboBox<>(Setores.values());
        gbc.gridx = 1;
        painelFundo.add(comboSetor, gbc);
        gbc.gridx = 0;

        // Valor
        gbc.gridy++;
        painelFundo.add(new JLabel("Valor Unitário:"), gbc);
        txtValor = new JTextField();
        gbc.gridx = 1;
        painelFundo.add(txtValor, gbc);
        gbc.gridx = 0;

        // Botões
        gbc.gridy++;
        gbc.gridwidth = 2;

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        painelBotoes.setOpaque(false);

        btnSalvar = new JButton("Salvar");
        estilizarBotao(btnSalvar, new Color(60, 160, 60));
        painelBotoes.add(btnSalvar);

        btnCancelar = new JButton("Cancelar");
        estilizarBotao(btnCancelar, new Color(180, 60, 60));
        painelBotoes.add(btnCancelar);

        painelFundo.add(painelBotoes, gbc);

        add(painelFundo);

        setOnClickListener();
    }

    private void estilizarBotao(JButton botao, Color corFundo) {
        botao.setFont(new Font("Arial", Font.BOLD, 13));
        botao.setBackground(corFundo);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setPreferredSize(new Dimension(110, 35));
    }

    private void setOnClickListener() {
        btnSalvar.addActionListener(e -> {
            try {
                String nome = txtNome.getText().trim();
                TipoIngresso tipo = (TipoIngresso) comboTipo.getSelectedItem();
                int quantidade = Integer.parseInt(txtQuantidade.getText().trim());
                Setores setor = (Setores) comboSetor.getSelectedItem();
                double valor = Double.parseDouble(txtValor.getText().trim());
                double total = valor * quantidade;
                LocalDateTime dataHora = LocalDateTime.now();

                Ingresso ingresso = new Ingresso();
                ingresso.setNome(nome);
                ingresso.setTipoIngresso(tipo);
                ingresso.setQuantidade(quantidade);
                ingresso.setSetor(setor);
                ingresso.setValor(valor);
                ingresso.setValorTotal(total);
                ingresso.setDataHora(dataHora);
                IngressoDAO.getInstance().comprarIngresso(ingresso);

                JOptionPane.showMessageDialog(this, "Ingresso cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos corretamente.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dispose());
    }
}
