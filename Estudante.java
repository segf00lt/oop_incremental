import java.util.ArrayList;

public class Estudante extends Pessoa {
    private ArrayList<Falta> faltas;
    private Turma turma = null;

    public Estudante(String nome, String matricula, String dataDeNascimento) {
        super(nome, matricula, dataDeNascimento);
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

    public void setTurma(Turma t) {
        this.turma = t;
    }

    @Override
    public void apresentar() {
        System.out.println("Estudante");
        super.apresentarInterno();
        System.out.println(" | Turma: "+(this.turma != null ? this.turma.getId() : "N/A")+" | Faltas: "+this.getTotalFaltas());
    }

}
