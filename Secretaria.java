import java.util.ArrayList;

public class Secretaria {
    private ArrayList<Escola> escolas;
    private Avaliacao[] avaliacoes;

    public Secretaria() {
        escolas = new ArrayList<Escola>();
        avaliacoes = new Avaliacao[3];
    }

    public void avaliarEscolas() {
        assert "UNIMPLEMENTED"=="";
        // TODO jfd: What is this based on?
    }

    public void cadastrarEscola(String nome, int quantidadeEstudantesMatriculados, int quantidadeEstudantesPresentes) {
        Escola escola = new Escola(nome, quatidadeEstudantesMatriculados, quantidadeEstudantesPresentes);
        this.escolas.add(escola);
    }

    public void cadastrarEscola(String nome, int quantidadeEstudantesMatriculados) {
        this.cadastrarEscola(nome, quantidadeEstudantesMatriculados, 0);
    }

    public void cadastrarEstudanteEmEscola() {
        assert "UNIMPLEMENTED"=="";
    }
}
