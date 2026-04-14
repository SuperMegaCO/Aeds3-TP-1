package Controle;
import java.util.ArrayList;
import aed3.*;
import CRUD_Curso.*;
import aed3.ArvoreBMais;
import ArvoreBMais.ParIntInt;
public class CursoController {

    private ArquivoCurso crud;

    public CursoController() throws Exception {
        crud = new ArquivoCurso();
    }
    public void listarCursosUsuario(int id) throws Exception {
            ArvoreBMais<ParIntInt> arvore = new ArvoreBMais<>(ParIntInt.class.getConstructor(), 5, "dados/arvore.db");
            int n1 = id;

            ArrayList<ParIntInt> lista = arvore.read(null);

            System.out.println("Cursos encontrados:");

            for (ParIntInt p : lista) {
              if (p.getNum1() == n1)
                System.out.println(p.getNum2());
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