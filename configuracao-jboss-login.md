## Configuração do JBoss para a autenticação
Essa é a configuração do JBoss para criar o security domain e login module para acesso ao banco de dados e retirar informação de usuário/senha e Roles.

Caso queria usar CLI, use:
```
/subsystem=security/security-domain=jug-call4papers:add
/subsystem=security/security-domain=jug-call4papers/authentication=classic:add(login-modules=[{"code"=>"Database","flag"=>"required","module-options"=>[("unauthenticatedIdentity"=>"ANONIMO"),("dsJndiName"=>"java:jboss/datasources/JUGCall4PapersDS"),("principalsQuery"=>"SELECT senha AS password FROM Usuario WHERE login=?"),("rolesQuery"=>"SELECT role,'Roles' FROM Usuario WHERE login=?"),("hashAlgorithm"=>"MD5"),("hashEncoding"=>"hex")]}]){allow-resource-service-restart=true}
```

Caso queira modificar o arquivo standalone.xml para adicionar isso, aqui vai a versão em XML(não testada):
```
<subsystem xmlns="urn:jboss:domain:security:1.0">
 <security-domains>
   <security-domain name="jug-call4papers">
    <authentication>
          <login-module code="Database" flag="required">
               <module-option name="dsJndiName" value="java:jboss/datasources/JUGCall4PapersDS"/>
               <module-option name="principalsQuery" value="SELECT senha FROM Usuario WHERE login=?"/>
               <module-option name="rolesQuery">
                 SELECT role,'Roles' FROM Usuario WHERE login=?
                </module-option>
               <module-option name="hashAlgorithm">MD5</module-option>
               <module-option name="hashEncoding">hex</module-option>
          </login-module>
     </authentication>
  </security-domain>
 </security-domains>
</subsystem>
```
