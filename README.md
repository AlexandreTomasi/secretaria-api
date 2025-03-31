# PROJETO PRÁTICO IMPLEMENTAÇÃO BACK-END

### Meus Dados
Alexandre Tomasi  
Cargo interesse: DESENVOLVIMENTO, Desenvolvedor JAVA (Back-end)  
email: alexandretomasi18@gmail.com

### Arquitetura
- Java 24
- Framework Spring boot 3.4.4
- Apache Maven
- PostgreSQL 17
- windows 11

### Executando
Instale o docker(versao mais recente) no sistema operacional.  
Conforme foi pedido foi executado em container as quatro partes do sistema que são elas:  
banco de dados, sistema de arquivos minio, com nginx e o proprio back-end  
Para executar via docker compose, basta abrir o terminal na raiz do projeto
(na mesma pasta do arquivo "docker-compose.yml") e executar os seguinte comando.
Pode ser que demore um pouco até baixar.
docker-compose up -d  
Para parar todos os containers:  
docker-compose down  

## Testando as APIs
Depois de executar o docker compose abrir o navegador e abrir o Swagger:  
http://localhost:8080/swagger-ui/index.html#/  
Todas as APIS estão presentes para testar.  
**Todas as APIS ja tem exemplos prontos para executar no swagger, sendo somente
necessario clicar em *Try it out* e *Execute***  
É necessario autenticar vá em Autenticação  e na api de login, execute e pegue o accessToken.  
Vá ao topo da pagina e clique em Authorize e no campo coloque o accessToken.  
Pronto todas as Apis estarão autenticadas por 5 minutos, o refresh token dura 10 min.
#### Obs: O swagger não consegue enviar varios arquivos, e sim somente um, sendo assim foi feito duas apis para enviar uma foto e outra para enviar varias fotos, a de enviar uma foto irá funcionar no swagger. Caso queira testar enviar varias fotos tera que usar um programa de sua preferencia como Postman, Talent Api tester e etc. Exemplo via Curl:  

curl -i -X POST \
-H "Content-Type:multipart/form-data" \
-H "Authorization:Bearer eyJhbGciOiJIUzI1NiJ9.eyJub21lIjoiSm_Do28gU2lsdmEiLCJpZCI6MSwiZW1haWwiOiJhbGV4YW5kcmVAZ21haWwuY29tIiwic3ViIjoiYWxleGFuZHJlIiwiaWF0IjoxNzQzMjk0MjMzLCJleHAiOjE3NDMyOTQ4MzN9.nBzH5bi7-u6FH61MJfoLwmWpyGEOixZ_5FKnvrGxn0s" \
-F "pessoaId=2" \
-F "arquivos=@\"./java.png\";type=image/png;filename=\"java.png\"" \
-F "arquivos=@\"./Java_programming_language_logo.svg.png\";type=image/png;filename=\"Java_programming_language_logo.svg.png\"" \
'http://localhost:8080/pessoa/foto/varias/upload'

O projeto foi feito para receber requisições somente localhost. A classe SecurityConfig no metodo corsConfigurationSource está
definindo de onde poderá receber requisições:  
*configuration.setAllowedOrigins(List.of("http://localhost:8080", "http://127.0.0.1:8080"));*
