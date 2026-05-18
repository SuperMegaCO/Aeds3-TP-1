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
                System.out.println(
                        "(" + count + ") " + c.getNomeCurso() + " - " + c.getDataInscricao() + " (" + estadoStr + ")");
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
                    System.out.println("(" + (i + 1) + ") " + c.getNomeCurso() + " - " + c.getDataInscricao() + " ("
                            + estadoStr + ")");
                }
            }

            // Menu
            System.out.println("\n(A) Buscar curso por código");
            System.out.println("(B) Buscar curso por palavras-chave");
            System.out.println("(C) Listar todos os cursos");
            System.out.println();
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
                            menuDetalhesCurso(curso, arqCursos, idUsuario);
                        } else {
                            System.out.println("Curso não encontrado.");
                        }
                    } else if (opcao == 'B') {
                        // String palavrasChave = visao.buscaPorPalavrasChave_GetPalavrasChave();
                        // Implementar busca por palavras-chave no futuro
                        System.out.println("Busca por palavras-chave - não implementado ainda. Apenas no tp3");
                    } else if (opcao == 'C') {
                        menuListarCursosPaginado(arqCursos, idUsuario);
                    } else if (opcao != 'R') {
                        System.out.println("Opção inválida!");
                    }
                }
            } else {
                opcao = ' ';
            }
        } while (opcao != 'R');
    }

    private void menuInscricoesDetalhes(RelacionamentoCursoUsuario c, VisaoInscricoes visao) throws Exception {
        char op;
        do {
            CRUD_Curso.ArquivoCurso arqCurso = new CRUD_Curso.ArquivoCurso();
            CRUD_Curso.Curso curso = arqCurso.read(c.getIdCurso());

            op = visao.menuDetalhes(c, curso);

            switch (op) {
                case 'E':
                    crud.delete(c.getIdUsuario(), c.getIdCurso());
                    System.out.println("Inscrição cancelada com sucesso.");
                    op = 'R';
                    break;
                case 'R':
                    break;
                default:
                    break;
            }
        } while (op != 'R');
    }

    private void menuListarCursosPaginado(CRUD_Curso.ArquivoCurso arqCursos, int idUsuario) throws Exception {
        final int ITENS_POR_PAGINA = 10;
        int paginaAtual = 1;
        int totalCursos = arqCursos.contarTotalCursos();
        int totalPaginas = (int) Math.ceil((double) totalCursos / ITENS_POR_PAGINA);

        char op;
        do {
            System.out.println("\nEntrePares 1.0");
            System.out.println("> Início > Minhas inscrições > Lista de cursos");
            System.out.println("\nPágina " + paginaAtual + " de " + Math.max(1, totalPaginas));

            ArrayList<CRUD_Curso.Curso> cursos = arqCursos.readCursosPaginados(paginaAtual, ITENS_POR_PAGINA);

            for (int i = 0; i < cursos.size(); i++) {
                CRUD_Curso.Curso c = cursos.get(i);
                System.out.println("(" + (i + 1) + ") " + c.getNome() + " - " + c.getDataInicio());
            }

            System.out.println("\nDigite o número do curso para ver detalhes");
            System.out.println("\n(A) Página anterior");
            System.out.println("(B) Próxima página");
            System.out.println("(R) Retornar ao menu anterior");
            System.out.print("Opção: ");

            String input = new java.util.Scanner(System.in).nextLine();
            op = input.length() > 0 ? Character.toUpperCase(input.charAt(0)) : ' ';

            if (Character.isDigit(op)) {
                int index = op - '1';
                if (index >= 0 && index < cursos.size()) {
                    menuDetalhesCurso(cursos.get(index), arqCursos, idUsuario);
                    op = ' ';
                } else {
                    System.out.println("Número inválido!");
                    op = ' ';
                }
            } else {
                switch (op) {
                    case 'A':
                        if (paginaAtual > 1) {
                            paginaAtual--;
                        } else {
                            System.out.println("Você já está na primeira página.");
                        }
                        break;
                    case 'B':
                        if (paginaAtual < totalPaginas) {
                            paginaAtual++;
                        } else {
                            System.out.println("Você já está na última página.");
                        }
                        break;
                    case 'R':
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            }
        } while (op != 'R');
    }

    private void menuDetalhesCurso(CRUD_Curso.Curso curso, CRUD_Curso.ArquivoCurso arqCursos, int idUsuario)
            throws Exception {
        // Buscar nome do autor
        String autorNome = "Desconhecido";
        try {
            CRUD_Usuario.ArquivoUsuario arqUsuarios = new CRUD_Usuario.ArquivoUsuario();
            CRUD_Usuario.Usuario u = arqUsuarios.read(curso.getIdUsuario());
            if (u != null) {
                autorNome = u.getNome();
            }
        } catch (Exception e) {
            // Ignorar erro
        }

        char op;
        do {
            // Mostrar cabeçalho
            System.out.println("\nEntrePares 1.0");
            System.out.println("--------------");
            System.out.println("> Início > Minhas inscrições > Lista de cursos > " + curso.getNome());

            // Mostrar detalhes do curso
            System.out.println("\nCÓDIGO........: " + curso.getCodigo());
            System.out.println("CURSO.........: " + curso.getNome());
            System.out.println("AUTOR.........: " + autorNome);
            System.out.println("DESCRIÇÃO.....: " + curso.getDescricao());
            System.out.println("DATA DE INÍCIO: " + curso.getDataInicio());

            // Verificar estado do curso
            String estadoMsg = "";
            if (curso.getEstado() == 0) {
                estadoMsg = "\n(A) Fazer minha inscrição no curso";
            } else if (curso.getEstado() == 1) {
                estadoMsg = "\n(A) Fazer minha inscrição no curso (inscrições encerradas)";
            } else {
                estadoMsg = "\n(Curso não disponível para inscrição)";
            }

            System.out.println(estadoMsg);
            System.out.println("(R) Retornar ao menu anterior");
            System.out.print("Opção: ");

            String input = new java.util.Scanner(System.in).nextLine();
            op = input.length() > 0 ? Character.toUpperCase(input.charAt(0)) : ' ';

            switch (op) {
                case 'A':
                    if (curso.getEstado() == 0) {
                        String nomeUsuario = "Desconhecido";
                        try {
                            CRUD_Usuario.ArquivoUsuario arqUsuarios = new CRUD_Usuario.ArquivoUsuario();
                            CRUD_Usuario.Usuario u = arqUsuarios.read(idUsuario);
                            if (u != null) {
                                nomeUsuario = u.getNome();
                            }
                        } catch (Exception e) {
                            // Ignorar erro
                        }
                        criarInscricao(idUsuario, curso.getID(), curso.getNome(), nomeUsuario);
                    } else {
                        System.out.println("Inscrições encerradas para este curso.");
                    }
                    break;
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