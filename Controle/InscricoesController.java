package Controle;

import java.time.LocalDate;
import java.util.ArrayList;
import CRUD_Curso.*;
import Visao.VisaoInscricao.*;
import aed3.*;
import CRUD_RelacionamentoCursoUsuario.*;
import aed3.ArvoreBMais;

public class InscricoesController {

    private ArquivoRelacionamentoCursoUsuario crud;

    public InscricoesController() throws Exception {
        crud = new ArquivoRelacionamentoCursoUsuario();
    }

    public void listarInscricoesDoUsuario(int idUsuario) throws Exception {
        java.util.ArrayList<RelacionamentoCursoUsuario> Inscricoes = crud.readCursosDoUsuario(idUsuario);
        System.out.println("\nMinhas Inscrições Ativas");
        if (Inscricoes.isEmpty()) {
            System.out.println("Nenhuma inscrição encontrada.");
            return;
        }
        int count = 0;
        for (int i = 0; i < Inscricoes.size(); i++) {
            RelacionamentoCursoUsuario c = Inscricoes.get(i);
            CRUD_Curso.ArquivoCurso arqCurso = new CRUD_Curso.ArquivoCurso();
            CRUD_Curso.Curso curso = arqCurso.read(c.getIdCurso());
            if (curso != null && (curso.getEstado() == 0 || curso.getEstado() == 1)) {
                count++;
                String estadoStr = curso.getEstado() == 0 ? "Inscrições abertas" : "Inscrições encerradas";
                System.out.println("(" + count + ") " + c.getNomeCurso() + " - " + c.getDataInscricao() + " (" + estadoStr + ")");
            }
        }
        if (count == 0) {
            System.out.println("Nenhuma inscrição ativa encontrada.");
        }
    }

    public void listarInscricoesCurso(int idCurso) throws Exception {
        java.util.ArrayList<RelacionamentoCursoUsuario> Inscricoes = crud.readUsuariosDoCurso(idCurso);
        System.out.println("\nInscricoes");
        if (Inscricoes.isEmpty()) {
            System.out.println("Nenhuma inscricao encontrada.");
            return;
        }
        for (int i = 0; i < Inscricoes.size(); i++) {
            RelacionamentoCursoUsuario c = Inscricoes.get(i);
            System.out.println("(" + (i + 1) + ") " + c.getNomeUsuario());
        }
    }

    public void menuInscricoes(int idUsuario) throws Exception {
        VisaoInscricoes visao = new VisaoInscricoes();
        CRUD_Curso.ArquivoCurso arqCursos = new CRUD_Curso.ArquivoCurso(); // Instância única reutilizada
        char opcao;
        do {
            // Coletar inscrições ativas
            java.util.ArrayList<RelacionamentoCursoUsuario> inscricoesAtivas = new java.util.ArrayList<>();
            java.util.ArrayList<RelacionamentoCursoUsuario> todasInscricoes = crud.readCursosDoUsuario(idUsuario);
            for (RelacionamentoCursoUsuario c : todasInscricoes) {
                CRUD_Curso.Curso curso = arqCursos.read(c.getIdCurso());
                if (curso != null && (curso.getEstado() == 0 || curso.getEstado() == 1)) {
                    inscricoesAtivas.add(c);
                }
            }

            // Exibir lista
            System.out.println("\nMinhas Inscrições Ativas");
            if (inscricoesAtivas.isEmpty()) {
                System.out.println("Nenhuma inscrição ativa encontrada.");
            } else {
                for (int i = 0; i < inscricoesAtivas.size(); i++) {
                    RelacionamentoCursoUsuario c = inscricoesAtivas.get(i);
                    CRUD_Curso.Curso curso = arqCursos.read(c.getIdCurso());
                    String estadoStr = curso.getEstado() == 0 ? "Inscrições abertas" : "Inscrições encerradas";
                    System.out.println("(" + (i + 1) + ") " + c.getNomeCurso() + " - " + c.getDataInscricao() + " (" + estadoStr + ")");
                }
            }

            // Menu
            System.out.println("\n(A) Buscar curso por código");
            System.out.println("\n(B) Buscar curso por palavras-chave");
            System.out.println("(C) Listar todos os cursos");
            if (!inscricoesAtivas.isEmpty()) {
                System.out.println("Ou digite o número da inscrição para ver detalhes");
            }
            System.out.println("(R) Retornar ao menu anterior");
            System.out.print("Opção: ");
            String input = new java.util.Scanner(System.in).nextLine();
            if (input.length() > 0) {
                opcao = Character.toUpperCase(input.charAt(0));
                if (Character.isDigit(opcao)) {
                    int index = opcao - '1';
                    if (index >= 0 && index < inscricoesAtivas.size()) {
                        menuInscricoesDetalhes(inscricoesAtivas.get(index), visao);
                        opcao = ' '; // continuar loop
                    } else {
                        System.out.println("Opção inválida!");
                        opcao = ' ';
                    }
                } else {
                    if (opcao == 'A') {
                        String codigo = visao.buscaPorCodigo_GetCodigo();
                        CRUD_Curso.Curso curso = arqCursos.read(codigo);
                        if (curso != null) {
                            visao.mostraCurso(curso);
                        } else {
                            System.out.println("Curso não encontrado.");
                        }
                    } else if (opcao == 'B') {
                        //String palavrasChave = visao.buscaPorPalavrasChave_GetPalavrasChave();
                        // Implementar busca por palavras-chave se necessário
                        System.out.println("Busca por palavras-chave - não implementado ainda. Apenas no tp3");
                    } else if (opcao == 'C') {
                        ArrayList<CRUD_Curso.Curso> cursos = arqCursos.readAllCursosOrdenadosPorData();
                        if (cursos.isEmpty()) {
                            System.out.println("Nenhum curso encontrado.");
                        } else {
                            System.out.println("\nTodos os Cursos:");
                            for (int i = 0; i < cursos.size(); i++) {
                                CRUD_Curso.Curso c = cursos.get(i);
                                String estadoStr = c.getEstado() == 0 ? "Inscrições abertas" : "Inscrições encerradas";
                                System.out.println("(" + (i + 1) + ") " + c.getNome() + " - Código: " + c.getCodigo() + " (" + estadoStr + ")");
                            }
                        }
                    } else if (opcao != 'R') {
                        System.out.println("Opção inválida!");
                    }
                }
            } else {
                opcao = ' ';
            }
        } while (opcao != 'R');
    }

    private void menuInscricoesDetalhes(RelacionamentoCursoUsuario c,
            VisaoInscricoes visao) throws Exception {
        char op;
        do {
            // Mostrar detalhes da inscrição
            System.out.println("\nDetalhes da Inscrição:");
            System.out.println("Curso: " + c.getNomeCurso());
            System.out.println("Data de Inscrição: " + c.getDataInscricao());
            // Talvez mostrar mais detalhes do curso
            CRUD_Curso.ArquivoCurso arqCurso = new CRUD_Curso.ArquivoCurso();
            CRUD_Curso.Curso curso = arqCurso.read(c.getIdCurso());
            if (curso != null) {
                System.out.println("Descrição: " + curso.getDescricao());
                System.out.println("Estado: " + (curso.getEstado() == 0 ? "Inscrições abertas" : "Inscrições encerradas"));
            }

            System.out.println("\n(E) Cancelar inscrição");
            System.out.println("(R) Retornar");
            System.out.print("Opção: ");
            String input = new java.util.Scanner(System.in).nextLine();
            op = input.length() > 0 ? Character.toUpperCase(input.charAt(0)) : ' ';
            switch (op) {
                case 'E':
                    // Cancelar inscrição - deletar o relacionamento
                    crud.delete(c.getIdUsuario(), c.getIdCurso());
                    System.out.println("Inscrição cancelada.");
                    return; // sair do menu de detalhes
                case 'R':
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (op != 'R');
    }

    public void criarInscricao(int idUsuario, int idCurso, String nomeCurso, String nomeUsuario) throws Exception {

        LocalDate dataInscricao = LocalDate.now();

        RelacionamentoCursoUsuario c = new RelacionamentoCursoUsuario(-1, dataInscricao, idCurso, idUsuario, nomeCurso,
                nomeUsuario);

        int id = crud.create(c);

        System.out.println("Inscricao criado com sucesso!");
    }

    public boolean isUsuarioInscrito(int idUsuario, int idCurso) throws Exception {
        ArrayList<RelacionamentoCursoUsuario> inscricoes = crud.readCursosDoUsuario(idUsuario);

        for (RelacionamentoCursoUsuario inscricao : inscricoes) {
            if (inscricao.getIdCurso() == idCurso) {
                return true;
            }
        }
        return false;
    }

    public void deletarInscricao(int idCurso, int idUsuario) throws Exception {
        if (crud.delete(idCurso, idUsuario))
            System.out.println("Inscricao removida!");
        else
            System.out.println("Erro ao remover.");
    }

}