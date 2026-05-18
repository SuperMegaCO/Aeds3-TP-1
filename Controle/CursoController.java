package Controle;

import java.util.ArrayList;
import aed3.*;
import CRUD_Curso.*;
import aed3.ArvoreBMais;
import Visao.VisaoInscricao.*;
import CRUD_RelacionamentoCursoUsuario.*;
import CRUD_Usuario.ArquivoUsuario;
import TempDataManager.InscritoTempData;

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
                case 'A':
                    InscricoesController ic = new InscricoesController();
                    ArrayList<InscritoTempData> inscritos = new ArrayList<>();
                    ArquivoRelacionamentoCursoUsuario rcu = new ArquivoRelacionamentoCursoUsuario();
                    ArquivoUsuario au = new ArquivoUsuario();
                    ArrayList<RelacionamentoCursoUsuario> rcuArray = rcu.readUsuariosDoCurso(c.getCodigo());
                    for (int i = 0; i < rcuArray.size(); i++) {
                        InscritoTempData itd = new InscritoTempData(rcuArray.get(i).getIdUsuario(),
                                rcuArray.get(i).getNomeUsuario(), au.read(rcuArray.get(i).getIdUsuario()).getEmail(),
                                rcuArray.get(i).getDataInscricao());
                        inscritos.add(itd);
                    }
                    do {
                        char opcao = visao.menuInscritos(inscritos);
                        if (opcao >= '1' && opcao <= '9') {
                            int index = opcao - '1';
                            if (index >= 0 && index < inscritos.size()) {
                                char detalheOpcao = visao.mostrarDetalhesInscrito(inscritos.get(index));
                                if (detalheOpcao == 'A') {
                                    ic.deletarInscricao(c.getId(), inscritos.get(index).getIdUsuario());
                                    inscritos.remove(index);
                                } else if (detalheOpcao == 'R') {
                                    break;
                                }
                            }
                        } else if (opcao == 'A') {
                            try (java.io.PrintWriter writer = new java.io.PrintWriter(
                                    new java.io.File("inscritos.csv"))) {
                                writer.println("Nome,Email,Data de Inscricao");

                                for (InscritoTempData inscrito : inscritos) {
                                    writer.println(inscrito.getNome() + "," + inscrito.getEmail() + ","
                                            + inscrito.getDataInscricao());
                                }
                                System.out.println("Lista exportada para inscritos.csv com sucesso!");

                            } catch (java.io.FileNotFoundException e) {
                                System.out.println("Erro ao exportar a lista: " + e.getMessage());
                            }
                        } else if (opcao == 'R') {
                            break;
                        }
                    } while (true);
                    break;
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

    public Curso buscarCurso(String codigo) throws Exception {
        Curso c = crud.read(codigo);
        return c;
    }

    public void atualizarCurso(Curso c) throws Exception {
        if (crud.update(c))
            System.out.println("Curso atualizado!");
        else
            System.out.println("Erro ao atualizar.");
    }

    public void deletarCurso(int id) throws Exception {
        ArquivoRelacionamentoCursoUsuario rcu = new ArquivoRelacionamentoCursoUsuario();
        ArrayList<RelacionamentoCursoUsuario> inscricoesCurso = rcu.readUsuariosDoCurso(id);
        for(RelacionamentoCursoUsuario rel : inscricoesCurso) {
            rcu.delete(rel.getId());
        }

        if (crud.delete(id))
            System.out.println("Curso removido!");
        else
            System.out.println("Erro ao remover.");
    }

}