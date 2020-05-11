# CDP MIRANTE
================================================
Autor: Luis Bottino
Technologies: CDI, JPA, EJB, JPA, JAX-RS, BV, AngularJS
Resumo: Projeto prova da Mirante para cadastro de pessoas


# Descrição
--------------------------------------------------

Este é um projeto que usa as tecnologias Maven 3, Java EE 7, JBoss WildFly e AngularJS. As dependências do projeto angular já estão no projeto.

# Requerimentos
--------------------------------------------------

É preciso ter o Java 7.0 (Java SDK 1.7) ou superior, Maven 3.1 ou superior.

A aplicação  foi projetada para rodar usando o JBoss WildFly. 

# Inicie JBoss WildFly com o Web Profile
--------------------------------------------------

1. Abra a linha de comando e abra o diretório raiz do JBoss.
2. As linhas a seguir mostram os comandos para iniciar o servidor:

        Linux:   JBOSS_HOME/bin/standalone.sh
        Windows: JBOSS_HOME\bin\standalone.bat

 
# Build e Deploy
--------------------------------------------------


1. Tenha certeza que você iniciou o servidor JBoss Server como descrito acima.
2. Abra a linha de comando e navegue até o diretório raiz da aplicação.
3. Digite o comando abaixo para fazer o build e o deploy do arquivo:

        mvn clean package wildfly:deploy

4. Este comando fará o deploy em `target/cdpmirante.war` to the running instance of the server.
 

# Acesso à aplicação 
--------------------------------------------------

Aplicação estará rodando na URL: <http://localhost:8080/cdpmirante/>.



# Execute os Testes Arquillian 
--------------------------------------------------

1. Tenha certeza que você iniciou o servidor JBoss Server como descrito acima.
2. Abra a linha de comando e navegue até o diretório raiz da aplicação.
3. Digite o comando abaixo para executar os testes com o perfil ativado:

        mvn clean test -Parq-wildfly-remote

