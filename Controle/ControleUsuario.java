package Controle;

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
            if (tempUsr.getHashSenha().compareTo(logInf.password) != 0) {
             System.err.println("Senha incorreta");
             System.out.println(tempUsr.getHashSenha());
            }
        }
        
        catch(Exception e) {
            System.out.println("Erro em fazer login.");
           e.printStackTrace();
        }
       
        } while(tempUsr.getHashSenha() != logInf.password);
        System.out.println("Login feito com sucesso");
        return tempUsr.id;
    }
}
