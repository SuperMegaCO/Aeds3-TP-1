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
                System.out.println("(B) - Novo usuario\n");
                System.out.println("(S) - Sair");

                System.out.print("\nOpção: ");
                try {
                    String input = console.nextLine();
                    opcao = input.length() > 0 ? Character.toUpperCase(input.charAt(0)) : ' ';
                } catch (Exception e) {
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
                try {
                    String input = console.nextLine();
                    opcao = input.length() > 0 ? Character.toUpperCase(input.charAt(0)) : ' ';
                } catch (Exception e) {
                    opcao = ' ';
                }
                switch (opcao) {
                    case 'C':
                        try {
                            boolean deleted = new Controle.ControleUsuario().MeusDadosMenu(currentUserId);
                            if (deleted) {
                                currentUserId = -1;
                                opcao = ' ';
                            }
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage());
                        }
                        break;
                    case 'D':
                        try {
                            new Controle.CursoController().menuCursos(currentUserId);
                        } catch (Exception e) {
                            System.out.println("Erro ao abrir cursos: " + e.getMessage());
                        }
                        break;
                    case 'S':
                        currentUserId = -1;
                        opcao = ' ';
                        break;
                    default:
                        System.out.println("Opção inválida!");
                        break;
                }
            }
        } while (opcao != 'S');

    }
}
