import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import aed3.ArvoreBMais;

public class Main {

  public static void main(String[] args) {

    ArvoreBMais<ParIntInt> arvore;
    Scanner console = new Scanner(System.in);

    try {
      File d = new File("dados");
      if (!d.exists())
        d.mkdir();

      arvore = new ArvoreBMais<>(ParIntInt.class.getConstructor(), 5, "dados/arvore.db");

      int opcao;
      do {
        System.out.println("\n\n-------------------------------");
        System.out.println("              MENU");
        System.out.println("-------------------------------");
        System.out.println("1 - Inserir");
        System.out.println("2 - Buscar cursos do usuário");
        System.out.println("3 - Excluir");
        System.out.println("4 - Listar todas");
        System.out.println("5 - Imprimir árvore");
        System.out.println("0 - Sair");

        try {
          opcao = Integer.valueOf(console.nextLine());
        } catch (NumberFormatException e) {
          opcao = -1;
        }

        switch (opcao) {

          case 1: {
            System.out.println("\nINCLUSÃO");

            int n1;
            String nomeCurso;

            try {
              System.out.print("ID usuario: ");
              n1 = Integer.valueOf(console.nextLine());

              System.out.print("Nome do curso: ");
              nomeCurso = console.nextLine();

            } catch (Exception e) {
              System.out.println("Erro na leitura!");
              break;
            }

            arvore.create(new ParIntInt(n1, nomeCurso));
            arvore.print();
          }
            break;

          case 2: {
            System.out.println("\nBUSCA");

            System.out.print("ID usuario: ");
            int n1 = Integer.valueOf(console.nextLine());

            ArrayList<ParIntInt> lista = arvore.read(null);

            System.out.println("Cursos encontrados:");

            for (ParIntInt p : lista) {
              if (p.getNum1() == n1)
                System.out.println(p.getNum2());
            }
          }
            break;

          case 3: {
            System.out.println("\nEXCLUSÃO");

            System.out.print("ID usuario: ");
            int n1 = Integer.valueOf(console.nextLine());

            System.out.print("Nome do curso: ");
            String nomeCurso = console.nextLine();

            arvore.delete(new ParIntInt(n1, nomeCurso));
            arvore.print();
          }
            break;

          case 4: {
            System.out.println("\nLISTA COMPLETA");

            ArrayList<ParIntInt> lista = arvore.read(null);

            for (int i = 0; i < lista.size(); i++)
              System.out.println(lista.get(i));
          }
            break;

          case 5: {
            arvore.print();
          }
            break;

          case 0:
            break;

          default:
            System.out.println("Opção inválida");
        }

      } while (opcao != 0);

      console.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}