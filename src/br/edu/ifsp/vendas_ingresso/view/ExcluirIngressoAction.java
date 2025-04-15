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
        if (codigo <= 0) {
            JOptionPane.showMessageDialog(parent,
                    "Código inválido para exclusão.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        int confirmacao = JOptionPane.showConfirmDialog(
                parent,
                "Deseja realmente excluir o ingresso de código " + codigo + "?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            boolean excluido = IngressoDAO.getInstance().excluirIngresso(codigo);
            if (excluido) {
                JOptionPane.showMessageDialog(parent,
                        "Ingresso excluído com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(parent,
                        "Erro ao excluir o ingresso. Verifique se o ingresso existe.",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        return false;
    }
}