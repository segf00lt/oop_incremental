import java.util.ArrayList;

public class Turma {

    private int id;
    private ArrayList<Estudante> estudantes;
    private int[] quantidadeEstudantesRealizaramAvaliacao;
    private String etapaDeEnsino; // ???

    public Turma(Escola escola, String etapaDeEnsino) {
        this.id                                      = escola.alocarIdTurma();
        this.quantidadeEstudantesRealizaramAvaliacao = new int[Secretaria.NUMERO_DE_PERIODOS];
        for(int i = 0; i < this.quantidadeEstudantesRealizaramAvaliacao.length; i++) {
            this.quantidadeEstudantesRealizaramAvaliacao[i] = 0;
        }
        this.estudantes                              = new ArrayList<Estudante>();
        this.etapaDeEnsino                           = etapaDeEnsino;
    }

    public void cadastrarEstudante(Estudante estudante) {
        assert this.estudantes != null;
        this.estudantes.add(estudante);
    }

    public String getEtapaDeEnsino() {
        return this.etapaDeEnsino;
    }

    public int getId() {
        return this.id;
    }

    public ArrayList<Estudante> getEstudantes() {
        return this.estudantes;
    }

    public int getQuantidadeEstudantesRealizaramAvaliacao(int periodo) {
        assert 0 <= periodo && periodo <= Secretaria.NUMERO_DE_PERIODOS - 1;
        return this.quantidadeEstudantesRealizaramAvaliacao[periodo];
    }

    public void setQuantidadeEstudantesRealizaramAvaliacaoPorPeriodo(int periodo, int quantidade) {
        assert 0 <= periodo && periodo <= Secretaria.NUMERO_DE_PERIODOS - 1;

        this.quantidadeEstudantesRealizaramAvaliacao[periodo] = quantidade;
    }

    public void apresentar() {
        System.out.println("  Identificador: " + this.id);
        System.out.println("  Etapa de Ensino: " + this.etapaDeEnsino);
        System.out.println("  Numero de Estudantes: " + this.estudantes.size());
        System.out.println("  Numero de Estudantes que fizeram avaliação: ");
        for(int i = 0; i < Secretaria.NUMERO_DE_PERIODOS; i++) {
            System.out.println("    Periodo " + (i+1) + ": " + this.quantidadeEstudantesRealizaramAvaliacao[i]);
        }
    }


}
