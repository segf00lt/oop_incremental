import java.util.ArrayList;
import java.util.function.Predicate;

public class Secretaria {
    private ArrayList<Escola> escolas;
    private Avaliacao[] avaliacoes;
    private ArrayList<Professor> professores;

    private ArrayList<CadastroProfessorEscola> cadastrosProfessorEscola;

    private long contadorIdProfessor;
    private long contadorIdEscola;

    public static final int NUMERO_DE_PERIODOS = 3;
    public static final int PERIODO_PRIMEIRO = 0;
    public static final int PERIODO_SEGUNDO = 1;
    public static final int PERIODO_TERCEIRO = 2;
    public static final int MAXIMO_FALTAS_POR_PERIODO = 16;

    public Secretaria() {
        this.contadorIdProfessor = 0;
        this.contadorIdEscola = 0;

        this.escolas = new ArrayList<Escola>();
        this.professores = new ArrayList<Professor>();

        this.cadastrosProfessorEscola = new ArrayList<CadastroProfessorEscola>();

        this.avaliacoes = new Avaliacao[this.NUMERO_DE_PERIODOS];

        for(int i = 0; i < this.NUMERO_DE_PERIODOS; i++) {
            int numeroAvaliacao = i + 1;
            String idAvaliacao = "AV" + numeroAvaliacao;
            Avaliacao avaliacao = new Avaliacao(idAvaliacao, numeroAvaliacao);
            this.avaliacoes[i] = avaliacao;
        }
    }

    public void avaliarEscolas(int numeroAvaliacao) {
        assert 1 <= numeroAvaliacao && numeroAvaliacao <= this.avaliacoes.length;

        Avaliacao a = this.avaliacoes[numeroAvaliacao - 1];

        for(Escola e : this.escolas) {
            e.aplicarAvaliacao(a);
        }
    }

    public void apresentarEscolas() {
        System.out.printf("=== APRESENTANDO DADOS PARA %d ESCOLAS ===\n\n", this.escolas.size());
        for(Escola e : this.escolas) {
            e.apresentar();
            System.out.print("\n\n");
        }
    }

    public Escola cadastrarEscola(
        String nome,
        int quantidadeEstudantes,
        int quantidadeEstudantesPresentes,
        String endereco,
        String municipioRegiao
    ) {
        Escola escola = new Escola(this, nome, endereco, municipioRegiao, quantidadeEstudantes, quantidadeEstudantesPresentes);
        this.escolas.add(escola);
        return escola;
    }

    public Escola cadastrarEscola(String nome, int quantidadeEstudantes, int quantidadeEstudantesPresentes) {
        return this.cadastrarEscola(nome, quantidadeEstudantes, quantidadeEstudantesPresentes, "N/A", "N/A");
    }

    public Escola cadastrarEscola(String nome, int quantidadeEstudantes) {
        return this.cadastrarEscola(nome, quantidadeEstudantes, 0);
    }

    public int getEscolaIndicePorId(int id) {
        for(int i = 0; i < this.escolas.size(); i++) {
            Escola e = this.escolas.get(i);
            if(e.getId() == id) {
                return i;
            }
        }
        return -1;
    }

    public Escola getEscolaPorId(long id) {
        for(Escola e : this.escolas) {
            if(e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    public Escola[] getEscolas() {
        Escola[] escolas = new Escola[this.escolas.size()];
        escolas = this.escolas.toArray(escolas);
        return escolas;
    }

    public Professor getProfessorPorId(long id) {
        for(Professor p : this.professores) {
            if(p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public Escola getEscolaPorNome(String nome) {
        for(Escola e : this.escolas) {
            if(e.getNome().equals(nome)) {
                return e;
            }
        }
        return null;
    }

    public long getTotalEscolasJaRegistradas() {
        return this.contadorIdEscola;
    }

    public boolean removerEscolaPorId(int id) {
        int indice = this.getEscolaIndicePorId(id);

        if(indice >= 0) {
            this.escolas.remove(indice);
            return true;
        }

        return false;
    }

    public void cadastrarProfessorEscola(Professor professor, Escola escola) {
        CadastroProfessorEscola cadastro = new CadastroProfessorEscola(professor, escola);
        assert this.cadastrosProfessorEscola != null;
        this.cadastrosProfessorEscola.add(cadastro);
    }

    public void cadastrarProfessorEscola(long professorId, long escolaId) {
        Professor p = this.getProfessorPorId(professorId);
        assert p != null;
        Escola e = this.getEscolaPorId(escolaId);
        assert e != null;
        this.cadastrarProfessorEscola(p, e);
    }

    public void removerCadastroProfessorEscola(Professor professor, Escola escola) {
        int index = 0;
        for(int i = 0; i < this.cadastrosProfessorEscola.size(); i++) {
            CadastroProfessorEscola c = this.cadastrosProfessorEscola.get(i);
            if(c.getProfessor() == professor && c.getEscola() == escola) {
                index = i;
                break;
            }
        }
        this.cadastrosProfessorEscola.remove(index);
    }

    public void removerCadastroProfessorEscola(long professorId, long escolaId) {
        Professor p = this.getProfessorPorId(professorId);
        assert p != null;
        Escola e = this.getEscolaPorId(escolaId);
        assert e != null;
        this.removerCadastroProfessorEscola(p, e);
    }

    public CadastroProfessorEscola[] getCadastrosProfessorEscola(Predicate<CadastroProfessorEscola> condicaoBusca) {
        ArrayList<CadastroProfessorEscola> tmp = new ArrayList<>();

        for(CadastroProfessorEscola c : this.cadastrosProfessorEscola) {
            if(condicaoBusca.test(c)) {
                tmp.add(c);
            }
        }

        CadastroProfessorEscola[] cadastros = tmp.toArray(new CadastroProfessorEscola[0]);

        return cadastros;
    }

    public long alocarIdProfessor() {
        long id = this.contadorIdProfessor;
        this.contadorIdProfessor++;
        return id;
    }

    public long alocarIdEscola() {
        long id = this.contadorIdEscola;
        this.contadorIdEscola++;
        return id;
    }

    public Professor cadastrarProfessor(String nome, String area) {
        Professor p = new Professor(this, nome, area);
        this.professores.add(p);
        return p;
    }

    public void cadastrarEstudanteEmEscola(long idEscola, String nomeEstudante) {
        Escola escola = this.getEscolaPorId(idEscola);
        escola.cadastrarEstudante(nomeEstudante);
    }
}
