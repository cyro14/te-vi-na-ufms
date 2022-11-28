

Especificação de Requisitos de Software para o aplicativo rede social

1  **Introdução**

Este documento registra os requisitos gerais do Sistema Te Vi na UFMS, na forma de requisitos textuais do aplicativo.

2  **Visão Geral do Sistema**

O sistema Te Vi na UFMS trata-se de um aplicativo para o Sistema Android. Entre os procedimentos a serem realizados no aplicativo estão: cadastro de estudante, publicação de fotos e moderação de publicações. O foco será em uma rede social centrada para os estudantes terem um espaço de comunicação na UFMS, onde o login e cadastro serão realizados exclusivamente pelo e-mail institucional. Diversas saídas devem ser geradas para a melhora de visualização do aplicativo, como relatórios de publicações e interações.

3  **Classes de usuários**

Na presente seção devem ser descritas as várias classes de usuário relevantes para o sistema.


**Estudante** — Uma pessoa matriculada na Universidade Federal de Mato Grosso do Sul e deseja se informar sobre novidades e eventos, ou interagir com alunos do campus.

4  **Requisitos de Software**

Nesta seção são descritos os requisitos textuais da rede social acadêmica Te Vi na UFMS. Na Seção 5.1 são descritos os requisitos funcionais. Na Seção 5.2 são descritos os requisitos não-funcionais.

4.1 Requisitos funcionais

**Lançamentos diversos:**

RF-1. [**Estudante/Gerenciar conta**]O estudante pode realizar o cadastro, alterar e remover a

conta  com os seguintes atributos: nome, e-mail institucional e uma foto opcional.

RF-2. [**Administrador do sistema**/**Gerenciar administrador**]O sistema deve permitir o

cadastro, alteração e remoção de administrador com os seguintes atributos: nome, CPF, endereço e telefone e contratação.

RF-3. [**Administrador/Gerenciar estudante**]O sistema deve permitir a inclusão, alteração e

remoção de estudantes, com os seguintes atributos: nome, e-mail institucional.

**Transações:**

RF-4. [**Estudante/Efetuar publicação**] o aplicativo deve permitir a realizar uma publicação , o

estudante deve adicionar uma foto e uma legenda, ambas são obrigatórias. A localização da publicação é opcional e pode aderir a qualquer ambiente.

RF-5. [**Estudante/Efetuar publicação**] O aplicativo deve permitir a edição, remoção e denúncia

de uma publicação, por qualquer estudante.

RF-6. [**Estudante/Efetuar publicação**] O aplicativo deve permitir a edição, remoção e denúncia

de uma publicação, por qualquer estudante.

RF-7. [**Estudante/Efetuar publicação**] O aplicativo deve permitir que em publicações sejam

realizadas as ações de curtir e comentar.

RF-8. [**Estudante/Denunciar publicação**] O estudante pode denunciar publicações que são

contra os termos do aplicativo ao administrador.

RF-9. [**Administrador/Moderar publicação**] O aplicativo deve permitir ao administrador

moderar uma lista de publicações denunciadas, assim como a aceitação ou negação da denúncia.

**Comprovantes e relatórios:**

RF-10. [**Administrador, Estudante/Listagem**] O aplicativo deve permitir a listagem de publicação

por localização mais próxima da UFMS.

RF-11. [**Administrador, Estudante/Listagem**] O aplicativo deve permitir a listagem de

publicações por quantidade de curtidas.

**Controle e nível de acesso:**

RF-12. O sistema deve exigir que os usuários façam login.

RF-13. Somente o administrador pode realizar a inclusão, alteração e exclusão de estudantes do

sistema.

RF-14. O sistema deve emitir uma mensagem de erro em caso de acesso não autorizado ou falta

de permissão para realizar ações.

5  **Histórico de versões do documento**

Essa seção apresenta o histórico de versões desse documento.



|**Versão**|**Publicação**|**Autor(es)**|**Ações realizadas**|
| - | - | - | - |
|1.0|01/nov/2022|Davi Nogueira, Cyro Silvany|- Versão inicial do documento de requisitos para um aplicativo|
||||-|


6  **Acadêmicos:**

Cyro Silvany e Davi Nogueira
