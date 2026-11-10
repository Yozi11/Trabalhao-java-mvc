package projeto.com.example.controle;

import java.util.ArrayList;

import projeto.com.example.dao.FuncionarioDAO;
import projeto.com.example.modelo.Funcionario;
import projeto.com.example.modelo.Gerente;
import projeto.com.example.modelo.Operador;

public class FuncionarioController {
    
    private FuncionarioDAO dao;

    public FuncionarioController() {
        dao = new FuncionarioDAO();
    }
    
    // Método com a assinatura no seu estilo, mas com as validações de regra de negócio
    public boolean cadastrar(int tipo, int matricula, String nome, int idade, String especifico) {
        // Validações básicas (nome preenchido e idade válida)
        if (nome == null || nome.trim().isEmpty() || idade <= 0) {
            return false;
        }
        
        // Regra: Verifica se a matrícula já existe no banco antes de cadastrar
        if (dao.buscar(matricula) != null) {
            return false; 
        }

        Funcionario funcionario = null;
        
        // Lógica de herança baseada no tipo escolhido
        if (tipo == 1) { // Gerente
            if (especifico == null || especifico.trim().isEmpty()) return false;
            funcionario = new Gerente(matricula, nome, idade, especifico);
        } else if (tipo == 2) { // Operador
            if (especifico == null || especifico.trim().isEmpty()) return false;
            funcionario = new Operador(matricula, nome, idade, especifico);
        } else {
            return false; // Tipo inválido
        }

        return dao.cadastrar(funcionario);
    }
    
    public ArrayList<Funcionario> listar() {
        return dao.listar();
    }

    public Funcionario buscar(int matricula) {
        return dao.buscar(matricula);
    }
    
    public boolean alterar(int matricula, String nome, int idade, String especifico) {
        // Validações
        if (nome == null || nome.trim().isEmpty() || idade <= 0) {
            return false;
        }

        // Regra: Verifica se o funcionário existe antes de alterar
        Funcionario fAtual = dao.buscar(matricula);
        if (fAtual == null) {
            return false; 
        }

        fAtual.setNome(nome);
        fAtual.setIdade(idade);

        // Atualiza o atributo específico dependendo da classe (Herança)
        if (fAtual instanceof Gerente) {
            if (especifico == null || especifico.trim().isEmpty()) return false;
            ((Gerente) fAtual).setDepartamento(especifico);
        } else if (fAtual instanceof Operador) {
            if (especifico == null || especifico.trim().isEmpty()) return false;
            ((Operador) fAtual).setFuncao(especifico);
        }

        return dao.alterar(fAtual);
    }
    
    public boolean remover(int matricula) {
        // Regra: Verifica se a matrícula existe antes de tentar excluir
        if (dao.buscar(matricula) == null) {
            return false;
        }
        return dao.remover(matricula);
    }
}