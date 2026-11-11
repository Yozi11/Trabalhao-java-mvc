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
    
    public boolean cadastrar(int tipo, int matricula, String nome, int idade, String especifico) {
       
        if (nome == null || nome.trim().isEmpty() || idade <= 0) {
            return false;
        }
        
        
        if (dao.buscar(matricula) != null) {
            return false; 
        }

        Funcionario funcionario = null;
        

        if (tipo == 1) { 
            if (especifico == null || especifico.trim().isEmpty()) 
                return false;
            funcionario = new Gerente(matricula, nome, idade, especifico);
        } else if (tipo == 2) { 
            if (especifico == null || especifico.trim().isEmpty()) 
                return false;
            funcionario = new Operador(matricula, nome, idade, especifico);
        } else {
            return false; 
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
        
        if (nome == null || nome.trim().isEmpty() || idade <= 0) {
            return false;
        }

     
        Funcionario fAtual = dao.buscar(matricula);
        if (fAtual == null) {
            return false; 
        }

        fAtual.setNome(nome);
        fAtual.setIdade(idade);

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
        
        if (dao.buscar(matricula) == null) {
            return false;
        }
        return dao.remover(matricula);
    }
}