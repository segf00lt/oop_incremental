public class Estudante {
    private String matricula;
    private String nome;
    private int faltas;

    public Estudante(String matricula, String nome) {
        this.matricula = matricula;
        this.nome = nome;
        this.faltas = 0;
    }

    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }

}
