package Visao.VisaoInscricao;

import java.util.Scanner;
import CRUD_Curso.Curso;

public class VisaoInscricoes {
    private static Scanner console = new Scanner(System.in);


    public char menuMinhasInscricoes() {
        System.out.println("\n(A) Buscar curso por código");
        System.out.println("(B) Buscar curso por palavras-chave");
        System.out.println("(C) Listar todos os cursos");
        System.out.println("(C) Listar todos os cursos");
        System.out.println();
        System.out.println("(R) Retornar ao menu anterior");
        System.out.print("Opção: ");
        try {
            String input = console.nextLine();
            return input.length() > 0 ? Character.toUpperCase(input.charAt(0)) : ' ';
        } catch (Exception e) {
            return 'R';
        }
    }
    public String buscaPorCodigo_GetCodigo() {
        System.out.print("Codigo: ");
        String input = console.nextLine();
        return input.length() > 0 ? input : "";
    }
}
