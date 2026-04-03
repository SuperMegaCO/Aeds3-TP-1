import java.util.Scanner;

import Controle.ControleUsuario;


public class Principal {

public static void main(String[] args) {

    Scanner console;
    int currentUserId;
    try {
        console = new Scanner(System.in);
        char opcao;
        do {

            System.out.println("\n\nAEDsIII TP1 1:N");
            System.out.println("-------");
            System.out.println("> Início");
            System.out.println("\n(A) - Login");
            System.out.println("\n(B) - Novo usuario\n");
            System.out.println("(S) - Sair");

            System.out.print("\nOpção: ");
            try {
                opcao = console.nextLine().charAt(0);
            } catch(NumberFormatException e) {
                opcao = ' ';
            }

            switch (opcao) {
                case 'A':
                    (new ).menu();
                    break;
                case 'B':
                    currentUserId = (new ControleUsuario()).NewUserMenu();
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (opcao != 'S');


    } catch(Exception e) {
        e.printStackTrace();
    }

}

}