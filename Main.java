public class Main {
    public static void main(String[] args) {
        Secretaria secretaria = new Secretaria();

        Escola escolaMunicipalJoaoDaSilva = secretaria.cadastrarEscola(
            "Escola Municipal João da Silva",
            20,
            18,
            "Rua das Flores, 100",
            "São Paulo"
        );

        secretaria.cadastrarEstudanteEmEscola(escolaMunicipalJoaoDaSilva.getId(), "Aurélio");

        Professor profMarcos = secretaria.cadastrarProfessor("Marcos", "Matematica");
        Professor profJoao = secretaria.cadastrarProfessor("Joao", "Latim");
        Professor profHelena = secretaria.cadastrarProfessor("Helena", "Gramatica");

        escolaMunicipalJoaoDaSilva.cadastrarProfessor(profMarcos);
        escolaMunicipalJoaoDaSilva.cadastrarProfessor(profJoao);
        escolaMunicipalJoaoDaSilva.cadastrarProfessor(profHelena);

        for(int i = 1; i <= Secretaria.PERIODO_MAXIMO; i++) {
            System.out.printf("=== REALIZANDO AV%d ===\n\n", i);
            secretaria.avaliarEscolas(i);
            System.out.println();
            secretaria.apresentarEscolas();
        }


    }
}
