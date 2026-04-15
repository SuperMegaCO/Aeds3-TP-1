# AEDS III - Trabalho Prático 1

**Membros do Grupo**: Gabriel Couto, Leonardo Amaral, Rafael Cortat

## Descrição do Sistema
Este projeto é a primeira etapa de um sistema de gestão de inscrições em cursos livres (EntrePares). Ele implementa o cadastro de usuários e a gestão de cursos ofertados por esses usuários (com relacionamento 1:N utilizando Árvore B+), seguindo o padrão MVC. O acesso de dados foi construído com a base de CRUD genérico fornecida pelo professor, com índices diretos (Hash Extensível) e indiretos (Árvore B+). O menu inicial guia o usuário a fazer login ou criar conta. E o submenu interno permite acessar os Próprios Dados (para possível exclusão de conta) e gerenciar os cursos que ofertou.

O sistema cumpre com os requisitos de exclusão restrita: Um usuário só pode deletar sua conta se ele não possui cursos com inscrições abertas (estado 0) ou cursos em andamento (estado 1). Quando deletado com sucesso, todos os seus cursos finalizados/cancelados são descartados em cascata por navegação na árvore B+.

### Classes e Arquitetura
* **Principal.java**: Navegação de menus de login e sessão ativa principal.
* **Modelo (CRUD)**: `ArquivoUsuario` e `ArquivoCurso` que herdam de `aed3.Arquivo` estendendo operações para índices Hash Extensível e Árvore B+. 
* **Visão**: `VisaoUsuario` e `VisaoCurso` que promovem a interação de prints e prompts. 
* **Controle**: `ControleUsuario` centraliza as rotinas de Login e exclusão. `CursoController` encapsula a listagem em B+ tree de todos cursos que batem com usuário ID bem como adição e modificação de cursos, instanciando os repositórios/arquivos localmente.

## Checklist do Projeto

1. **Há um CRUD de usuários que funciona corretamente?**
   **Sim.** O `ArquivoUsuario` estende o CRUD Base genérico suportado no diretório aed3. Ele faz uso da `HashExtensivel` por Email como índice base e é instanciado em todo o projeto. Funciona sem falhas.

2. **Há um CRUD de cursos que funciona corretamente?**
   **Sim.** O `ArquivoCurso` usa as estruturas originais de `aed3.Arquivo` genérico e cria as lógicas de B+Tree para associação de IDs nos overrides pertinentes para o CRUD.

3. **Os cursos estão vinculados aos usuários usando o idUsuario como chave estrangeira?**
   **Sim.** Na declaração da classe `Curso`, é exigido um `idUsuario`. Todo curso criado pelo `CursoController` puxa o `currentUserId` da sessão nativamente no construtor.

4. **Há uma árvore B+ que registre o relacionamento 1:N entre usuários e cursos?**
   **Sim.** O `ArquivoCurso` inicializa e escreve `ParIdUsuarioIdCurso` no índice secundário `indiceUsuario` estruturado por uma Árvore B+. O Lookup 1:N é viabilizado pelo método `readCursosDoUsuario`.

5. **O trabalho compila corretamente?**
   **Sim.** O código Java base compila perfeitamente através de uma build padrão englobando as pastas.

6. **O trabalho está completo e funcionando sem erros de execução?**
   **Sim.** As chamadas no `Principal.java` previnem input problemático do Scanner capturando bounds, garantindo loops interativos imunes a crashes, e cobrem 100% da descrição MVC solicitada pelas restrições do TP1.

7. **O trabalho é original e não a cópia de um trabalho de outro grupo?**
   **Sim.** Trabalho original desenvolvido no decorrer da disciplina. 

---
_Aguardando o anexo do vídeo._
