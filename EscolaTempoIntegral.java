public class EscolaTempoIntegral extends Escola {
    private int cargaHorariaDiaria;
    private int quantidadeAtividadesComplementares;

     public EscolaTempoIntegral(
        Secretaria secretaria,
        String nome,
        String endereco,
        String municipioRegiao,
        int quantidadeEstudantes,
        int quantidadeEstudantesPresentes,
        int cargaHorariaDiaria,
        int quantidadeAtividadesComplementares
    ) {
        super(secretaria, nome, endereco, municipioRegiao, quantidadeEstudantes, quantidadeEstudantesPresentes);

        this.cargaHorariaDiaria = cargaHorariaDiaria;
        this.quantidadeAtividadesComplementares = quantidadeAtividadesComplementares;
    }

    // TODO: refatorar como frequencia é representado
    @Override
    public String classificarMediaDeFaltasPorEstudante() {
        float f = super.calcularMediaDeFaltasPorEstudante() / (4*31);
        if(1 - f <= 0.85f) {
            return "Adequada";
        } else {
            return "Inadequada";
        }
    }

}
