public class Main {

    private static void test(Secretaria secretaria) {
        Escola escolaMunicipalJoaoDaSilva = secretaria.cadastrarEscola(
            "Escola Municipal João da Silva",
            20,
            18,
            "Rua das Flores, 100",
            "São Paulo"
        );

        secretaria.cadastrarEstudanteEmEscola(escolaMunicipalJoaoDaSilva, "Aurélio", "123111", "14/07/2002");

        Professor profMarcos = secretaria.cadastrarProfessor("Marcos", "12312312", "13/01/2212", "Matematica");
        Professor profJoao   = secretaria.cadastrarProfessor("Joao",   "67456", "05/05/2004", "Latim");
        Professor profHelena = secretaria.cadastrarProfessor("Helena", "5534333", "09/02/393", "Gramatica");

        secretaria.cadastrarProfessorEscola(profMarcos, escolaMunicipalJoaoDaSilva);
        secretaria.cadastrarProfessorEscola(profJoao,   escolaMunicipalJoaoDaSilva);
        secretaria.cadastrarProfessorEscola(profHelena, escolaMunicipalJoaoDaSilva);

        Turma turma1 = escolaMunicipalJoaoDaSilva.cadastrarTurma("Fundamental");

        for(Estudante e : escolaMunicipalJoaoDaSilva.getEstudantes()) {
            turma1.cadastrarEstudante(e);
        }

        Escola escola2 = secretaria.cadastrarEscola(
            "Escola do Prof Raimundo",
            20,
            18,
            "Rua da Rua",
            "Nowhere"
        );

        secretaria.cadastrarEstudanteEmEscola(escola2, "Marcelo", "91881989", "01/01/0003");

        secretaria.removerCadastroProfessorEscola(profMarcos, escolaMunicipalJoaoDaSilva);
        secretaria.cadastrarProfessorEscola(profMarcos, escola2);

        for(int i = 1; i <= Secretaria.NUMERO_DE_PERIODOS; i++) {
            System.out.printf("=== REALIZANDO AV%d ===\n\n", i);
            secretaria.avaliarEscolas(i);
            System.out.println();
            secretaria.apresentarEscolas();
        }


    }

    public static void main(String[] args) {

        Secretaria secretaria = new Secretaria();

        // Main.test(secretaria);

        InterfaceUsuario ui = new InterfaceUsuario();

        ui.ajuda();
        while(true) {
            ui.processarComando(secretaria);
        }

    }
}
