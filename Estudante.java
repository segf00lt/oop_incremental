public class Estudante {
    private int matricula;
    private String nome;
    private int faltas;

    public Estudante(int matricula, String nome) {
        this.matricula = matricula;
        this.nome = nome;
        this.faltas = 0;
    }

    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }

    public int getFaltas() {
        return this.faltas;
    }

    public int getMatricula() {
        return this.matricula;
    }

    public String getNome() {
        return this.nome;
    }


}
