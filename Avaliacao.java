public class Avaliacao {
    private String identificador;
    private int numero;

    public Avaliacao(String identificador, int numero) {
        this.identificador = identificador;
        this.numero = numero;
    }

    public String getIdentificador() {
        return this.identificador;
    }

    public int getNumero() {
        return this.numero;
    }

    public void apresentar() {
        System.out.println("Avaliação");
        System.out.println("  Identificador: "+this.identificador+" | Número: "+this.numero);
    }

}
