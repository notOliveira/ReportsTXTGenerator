package com.example.reports.service;

import com.example.reports.dto.SumarioDTO;
import com.example.reports.model.Cliente;
import com.example.reports.repository.ClienteRepository;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class RelatorioService {

    private final ClienteRepository clienteRepository;

    public RelatorioService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public void createReport(SumarioDTO sumarioDTO) throws IOException {
        List<Cliente> clientes = clienteRepository.findAll();

        Map<String, Object> dados = new HashMap<>();
        dados.put("nomeRelatorio", sumarioDTO.getNomeRelatorio());
        dados.put("dataHora", LocalDateTime.now().toString());
        dados.put("nomeCiclo", sumarioDTO.getNomeCiclo());
        dados.put("clientes", clientes);

        InputStream templateStream = new ClassPathResource("templates/template.mustache").getInputStream();

        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(new InputStreamReader(templateStream, StandardCharsets.UTF_8), "template");

        try (Writer writer = new FileWriter(String.format("relatorio_gerado_%s.txt", UUID.randomUUID()))) {
            mustache.execute(writer, dados).flush();
        }

        System.out.println("Relatório gerado com sucesso.");
    }

    public void createData(Integer registers) {
        int i = 0;

        while (i < registers) {
            Cliente cliente = new Cliente();
            cliente.setNome("Cliente " + i);
            String ano = "19" + String.format("%02d", i);
            String dataNascimento = ano + "/01/01";
            cliente.setDataNascimento(dataNascimento);
            cliente.setNumContrato("CTN" + i);

            clienteRepository.insert(cliente);
            i++;
        }
    }
}
