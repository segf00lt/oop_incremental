// import java.util.ArrayList;

public class Escola {
    private String codigo;
    private String nome;
    private String endereco;
    private String municipioRegiao;
    private ResultadoAvaliacao[] resultadosAvaliacoes;
    private float mediaDesempenho;
    // TODO: Should this be ArrayList?
    // private ArrayList<Estudante> estudantesMatriculados;
    private Estudante[] estudantesMatriculados;
    private int quantidadeEstudantesPresentes;

    public Escola(
        String codigo,
        String nome,
        String endereco,
        String municipioRegiao,
        int quantidadeEstudantesMatriculados,
        int quantidadeEstudantesPresentes
    ) {

        this.codigo                        = codigo;
        this.nome                          = nome;
        this.endereco                      = endereco;
        this.municipioRegiao               = municipioRegiao;
        this.resultadosAvaliacoes          = new ResultadoAvaliacao[3];
        this.mediaDesempenho               = 0.0f;
        this.estudantesMatriculados        = new Estudante[quantidadeEstudantesMatriculados];
        this.quantidadeEstudantesPresentes = quantidadeEstudantesPresentes;

    }

    public Escola(
        String nome,
        int quantidadeEstudantesMatriculados,
        int quantidadeEstudantesPresentes
    ) {
        this("N/A", nome, "N/A", "N/A", quantidadeEstudantesMatriculados, quantidadeEstudantesPresentes);
    }

    // NOTE jfd 25/09/26: This method may be redundant due to the overload of Secretaria.cadastrarEscola()
    public Escola(
        String nome,
        int quantidadeEstudantesMatriculados
    ) {
        this(nome, quantidadeEstudantesMatriculados, 0);
    }

    public float getMediaDesempenho(int avaliacoesConsideradas) {
        float result = 0;

        for(int i = 0; i < avaliacoesConsideradas; i++) {
            ResultadoAvaliacao resultadoAvaliacao = this.resultadosAvaliacoes[0];

            if(resultadoAvaliacao != null) {
                result += resultadoAvaliacao.getNota();
            }
        }

        if(avaliacoesConsideradas > 0) {
            result /= (float)avaliacoesConsideradas;
        }

        return result;
    }

    /*

    public Estudante[] getEstudantesMatriculados();

    public void aplicarAvaliacao();
    */
}
