package Visao.VisaoCurso;

import java.util.Scanner;
import CRUD_Curso.Curso;

public class VisaoCurso {
    private static Scanner console = new Scanner(System.in);

    public Curso lerCurso() {
        System.out.println("\nNovo Curso");
        System.out.print("Nome: ");
        String nome = console.nextLine();
        
        System.out.print("Data de Inicio (DD/MM/YYYY): ");
        String dataInicio = console.nextLine();
        
        System.out.print("Descricao: ");
        String descricao = console.nextLine();
        
        return new Curso(-1, -1, nome, dataInicio, descricao, "", 0);
    }
    
    public void mostraCurso(Curso c) {
        System.out.println("\nCÓDIGO........: " + c.getCodigo());
        System.out.println("NOME..........: " + c.getNome());
        System.out.println("DESCRIÇÃO.....: " + c.getDescricao());
        System.out.println("DATA DE INÍCIO: " + c.getDataInicio());
        System.out.print("\nEste curso está ");
        switch(c.getEstado()) {
            case 0: System.out.println("aberto para inscrições!"); break;
            case 1: System.out.println("ativo, mas as inscrições estão encerradas."); break;
            case 2: System.out.println("concluído."); break;
            case 3: System.out.println("cancelado."); break;
        }
    }

    public char menuCursoDetalhes() {
        System.out.println("\n(A) Gerenciar inscritos no curso");
        System.out.println("(B) Corrigir dados do curso");
        System.out.println("(C) Encerrar inscrições");
        System.out.println("(D) Concluir curso");
        System.out.println("(E) Cancelar curso");
        System.out.println("\n(R) Retornar ao menu anterior");
        System.out.print("\nOpção: ");
        try {
            String input = console.nextLine();
            return input.length() > 0 ? Character.toUpperCase(input.charAt(0)) : ' ';
        } catch (Exception e) {
            return ' ';
        }
    }

    public char menuMeusCursos() {
        System.out.println("\n(A) Novo curso");
        System.out.println("(R) Retornar ao menu anterior");
        System.out.print("\nOpção (ou index do curso para abrir): ");
        try {
            String input = console.nextLine();
            return input.length() > 0 ? Character.toUpperCase(input.charAt(0)) : ' ';
        } catch (Exception e) {
            return ' ';
        }
    }
}
