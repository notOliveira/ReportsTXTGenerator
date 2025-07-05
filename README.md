# Teste para geração de relatórios

### Criar relatórios

<b>POST /createReport</b>

Payload teste:
<code>
{
    "nomeRelatorio": "Faturamento",
    "nomeCiclo": "Especial 2022"
}
</code>

### Gerar dados para o relatório

<b>POST /createData/{qtdRegistros}</b>

Obs: Recomendo colocar a quantidade de registros entre 1 e 99 no máximo. Pode criar quantos dados quiser, mas como existe a data de nascimento, vai ficar feio se a data tiver mais que 4 digitos no ano.

## Collection no Mongo

Irá ser criada automaticamente após rodar o endpoint createData.


