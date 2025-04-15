package br.edu.ifsp.vendas_ingresso.model.data;

import br.edu.ifsp.vendas_ingresso.model.entity.Ingresso;

import java.util.ArrayList;

public class IngressoDAO {

    private ArrayList<Ingresso> ingressos = new ArrayList<>();
    private static int prox = 0;
    private static IngressoDAO gerenciadorDeIngressos;

    private IngressoDAO() {}

    public boolean comprarIngresso(Ingresso ingresso) {
        if (ingresso != null) {
            ingresso.setCodigo(++prox);
            ingressos.add(ingresso);
            return true;
        }else{
            return false;
        }
    }

    public boolean excluirIngresso(int codigo) {
        return ingressos.removeIf(i -> i.getCodigo() == codigo);
    }

    public ArrayList<Ingresso> getIngressos() {
        return ingressos;
    }

    public static IngressoDAO getInstance() {
        if (gerenciadorDeIngressos == null) {
            gerenciadorDeIngressos = new IngressoDAO();
        }
        return gerenciadorDeIngressos;
    }

    public Ingresso buscarPorCodigo(int codigo) {
        return getIngressos().stream()
                .filter(i -> i.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }

    public boolean atualizarIngresso(Ingresso ingressoAtualizado) {
        if (ingressoAtualizado == null || ingressoAtualizado.getCodigo() <= 0) {
            return false;
        }

        for (int i = 0; i < ingressos.size(); i++) {
            Ingresso ingresso = ingressos.get(i);
            if (ingresso.getCodigo() == ingressoAtualizado.getCodigo()) {
                // Atualizar os campos do ingresso existente
                ingresso.setNome(ingressoAtualizado.getNome());
                ingresso.setSetor(ingressoAtualizado.getSetor());
                ingresso.setQuantidade(ingressoAtualizado.getQuantidade());
                ingresso.setTipoIngresso(ingressoAtualizado.getTipoIngresso());
                ingresso.setValor(ingressoAtualizado.getValor());
                ingresso.setValorTotal(ingressoAtualizado.getValorTotal());
                ingresso.setDataHora(ingressoAtualizado.getDataHora());
                return true;
            }
        }
        return false; // Ingresso não encontrado
    }
}






