package br.edu.ifsp.vendas_ingresso.view;

import br.edu.ifsp.vendas_ingresso.model.data.IngressoDAO;
import br.edu.ifsp.vendas_ingresso.model.entity.Ingresso;
import br.edu.ifsp.vendas_ingresso.view.enums.Setores;
import br.edu.ifsp.vendas_ingresso.view.enums.TipoIngresso;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDateTime;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JanelaCadastroIngresso extends JDialog {

    private JPanel painelFundo;
    private JButton btnSalvar;
    private JButton btnVoltarTelaInicial;
    private JLabel lblNome;
    private JLabel lblQtde;
    private JTextField txtNome;
    private JTextField txtQtde;

    private JComboBox<Setores> cbxSetores;
    private JComboBox<TipoIngresso> cbxTipoIngresso;
    private IngressoDAO gerenciador = IngressoDAO.getInstance();

    private Setores setor;
    private TipoIngresso tipoIngresso;

    public JanelaCadastroIngresso() {
        criarComponentesInsercao();
        setOnClickListener();
    }

    private void criarComponentesInsercao() {

        btnSalvar = new JButton("Salvar");
        btnVoltarTelaInicial = new JButton("Voltar para Tela Inicial");

        btnSalvar.addActionListener((e) -> {
            comprarIngresso();
        });

        btnVoltarTelaInicial.addActionListener((e) -> {
            setVisible(false);
        });

        lblNome = new JLabel("Nome:");
        cbxTipoIngresso = new JComboBox(TipoIngresso.values());
        lblQtde = new JLabel("Quantidade:");
        txtNome = new JTextField(10);
        txtQtde = new JTextField(5);
        cbxSetores = new JComboBox(Setores.values());

        painelFundo = new JPanel();
        painelFundo.add(lblNome);
        painelFundo.add(txtNome);
        painelFundo.add(cbxTipoIngresso);
        painelFundo.add(lblQtde);
        painelFundo.add(txtQtde);
        painelFundo.add(cbxSetores);
        painelFundo.add(btnSalvar);
        painelFundo.add(btnVoltarTelaInicial);

        add(painelFundo);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);// Só fecha a janela(Esconde). Não fecha a aplicação(EXIT_ON_CLOSE)
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    private void comprarIngresso() {
        Ingresso ingresso = new Ingresso();
        double valorIngresso = 0.00;

        ingresso.setNome(txtNome.getText());
        setor = Setores.valueOf(cbxSetores.getSelectedItem().toString());
        ingresso.setSetor(setor);
        String qtde = txtQtde.getText();
        if(!qtde.matches("\\d+")){
            JOptionPane.showMessageDialog(null, "Insira uma quantidade válida de ingressos!");
        }else {
            ingresso.setQuantidade(Integer.parseInt(txtQtde.getText()));
            tipoIngresso = TipoIngresso.valueOf(cbxTipoIngresso.getSelectedItem().toString());
            valorIngresso = getValorIngresso(valorIngresso);
            ingresso.setValor(valorIngresso);

            // Calcula o valor total
            double valorTotal = ingresso.getValor() * ingresso.getQuantidade();
            ingresso.setValorTotal(valorTotal);

            // Captura a data e hora local da maquina
            ingresso.setDataHora(LocalDateTime.now());

            if (gerenciador.comprarIngresso(ingresso)) {
                limpar();
                JOptionPane.showMessageDialog(null, "Ingresso comprado com sucesso!");
            } else {
                limpar();
                JOptionPane.showMessageDialog(null, "Ingressos esgotados! Por favor, selecione outro setor.");
            }
        }
    }

    private void limpar() {
        txtNome.setText("");
        txtQtde.setText("");
    }

    private double getValorIngresso(double valorIngresso) {
        // Identifica valores dos ingressos
        valorIngresso = switch (setor) {
            case AMARELO -> 180.00;
            case AZUL -> 100.00;
            case BRANCO -> 60.00;
            case VERDE -> 350.00;
            default -> valorIngresso;
        };

        // se for estudante ou aposentado, calcula meia entrada
        if (tipoIngresso.equals(TipoIngresso.MEIA)) {
            valorIngresso = valorIngresso / 2;
        }
        return valorIngresso;
    }

    private void setOnClickListener() {

        cbxSetores.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    setor = Setores.valueOf(cbxSetores.getSelectedItem().toString());
                }
            }
        });

        cbxSetores.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    tipoIngresso = TipoIngresso.valueOf(cbxTipoIngresso.getSelectedItem().toString());
                }
            }
        });
    }

}
