package Visao.VisaoCurso;
import java.util.Scanner;
import Controle.*;
import CRUD_Curso.*;

public class CursoView {

    private CursoController controller;
    private Scanner sc;

    public CursoView() throws Exception {
        controller = new CursoController();
        sc = new Scanner(System.in);
    }

    public void menu() throws Exception {
        int op;

        do {
            System.out.println("\n=== CURSOS ===");
            System.out.println("1 - Criar curso");
            System.out.println("2 - Buscar curso");
            System.out.println("3 - Atualizar curso");
            System.out.println("4 - Deletar curso");
            System.out.println("5 - Listar cursos por usuário");
            System.out.println("0 - Sair");

            op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1 -> criar();
                case 2 -> buscar();
                case 3 -> atualizar();
                case 4 -> deletar();
                case 5 -> listarPorUsuario();
            }

        } while (op != 0);
    }

    private void criar() throws Exception {
        System.out.print("ID do usuário: ");
        int idUsuario = sc.nextInt(); sc.nextLine();

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Data início: ");
        String data = sc.nextLine();

        System.out.print("Descrição: ");
        String desc = sc.nextLine();

        System.out.print("Estado (0-3): ");
        int estado = sc.nextInt();

        controller.criarCurso(idUsuario, nome, data, desc, estado);
    }

    private void buscar() throws Exception {
        System.out.print("ID do curso: ");
        int id = sc.nextInt();

        controller.buscarCurso(id);
    }

    private void atualizar() throws Exception {
        System.out.print("ID do curso: ");
        int id = sc.nextInt(); sc.nextLine();

        System.out.print("Novo nome: ");
        String nome = sc.nextLine();

        System.out.print("Nova data: ");
        String data = sc.nextLine();

        System.out.print("Nova descrição: ");
        String desc = sc.nextLine();

        System.out.print("Novo estado: ");
        int estado = sc.nextInt();

        Curso c = new Curso(-1, 0, nome, data, desc, "", estado);
        c.setId(id);

        controller.atualizarCurso(c);
    }

    private void deletar() throws Exception {
        System.out.print("ID do curso: ");
        int id = sc.nextInt();

        controller.deletarCurso(id);
    }

    private void listarPorUsuario() throws Exception {
        System.out.print("ID do usuário: ");
        int id = sc.nextInt();

        controller.listarCursosUsuario(id);
    }
}