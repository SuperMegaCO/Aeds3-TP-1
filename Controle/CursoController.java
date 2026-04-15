package Controle;

import java.util.ArrayList;
import aed3.*;
import CRUD_Curso.*;
import aed3.ArvoreBMais;

public class CursoController {

    private ArquivoCurso crud;

    public CursoController() throws Exception {
        crud = new ArquivoCurso();
    }

    public void listarCursosUsuario(int idUsuario) throws Exception {
        java.util.ArrayList<Curso> cursos = crud.readCursosDoUsuario(idUsuario);
        System.out.println("\nCURSOS");
        if (cursos.isEmpty()) {
            System.out.println("Nenhum curso encontrado.");
            return;
        }
        for (int i = 0; i < cursos.size(); i++) {
            Curso c = cursos.get(i);
            System.out.println("(" + (i + 1) + ") " + c.getNome() + " - " + c.getDataInicio());
        }
    }

    public void menuCursos(int idUsuario) throws Exception {
        Visao.VisaoCurso.VisaoCurso visao = new Visao.VisaoCurso.VisaoCurso();
        char opcao;
        do {
            java.util.ArrayList<Curso> cursos = crud.readCursosDoUsuario(idUsuario);
            System.out.println("\n> Início > Meus cursos\n\nCURSOS");
            if (cursos.isEmpty()) {
                System.out.println("Nenhum curso encontrado.");
            } else {
                for (int i = 0; i < cursos.size(); i++) {
                    Curso c = cursos.get(i);
                    System.out.println("(" + (i + 1) + ") " + c.getNome() + " - " + c.getDataInicio());
                }
            }
            
            opcao = visao.menuMeusCursos();
            if (opcao == 'A') {
                Curso novo = visao.lerCurso();
                novo.setIdUsuario(idUsuario);
                crud.create(novo);
                System.out.println("Curso criado!");
            } else if (opcao >= '1' && opcao <= '9') {
                int index = opcao - '1';
                if (index >= 0 && index < cursos.size()) {
                    menuCursoDetalhes(cursos.get(index), visao);
                }
            }
        } while (opcao != 'R');
    }

    private void menuCursoDetalhes(Curso c, Visao.VisaoCurso.VisaoCurso visao) throws Exception {
        char op;
        do {
            visao.mostraCurso(c);
            op = visao.menuCursoDetalhes();
            switch (op) {
                case 'B':
                    Curso atualizado = visao.lerCurso();
                    atualizado.setId(c.getId());
                    atualizado.setIdUsuario(c.getIdUsuario());
                    atualizado.setCodigo(c.getCodigo());
                    atualizado.setEstado(c.getEstado());
                    crud.update(atualizado);
                    c = atualizado;
                    break;
                case 'C':
                    c.setEstado(1);
                    crud.update(c);
                    System.out.println("Inscrições encerradas.");
                    break;
                case 'D':
                    c.setEstado(2);
                    crud.update(c);
                    System.out.println("Curso concluído.");
                    break;
                case 'E':
                    c.setEstado(3);
                    crud.update(c);
                    System.out.println("Curso cancelado.");
                    break;
            }
        } while (op != 'R');
    }

    public void criarCurso(int idUsuario, String nome, String data,
            String descricao, int estado) throws Exception {

        String codigo = GeradorCodigo.gerar();

        Curso c = new Curso(-1, idUsuario, nome, data, descricao, codigo, estado);

        int id = crud.create(c);

        System.out.println("Curso criado com ID: " + id);
    }

    public void buscarCurso(int id) throws Exception {
        Curso c = crud.read(id);

        if (c != null)
            System.out.println(c);
        else
            System.out.println("Curso não encontrado.");
    }

    public void atualizarCurso(Curso c) throws Exception {
        if (crud.update(c))
            System.out.println("Curso atualizado!");
        else
            System.out.println("Erro ao atualizar.");
    }

    public void deletarCurso(int id) throws Exception {
        if (crud.delete(id))
            System.out.println("Curso removido!");
        else
            System.out.println("Erro ao remover.");
    }

}