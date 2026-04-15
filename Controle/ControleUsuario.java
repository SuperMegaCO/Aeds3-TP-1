package Controle;

import java.util.ArrayList;
import java.util.Scanner;
    import CRUD_Usuario.Usuario;
import CRUD_Usuario.ArquivoUsuario;
import Visao.VisaoUsuario.*;
public class ControleUsuario {
    ArquivoUsuario arqUsuarios;
    VisaoUsuario visUsuario = new VisaoUsuario();
    private static Scanner console = new Scanner(System.in);

    public ControleUsuario() throws Exception {
        arqUsuarios = new ArquivoUsuario();
    }
    public int NewUserMenu() {
        System.out.println("\n\nAEDsIII TP1 1:N");
        System.out.println("-------");
        System.out.println("> Inicio > Novo Usuario");
        Usuario tempUser = visUsuario.lerUsuario();

        try {
            return arqUsuarios.create(tempUser);
        }
        catch(Exception e) {
            
            System.out.println("Erro em salvar o usuario novo.");
            e.printStackTrace();
            return -1;
        }

    }
    public int Login() {
        LoginInfo logInf;
        Usuario tempUsr = null;
        System.out.println("\n\nAEDsIII TP1 1:N");
        System.out.println("-------");
        System.out.println("> Inicio > Login");
        do {
           logInf = visUsuario.Login();
        try {
            tempUsr = arqUsuarios.read(logInf.Email);
                if (tempUsr == null) {
                    System.err.println("Usuário não encontrado.");
                } else if (!tempUsr.getHashSenha().equals(logInf.password)) {
             System.err.println("Senha incorreta");
        }
            } catch(Exception e) {
            System.out.println("Erro em fazer login.");
           e.printStackTrace();
                tempUsr = null;
            }
        } while(tempUsr == null || !tempUsr.getHashSenha().equals(logInf.password));
        
        System.out.println("Login feito com sucesso");
        return tempUsr.getId();
    }

    public boolean MeusDadosMenu(int idUsuario) {
        System.out.println("\n> Início > Meus dados");
        try {
            Usuario u = arqUsuarios.read(idUsuario);
            System.out.println("Nome  : " + u.getNome());
            System.out.println("E-mail: " + u.getEmail());
            System.out.println("\n(E) Excluir minha conta");
            System.out.println("(R) Retornar ao menu anterior");
            System.out.print("\nOpção: ");
            String input = console.nextLine();
            char op = input.length() > 0 ? Character.toUpperCase(input.charAt(0)) : ' ';
            if (op == 'E') {
                CRUD_Curso.ArquivoCurso arqCursos = new CRUD_Curso.ArquivoCurso();
                ArrayList<CRUD_Curso.Curso> cursos = arqCursos.readCursosDoUsuario(idUsuario);
                boolean hasActive = false;
                for (CRUD_Curso.Curso c : cursos) {
                    // Estado 0 e 1 são cursos ativos
                    if (c.getEstado() == 0 || c.getEstado() == 1) {
                        hasActive = true;
                        break;
                    }
                }
                if (hasActive) {
                    System.out.println("Não é possivel excluir a conta. Você possui cursos ativos.");
                    return false;
                } else {
                    for (CRUD_Curso.Curso c : cursos) {
                         arqCursos.delete(c.getId());
                    }
                    arqUsuarios.delete(idUsuario);
                    System.out.println("Conta excluída com sucesso.");
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao abrir dados: " + e.getMessage());
        }
        return false;
    }
}
