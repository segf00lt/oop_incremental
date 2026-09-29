import java.util.ArrayList;

public class Secretaria {
    private ArrayList<Escola> escolas;
    private Avaliacao[] avaliacoes;

    public static final int PERIODO_MAXIMO = 3;

    public Secretaria() {
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

    public void apresentarDadosEscolas() {
        System.out.printf("=== APRESENTANDO DADOS PARA %d ESCOLAS ===\n\n", this.escolas.size());
        for(Escola e : this.escolas) {
            e.apresentarDados();
            System.out.print("\n\n");
        }
    }

    public void cadastrarEscola(
        String codigo,
        String nome,
        String endereco,
        String municipioRegiao,
        int quantidadeEstudantesMatriculados,
        int quantidadeEstudantesPresentes
    ) {
        Escola escola = new Escola(codigo, nome, endereco, municipioRegiao, quantidadeEstudantesMatriculados, quantidadeEstudantesPresentes);
        this.escolas.add(escola);
    }

    public void cadastrarEscola(String nome, int quantidadeEstudantesMatriculados, int quantidadeEstudantesPresentes) {
        Escola escola = new Escola(nome, quantidadeEstudantesMatriculados, quantidadeEstudantesPresentes);
        this.escolas.add(escola);
    }

    public void cadastrarEscola(String nome, int quantidadeEstudantesMatriculados) {
        this.cadastrarEscola(nome, quantidadeEstudantesMatriculados, 0);
    }

    public Escola getEscolaPorCodigo(String codigo) {
        for(Escola e : this.escolas) {
            if(e.getCodigo().equals(codigo)) {
                return e;
            }
        }
        return null;
    }

    public void matricularEstudanteEmEscola(String codigoEscola, String nomeEstudante) {
        Escola escola = this.getEscolaPorCodigo(codigoEscola);
        escola.matricularEstudante(nomeEstudante);
    }
}
