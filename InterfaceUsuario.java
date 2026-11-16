import java.util.Scanner;

public class InterfaceUsuario {

    Scanner scanner = new Scanner(System.in);

    private enum TokenKind {
        AJUDA("ajuda"),
        CADASTRAR("cadastrar"),
        REGISTRAR("registrar"),
        REMOVER("remover"),
        LISTAR("listar"),
        EXIBIR("exibir"),
        VINCULAR("vincular"),
        TURMA("turma"),
        PROFESSORES("professores"),
        PROFESSOR("professor"),
        ESTUDANTE("estudante"),
        DIRETOR("diretor"),
        PESSOAS("pessoas"),
        PESSOA("pessoa"),
        TOTAL("total"),
        JA("ja"),
        PERIODO("periodo"),
        FALTA("falta"),
        CADASTRADAS("cadastradas"),
        ESCOLAS("escolas"),
        ESCOLA("escola"),
        NOTA("nota"),
        AVALIACOES("avaliacoes"),
        AVALIACAO("avaliacao"),
        INTEGRAL("integral"),
        STRING,
        INT,
        FLOAT,
        EOL,
        INVALID;

        public String s = null;

        TokenKind() {}

        TokenKind(String s) {
            this.s = s;
        }
    }

    private class Token {
        public TokenKind kind;
        public String text;
        public int i;
        public float f;
        public long l;

        public Token(TokenKind kind) {
            this.kind = kind;
        }

        public Token(TokenKind kind, String text) {
            this.kind = kind;
            this.text = text;
        }

        public Token(TokenKind kind, String text, int i) {
            this(kind, text);
            this.i = i;
        }

        public Token(TokenKind kind, String text, float f) {
            this(kind, text);
            this.f = f;
        }

        public Token(TokenKind kind, String text, long l) {
            this(kind, text);
            this.l = l;
        }
    }

    private class Lexer {
        public String commandString = null;
        public int curPos = 0;
        public int prevPos = 0;

        public Lexer(String commandString) {
            this.commandString = commandString;
        }

        public Token lex() {
            String curString = this.commandString.substring(this.curPos);
            int leadingWhitespaceCount = 0;
            while(leadingWhitespaceCount < curString.length() && Character.isWhitespace(curString.charAt(leadingWhitespaceCount))) {
                leadingWhitespaceCount++;
            }
            this.curPos += leadingWhitespaceCount;
            curString = this.commandString.substring(this.curPos);
            String curStringLower = curString.toLowerCase();

            Token result = null;

            if(curString.length() == 0) {
                result = new Token(TokenKind.EOL);
                return result;
            }

            for(TokenKind t : TokenKind.values()) {
                if(t.s != null && curStringLower.startsWith(t.s)) {
                    result = new Token(t, t.s);
                    this.curPos += result.text.length();
                    return result;
                }
            }

            assert result == null;

            Scanner scan = new Scanner(curString);

            if(scan.hasNext("[+-]?(?:\\d+[,.]\\d*|[,.]\\d+)(?:[eE][+-]?\\d+)?")) {
                String text = scan.next();
                text = text.replace(',', '.');
                float f = Float.parseFloat(text);
                this.curPos += text.length();
                result = new Token(TokenKind.FLOAT, text, f);
            } else if(scan.hasNextInt()) {
                String text = scan.next();
                int i = Integer.parseInt(text);
                this.curPos += text.length();
                result = new Token(TokenKind.INT, text, i);
            } else if(curString.charAt(0) == '"') {
                String stringLiteralText = curString.substring(1);
                int nextQuoteIndex = stringLiteralText.indexOf("\"");
                if(nextQuoteIndex >= 0) {
                    stringLiteralText = stringLiteralText.substring(0, nextQuoteIndex);
                    // NOTE I don't care about " chars in string literals
                    this.curPos += 2 + stringLiteralText.length();
                    result = new Token(TokenKind.STRING, stringLiteralText);
                }
            }

            if(result == null) {
                result = new Token(TokenKind.INVALID);
                this.curPos += curString.length();
            }

            assert result != null;

            return result;
        }

        public void save() {
            this.prevPos = this.curPos;
        }

        public void unlex() {
            this.curPos = this.prevPos;
        }
    }

    public void ajuda() {
        System.out.println("Comandos disponíveis:");
        System.out.println();
        System.out.println("NOTAÇÃO:");
        System.out.println("  <nome: String>  Argumento obrigatório do tipo String.");
        System.out.println("  <n: int>        Argumento obrigatório do tipo inteiro.");
        System.out.println("  <n: float>      Argumento obrigatório do tipo decimal.");
        System.out.println("  <n: int|float>  Argumento inteiro ou decimal.");
        System.out.println("  [argumentos]   Argumentos opcionais.");
        System.out.println("  Strings devem ser delimitadas por aspas duplas.");
        System.out.println("  Um comando ocupa uma única linha e cada argumento é separado por um espaço.");
        System.out.println();

        System.out.println("  ajuda");
        System.out.println("      Exibe este menu de ajuda.");
        System.out.println();

        System.out.println("  cadastrar escola <nome: String> <quantidadeEstudantes: int>");
        System.out.println("      [<quantidadeEstudantesPresentes: int>]");
        System.out.println("      [<endereco: String> <municipioRegiao: String>]");
        System.out.println("      Cadastra uma escola.");
        System.out.println("      Os argumentos opcionais devem ser informados na ordem apresentada.");
        System.out.println();

        System.out.println("  cadastrar escola integral <nome: String>");
        System.out.println("      <quantidadeEstudantes: int>");
        System.out.println("      <quantidadeEstudantesPresentes: int>");
        System.out.println("      <endereco: String> <municipioRegiao: String>");
        System.out.println("      <cargaHorariaDiaria: int>");
        System.out.println("      <quantidadeAtividadesComplementares: int>");
        System.out.println("      Cadastra uma escola de tempo integral.");
        System.out.println();

        System.out.println("  cadastrar turma <etapaDeEnsino: String>");
        System.out.println("      escola <nomeEscola: String>");
        System.out.println("      Cadastra uma turma em uma escola existente.");
        System.out.println();

        System.out.println("  cadastrar professor <nome: String> <dataDeNascimento: String> <area: String>");
        System.out.println("      Cadastra um professor.");
        System.out.println();

        System.out.println("  cadastrar estudante <nome: String> <dataDeNascimento: String> escola <nomeEscola: String>");
        System.out.println("      Cadastra um estudante em uma escola.");
        System.out.println();

        System.out.println("  cadastrar diretor <nomeProfessor: String> <nomeEscola: String> <dataQueAssumiu: String>");
        System.out.println("      Cadastra um diretor.");
        System.out.println();

        System.out.println("  vincular professor <nomeProfessor: String>");
        System.out.println("      escola <nomeEscola: String>");
        System.out.println("      Vincula um professor a uma escola.");
        System.out.println();

        System.out.println("  registrar nota avaliacao <numeroAvaliacao: int>");
        System.out.println("      <nota: int|float> escola <nomeEscola: String>");
        System.out.println("      Registra uma nota para uma avaliacao de uma escola.");
        System.out.println();

        System.out.println("  registrar falta estudante <nome: String> escola <nomeEscola: String> periodo <numeroPeriodo: int>");
        System.out.println("      Registra uma falta para um estudante em uma escola dentro de um dos 3 periodos (0, 1 ou 2).");
        System.out.println();

        System.out.println("  listar escolas");
        System.out.println("  listar professores");
        System.out.println("  listar avaliacoes");
        System.out.println("  listar pessoas");
        System.out.println("      Lista escolas, professores, avaliacoes ou pessoas cadastradas.");
        System.out.println();

        System.out.println("  remover escola <nomeEscola: String>");
        System.out.println("      Remove uma escola cadastrada.");
        System.out.println();

        System.out.println("  exibir escola <nomeEscola: String>");
        System.out.println("  exibir professor <nomeProfessor: String>");
        System.out.println("  exibir pessoa <nomePessoa: String>"); // nocheckin
        System.out.println("  exibir total escolas ja cadastradas");
        System.out.println("      Exibe os dados solicitados.");
        System.out.println();
    }



    public void processarComando(Secretaria secretaria) {
        // this.ajuda();

        System.out.print(">>> ");
        String line = scanner.nextLine();
        Lexer lexer = new Lexer(line);

        Token t = lexer.lex();

        boolean error = false;

        switch(t.kind) {
            case CADASTRAR: {
                t = lexer.lex();
                switch(t.kind) {
                    case ESCOLA: {

                        t = lexer.lex();

                        if(t.kind == TokenKind.INTEGRAL) {
                            Token nome = lexer.lex();
                            Token quantidadeEstudantes = lexer.lex();
                            Token quantidadeEstudantesPresentes = lexer.lex();
                            Token endereco = lexer.lex();
                            Token municipioRegiao = lexer.lex();
                            Token cargaHorariaDiaria = lexer.lex();
                            Token quantidadeAtividadesComplementares = lexer.lex();

                            if(nome.kind != TokenKind.STRING || quantidadeEstudantes.kind != TokenKind.INT) {
                                error = true;
                                break;
                            }

                            if(quantidadeEstudantesPresentes.kind != TokenKind.INT) {
                                error = true;
                                break;
                            }

                            if(endereco.kind != TokenKind.STRING || municipioRegiao.kind != TokenKind.STRING) {
                                error = true;
                                break;
                            }

                            if(cargaHorariaDiaria.kind != TokenKind.INT || quantidadeAtividadesComplementares.kind != TokenKind.INT) {
                                error = true;
                                break;
                            }

                            secretaria.cadastrarEscolaTempoIntegral(
                                nome.text,
                                quantidadeEstudantes.i,
                                quantidadeEstudantesPresentes.i,
                                endereco.text,
                                municipioRegiao.text,
                                cargaHorariaDiaria.i,
                                quantidadeAtividadesComplementares.i
                            );

                        } else {
                            Token nome = t;
                            Token quantidadeEstudantes = lexer.lex();

                            if(nome.kind != TokenKind.STRING || quantidadeEstudantes.kind != TokenKind.INT) {
                                error = true;
                                break;
                            }


                            Token quantidadeEstudantesPresentes = lexer.lex();
                            if(quantidadeEstudantesPresentes.kind == TokenKind.EOL) {
                                secretaria.cadastrarEscola(nome.text, quantidadeEstudantes.i);
                                return;
                            }

                            if(quantidadeEstudantesPresentes.kind != TokenKind.INT) {
                                error = true;
                                break;
                            }

                            Token endereco = lexer.lex();
                            if(endereco.kind == TokenKind.EOL) {
                                secretaria.cadastrarEscola(nome.text, quantidadeEstudantes.i, quantidadeEstudantesPresentes.i);
                                return;
                            }

                            Token municipioRegiao = lexer.lex();
                            if(endereco.kind != TokenKind.STRING || municipioRegiao.kind != TokenKind.STRING) {
                                error = true;
                                break;
                            }

                            secretaria.cadastrarEscola(
                                nome.text,
                                quantidadeEstudantes.i,
                                quantidadeEstudantesPresentes.i,
                                endereco.text,
                                municipioRegiao.text
                            );
                        }

                    } break;
                    case TURMA: {
                        Token etapaDeEnsino = lexer.lex();
                        if(etapaDeEnsino.kind != TokenKind.STRING) {
                            error = true;
                            break;
                        }
                        Token escolaKeyword = lexer.lex();
                        if(escolaKeyword.kind != TokenKind.ESCOLA) {
                            error = true;
                            break;
                        }
                        Token nomeEscola = lexer.lex();
                        if(nomeEscola.kind != TokenKind.STRING) {
                            error = true;
                            break;
                        }
                        Escola e = secretaria.getEscolaPorNome(nomeEscola.text);
                        if(e == null) {
                            System.out.println("erro: escola '"+nomeEscola.text+"' não existe");
                            return;
                        }
                        e.cadastrarTurma(etapaDeEnsino.text);
                    } break;
                    case PROFESSOR: {
                        Token nome = lexer.lex();
                        Token dataDeNascimento = lexer.lex();
                        Token area = lexer.lex();
                        if(nome.kind != TokenKind.STRING || dataDeNascimento.kind != TokenKind.STRING || area.kind != TokenKind.STRING) {
                            error = true;
                            break;
                        }
                        secretaria.cadastrarProfessor(nome.text, dataDeNascimento.text, area.text);
                    } break;
                    case ESTUDANTE: {
                        Token nome = lexer.lex();
                        Token dataDeNascimento = lexer.lex();
                        if(nome.kind != TokenKind.STRING || dataDeNascimento.kind != TokenKind.STRING) {
                            error = true;
                            break;
                        }

                        if(lexer.lex().kind != TokenKind.ESCOLA) {
                            error = true;
                            break;
                        }

                        Token nomeEscola = lexer.lex();
                        Escola escola = secretaria.getEscolaPorNome(nomeEscola.text);
                        if(escola == null) {
                            System.out.println("erro: escola '"+nomeEscola.text+"' não existe");
                            return;
                        }
                        escola.cadastrarEstudante(nome.text, dataDeNascimento.text);
                    } break;
                    case DIRETOR: {
                        Token nomeProfessor = lexer.lex();
                        Token nomeEscola = lexer.lex();
                        Token dataQueAssumiu = lexer.lex();
                        if(nomeProfessor.kind != TokenKind.STRING || nomeEscola.kind != TokenKind.STRING || dataQueAssumiu.kind != TokenKind.STRING) {
                            error = true;
                            break;
                        }
                        Professor p = secretaria.getProfessorPorNome(nomeProfessor.text);
                        if(p == null) {
                            System.out.println("erro: professor '"+nomeProfessor.text+"' não existe");
                        }
                        Escola escola = secretaria.getEscolaPorNome(nomeEscola.text);
                        if(escola == null) {
                            System.out.println("erro: escola '"+nomeEscola.text+"' não existe");
                            return;
                        }
                        secretaria.cadastrarDiretor(p, escola, dataQueAssumiu.text);
                    } break;
                    default:
                    error = true;
                    break;
                }
            } break;
            case VINCULAR: {
                lexer.save();

                Token tokenProfessor = lexer.lex();
                Token tokenNomeProfessor = lexer.lex();

                if(tokenProfessor.kind != TokenKind.PROFESSOR || tokenNomeProfessor.kind != TokenKind.STRING) {
                    error = true;
                    break;
                }

                Token tokenEscola = lexer.lex();
                Token tokenNomeEscola = lexer.lex();

                if(tokenEscola.kind != TokenKind.ESCOLA || tokenNomeEscola.kind != TokenKind.STRING) {
                    error = true;
                    break;
                }

                Professor professor = secretaria.getProfessorPorNome(tokenNomeProfessor.text);
                if(professor == null) {
                    System.out.println("erro: professor '"+tokenNomeProfessor.text+"' não existe");
                    return;
                }

                Escola escola = secretaria.getEscolaPorNome(tokenNomeEscola.text);
                if(escola == null) {
                    System.out.println("erro: escola '"+tokenNomeEscola.text+"' não existe");
                    return;
                }

                secretaria.cadastrarProfessorEscola(professor, escola);
                System.out.println("professor vinculado com sucesso!");

            } break;
            case REGISTRAR: {
                lexer.save();
                Token t1 = lexer.lex();

                if(t1.kind == TokenKind.NOTA) {

                    Token t2 = lexer.lex();
                    Token t3 = lexer.lex();

                    if(t1.kind != TokenKind.NOTA || t2.kind != TokenKind.AVALIACAO || t3.kind != TokenKind.INT) {
                        error = true;
                        break;
                    }

                    int numeroAvaliacao = t3.i;
                    t = lexer.lex();
                    float nota = 0;
                    if(t.kind == TokenKind.INT) {
                        nota = (float)t.i;
                    } else if(t.kind == TokenKind.FLOAT) {
                        nota = t.f;
                    } else {
                        error = true;
                        break;
                    }

                    // TODO 16/11/26: The way these periodos work is really weird
                    if(numeroAvaliacao <= 0 || Secretaria.NUMERO_DE_PERIODOS < numeroAvaliacao) {
                        System.out.println("erro: avaliacao com número inválido");
                        return;
                    }

                    t = lexer.lex();
                    if(t.kind != TokenKind.ESCOLA) {
                        error = true;
                        break;
                    }

                    t = lexer.lex();
                    Escola escola = secretaria.getEscolaPorNome(t.text);
                    if(escola == null) {
                        System.out.println("erro: escola '"+t.text+"' não existe");
                        return;
                    }

                    Avaliacao[] avaliacoes = secretaria.getAvaliacoes();
                    for(Avaliacao a : avaliacoes) {
                        if(a.getNumero() == numeroAvaliacao) {
                            escola.registrarNotaAvaliacao(a, nota);
                        }
                    }
                } else if(t1.kind == TokenKind.FALTA) {
                    if(lexer.lex().kind != TokenKind.ESTUDANTE) {
                        error = true;
                        break;
                    }

                    Token nomeEstudante = lexer.lex();
                    if(nomeEstudante.kind != TokenKind.STRING) {
                        error = true;
                        break;
                    }

                    if(lexer.lex().kind != TokenKind.ESCOLA) {
                        error = true;
                        break;
                    }

                    Token nomeEscola = lexer.lex();
                    if(nomeEscola.kind != TokenKind.STRING) {
                        error = true;
                        break;
                    }

                    if(lexer.lex().kind != TokenKind.PERIODO) {
                        error = true;
                        break;
                    }

                    Token numeroPeriodo = lexer.lex();
                    if(numeroPeriodo.kind != TokenKind.INT) {
                        error = true;
                        break;
                    }

                    Escola escola = secretaria.getEscolaPorNome(nomeEscola.text);
                    if(escola == null) {
                        System.out.println("erro: escola '"+nomeEscola.text+"' não existe");
                        return;
                    }

                    Estudante estudante = null;

                    for(Estudante e : escola.getEstudantes()) {
                        if(e.getNome().equals(nomeEstudante.text)) {
                            estudante = e;
                            break;
                        }
                    }

                    if(estudante == null) {
                        System.out.println("erro: estudante '"+nomeEstudante.text+"' não existe");
                        return;
                    }

                    if(Secretaria.numeroPeriodoValido(numeroPeriodo.i) == false) {
                        System.out.println("erro: periodo invalido");
                        return;
                    }

                    estudante.addFalta(numeroPeriodo.i);

                }

            } break;
            case LISTAR: {
                t = lexer.lex();

                if(t.kind == TokenKind.ESCOLAS) {
                    Escola[] escolas = secretaria.getEscolas();
                    for(Escola e : escolas) {
                        float mediaDesempenho = e.getMediaDesempenho();

                        System.out.println("Escola");
                        System.out.println("  Identificador: "+e.getId()+
                            " | Nome: "+e.getNome()+
                            " | Total estudantes: "+e.getTotalEstudantes()+
                            " | Faltas por estudante: "+e.calcularMediaDeFaltasPorEstudante()+
                            " | Média de desempenho: "+mediaDesempenho+" ("+Utilidades.classificarDesempenho(mediaDesempenho)+")"+
                            " | Média de faltas por estudante: "+e.calcularMediaDeFaltasPorEstudante()+" ("+e.classificarMediaDeFaltasPorEstudante()+")"
                        );
                    }
                } else if(t.kind == TokenKind.PROFESSORES) {
                    Professor[] professores = secretaria.getProfessores();
                    for(Professor p : professores) {
                        p.apresentar();
                    }
                } else if(t.kind == TokenKind.AVALIACOES) {
                    Avaliacao[] avaliacoes = secretaria.getAvaliacoes();
                    for(Avaliacao a : avaliacoes) {
                        a.apresentar();
                    }
                } else if(t.kind == TokenKind.PESSOAS) {
                    Pessoa[] pessoas = secretaria.getPessoas();
                    for(Pessoa p : pessoas) {
                        p.apresentar();
                    }
                } else {
                    error = true;
                    break;
                }

            } break;
            case REMOVER: {
                t = lexer.lex();
                if(t.kind == TokenKind.ESCOLA) {
                    t = lexer.lex();
                    Escola escola = secretaria.getEscolaPorNome(t.text);
                    if(escola == null) {
                        System.out.println("erro: escola '"+t.text+"' não existe");
                        return;
                    }
                    secretaria.removerEscolaPorId(escola.getId());
                } else {
                    error = true;
                    break;
                }
            } break;
            case EXIBIR: {
                t = lexer.lex();

                switch(t.kind) {
                    case ESCOLA: {
                        t = lexer.lex();
                        if(t.kind != TokenKind.STRING) {
                            error = true;
                            break;
                        }
                        String nome = t.text;

                        Escola escola = secretaria.getEscolaPorNome(nome);
                        if(escola == null) {
                            System.out.println("erro: escola '"+nome+"' não existe");
                            return;
                        }

                        escola.apresentar();

                    } break;
                    case PROFESSOR: {
                        t = lexer.lex();
                        if(t.kind != TokenKind.STRING) {
                            error = true;
                            break;
                        }
                        String nome = t.text;

                        Professor professor = secretaria.getProfessorPorNome(nome);
                        if(professor == null) {
                            System.out.println("erro: professor '"+nome+"' não existe");
                            return;
                        }

                        professor.apresentar();

                    } break;
                    case PESSOA: {
                        t = lexer.lex();
                        if(t.kind != TokenKind.STRING) {
                            error = true;
                            break;
                        }
                        String nome = t.text;

                        Pessoa[] pessoas = secretaria.getPessoas();

                        Pessoa pessoaBuscada = null;
                        for(Pessoa p : pessoas) {
                            if(p.getNome().equals(nome)) {
                                pessoaBuscada = p;
                                break;
                            }
                        }

                        if(pessoaBuscada == null) {
                            System.out.println("erro: pessa '"+nome+"' não existe");
                            return;
                        }

                        pessoaBuscada.apresentar();
                    } break;
                    case TOTAL: {
                        Token t1 = lexer.lex();
                        Token t2 = lexer.lex();
                        Token t3 = lexer.lex();
                        if(t1.kind != TokenKind.ESCOLAS || t2.kind != TokenKind.JA || t3.kind != TokenKind.CADASTRADAS) {
                            error = true;
                            break;
                        }
                        System.out.println("Total escolas ja cadastradas: "+secretaria.getTotalEscolasJaRegistradas());
                    } break;
                    default:
                    error = true;
                    break;
                }
            } break;
            case AJUDA: {
                this.ajuda();
            } break;
            case EOL:
            break;
            case INVALID:
            default: {
                error = true;
                // System.out.println("Token: "+t.kind+" "+t.text);
            } break;
        }

        if(error) {
            System.out.println("erro: comando invalido, digite 'ajuda' para ver menu de opções");
        }

    }

}
