

public class Professor extends Pessoa {

    private String area;

    public Professor(String nome, String matricula, String dataDeNascimento, String area) {
        super(nome, matricula, dataDeNascimento);
        this.area = area;
    }

    public String getArea() {
        return this.area;
    }

    @Override
    public void apresentar() {
        System.out.println("Professor");
        super.apresentarInterno();
        System.out.println(" | Area: "+this.area);
    }


}
