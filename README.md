jug-call4papers
===============

![Build status](https://travis-ci.org/CodeVale/jug-call4papers.png)

Uma aplicação de Call4Paper com JEE 6 para uso nos eventos do JUG Vale

### O que é isso?

Um projeto para registro de propostas de trabalhos (Papers) para eventos. A ideia é possibilitar a inscrição de trabalhos para um evento.

A aplicação na WEB para o usuário não logado permitirá:

* Ver eventos abertos, fechados, papers e autores

A aplicação WEB para autores permitirá:

* Logar e submeter papers para eventos em aberto. Apagar Papers já submetidos.

A aplicação ainda contará com uma interface que permite modificar, criar e apagar Papers, Autores e Eventos. 

### Como funciona?

Temos o JPA para acesso ao banco. Temos também uma interface REST usando JAX-RS. Nada demais...

A aplicação também tem segurança usando JAAS. A interface REST tem os métodos GET em modo público, ou seja, com acesso por qualquer um, mas métodos de modificação deverão exigir autenticação. 

Para entender a aplicação, olhe o resumo dos métodos REST abaixo:


Método 	  | URI											              | Retorno 
:-----:	  | :-------------------------------------| :------------------
GET		    | /rest/v1/paper                        |  Status 200 (Ok) e um JSON com uma lista Papers.
GET		    | /rest/v1/autor                        |  Status 200 (Ok) e um JSON com uma lista Autores.
GET		    | /rest/v1/evento                       |  Status 200 (Ok) e um JSON com uma lista Eventos.
GET		    | /rest/v1/evento/{id}/papers           |  Status 200 (Ok) e um JSON com uma lista Papers para um Evento. Ou retorna 404 (Not Found), caso não encontre o Evento.
GET		    | /rest/v1/autor/{id}/papers            |  Status 200 (Ok) e um JSON com uma lista Papers para um Determinado Autor. Ou retorna 404 (Not Found), caso não encontre o Autor.

Observe que os métodos acima retornam o que está na `URI` e são desprotegidos. Também é possível acessar recursos paper, autor e evento individualmente usando o id dos mesmos;

Método 	  | URI											              | Retorno           
:-----:	  | :-------------------------------------| :------------------
POST		  | /rest/v1/autor                        | Status 201 (Created) em caso de sucesso e o Location do novo Autor criado presente no HEADER.

Este métdo POST permite um usuário anônimo criar um novo autor (usuário com role AUTOR) através do site.

Método 	                | URI											              | Retorno           
:----------------------:| :-------------------------------------| :------------------
POST e PUT		  | /rest/v1/paper                                | Status 201 (Created) em caso de sucesso.
DELETE                  | /rest/v1/paper/{id}                   | Status 200 (Ok) caso tenha removido corretamente.
PUT                     | /rest/v1/paper/{id}                   | Status 200 (Ok) caso tenha atualizado o paper corretamente.
PUT		                  | /rest/v1/autor/{id}                   | Status 200 (Ok) caso tenha atualizado o autor com sucesso.


Estes são permitidos **SE E SOMENTE SE** o autor **LOGADO** for dono desses itens (se ele criou).

* _Usuários com role de **ADMINISTRADOR** podem acessar todos os métodos do sistema._

### Empacotando e rodando a aplicação

Temos uma aplicação Maven aqui com JEE 6 e Java 7, usando o JBoss AS7. Para realizar o build (construir um WAR) você pode rodar:

`mvn clean package -DskipTests`

Aí teremos um projeto construido e pronto para se realizar o deploy. Recomendamos o JBoss AS 7, pois assim você não precisará configurar o banco de dados.
Se você já baixou e descompactou o JBoss AS 7, suba ele usando `bin/standalone.sh`. Aí, você pode fazer deploy do WAR nosso. 

Você então deve adicionar a configuração de segurança como descrito em [`configuracao-jboss-login.md`](https://github.com/CodeVale/jug-call4papers/blob/master/configuracao-jboss-login.md). Ela irá buscar dados nas tabelas que serão criadas após o deploy da aplicação, então faça o deploy antes!

Fazendo o deploy, uns dados inicíais você já terá para ver a aplicação rodando. Você pode ver o evento de teste em: `http://localhost:8080/jugvale-call4papers/rest/v1/evento/`.







