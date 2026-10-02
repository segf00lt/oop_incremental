import java.util.ArrayList;

public class Secretaria {
    private ArrayList<Escola> escolas;
    private Avaliacao[] avaliacoes;

    private int contadorIdProfessor;
    private int contadorIdEscola;

    public static final int PERIODO_MAXIMO = 3;

    public Secretaria() {
        this.contadorIdProfessor = 0;
        this.contadorIdEscola = 0;

        this.escolas = new ArrayList<Escola>();

        this.avaliacoes = new Avaliacao[this.PERIODO_MAXIMO];

        for(int i = 0; i < this.PERIODO_MAXIMO; i++) {
            int numeroAvaliacao = i + 1;
            String idAvaliacao = "AV" + numeroAvaliacao;
            Avaliacao avaliacao = new Avaliacao(idAvaliacao, numeroAvaliacao);
            this.avaliacoes[i] = avaliacao;
        }
    }

    public void avaliarEscolas(int numeroAvaliacao) {
        assert 1 <= numeroAvaliacao && numeroAvaliacao <= this.avaliacoes.length;

        Avaliacao a = this.avaliacoes[numeroAvaliacao - 1];

        for(Escola e : this.escolas) {
            e.aplicarAvaliacaoAtualizarMedia(a);
        }
    }

    public void apresentarEscolas() {
        System.out.printf("=== APRESENTANDO DADOS PARA %d ESCOLAS ===\n\n", this.escolas.size());
        for(Escola e : this.escolas) {
            e.apresentar();
            System.out.print("\n\n");
        }
    }

    public Escola cadastrarEscola(
        String nome,
        int quantidadeEstudantes,
        int quantidadeEstudantesPresentes,
        String endereco,
        String municipioRegiao
    ) {
        Escola escola = new Escola(this, nome, endereco, municipioRegiao, quantidadeEstudantes, quantidadeEstudantesPresentes);
        this.escolas.add(escola);
        return escola;
    }

    public Escola cadastrarEscola(String nome, int quantidadeEstudantes, int quantidadeEstudantesPresentes) {
        return this.cadastrarEscola(nome, quantidadeEstudantes, quantidadeEstudantesPresentes, "N/A", "N/A");
    }

    public Escola cadastrarEscola(String nome, int quantidadeEstudantes) {
        return this.cadastrarEscola(nome, quantidadeEstudantes, 0);
    }

    public Escola getEscolaPorId(int id) {
        for(Escola e : this.escolas) {
            if(e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    public int alocarIdProfessor() {
        int id = this.contadorIdProfessor;
        this.contadorIdProfessor++;
        return id;
    }

    public int alocarIdEscola() {
        int id = this.contadorIdEscola;
        this.contadorIdEscola++;
        return id;
    }

    public Professor cadastrarProfessor(String nome, String area) {
        return new Professor(this, nome, area);
    }

    public void cadastrarEstudanteEmEscola(int idEscola, String nomeEstudante) {
        Escola escola = this.getEscolaPorId(idEscola);
        escola.cadastrarEstudante(nomeEstudante);
    }
}
