package com.example.reports.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TipoPessoaBloco {
    private String tipo;
    private List<Cliente> clientes;

    public TipoPessoaBloco(String tipo, List<Cliente> clientes) {
        this.tipo = tipo;
        this.clientes = clientes;
    }
}
