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

    public ArrayList<Ingresso> getIngressos() {
        return ingressos;
    }

    public static IngressoDAO getInstance() {
        if (gerenciadorDeIngressos == null) {
            gerenciadorDeIngressos = new IngressoDAO();
        }
        return gerenciadorDeIngressos;
    }

}

    
    
    
    

