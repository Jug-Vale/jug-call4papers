jug-call4papers
===============

[![Build status](https://travis-ci.org/CodeVale/jug-call4papers.png)](https://travis-ci.org/CodeVale/jug-call4papers.png?branch=master)

Uma aplicação de Call4Paper com JEE 6 para uso nos eventos do JUG Vale

![JUG](src/main/webapp/img/logo_jug_vale_final.jpg)

### O que é isso?

Um projeto para registro de propostas de trabalhos (Papers) para eventos. A ideia é possibilitar a inscrição de trabalhos para um evento.

A aplicação na WEB para o usuário não logado permitirá:

* Ver eventos abertos, fechados, papers e autores

A aplicação ainda contará com uma interface que permite modificar, criar e apagar Papers, Autores e Eventos. 

### Como funciona?

Temos o JPA para acesso ao banco. Temos também uma interface REST usando JAX-RS. Nada demais...

A aplicação também tem segurança usando JAAS. A interface REST tem os métodos GET em modo público, ou seja, com acesso por qualquer um, mas métodos de modificação deverão exigir autenticação. 

Para entender a aplicação, olhe o resumo dos métodos REST abaixo, com os devidos [Status HTTP](http://www.restapitutorial.com/httpstatuscodes.html):


Método 	  | URI											              | Retorno 
:-----:	  | :-------------------------------------| :------------------
GET		    | /rest/paper                        |  Status 200 e um JSON com uma lista Papers.
GET		    | /rest/autor                        |  Status 200 e um JSON com uma lista Autores.
GET		    | /rest/evento                       |  Status 200 e um JSON com uma lista Eventos.
GET		    | /rest/evento/{id}/papers           |  Status 200 e um JSON com uma lista Papers para um Evento. Ou retorna 404, caso não encontre o Evento.
GET		    | /rest/autor/{id}/papers            |  Status 200 e um JSON com uma lista Papers para um Determinado Autor. Ou retorna 404, caso não encontre o Autor.
POST		  | /rest/paper                                      | Status 201 em caso de sucesso.

* _Usuários com role de **ADMINISTRADOR** podem acessar todos os métodos do sistema._


Para os desenvolvedores das páginas WEB, não é necessário codificar Javascript para as requisições. Uma API está disponível na URL `http://localhost:8080/jug-call4papers/rest-js`. Para mais informações sobre essa API, veja a [documentação do RESTEasy](http://docs.jboss.org/resteasy/docs/2.3.7.Final/userguide/html/AJAX_Client.html#d4e1923).

Empacotando e rodando a aplicação
--------

Temos uma aplicação Maven aqui com JEE 6 e Java 7.
Antes de qualquer coisa, crie um arquivo em `src/main/resources` chamado `config.properties`. Ele deverá conter a configuração para o captcha na submissão de paper e participante como segue
~~~
CAPTCHA_CHAVE_PUBLICA={valor para a chave pública}
CAPTCHA_CHAVE_PRIVADA={valor para a chave privada}
~~~


**WILDFLY 8**

Para subir a aplicação no `WildFly 8` basta fazer o [download](http://wildfly.org/downloads/) da versão **8.1.0.Final** e subir o servidor executando o comando `{WILDFLY_HOME}/bin/standalone.sh`.

Com o Servidor rodando, execute o segunte comando, na raiz do nosso projeto clonado: `mvn package wildfly:deploy` e para fazer o undeploy, execute: `mvn wildfly:undeploy`

Feito isto, siga as instruções contidas na seção **Ativando a Segurança**.

Para executar os Testes de Integração para testar todos os serviços, execute o comando: `mvn integration-test`, este comando irá fazer o _deploy_ do `WAR`, e logo em seguida realizará os testes de integração finalizando com o _undeploy_ do mesmo.

**Ativando a Segurança**

A configuração de segurança foi simplicada já que não temos mais a role de autor. Para ter um usuário admnistrador simplesmente [crie um usuário do JBoss](https://docs.jboss.org/author/display/AS71/add-user+utility) no "realm"   `ApplicationRealm` e dê o role ADMINISTRADOR para ele.

Fazendo o deploy, uns dados inicíais você já terá para ver a aplicação rodando. Você pode ver o evento de teste em: `http://localhost:8080/jugvale-call4papers/rest/evento/` ou a página inicial em `http://localhost:8080/jugvale-call4papers/`.
