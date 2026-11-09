package projeto.com.example.modelo;

public class Gerente extends Funcionario {
    
    private String departamento;

    // Construtor vazio
    public Gerente() {
        super(); // Chama o construtor vazio da classe Funcionario
    }

    // Construtor com todos os dados
    public Gerente(int matricula, String nome, int idade, String departamento) {
        super(matricula, nome, idade); 
        this.departamento = departamento; 
    }

   
    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    @Override
    public String getDadosEspecificos() {
        
       return "Tipo: Gerente | Departamento: " + departamento;
    }
}