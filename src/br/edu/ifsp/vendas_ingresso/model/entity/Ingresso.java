package br.edu.ifsp.vendas_ingresso.model.entity;

import br.edu.ifsp.vendas_ingresso.view.enums.Setores;
import br.edu.ifsp.vendas_ingresso.view.enums.TipoIngresso;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ingresso {

    private int codigo;
    private String nome;
    private Setores setor;
    private double valor;
    private int quantidade;
    private double valorTotal;
    private String dataHora;
    private TipoIngresso tipoIngresso;

    public void setTipoIngresso(TipoIngresso tipoIngresso) {
        this.tipoIngresso = tipoIngresso;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public TipoIngresso getTipoIngresso() {
        return tipoIngresso;
    }

    public Ingresso() {
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Setores getSetor() {
        return setor;
    }

    public void setSetor(Setores setor) {
        this.setor = setor;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDataHora() {
        return dataHora;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void setDataHora(LocalDateTime dataHora) {
        // formatar a data
        DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        String dataFormatada = formatterData.format(dataHora);

        // formatar a hora
        DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm:ss");
        String horaFormatada = formatterHora.format(dataHora);

        this.dataHora = dataFormatada + ' ' + horaFormatada;
    }


    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.codigo;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ingresso other = (Ingresso) obj;
        if (this.codigo != other.codigo) {
            return false;
        }
        return true;
    }





}
