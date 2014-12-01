[![Stories in Ready](https://badge.waffle.io/Jug-Vale/jug-call4papers.png?label=ready&title=Ready)](https://waffle.io/Jug-Vale/jug-call4papers)
[![Build status](https://travis-ci.org/Jug-Vale/jug-call4papers.png)](https://travis-ci.org/Jug-Vale/jug-call4papers.png?branch=master)
jug-call4papers
===============
[![Gitter](https://badges.gitter.im/Join Chat.svg)](https://gitter.im/Jug-Vale/jug-call4papers?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

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

**Empacotando e rodando a aplicação**
--------

Temos uma aplicação Maven aqui com JEE 6 e Java 7.
Antes de qualquer coisa, procure como adicionar propriedades de sistema no seu servidor de aplicação. Você deverá configurar duas propriedades CAPTCHA_CHAVE_PUBLICA e CAPTCHA_CHAVE_PRIVADA. No Wildfly 8.1 isso pode ser feito adicionar a declaração seguinte usando as chaves que você configurou no recaptcha.
~~~
<system-properties>
        <property name="CAPTCHA_CHAVE_PUBLICA" value="{SUA CHAVE PUBLICA}"/>
        <property name="CAPTCHA_CHAVE_PRIVADA" value={SUA CHAVE PRIVADA}"/>
</system-properties>
~~~
Essas chaves devem ser geradas para o seu usuário no [recaptcha](http://www.google.com/recaptcha/intro/index.html).

**WILDFLY 8**

Para subir a aplicação no `WildFly 8` basta fazer o [download](http://wildfly.org/downloads/) da versão **8.1.0.Final** e subir o servidor executando o comando `{WILDFLY_HOME}/bin/standalone.sh`.

Com o Servidor rodando, execute o segunte comando, na raiz do nosso projeto clonado: `mvn package wildfly:deploy` e para fazer o undeploy, execute: `mvn wildfly:undeploy`

Feito isto, siga as instruções contidas na seção **Ativando a Segurança**.

Para executar os Testes de Integração para testar todos os serviços, execute o comando: `mvn integration-test`, este comando irá fazer o _deploy_ do `WAR`, e logo em seguida realizará os testes de integração finalizando com o _undeploy_ do mesmo.

**OpenShift**

Para fazer o deploy no Openshift, você pode copiar o projeto maven  completo para o repositório GIT do Openshift no diretório `src`, onde o arquivo `pom.xml` do nosso projeto deverá estar em `src` do repositório clonado do Openshift.
Em seguida, você deve modificar o arquivo `` do seu repositório do openshift para configurar as propriedades de sistema conforme mencionado na seção **Empacotando e rodando a aplicação**.
Por fim, se estiver usando outro banco de dados, modifique o arquivo `persistence.xml`. Por exemplo, para MySql:
~~~
 <jta-data-source>java:jboss/datasources/MySQLDS</jta-data-source>
~~~

**Ativando a Segurança**

A configuração de segurança foi simplicada já que não temos mais a role de autor. Para ter um usuário admnistrador simplesmente [crie um usuário do JBoss](https://docs.jboss.org/author/display/AS71/add-user+utility) no "realm"   `ApplicationRealm` e dê o role ADMINISTRADOR para ele.
No Openshift você deverá fazer ssh ao servidor para adicionar esse usuário.

Fazendo o deploy, uns dados inicíais você já terá para ver a aplicação rodando. Você pode ver o evento de teste em: `http://localhost:8080/jugvale-call4papers/rest/evento/` ou a página inicial em `http://localhost:8080/jugvale-call4papers/`.
