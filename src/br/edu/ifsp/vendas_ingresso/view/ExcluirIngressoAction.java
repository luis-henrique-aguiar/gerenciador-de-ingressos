package br.edu.ifsp.vendas_ingresso.view;

import br.edu.ifsp.vendas_ingresso.model.data.IngressoDAO;

import javax.swing.*;
import java.awt.*;

public class ExcluirIngressoAction {

    private final Component parent;

    public ExcluirIngressoAction(Component parent) {
        this.parent = parent;
    }

    public boolean executarExclusao(int codigo) {
        int confirmacao = JOptionPane.showConfirmDialog(
                parent,
                "Tem certeza que deseja excluir o ingresso de código " + codigo + "?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            boolean excluido = IngressoDAO.getInstance().excluirIngresso(codigo);
            if (excluido) {
                JOptionPane.showMessageDialog(parent,
                        "Ingresso excluído com sucesso!");
                return true;
            } else {
                JOptionPane.showMessageDialog(parent,
                        "Erro ao excluir o ingresso.");
            }
        }

        return false;
    }
}
