package Visao.VisaoInscricao;

import java.util.Scanner;
import CRUD_Curso.Curso;
import CRUD_RelacionamentoCursoUsuario.RelacionamentoCursoUsuario;

public class VisaoInscricoes {
  private static Scanner console = new Scanner(System.in);

  public char menuDetalhes(RelacionamentoCursoUsuario c, CRUD_Curso.Curso curso) {
    System.out.println("\nEntrePares 1.0");
    System.out.println("--------------");
    System.out.println("> Início > Minhas inscrições > " + c.getNomeCurso());
    System.out.println("\nDetalhes da Inscrição:");
    System.out.println("Curso: " + c.getNomeCurso());
    System.out.println("Data de Inscrição: " + c.getDataInscricao());

    if (curso != null) {
      System.out.println("Descrição: " + curso.getDescricao());
      System.out.println("Estado: " + (curso.getEstado() == 0 ? "Inscrições abertas" : "Inscrições encerradas"));
    }

    System.out.println("\n(E) Cancelar minha inscrição no curso"); // Atualizado com o texto do roteiro
    System.out.println("(R) Retornar ao menu anterior");
    System.out.print("\nOpção: ");

    String input = new java.util.Scanner(System.in).nextLine();
    return input.length() > 0 ? Character.toUpperCase(input.charAt(0)) : ' ';
  }

  public String buscaPorCodigo_GetCodigo() {
    System.out.println("\nEntrePares 1.0");
    System.out.println("--------------");
    System.out.println("> Início > Minhas inscrições > Buscar por código");
    System.out.print("Código do curso: ");
    return new java.util.Scanner(System.in).nextLine();
  }

  public void mostraCurso(Curso curso) {
    System.out.println("\nEntrePares 1.0");
    System.out.println("--------------");
    System.out.println("> Início > Minhas inscrições >" + curso.getNome());
    System.out.println("\nDetalhes do Curso:");
    System.out.println("Nome: " + curso.getNome());
    System.out.println("Descrição: " + curso.getDescricao());
    System.out.println("Estado: " + (curso.getEstado() == 0 ? "Inscrições abertas" : "Inscrições encerradas"));
  }

  public int menuOpcoes() {
    System.out.println("\nEntrePares 1.0");
    System.out.println("--------------");
    System.out.println("> Início > Minhas inscrições");
    System.out.println("\n(A) Fazer minha inscrição no curso");
    System.out.println("(C) Cancelar minha inscrição no curso");
    System.out.println("(R) Retornar ao menu anterior");
    System.out.print("\nOpção: ");
    String input = new java.util.Scanner(System.in).nextLine();
    return input.length() > 0 ? Character.toUpperCase(input.charAt(0)) : ' ';
  }
}
