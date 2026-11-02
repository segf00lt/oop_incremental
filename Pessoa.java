
public class Pessoa {
    protected String nome;
    protected String matricula;
    protected String dataDeNascimento;

    public Pessoa(String nome, String matricula, String dataDeNascimento) {
        this.nome             = nome;
        this.matricula        = matricula;
        this.dataDeNascimento = dataDeNascimento;
    }

    public String getNome() {
        return this.nome;
    }

    public String getMatricula() {
        return this.matricula;
    }

    public String getDataDeNascimento() {
        return this.dataDeNascimento;
    }

    protected void apresentarInterno() {
        System.out.print("  Nome: "+this.nome+" | Matricula: "+this.matricula+" | Data Nascimento: "+this.dataDeNascimento);
    }

    public void apresentar() {
        this.apresentarInterno();
        System.out.println();
    }

}
