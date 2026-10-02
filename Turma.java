import java.util.ArrayList;

public class Turma {

    private int id;
    private ArrayList<Estudante> estudantes;
    private int quantidadeEstudantesRealizaramAvaliacao;

    public Turma(Escola escola) {
        this.id                                      = escola.alocarIdTurma();
        this.quantidadeEstudantesRealizaramAvaliacao = 0;
        this.estudantes                              = new ArrayList<Estudante>();
    }

    public void cadastrarEstudante(Estudante estudante) {
        assert this.estudantes != null;
        this.estudantes.add(estudante);
    }

    public int getId() {
        return this.id;
    }

    public ArrayList<Estudante> getEstudantes() {
        return this.estudantes;
    }

    public int getQuantidadeEstudantesRealizaramAvaliacao() {
        return this.quantidadeEstudantesRealizaramAvaliacao;
    }

}
