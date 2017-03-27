# padrao-ws
Criação de um simples WebService com token de acesso (JWT)

O WebService utiliza uma chave privada para fornecer chaves públicas (token de acesso), esta chave privada pode ser encontrada no pacote br.com.thiago10gr.util
com o nome de SECRET_KEY.

O usuário obtém um token de acesso ao realizar o login e este token deve ser fornecido a cada requisição. Existe um filter que intercepta as requisições feitas, fazendo assim, a validação do token fornecido. 

Os Métodos que necessitam de um Token de acesso são anotados com @Secured. 

Os tokens de acesso podem ser testados em: https://jwt.io/

Para realizar a requisição do Token é necessário fornecer um Header:
Content-Type Value - application/x-www-form-urlencoded

Para os métodos anotados com @Secured, é necessário fornecer no Header:
Content-Type Value - application/x-www-form-urlencoded
Authorization - Bearer token_de_acesso

Para este projeto foi utilizado MySql e então criado a base de dados projetows e a tabela tbl_usuario:

CREATE TABLE TBL_USUARIO
(
	ID_USUARIO INT AUTO_INCREMENT,
	NOME VARCHAR(100) NOT NULL,
	EMAIL VARCHAR(150) NOT NULL UNIQUE,
	SENHA VARCHAR(64) NOT NULL,
	TELEFONE VARCHAR(14) NOT NULL,
	FOTO LONGBLOB,
	DATA_CADASTRO DATETIME NOT NULL,
	DATA_NASC DATE,
	SEXO CHAR(1),
	ATIVO CHAR(1)NOT NULL,
	PRIMARY KEY (ID_USUARIO)
);

Lembrando que é necessário que estejam dentro da pasta lib do Tomcat o jar do driver do Mysql e o jar do Servlet API.

Para a realização da conexão com a base de dados é feito um lookup. As configurações de acesso a base de dados devem ser postas no arquivo context.xml do Tomcat.

   <Resource auth="Container"
       driverClassName="com.mysql.jdbc.Driver" 
       global="jdbc/PadraoWS" 
       maxActive="8" maxIdle="4" 
       name="jdbc/PadraoWS" 
       username="root" password="1234" 
       type="javax.sql.DataSource" 
       url="jdbc:mysql://localhost:3306/padraows" /> 
       
      
Os testes podem ser realizados utilizado o software Postman: https://www.getpostman.com/

       
 
