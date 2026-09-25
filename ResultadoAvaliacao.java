public class ResultadoAvaliacao {
    private Avaliacao avaliacao;
    private float nota;

    public ResultadoAvaliacao(Avaliacao avaliacao, float nota) {
        this.avaliacao = avaliacao;
        this.nota = nota;
    }

    public float getNota() {
        return this.nota;
    }

}
