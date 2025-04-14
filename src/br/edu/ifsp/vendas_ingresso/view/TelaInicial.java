package br.edu.ifsp.vendas_ingresso.view;

import br.edu.ifsp.vendas_ingresso.model.data.IngressoDAO;
import br.edu.ifsp.vendas_ingresso.model.entity.Ingresso;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class TelaInicial extends JDialog {

    private JPanel painelFundo;
    private JButton btnComprar;
    private JButton btnGerarRelatorio;
    private JButton btnListarIngressos;

    IngressoDAO gerenciador = IngressoDAO.getInstance();
    ArrayList<Ingresso> ingressos = gerenciador.getIngressos();

    public TelaInicial() {
        criarComponentesTela();
        setOnClickListener();
    }

    private void criarComponentesTela() {

        btnComprar = new JButton("Comprar Ingresso");
        btnGerarRelatorio = new JButton("Gerar Relatório");
        btnListarIngressos = new JButton("Listar Ingressos");
        painelFundo = new JPanel();
        painelFundo.add(btnComprar);
        painelFundo.add(btnGerarRelatorio);
        painelFundo.add(btnListarIngressos);

        add(painelFundo);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
        setSize(300, 200);
        setVisibleView(this);
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

        btnListarIngressos.addActionListener( e -> {
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
