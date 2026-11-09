package projeto.com.example.modelo;

public class Operador extends Funcionario {
    
    private String funcao;

    // Construtor vazio
    public Operador() {
        super();
    }

    // Construtor com todos os dados
    public Operador(int matricula, String nome, int idade, String funcao) {
        super(matricula, nome, idade); 
        this.funcao = funcao; 
    }

    // Getters e Setters específicos do Operador
    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    @Override
    public String getDadosEspecificos() {
       
        return "Tipo: Operador | Função: " + funcao;
    }
}