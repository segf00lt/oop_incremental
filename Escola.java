import java.util.ArrayList;
import java.util.Random;

public class Escola {
    private String codigo;
    private String nome;
    private String endereco;
    private String municipioRegiao;
    private ResultadoAvaliacao[] resultadosAvaliacoes;
    private int[] quantidadeEstudantesPresentesPorPeriodo;
    private float mediaDesempenho;
    private ArrayList<Estudante> estudantesMatriculados;
    private int contadorMatricula;

    public Escola(
        String codigo,
        String nome,
        String endereco,
        String municipioRegiao,
        int quantidadeEstudantesMatriculados,
        int quantidadeEstudantesPresentes
    ) {

        this.codigo                        = codigo;
        this.nome                          = nome;
        this.endereco                      = endereco;
        this.municipioRegiao               = municipioRegiao;
        this.resultadosAvaliacoes          = new ResultadoAvaliacao[Secretaria.PERIODO_MAXIMO];
        this.quantidadeEstudantesPresentesPorPeriodo = new int[Secretaria.PERIODO_MAXIMO];

        // NOTE:
        // Inicializar a quantidade de estudantes presentes em cada periodo supondo que se mantém igual ao longo do ano,
        // podendo ser alterado posteriormente.
        for(int i = 0; i < Secretaria.PERIODO_MAXIMO; i++) {
            this.quantidadeEstudantesPresentesPorPeriodo[i] = quantidadeEstudantesPresentes;
        }

        this.contadorMatricula = 0;

        this.mediaDesempenho               = 0.0f;
        this.estudantesMatriculados        = new ArrayList<Estudante>(quantidadeEstudantesMatriculados);

        this.initEstudantes(quantidadeEstudantesMatriculados);

    }

    public Escola(
        String nome,
        int quantidadeEstudantesMatriculados,
        int quantidadeEstudantesPresentes
    ) {
        this("N/A", nome, "N/A", "N/A", quantidadeEstudantesMatriculados, quantidadeEstudantesPresentes);
    }

    // NOTE jfd 25/09/26: This method may be redundant due to the overload of Secretaria.cadastrarEscola()
    public Escola(
        String nome,
        int quantidadeEstudantesMatriculados
    ) {
        this(nome, quantidadeEstudantesMatriculados, 0);
    }

    private void initEstudantes(int quantidadeEstudantesMatriculados) {
        String[] nomes = {
            "João", "José", "Antônio", "Francisco", "Carlos",
            "Paulo", "Pedro", "Lucas", "Gabriel", "Miguel",
            "Rafael", "Daniel", "Felipe", "Bruno", "André",
            "Marcos", "Rodrigo", "Eduardo", "Marcelo", "Fernando",
            "Ricardo", "Guilherme", "Gustavo", "Leonardo", "Matheus",
            "Henrique", "Thiago", "Vinícius", "Caio", "Diego",
            "Luiz", "Luís", "Alexandre", "Samuel", "Davi",
            "Arthur", "Bernardo", "Murilo", "Victor", "Júlio",
            "Renato", "Fábio", "Sérgio", "Márcio", "Otávio",
            "Joaquim", "Tomás", "Manuel", "Maria", "Ana",
            "Francisca", "Joana", "Beatriz", "Mariana", "Juliana",
            "Camila", "Fernanda", "Gabriela", "Carolina", "Daniela",
            "Amanda", "Letícia", "Larissa", "Isabela", "Isabella",
            "Vitória", "Luana", "Bruna", "Jéssica", "Aline",
            "Bianca", "Renata", "Patrícia", "Débora", "Priscila",
            "Natália", "Vanessa", "Tatiana", "Raquel", "Cláudia",
            "Cristina", "Adriana", "Luciana", "Márcia", "Mônica",
            "Simone", "Elaine", "Helena", "Alice", "Laura",
            "Sofia", "Clara", "Lívia", "Manuela", "Cecília",
            "Teresa"
        };

        Random r = new Random();

        int maximoDeFaltas = 25;

        for(int i = 0; i < quantidadeEstudantesMatriculados; i++) {
            int nomeIndice = r.nextInt(nomes.length);
            String nome = nomes[nomeIndice];
            Estudante e = matricularEstudante(nome);
            e.setFaltas(r.nextInt(maximoDeFaltas + 1));
        }

    }

    public Estudante matricularEstudante(String nome) {
        int matricula = this.contadorMatricula;
        this.contadorMatricula++;
        Estudante e = new Estudante(matricula, nome);
        this.estudantesMatriculados.add(e);
        return e;
    }

    private float calcularMediaDesempenho(ResultadoAvaliacao[] resultados, int numeroResultadosConsiderados) {
        assert numeroResultadosConsiderados <= resultados.length;

        float result = 0;

        for(int i = 0; i < numeroResultadosConsiderados; i++) {
            ResultadoAvaliacao resultadoAvaliacao = resultados[i];

            assert resultadoAvaliacao != null;

            result += resultadoAvaliacao.getNota();
        }

        result /= (float)numeroResultadosConsiderados;

        return result;
    }


    private ResultadoAvaliacao aplicarAvaliacaoEmPeriodo(Avaliacao avaliacao, int periodo) {
        assert 0 <= periodo && periodo <= this.quantidadeEstudantesPresentesPorPeriodo.length - 1;

        ResultadoAvaliacao resultado = null;

        int quantidadeEstudantesPresentes = this.quantidadeEstudantesPresentesPorPeriodo[periodo];

        Random r = new Random();

        if(quantidadeEstudantesPresentes > 0) {
            float mediaNotasEstudantes = 0.0f;

            for(int i = 0; i < quantidadeEstudantesPresentes; i++) {
                mediaNotasEstudantes += (float)r.nextInt(11);
            }

            mediaNotasEstudantes /= (float)quantidadeEstudantesPresentes;

            resultado = new ResultadoAvaliacao(avaliacao, mediaNotasEstudantes);
        }

        return resultado;
    }

    public void aplicarAvaliacaoAtualizarMedia(Avaliacao avaliacao) {
        int numeroAvaliacao = avaliacao.getNumero();

        assert 1 <= numeroAvaliacao && numeroAvaliacao <= Secretaria.PERIODO_MAXIMO;

        int periodo = numeroAvaliacao - 1;

        ResultadoAvaliacao resultado = this.aplicarAvaliacaoEmPeriodo(avaliacao, periodo);

        assert 0 <= periodo && periodo <= this.resultadosAvaliacoes.length - 1;

        this.resultadosAvaliacoes[periodo] = resultado;

        // atualizar a media de desempenho
        this.mediaDesempenho = this.calcularMediaDesempenho(this.resultadosAvaliacoes, periodo + 1);
    }

    public void atualizarQuantidadeEstudantesPresentesEmPeriodo(int quantidade, int periodo) {
        assert 0 <= periodo && periodo <= this.quantidadeEstudantesPresentesPorPeriodo.length - 1;
        this.quantidadeEstudantesPresentesPorPeriodo[periodo] = quantidade;
    }

    public ArrayList<Estudante> getEstudantesMatriculados() {
        return this.estudantesMatriculados;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setMunicipioRegiao(String municipioRegiao) {
        this.municipioRegiao = municipioRegiao;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public String getNome() {
        return this.nome;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public String getMunicipioRegiao() {
        return this.municipioRegiao;
    }

    public void apresentarDados() {
        System.out.println("========================================");
        System.out.println("Dados da Escola");
        System.out.println("========================================");

        System.out.println("Código: " + this.codigo);
        System.out.println("Nome: " + this.nome);
        System.out.println("Endereço: " + this.endereco);
        System.out.println("Município/Região: " + this.municipioRegiao);

        System.out.println();
        System.out.println("Quantidade de estudantes: " + this.estudantesMatriculados.size());

        System.out.println();
        System.out.println("Estudantes:");

        for(Estudante estudante : this.estudantesMatriculados) {
            System.out.println(
                "  Matrícula: " + estudante.getMatricula() +
                " | Nome: " + estudante.getNome() +
                " | Faltas: " + estudante.getFaltas()
            );
        }

        System.out.println();
        System.out.println("Frequência:");

        int totalFaltas = 0;

        for(Estudante estudante : this.estudantesMatriculados) {
            totalFaltas += estudante.getFaltas();
        }

        int totalEstudantes = this.estudantesMatriculados.size();

        if(totalEstudantes > 0) {
            float mediaDeFaltasPorEstudante = ((float)totalFaltas / (float)totalEstudantes);

            System.out.println("  Total de faltas: " + totalFaltas);
            System.out.println("  Média de faltas por estudante: " + mediaDeFaltasPorEstudante );
        } else {
            System.out.println("  Nenhum estudante matriculado.");
        }

        System.out.println();
        System.out.println("Avaliações:");

        int avaliacoesRealizadas = 0;

        for(int i = 0; i < this.resultadosAvaliacoes.length; i++) {
            ResultadoAvaliacao resultado = this.resultadosAvaliacoes[i];

            if(resultado != null) {
                avaliacoesRealizadas++;

                System.out.println(
                    "  Avaliação " + (i + 1) +
                    ": " + resultado.getNota()
                );
            }
        }

        if(avaliacoesRealizadas > 0) {
            System.out.println();
            System.out.println(
                "Desempenho médio (" +
                avaliacoesRealizadas +
                " avaliações): " +
                this.mediaDesempenho
            );
        } else {
            System.out.println("  Nenhuma avaliação realizada.");
        }

        System.out.println("========================================");
    }


}
