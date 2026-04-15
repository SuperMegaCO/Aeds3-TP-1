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