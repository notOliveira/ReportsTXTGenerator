package com.example.reports.service;

import com.example.reports.dto.SumarioDTO;
import com.example.reports.model.Cliente;
import com.example.reports.model.TipoPessoaBloco;
import com.example.reports.repository.ClienteRepository;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class RelatorioService {

    private final ClienteRepository clienteRepository;

    public RelatorioService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public void createReport(SumarioDTO sumarioDTO) throws IOException {
        List<Cliente> clientes = clienteRepository.findAll();

        // Filtra os tipos
        List<Cliente> fisicas = clientes.stream()
                .filter(c -> "F".equals(c.getTipoPessoa()))
                .toList();

        List<Cliente> juridicas = clientes.stream()
                .filter(c -> "J".equals(c.getTipoPessoa()))
                .toList();

        List<Cliente> inativas = clientes.stream()
                .filter(c -> "I".equals(c.getTipoPessoa()))
                .toList();

        // Prepara o map de dados pro Mustache
        Map<String, Object> dados = new HashMap<>();
        dados.put("nomeRelatorio", sumarioDTO.getNomeRelatorio());
        dados.put("dataHora", LocalDateTime.now().toString());
        dados.put("nomeCiclo", sumarioDTO.getNomeCiclo());

        List<TipoPessoaBloco> pessoas = new ArrayList<>();

        if (!fisicas.isEmpty())
            pessoas.add(new TipoPessoaBloco("Física", fisicas));
        if (!juridicas.isEmpty())
            pessoas.add(new TipoPessoaBloco("Jurídica", juridicas));
        if (!inativas.isEmpty())
            pessoas.add(new TipoPessoaBloco("Inativa", inativas));

        dados.put("pessoas", pessoas);

        // Carrega e compila o template Mustache
        InputStream templateStream = new ClassPathResource("templates/template.mustache").getInputStream();
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(new InputStreamReader(templateStream, StandardCharsets.UTF_8), "template");

        // Gera o arquivo
        String nomeArquivo = String.format("relatorio_gerado_%s.txt", UUID.randomUUID());
        try (Writer writer = new FileWriter(nomeArquivo)) {
            mustache.execute(writer, dados).flush();
        }

        System.out.println("Relatório gerado com sucesso: " + nomeArquivo);
    }

    public void createData(Integer registers) {
        String[] tipos = {"F", "J", "I"};
        Random random = new Random();

        for (int i = 0; i < registers; i++) {

            Cliente cliente = new Cliente();
            cliente.setNome("Cliente " + i);
            String ano = "19" + String.format("%02d", i);
            String dataNascimento = ano + "/01/01";
            cliente.setDataNascimento(dataNascimento);
            int numero = 100000 + random.nextInt(900000);
            cliente.setNumContrato("CTN" + numero);
            cliente.setTipoPessoa(tipos[random.nextInt(tipos.length)]);
            clienteRepository.insert(cliente);
        }
    }
}
