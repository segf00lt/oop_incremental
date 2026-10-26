import java.util.ArrayList;
import java.util.Random;

public class Escola {
    private Secretaria secretaria;

    private long id;
    private String nome;
    private String endereco;
    private String municipioRegiao;
    private ResultadoAvaliacao[] resultadosAvaliacoes;
    private int[] quantidadeEstudantesPresentesPorPeriodo;
    private float mediaDesempenho;
    private ArrayList<Estudante> estudantes;
    private ArrayList<Turma> turmas;

    private int contadorIdEstudante;
    private int contadorIdTurma;

    public Escola(
        Secretaria secretaria,
        String nome,
        String endereco,
        String municipioRegiao,
        int quantidadeEstudantes,
        int quantidadeEstudantesPresentes
    ) {

        this.secretaria = secretaria;

        this.id                            = secretaria.alocarIdEscola();
        this.nome                          = nome;
        this.endereco                      = endereco;
        this.municipioRegiao               = municipioRegiao;
        this.resultadosAvaliacoes          = new ResultadoAvaliacao[Secretaria.NUMERO_DE_PERIODOS];
        this.quantidadeEstudantesPresentesPorPeriodo = new int[Secretaria.NUMERO_DE_PERIODOS];

        // NOTE:
        // Inicializar a quantidade de estudantes presentes em cada periodo supondo que se mantém igual ao longo do ano,
        // podendo ser alterado posteriormente.
        for(int i = 0; i < Secretaria.NUMERO_DE_PERIODOS; i++) {
            this.quantidadeEstudantesPresentesPorPeriodo[i] = quantidadeEstudantesPresentes;
        }

        this.contadorIdEstudante = 0;
        this.contadorIdTurma = 0;

        this.mediaDesempenho               = 0.0f;
        this.estudantes        = new ArrayList<Estudante>(quantidadeEstudantes);
        this.turmas            = new ArrayList<Turma>();

        this.testRandomInitEstudantes(quantidadeEstudantes);

    }

    public Escola(
        Secretaria secretaria,
        String nome,
        int quantidadeEstudantes,
        int quantidadeEstudantesPresentes
    ) {
        this(secretaria, nome, "N/A", "N/A", quantidadeEstudantes, quantidadeEstudantesPresentes);
    }

    // NOTE jfd 25/09/26: This method may be redundant due to the overload of Secretaria.cadastrarEscola()
    public Escola(
        Secretaria secretaria,
        String nome,
        int quantidadeEstudantes
    ) {
        this(secretaria, nome, quantidadeEstudantes, 0);
    }

    private void testRandomInitEstudantes(int quantidadeEstudantes) {
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

        int maximoDeFaltasPorEstudante = 25;

        for(int i = 0; i < quantidadeEstudantes; i++) {
            int nomeIndice = r.nextInt(nomes.length);
            String nome = nomes[nomeIndice];
            Estudante e = cadastrarEstudante(nome);

            for(int j = 0; j < Secretaria.NUMERO_DE_PERIODOS; j++) {
                int totalFaltas = r.nextInt(maximoDeFaltasPorEstudante + 1);
                for(int k = 0; k < totalFaltas; k++) {
                    e.addFalta(j);
                }
            }

        }

    }

    public Estudante cadastrarEstudante(String nome) {
        int id = this.contadorIdEstudante;
        this.contadorIdEstudante++;
        Estudante e = new Estudante(id, nome);
        this.estudantes.add(e);
        return e;
    }

    public int alocarIdTurma() {
        int result = this.contadorIdTurma;
        this.contadorIdTurma++;
        return result;
    }

    public Turma cadastrarTurma(String etapaDeEnsino) {
        Turma t = new Turma(this, etapaDeEnsino);
        assert this.turmas != null;
        this.turmas.add(t);
        return t;
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


    private ResultadoAvaliacao aplicarAvaliacaoEmPeriodo(Avaliacao avaliacao, int numeroEstudantesAptos, int periodo) {
        assert 0 <= periodo && periodo <= this.quantidadeEstudantesPresentesPorPeriodo.length - 1;
        assert numeroEstudantesAptos > 0;

        ResultadoAvaliacao resultado = null;

        // TODO: get rid of this
        // int quantidadeEstudantesPresentes = this.quantidadeEstudantesPresentesPorPeriodo[periodo];

        Random r = new Random();

        float mediaNotasEstudantes = 0.0f;

        for(int i = 0; i < numeroEstudantesAptos; i++) {
            mediaNotasEstudantes += (float)r.nextInt(11);
        }

        mediaNotasEstudantes /= (float)numeroEstudantesAptos;

        resultado = new ResultadoAvaliacao(avaliacao, mediaNotasEstudantes);

        return resultado;
    }

    public void aplicarAvaliacao(Avaliacao avaliacao) {
        int numeroAvaliacao = avaliacao.getNumero();

        assert 1 <= numeroAvaliacao && numeroAvaliacao <= Secretaria.NUMERO_DE_PERIODOS;

        int periodo = numeroAvaliacao - 1;

        for(Turma turma : this.turmas) {
            int numeroEstudantesAptos = 0;
            { // calcularEstudantesAptos
                for(Estudante e : turma.getEstudantes()) {
                    if(e.getFaltasPorPeriodo(periodo) < Secretaria.MAXIMO_FALTAS_POR_PERIODO) {
                        numeroEstudantesAptos++;
                    }
                }
            } // calcularEstudantesAptos

            ResultadoAvaliacao resultado = this.aplicarAvaliacaoEmPeriodo(avaliacao, numeroEstudantesAptos, periodo);

            assert 0 <= periodo && periodo <= this.resultadosAvaliacoes.length - 1;

            this.resultadosAvaliacoes[periodo] = resultado;

            // NOTE: atualizar a media de desempenho
            this.mediaDesempenho = this.calcularMediaDesempenho(this.resultadosAvaliacoes, periodo + 1);

            // NOTE: atualizar contagem de alunos em cada turma que fizeram avaliação
            // NOTE: Se o número de faltas no período for superior a um determinado limite o estudante não faz avaliação
            turma.setQuantidadeEstudantesRealizaramAvaliacaoPorPeriodo(periodo, numeroEstudantesAptos);
        }

    }

    public void registrarNotaAvaliacao(Avaliacao avaliacao, float nota) {
        int numeroAvaliacao = avaliacao.getNumero();

        assert 1 <= numeroAvaliacao && numeroAvaliacao <= Secretaria.NUMERO_DE_PERIODOS;

        int periodo = numeroAvaliacao - 1;

        for(Turma turma : this.turmas) {
            int numeroEstudantesAptos = 0;
            { // calcularEstudantesAptos
                for(Estudante e : turma.getEstudantes()) {
                    if(e.getFaltasPorPeriodo(periodo) < Secretaria.MAXIMO_FALTAS_POR_PERIODO) {
                        numeroEstudantesAptos++;
                    }
                }
            } // calcularEstudantesAptos

            ResultadoAvaliacao resultado = new ResultadoAvaliacao(avaliacao, nota);

            assert 0 <= periodo && periodo <= this.resultadosAvaliacoes.length - 1;

            this.resultadosAvaliacoes[periodo] = resultado;

            // NOTE: atualizar a media de desempenho
            this.mediaDesempenho = this.calcularMediaDesempenho(this.resultadosAvaliacoes, periodo + 1);

            // NOTE: atualizar contagem de alunos em cada turma que fizeram avaliação
            // NOTE: Se o número de faltas no período for superior a um determinado limite o estudante não faz avaliação
            turma.setQuantidadeEstudantesRealizaramAvaliacaoPorPeriodo(periodo, numeroEstudantesAptos);
        }

    }

    public void atualizarQuantidadeEstudantesPresentesEmPeriodo(int quantidade, int periodo) {
        assert 0 <= periodo && periodo <= this.quantidadeEstudantesPresentesPorPeriodo.length - 1;
        this.quantidadeEstudantesPresentesPorPeriodo[periodo] = quantidade;
    }

    public ArrayList<Estudante> getEstudantes() {
        return this.estudantes;
    }

    public void setId(int id) {
        this.id = id;
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

    public long getId() {
        return this.id;
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

    public float getMediaDesempenho() {
        return this.mediaDesempenho;
    }

    public float calcularMediaDeFaltasPorEstudante() {
        int totalFaltas = this.calcularTotalFaltas();
        int totalEstudantes = this.estudantes.size();
        float mediaDeFaltasPorEstudante = 0;

        if(totalEstudantes > 0) {
            mediaDeFaltasPorEstudante = ((float)totalFaltas / (float)totalEstudantes);
        }

        return mediaDeFaltasPorEstudante;
    }


    public int calcularTotalFaltas() {
        int totalFaltas = 0;

        for(Estudante estudante : this.estudantes) {
            totalFaltas += estudante.getTotalFaltas();
        }

        return totalFaltas;
    }

    public int getTotalEstudantes() {
        return this.estudantes.size();
    }

    public void apresentar() {
        System.out.println("========================================");
        System.out.println("Dados da Escola");
        System.out.println("========================================");

        System.out.println("Identificador: " + this.id);
        System.out.println("Nome: " + this.nome);
        System.out.println("Endereço: " + this.endereco);
        System.out.println("Município/Região: " + this.municipioRegiao);


        // TODO: Does this work?
        CadastroProfessorEscola[] cadastrosProfessorEscola = this.secretaria.getCadastrosProfessorEscola(
            (c) -> (c.getEscola().id == this.id)
        );

        System.out.println();
        System.out.println("Quantidade de professores: " + cadastrosProfessorEscola.length);
        System.out.println("Professores: ");
        for(CadastroProfessorEscola c : cadastrosProfessorEscola) {
            Professor p = c.getProfessor();
            System.out.println(
                "  Identificador: " + p.getId() +
                " | Nome: " + p.getNome() +
                " | Area: " + p.getArea()
            );
        }

        System.out.println();
        System.out.println("Quantidade de estudantes: " + this.estudantes.size());

        System.out.println();
        System.out.println("Estudantes:");

        for(Estudante e : this.estudantes) {
            e.apresentar();
        }

        System.out.println();
        System.out.println("Frequência:");

        if(this.getTotalEstudantes() > 0) {
            System.out.println("  Total de faltas: " + this.calcularTotalFaltas());
            System.out.println("  Média de faltas por estudante: " + this.calcularMediaDeFaltasPorEstudante() );
        } else {
            System.out.println("  Nenhum estudante cadastrado.");
        }

        System.out.println();
        System.out.println("Turmas:");

        for(Turma t : this.turmas) {
            t.apresentar();
            System.out.println();
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
