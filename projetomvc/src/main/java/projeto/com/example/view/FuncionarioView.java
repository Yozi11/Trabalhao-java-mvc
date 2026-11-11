package projeto.com.example.view;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import projeto.com.example.controle.FuncionarioController; 
import projeto.com.example.modelo.Funcionario;          
import projeto.com.example.modelo.Gerente;
import projeto.com.example.modelo.Operador;

public class FuncionarioView {
    
    private FuncionarioController controller = new FuncionarioController();

    public void iniciarMenu() {
        while (true) {
            String menu = "=== SISTEMA DE RH ===\n\n"
                        + "1 - Cadastrar Funcionário\n"
                        + "2 - Listar Funcionários\n"
                        + "3 - Pesquisar Funcionário\n"
                        + "4 - Alterar Funcionário\n"
                        + "5 - Excluir Funcionário\n"
                        + "6 - Sair\n\n"
                        + "Escolha uma opção:";

            String entrada = JOptionPane.showInputDialog(null, menu, "Menu", JOptionPane.QUESTION_MESSAGE);
            if (entrada == null) break; 

            try {
                int opcao = Integer.parseInt(entrada);
                processarOpcao(opcao);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Digite apenas números!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                try {
                    int tipo = Integer.parseInt(JOptionPane.showInputDialog("Tipo:\n1 - Gerente\n2 - Operador"));
                    if (tipo != 1 && tipo != 2) {
                        JOptionPane.showMessageDialog(null, "Tipo inválido!");
                        return;
                    }
                    int matricula = Integer.parseInt(JOptionPane.showInputDialog("Matrícula:"));
                    String nome = JOptionPane.showInputDialog("Nome:");
                    int idade = Integer.parseInt(JOptionPane.showInputDialog("Idade:"));
                    String especifico = JOptionPane.showInputDialog(tipo == 1 ? "Departamento:" : "Função:");
                    
                    
                    if (controller.cadastrar(tipo, matricula, nome, idade, especifico)) {
                        JOptionPane.showMessageDialog(null, "Funcionário cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Erro ao cadastrar! Verifique os dados ou se a matrícula já existe.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Dados numéricos inválidos!");
                }
                break;

            case 2:
                
                ArrayList<Funcionario> lista = controller.listar();
                if (lista.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Nenhum funcionário cadastrado.");
                } else {
                    StringBuilder sb = new StringBuilder("Funcionários:\n\n");
                    for (Funcionario f : lista) {
                        sb.append(f.getMatricula()).append(" - ").append(f.getNome())
                          .append(" (").append(f.getIdade()).append(" anos)\n");
                        if (f instanceof Gerente) {
                            sb.append("   Cargo: Gerente | Depto: ").append(((Gerente) f).getDepartamento()).append("\n\n");
                        } else if (f instanceof Operador) {
                            sb.append("   Cargo: Operador | Função: ").append(((Operador) f).getFuncao()).append("\n\n");
                        }
                    }
                    JOptionPane.showMessageDialog(null, sb.toString());
                }
                break;

            case 3:
                try {
                    int matBusca = Integer.parseInt(JOptionPane.showInputDialog("Digite a Matrícula:"));
                    
                    
                    Funcionario f = controller.buscar(matBusca); 
                    
                    if (f != null) {
                        String info = "Nome: " + f.getNome() + "\nIdade: " + f.getIdade() + "\n";
                        if (f instanceof Gerente) info += "Cargo: Gerente\nDepto: " + ((Gerente)f).getDepartamento();
                        if (f instanceof Operador) info += "Cargo: Operador\nFunção: " + ((Operador)f).getFuncao();
                        JOptionPane.showMessageDialog(null, info);
                    } else {
                        JOptionPane.showMessageDialog(null, "Matrícula não encontrada.");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Matrícula inválida!");
                }
                break;

            case 4:
                try {
                    int matAlt = Integer.parseInt(JOptionPane.showInputDialog("Matrícula para alterar:"));
                    Funcionario fAtual = controller.buscar(matAlt); 
                    
                    if (fAtual == null) {
                        JOptionPane.showMessageDialog(null, "Matrícula não encontrada!");
                        return;
                    }
                    
                    String novoNome = JOptionPane.showInputDialog("Novo nome (Atual: " + fAtual.getNome() + "):");
                    int novaIdade = Integer.parseInt(JOptionPane.showInputDialog("Nova idade (Atual: " + fAtual.getIdade() + "):"));
                    String novoEspecifico = JOptionPane.showInputDialog(
                        fAtual instanceof Gerente ? "Novo depto (Atual: " + ((Gerente)fAtual).getDepartamento() + "):" 
                                                  : "Nova função (Atual: " + ((Operador)fAtual).getFuncao() + "):"
                    );
                    
                    // Ajustado para o boolean
                    if (controller.alterar(matAlt, novoNome, novaIdade, novoEspecifico)) {
                        JOptionPane.showMessageDialog(null, "Funcionário alterado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Erro ao alterar o funcionário.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Dados inválidos!");
                }
                break;

            case 5:
                try {
                    int matExc = Integer.parseInt(JOptionPane.showInputDialog("Matrícula para excluir:"));
                    Funcionario fExc = controller.buscar(matExc); // ajustado para buscar()
                    
                    if (fExc == null) {
                        JOptionPane.showMessageDialog(null, "Matrícula não encontrada!");
                        return;
                    }
                    
                    int resposta = JOptionPane.showConfirmDialog(null, 
                            "Deseja realmente excluir " + fExc.getNome() + "?", "Aviso", JOptionPane.YES_NO_OPTION);
                            
                    if (resposta == JOptionPane.YES_OPTION) {
                        
                        if (controller.remover(matExc)) {
                            JOptionPane.showMessageDialog(null, "Funcionário removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Erro ao remover o funcionário.", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Matrícula inválida!");
                }
                break;

            case 6:
                System.exit(0);
                break;
                
            default:
                JOptionPane.showMessageDialog(null, "Opção inválida!");
        }
    }
}