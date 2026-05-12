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
        java.util.ArrayList<RelacionamentoCursoUsuario> Inscricoes = crud.readUsuariosDoCurso(idUsuario);
        System.out.println("\nInscricoes");
        if (Inscricoes.isEmpty()) {
            System.out.println("Nenhumas inscricoes encontradas.");
            return;
        }
        for (int i = 0; i < Inscricoes.size(); i++) {
            RelacionamentoCursoUsuario c = Inscricoes.get(i);
            if (new ArquivoCurso().read(c.getIdCurso()).getEstado() != 1) {
                System.out.println("(" + (i + 1) + ") " + c.getNomeCurso() + " - " + c.getDataInscricao());
            } else {
                System.out.println("(" + (i + 1) + ") " + c.getNomeCurso() + " - " + c.getDataInscricao()
                        + "(INSCRIÇÕES ENCERRADAS)");
            }
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
        char opcao;
        do {
            listarInscricoesDoUsuario(idUsuario);

            opcao = visao.menuMinhasInscricoes();
            if (opcao == 'A') {
                String codigo = visao.buscaPorCodigo_GetCodigo();
                
                System.out.println("Inscricoes criado!");
            } else if (opcao >= '1' && opcao <= '9') {
                int index = opcao - '1';
                if (index >= 0 && index < Inscricoes.size()) {
                    menuInscricoesDetalhes(Inscricoes.get(index), visao);
                }
            }
        } while (opcao != 'R');
    }

    private void menuInscricoesDetalhes(Inscricoes c,
            VisaoInscricoes visao) throws Exception {
        char op;
        do {
            visao.mostraInscricoes(c);
            op = visao.menuInscricoesDetalhes();
            switch (op) {
                case 'B':
                    Inscricoes atualizado = visao.lerInscricoes();
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
                    System.out.println("Inscricoes concluído.");
                    break;
                case 'E':
                    c.setEstado(3);
                    crud.update(c);
                    System.out.println("Inscricoes cancelado.");
                    break;
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