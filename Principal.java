import java.util.Scanner;

import Controle.ControleUsuario;

public class Principal {

    public static void main(String[] args) throws Exception {

        Scanner console;
        int currentUserId = -1;

        console = new Scanner(System.in);
        char opcao = ' ';
        do {

            System.out.println("\n\nAEDsIII TP1 1:N");
            System.out.println("-------");
            System.out.println("> Início");
            if (currentUserId == -1) {
                System.out.println("\n(A) - Login");
                System.out.println("\n(B) - Novo usuario\n");
                System.out.println("(S) - Sair");

                System.out.print("\nOpção: ");
                try {
                    opcao = console.nextLine().charAt(0);
                } catch (NumberFormatException e) {
                    opcao = ' ';
                }

                switch (opcao) {
                    case 'A':
                        currentUserId = (new ControleUsuario()).Login();
                        break;
                    case 'B':
                        currentUserId = (new ControleUsuario()).NewUserMenu();
                        break;
                    default:
                        System.out.println("Opção inválida!");
                        break;
                }
            } else {
                System.out.println("\n(C) - Meus dados");
                System.out.println("(D) - Meus cursos");
                System.out.println("(S) - Deslogar");
                System.out.print("\nOpção: ");
                switch (opcao) {
                    case 'C':
                        System.out.println("Abrindo meus dados...");
                        break;
                    case 'D':
                        System.out.println("Abrindo meus cursos...");
                        break;
                    case 'S':
                        currentUserId = -1;
                        opcao = ' ';
                        break;
                }
            }
        } while (opcao != 'S');

    }
}
