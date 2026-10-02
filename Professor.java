

public class Professor {

    private String nome;
    private int id;
    private String area;

    public Professor(Secretaria secretaria, String nome, String area) {
        this.nome = nome;
        this.area = area;
        this.id = secretaria.alocarIdProfessor();
    }

    public String getNome() {
        return this.nome;
    }

    public int getId() {
        return this.id;
    }

    public String getArea() {
        return this.area;
    }


}
