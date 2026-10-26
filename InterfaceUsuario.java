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
        TOTAL("total"),
        JA("já"),
        CADASTRADAS("cadastradas"),
        ESCOLAS("escolas"),
        ESCOLA("escola"),
        NOTA("nota"),
        AVALIACOES("avaliações"),
        AVALIACAO("avaliação"),
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

    private void ajuda() {
        System.out.println("Comandos disponíveis:");
        System.out.println();

        System.out.println("  ajuda");
        System.out.println("      Exibe este menu de ajuda.");
        System.out.println();

        System.out.println("  cadastrar escola \"nome\" INT [INT [\"endereço\" \"município/região\"]]");
        System.out.println("      Cadastra uma escola, com quantidade total de estudantes,");
        System.out.println("      quantidade opcional de estudantes presentes e endereço opcional.");
        System.out.println();

        System.out.println("  cadastrar turma \"etapa de ensino\" escola \"nome da escola\"");
        System.out.println("      Cadastra uma turma em uma escola existente.");
        System.out.println();

        System.out.println("  cadastrar professor \"nome\" \"área\"");
        System.out.println("      Cadastra um professor.");
        System.out.println();

        System.out.println("  vincular professor \"nome\" escola \"nome da escola\"");
        System.out.println("      Vincula um professor a uma escola.");
        System.out.println();

        System.out.println("  registrar nota avaliação INT (INT|FLOAT) escola \"nome\"");
        System.out.println("      Registra uma nota para uma avaliação de uma escola.");
        System.out.println();

        System.out.println("  listar escolas");
        System.out.println("  listar professores");
        System.out.println("  listar avaliações");
        System.out.println("      Lista as escolas, os professores ou as avaliações.");
        System.out.println();

        System.out.println("  remover escola \"nome\"");
        System.out.println("      Remove uma escola cadastrada.");
        System.out.println();

        System.out.println("  exibir escola \"nome\"");
        System.out.println("  exibir professor \"nome\"");
        System.out.println("  exibir total escolas já cadastradas");
        System.out.println("      Exibe os dados de uma escola, de um professor ou");
        System.out.println("      o total de escolas já cadastradas.");
        System.out.println();
    }

    public void processarComando(Secretaria secretaria) {

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

                        Token nome = lexer.lex();
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
                        Token area = lexer.lex();
                        if(nome.kind != TokenKind.STRING || area.kind != TokenKind.STRING) {
                            error = true;
                            break;
                        }
                        secretaria.cadastrarProfessor(nome.text, area.text);
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

                if(numeroAvaliacao <= 0 || Secretaria.NUMERO_DE_PERIODOS < numeroAvaliacao) {
                    System.out.println("erro: avaliação com número inválido");
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
                            " | Total Estudantes: "+e.getTotalEstudantes()+
                            " | Faltas por Estudante: "+e.calcularMediaDeFaltasPorEstudante()+
                            " | Média de Desempenho: "+mediaDesempenho+" ("+Utilidades.classificarDesempenho(mediaDesempenho)+")"
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
                    case TOTAL: {
                        Token t1 = lexer.lex();
                        Token t2 = lexer.lex();
                        Token t3 = lexer.lex();
                        if(t1.kind != TokenKind.ESCOLAS || t2.kind != TokenKind.JA || t3.kind != TokenKind.CADASTRADAS) {
                            error = true;
                            break;
                        }
                        System.out.println("Total escolas já cadastradas: "+secretaria.getTotalEscolasJaRegistradas());
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
