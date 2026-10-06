public class CadastroProfessorEscola {

    private Professor professor;
    private Escola    escola;

    public CadastroProfessorEscola(Professor professor, Escola escola) {
        this.professor = professor;
        this.escola    = escola;
    }

    public Professor getProfessor() {
        return this.professor;
    }

    public Escola getEscola() {
        return this.escola;
    }


}
