package projeto.com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import projeto.com.example.conexao.Conexao;
import projeto.com.example.modelo.Funcionario;
import projeto.com.example.modelo.Gerente;
import projeto.com.example.modelo.Operador;

public class FuncionarioDAO {

    public boolean cadastrar(Funcionario funcionario) {
        String sql = "INSERT INTO funcionario(matricula, nome, idade, tipo, departamento, funcao) VALUES(?,?,?,?,?,?)";
        
        try (Connection conexao = Conexao.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
            
            comando.setInt(1, funcionario.getMatricula());
            comando.setString(2, funcionario.getNome());
            comando.setInt(3, funcionario.getIdade());
            
            if (funcionario instanceof Gerente) {
                Gerente g = (Gerente) funcionario;
                comando.setString(4, "GERENTE");
                comando.setString(5, g.getDepartamento());
                comando.setString(6, null);
            } else if (funcionario instanceof Operador) {
                Operador o = (Operador) funcionario;
                comando.setString(4, "OPERADOR");
                comando.setString(5, null);
                comando.setString(6, o.getFuncao());
            }
            
            comando.executeUpdate();
            return true;
        } catch(SQLException e) {
            System.out.println("Erro ao cadastrar: " + e.getMessage());
            return false;
        }  
    }
    
    public ArrayList<Funcionario> listar() {
        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT matricula, nome, idade, tipo, departamento, funcao FROM funcionario ORDER BY matricula";
        
        try (Connection conexao = Conexao.getConnection(); 
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {
             
            while (resultado.next()) {
                String tipo = resultado.getString("tipo");
                if (tipo.equals("GERENTE")) {
                    funcionarios.add(new Gerente(
                        resultado.getInt("matricula"),
                        resultado.getString("nome"),
                        resultado.getInt("idade"),
                        resultado.getString("departamento")
                    ));
                } else if (tipo.equals("OPERADOR")) {
                    funcionarios.add(new Operador(
                        resultado.getInt("matricula"),
                        resultado.getString("nome"),
                        resultado.getInt("idade"),
                        resultado.getString("funcao")
                    ));
                }
            }
        } catch(SQLException e) {
            System.out.println("Erro ao listar: " + e.getMessage());
        }
        return funcionarios;
    }

    public Funcionario buscar(int matricula) {
        String sql = "SELECT matricula, nome, idade, tipo, departamento, funcao FROM funcionario WHERE matricula=?";

        try (Connection conexao = Conexao.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
             
            comando.setInt(1, matricula);
            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    String tipo = resultado.getString("tipo");
                    if (tipo.equals("GERENTE")) {
                        return new Gerente(
                            resultado.getInt("matricula"), resultado.getString("nome"),
                            resultado.getInt("idade"), resultado.getString("departamento")
                        );
                    } else if (tipo.equals("OPERADOR")) {
                        return new Operador(
                            resultado.getInt("matricula"), resultado.getString("nome"),
                            resultado.getInt("idade"), resultado.getString("funcao")
                        );
                    }
                }
            }
        } catch(SQLException e) {
            System.out.println("Erro ao buscar: " + e.getMessage());
        }
        return null;    
    }
    
    public boolean alterar(Funcionario funcionario) {
        String sql = "UPDATE funcionario SET nome=?, idade=?, tipo=?, departamento=?, funcao=? WHERE matricula=?";

        try (Connection conexao = Conexao.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
             
            comando.setString(1, funcionario.getNome());
            comando.setInt(2, funcionario.getIdade());
            
            if (funcionario instanceof Gerente) {
                Gerente g = (Gerente) funcionario;
                comando.setString(3, "GERENTE");
                comando.setString(4, g.getDepartamento());
                comando.setString(5, null);
            } else if (funcionario instanceof Operador) {
                Operador o = (Operador) funcionario;
                comando.setString(3, "OPERADOR");
                comando.setString(4, null);
                comando.setString(5, o.getFuncao());
            }
            
            comando.setInt(6, funcionario.getMatricula());
            return comando.executeUpdate() > 0;
        } catch(SQLException e) {
            System.out.println("Erro ao alterar: " + e.getMessage());
            return false;
        }   
    }
    
    public boolean remover(int matricula) {
        String sql = "DELETE FROM funcionario WHERE matricula=?";

        try (Connection conexao = Conexao.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {
             
            comando.setInt(1, matricula);
            return comando.executeUpdate() > 0;
        } catch(SQLException e) {
            System.out.println("Erro ao remover: " + e.getMessage());
            return false;
        }   
    }
}