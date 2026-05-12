package Visao.VisaoUsuario;

import java.util.Scanner;

import CRUD_Usuario.ArquivoUsuario;
import CRUD_Usuario.Usuario;

public class VisaoUsuario {
   
    private static Scanner console = new Scanner(System.in);

    public VisaoUsuario() throws Exception {
    }
    
    
    public Usuario lerUsuario() {
        System.out.println("\nNOVO USUÁRIO");
        System.out.print("Nome: ");
        String nome = console.nextLine();
        System.out.print("E-mail: ");
        String email = console.nextLine();
        System.out.print("Senha: ");
        String senha = console.nextLine();
        System.out.print("Pergunta Secreta: ");
        String pergunta = console.nextLine();
        System.out.print("Resposta Secreta: ");
        String resposta = console.nextLine();
        return new Usuario(-1, nome, email, senha, pergunta, resposta);
    }
    public LoginInfo Login() {
            System.out.println("\nLOGIN:");
            System.out.print("E-mail: ");
            String email = console.nextLine();
            System.out.print("Senha: ");
            String senha = console.nextLine();
        return new LoginInfo(email, senha);
    }
    public void mostraUsuario(Usuario u) {
        if (u != null) {
            System.out.println(u.toString()); // Usa o toString que você já criou
        } else {
            System.out.println("Usuário não encontrado.");
        }
    }

    public Usuario editarUsuario(Usuario u) {
        System.out.println("\nEDITAR DADOS");
        System.out.println("Deixe em branco para manter o valor atual.");
        System.out.print("Nome atual: " + u.getNome() + "\nNovo nome: ");
        String nome = console.nextLine();
        if (nome.trim().isEmpty()) nome = u.getNome();
        
        System.out.print("E-mail atual: " + u.getEmail() + "\nNovo e-mail: ");
        String email = console.nextLine();
        if (email.trim().isEmpty()) email = u.getEmail();
        
        System.out.print("Nova senha (deixe em branco para manter): ");
        String senha = console.nextLine();
        if (senha.trim().isEmpty()) senha = u.getHashSenha();
        
        return new Usuario(u.getId(), nome, email, senha, u.getPerguntaSecreta(), u.getHashRespostaSecreta());
    }
}
