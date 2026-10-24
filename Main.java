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
        Professor profJoao   = secretaria.cadastrarProfessor("Joao", "Latim");
        Professor profHelena = secretaria.cadastrarProfessor("Helena", "Gramatica");

        secretaria.cadastrarProfessorEscola(profMarcos.getId(), escolaMunicipalJoaoDaSilva.getId());
        secretaria.cadastrarProfessorEscola(profJoao.getId(),   escolaMunicipalJoaoDaSilva.getId());
        secretaria.cadastrarProfessorEscola(profHelena.getId(), escolaMunicipalJoaoDaSilva.getId());

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

        secretaria.cadastrarEstudanteEmEscola(escola2.getId(), "Marcelo");

        secretaria.removerCadastroProfessorEscola(profMarcos.getId(), escolaMunicipalJoaoDaSilva.getId());
        secretaria.cadastrarProfessorEscola(profMarcos.getId(), escola2.getId());

        for(int i = 1; i <= Secretaria.NUMERO_DE_PERIODOS; i++) {
            System.out.printf("=== REALIZANDO AV%d ===\n\n", i);
            secretaria.avaliarEscolas(i);
            System.out.println();
            secretaria.apresentarEscolas();
        }

        InterfaceUsuario ui = new InterfaceUsuario();

        while(true) {
            ui.processarComando(secretaria);
        }

    }
}
