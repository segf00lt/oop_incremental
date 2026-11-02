public class Diretor extends Professor {
    private Escola escola;
    private String dataQueAssumiu;

    public Diretor(Professor professor, Escola escola, String dataQueAssumiu) {
        super(professor.getNome(), professor.getMatricula(), professor.getDataDeNascimento(), professor.getArea());
        this.escola = escola;
        this.dataQueAssumiu = dataQueAssumiu;
    }

    @Override
    public void apresentar() {
        System.out.println("Diretor");
        super.apresentarInterno();
        // System.out.println("  Identificador: "+this.id+" | Nome: "+this.nome+" | Area: "+this.area);
        System.out.println(" | Area: "+super.getArea()+" | Escola: "+this.escola.getNome()+" | Data que assumiu: "+this.dataQueAssumiu);
    }


}
