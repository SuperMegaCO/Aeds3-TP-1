## TP3: ÍNDICE INVERTIDO

## Descrição Geral

Neste trabalho prático, evoluímos o sistema desenvolvido no TP2 para lidar com busca utilizando índice invertido

Nosso sistema é capaz de:

- Armazenar usuários e cursos em arquivos binários utilizando `RandomAccessFile`.
- Realizar operações completas de **CRUD** para todas as entidades.
- Implementar relacionamentos N:N entre Usuários e Cursos através de uma entidade **CursoUsuario** e duas **árvores B+**, permitindo a consulta eficiente de:
  - Quais usuários estão inscritos em determinado curso.
  - Em quais cursos um determinado usuário está inscrito.
- Garantir **consistência dos dados**, permitindo gerenciamento de inscrições, cancelamentos e estados de cursos.
- O índice invertido nos permite fazer buscas por entidades a partir de seus termos (palavras). Para isso, a gente precisa criar uma lista de IDs para cada termo indexado

A modelagem foi orientada a objetos e o projeto foi modularizado para facilitar testes, manutenção e evolução.

---

## Participantes

- Gabriel Couto
- Leonardo Amaral
- Rafael Cortat

---

## Estrutura de Classes

### Model:

#### 'ParIdId'

Índices para armazenar pares de dados na árvore B+.

#### 'CursoUsuario' 

Classe que representa o relacionamento entre um usuário e um curso. Contém:
- idCursoUsuario (chave primária)
- idCurso (chave estrangeira)
- idUsuario (chave estrangeira)
- Data da Inscrição

#### 'Usuario', 'Curso'

Classes modelo que representam uma entidade armazenada

### 'View'

Classes responsáveis pela interação com o usuário

#### 'MenuPrincipal'

Menu de navegação principal com acesso a:
- (1) Meus Cursos
- (2) Minhas Inscrições
- (3) Sair

#### 'MenuMinhasInscricoes'

- Métodos principais:
  - mostrarMinhasInscricoes() – Exibe os cursos em que o usuário está inscrito
  - buscarCursoPorCodigo() – Busca um curso específico pelo código
  - listarTodosCursos() – Lista todos os cursos com paginação (10 por página)
  - selecionarCurso() – Exibe os detalhes completos do curso
  - efetivarInscricao() – Realiza a inscrição do usuário no curso
  - cancelarInscricao() – Cancela a inscrição do usuário
 
#### 'MenuMeusCursos'

- Métodos principais:
  - listarMeusCursos() – Lista todos os cursos criados pelo usuário
  - selecionarCurso() – Exibe os detalhes do curso e opções de gerenciamento
  - gerenciarInscritos() – Exibe lista de inscritos no curso
  - exportarLista() – Exporta a lista de inscritos
  - corrigirDadosCurso() – Permite editar os dados do curso
  - encerrarInscricoes() – Encerra as inscrições do curso
  - concluirCurso() – Marca o curso como concluído
  - cancelarCurso() – Cancela o curso

#### 'MenuCursoUsuario'

- Métodos principais:
  - incluirInscricao() – Cria a associação entre usuário e curso
  - cancelarInscricao() – Remove a associação entre usuário e curso
  - listarInscricoesCurso() – Lista todas as inscrições de um curso
  - listarInscricoesUsuario() – Lista todas as inscrições de um usuário

### 'Arquivos'

#### 'ArqCursoUsuario'

Classe que gerencia o CRUD das inscrições (CursoUsuario), mantendo dois índices B+:

- Métodos principais:
  - 'readCurso(int idCurso)' – retorna todas as inscrições de um curso
  - 'readUsuario(int idUsuario)' – retorna todas as inscrições de um usuário
  - 'readPor(int idCurso, int idUsuario)' – retorna a inscrição específica
  - 'create(CursoUsuario cu)' – cria uma nova inscrição

#### 'ArqCurso'

Classe que gerencia o índice de cursos e o armazenamento dos cursos

Métodos principais:
  - 'readCodigo(String codigo)' - recebe um código e retorna o curso
  - 'readNome(String nome)' - recebe um nome e retorna cursos por meio da árvore B+
  - 'create(Curso c)' - cria um curso, atualiza o índice e salva na árvore B+

#### 'ArqUsuario'

Classe que gerencia os índices de usuário e o armazenamento dos usuários.

Métodos principais:
  - 'readNome(String nome)' - recebe um nome e retorna um array com os usuários por meio da árvore B+
  - 'create(Usuario u)' - cria um usuário, atualiza o índice e salva na árvore B+

## Relacionamento N:N

Uma inscrição em um curso será uma associação de um usuário a um curso através de uma entidade **CursoUsuario**. 

### Tipos de Relacionamentos

1. **1:N** - Uma entidade de um tipo se relaciona com várias entidades de outro tipo. Exemplo: uma categoria possui vários produtos (já implementado no TP1).

2. **N:N** - As entidades de um tipo se relacionam com várias entidades de outro tipo. Exemplos:
   - Um usuário se inscreve em vários cursos
   - Um curso tem vários usuários inscritos
   - Um autor escreve vários livros; um livro tem vários autores
   - Uma playlist contém várias músicas; uma música está em várias playlists


Utilizamos duas **árvores B+** como índices:

1. **Índice por Curso**: (idCurso; idCursoUsuario)
   - Permite recuperar todas as inscrições de um curso
   - Usado para listar inscritos e gerenciar participantes

2. **Índice por Usuário**: (idUsuario; idCursoUsuario)
   - Permite recuperar todas as inscrições de um usuário
   - Usado para listar os cursos em que o usuário está inscrito

A razão pela qual criamos duas estruturas é que as estruturas de dados (como árvores B+) geralmente permitem busca eficiente por uma única chave de ordenação. Uma única árvore B+ não pode ter dois critérios de ordenação simultaneamente.

## CHECKLIST?

- [x] O índice invertido com os termos dos nomes dos cursos foi criado usando a classe ListaInvertida? *SIM*
- [x] É possível buscar cursos por palavras no menu de inscrição? *SIM*
- [x] O trabalho compila corretamente? *SIM*
- [x] O trabalho está completo e funcionando sem erros de execução? *SIM*
- [x] O trabalho é original e não a cópia de um trabalho de outro grupo? *SIM*
---
