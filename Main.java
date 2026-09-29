public class Main {
    public static void main(String[] args) {
        Secretaria secretaria = new Secretaria();

        secretaria.cadastrarEscola(
            "ESC001",
            "Escola Municipal João da Silva",
            "Rua das Flores, 100",
            "São Paulo",
            20,
            18
        );

        for(int i = 1; i <= Secretaria.PERIODO_MAXIMO; i++) {
            System.out.printf("=== REALIZANDO AV%d ===\n\n", i);
            secretaria.avaliarEscolas(i);
            System.out.println();
            secretaria.apresentarDadosEscolas();
        }


    }
}
