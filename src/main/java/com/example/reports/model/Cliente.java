package com.example.reports.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "clientes")
@Getter
@Setter
public class Cliente {

    @Id
    private String id;
    private String nome;
    private String numContrato;
    private String dataNascimento;
    private String tipoPessoa;
}
