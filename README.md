jug-call4papers
===============

[![Build status](https://travis-ci.org/CodeVale/jug-call4papers.png)](https://travis-ci.org/dropwizard/dropwizard.png?branch=master)
[![Coverage Status](https://coveralls.io/repos/CodeVale/jug-call4papers/badge.png)](https://coveralls.io/r/CodeVale/jug-call4papers)

Uma aplicação de Call4Paper com JEE 6 para uso nos eventos do JUG Vale

![JUG](src/main/webapp/img/logo_jug_vale_final.jpg)

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

Para entender a aplicação, olhe o resumo dos métodos REST abaixo, com os devidos [Status HTTP](http://www.restapitutorial.com/httpstatuscodes.html):


Método 	  | URI											              | Retorno 
:-----:	  | :-------------------------------------| :------------------
GET		    | /rest/v1/paper                        |  Status 200 e um JSON com uma lista Papers.
GET		    | /rest/v1/autor                        |  Status 200 e um JSON com uma lista Autores.
GET		    | /rest/v1/evento                       |  Status 200 e um JSON com uma lista Eventos.
GET		    | /rest/v1/evento/{id}/papers           |  Status 200 e um JSON com uma lista Papers para um Evento. Ou retorna 404, caso não encontre o Evento.
GET		    | /rest/v1/autor/{id}/papers            |  Status 200 e um JSON com uma lista Papers para um Determinado Autor. Ou retorna 404, caso não encontre o Autor.

Observe que os métodos acima retornam o que está na `URI` e são desprotegidos. Também é possível acessar recursos paper, autor e evento individualmente usando o id dos mesmos;

Método 	  | URI											              | Retorno           
:-----:	  | :-------------------------------------| :------------------
POST		  | /rest/v1/autor                        | Status 201, em caso de sucesso e o Location do novo Autor criado presente no HEADER.

Este métdo POST permite um usuário anônimo criar um novo autor (usuário com role AUTOR) através do site.

Método 	                | URI											              | Retorno           
:----------------------:| :-------------------------------------| :------------------
POST		  | /rest/v1/paper                                      | Status 201 em caso de sucesso.
DELETE                  | /rest/v1/paper/{id}                   | Status 200 caso tenha removido corretamente.
PUT                     | /rest/v1/paper/{id}                   | Status 200 caso tenha atualizado o paper corretamente.
PUT		                  | /rest/v1/autor/{id}                   | Status 200 caso tenha atualizado o autor com sucesso.


Estes são permitidos **SE E SOMENTE SE** o autor **LOGADO** for dono desses itens (se ele criou).

* _Usuários com role de **ADMINISTRADOR** podem acessar todos os métodos do sistema._


Empacotando e rodando a aplicação
--------

Temos uma aplicação Maven aqui com JEE 6 e Java 7

**WILDFLY 8**

Para subir a aplicação no `WildFly 8` basta fazer o [download](http://wildfly.org/downloads/) da versão **8.1.0.Final** e subir o servidor executando o comando `{WILDFLY_HOME}/bin/standalone.sh`.

Com o Servidor rodando, execute o segunte comando, na raiz do nosso projeto clonado: `mvn package wildfly:deploy` e para fazer o undeploy, execute: `mvn wildfly:undeploy`

Feito isto siga as isntruções contidas na seção **Ativando a Segurança**.

Para executar os Testes de Integração para testar todos os serviços, execute o comando: `mvn integration-test`, este comando irá fazer o _deploy_ do `WAR`, e logo em seguida realizará os testes de integração finalizando com o _undeploy_ do mesmo.

**Ativando a Segurança**

Você então deve adicionar a configuração de segurança como descrito em [`configuracao-jboss-login.md`](https://github.com/CodeVale/jug-call4papers/blob/master/configuracao-jboss-login.md). Ela irá buscar dados nas tabelas que serão criadas após o deploy da aplicação, então faça o deploy antes!

Fazendo o deploy, uns dados inicíais você já terá para ver a aplicação rodando. Você pode ver o evento de teste em: `http://localhost:8080/jugvale-call4papers/rest/v1/evento/`.
