import java.util.Scanner;

public class InterfaceUsuario {

    Scanner scanner = new Scanner(System.in);

    private final String CADASTRAR = "cadastrar";
    private final String ESCOLA    = "escola";
    private final String TURMA     = "turma";
    private final String PROFESSOR = "professor";
    private final String REGISTRAR = "registrar";
    private final String LISTAR    = "listar";
    private final String REMOVER   = "remover";
    private final String EXIBIR    = "exibir";
    private final String TOTAL     = "total";
    private final String ESCOLAS   = "escolas";
    private final String NOTA      = "nota";
    private final String AVALIACAO = "avaliação";
    private final String VINCULAR  = "vincular";

    private String commandString = "";
    private String originalCommandString = "";

    private boolean nextKeyword(String keyword) {
        if(this.commandString.startsWith(keyword)) {
            this.commandString = this.commandString.substring(keyword.length()).strip();
            return true;
        }

        return false;
    }

    private void help() {
        // TODO
        System.out.println();
    }

    public void processarComando(Secretaria secretaria) {

        System.out.print(">>> ");
        this.commandString = scanner.nextLine().toLowerCase();
        this.originalCommandString = this.commandString;

        boolean error = false;

        if(this.nextKeyword(CADASTRAR)) {
            System.out.println(this.commandString);
            if(this.nextKeyword(ESCOLA)) {
            } else if(this.nextKeyword(TURMA)) {
            } else if(this.nextKeyword(PROFESSOR)) {
            }
        } else if(this.nextKeyword(REMOVER)) {
        } else if(this.nextKeyword(VINCULAR)) {
        } else if(this.nextKeyword(EXIBIR)) {

            String[] following = { TOTAL, ESCOLAS };
            for(String k : following) {
                if(!this.nextKeyword(k)) {
                    error = true;
                    break;
                }
            }

            if(!error) {
                System.out.println("Total escolas já cadastradas: "+secretaria.getTotalEscolasJaRegistradas());
            }

        } else if(this.nextKeyword(LISTAR)) {
        } else if(this.nextKeyword(REGISTRAR)) {
        }

        if(error) {
            System.out.println("\'"+this.originalCommandString+"\' não é um commando válido");
            this.help();
        }

    }

}
