# padrao-ws
Criação de um simples WebService com token de acesso (JWT)

O usuário obtém um token de acesso ao realizar o login e este token deve ser fornecido a cada requisição. Existe um filter que intercepta cada 
requisição feita, fazendo assim a validação do token fornecido.

No pacote br.com.thiago10gr.util há uma chave secreta, basicamente o WebService utiliza essa chave secreta para fornecer chaves públicas.
public static final String SECRET_KEY = "pa158iu2UiyPnAyRq";

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

Para a realização da conexão com a base de dados é feito um lookup. As configurações devem ser postas no context.xml do Tomcat, 
onde estarão as configurações de acesso a base de dados:

   <Resource auth="Container"
       driverClassName="com.mysql.jdbc.Driver" 
       global="jdbc/PadraoWS" 
       maxActive="8" maxIdle="4" 
       name="jdbc/PadraoWS" 
       username="root" password="1234" 
       type="javax.sql.DataSource" 
       url="jdbc:mysql://localhost:3306/padraows" /> 
       
 Os tokens podem ser testados em: https://jwt.io/
