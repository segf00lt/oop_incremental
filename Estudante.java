public class Estudante {
    private int id;
    private String nome;
    private int faltas;

    public Estudante(int id, String nome) {
        this.id = id;
        this.nome = nome;
        this.faltas = 0;
    }

    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }

    public int getFaltas() {
        return this.faltas;
    }

    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }


}
