package Controle;

import java.util.Scanner;
    import CRUD_Usuario.Usuario;
import CRUD_Usuario.ArquivoUsuario;
import Visao.VisaoUsuario.*;
public class ControleUsuario {
    ArquivoUsuario arqUsuarios;
    VisaoUsuario visUsuario;
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
            return -1;
        }

    }
    public void Login() {
        Usuario tempUsr;
        System.out.println("\n\nAEDsIII TP1 1:N");
        System.out.println("-------");
        System.out.println("> Inicio > Login");
        do {
            LoginInfo logInf = visUsuario.Login();
        try {
            tempUsr = arqUsuarios.read(logInf.Email);
        }
        catch(Exception e) {
            System.out.println("Erro em fazer login.");
           
        }
        if (tempUsr.getHashSenha() != logInf.password) {
            System.err.println("Senha incorreta");
        }
        } while(tempUsr.getHashSenha() != logInf.password)
    }
}
