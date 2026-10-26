

public class Professor {

    private String nome;
    private long id;
    private String area;

    public Professor(Secretaria secretaria, String nome, String area) {
        this.nome = nome;
        this.area = area;
        this.id = secretaria.alocarIdProfessor();
    }

    public String getNome() {
        return this.nome;
    }

    public long getId() {
        return this.id;
    }

    public String getArea() {
        return this.area;
    }

    public void apresentar() {
        System.out.println("Professor");
        System.out.println("  Identificador: "+this.id+" | Nome: "+this.nome+" | Area: "+this.area);
    }


}
