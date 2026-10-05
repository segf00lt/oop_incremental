import java.util.ArrayList;

public class Estudante {
    private int id;
    private String nome;
    private ArrayList<Falta> faltas;

    public Estudante(int id, String nome) {
        this.id = id;
        this.nome = nome;
        this.faltas = new ArrayList<Falta>();
    }

    public void addFalta(int periodo) {
        Falta f = new Falta(periodo);
        this.faltas.add(f);
    }

    public int getTotalFaltas() {
        return this.faltas.size();
    }

    public int getFaltasPorPeriodo(int periodo) {
        int total = 0;

        for(Falta f : this.faltas) {
            if(f.getPeriodo() == periodo) {
                total++;
            }
        }

        return total;
    }

    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public void apresentar() {
        System.out.println(
            "  Identificador: " + this.id +
            " | Nome: " + this.nome +
            " | Faltas: " + this.getTotalFaltas()
        );
    }

}
