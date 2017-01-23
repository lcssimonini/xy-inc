# xy-inc
A aplicação permite:

  - cadastrar pontos de interesse;
  - listar todos os pontos cadastrados;
  - listar pontos a uma dada distância de um ponto informato.

Para executar a aplicação, será necessário:

  - importar essa aplicação no eclipse como um projeto existente do git; 
  - executar um servidor monngodb (configurado para a porta padrão 27017);

## Alterar configurações do servidor
  
Para mudar a porta, configurar no arquivo application.properties:

```sh
spring.data.mongodb.host= < seu host >
spring.data.mongodb.port= < número da porta >
```

Caso seja necessário modificar a porta do servidor http da aplicação, criar também a seguinte propriedade:
```sh
server.port = < número da porta >

```

  - executar como aplicação java (jdk 8) o método main da classe XyLocatorApplication;
  - para executar os testes rodar na raiz do projeto:
 
```sh
mvn test
```

## Documentação da API

Considerando que o sistema está sendo acessado do localhost:

### Listar todos os pontos cadastrados
 ```sh
[GET] http://localhost:8080/points
```
Retorno esperado:
 ```sh
[
  {
    "id": "58867d81737cdf172c658757",
    "name": "um ponto cadastrado",
    "xCoordinate": 45,
    "yCoordinate": 11,
    "errorsList": []
  },
  {
    "id": "58867ec0737cdf172c658758",
    "name": "putro ponto cadastrado",
    "xCoordinate": 45,
    "yCoordinate": 11,
    "errorsList": []
  }
]
```
### Cadastrar ponto
 ```sh
[POST] http://localhost:8080/points?name=padaria&xCoordinate=1&yCoordinate=1
```
 realizar requisição POST informando:
  - name: nome do ponto a ser cadastrado;
  - xCoordinate: valor da coordenada x, deve ser um valor inteiro positivo;
  - yCoordinate: valor da coordenada y, deve ser um valor inteiro positivo;
 
Retorno esperado:
 ```sh
{
  "id": "< id do ponto na base de dados>",
  "name": "padaria",
  "xCoordinate": 1,
  "yCoordinate": 1,
  "errorsList": []
}
```
Obs: O campo errorsList é utilizado para informar erros de validação caso existam.
### Cadastro de ponto com erros
 ```sh
[POST] http://localhost:8080/points?xCoordinate=1&yCoordinate=1
```
Retorno esperado:
 ```sh
{
  "id": null,
  "name": null,
  "xCoordinate": 1,
  "yCoordinate": 1,
  "errorsList": ["Nome não pode ser vazio"]
}
```
Nesse caso, como o ponto informado na requisição não possuia nome, o retorno foi um ponto de id nulo, pois não foi criado no banco de dados, e o campo errosList contendo a mensagem de validação referente ao nome;

### Outras mensagens de validação

 - "coordenada x não pode ser vazia", caso não seja informado valor para [xCoordinate];
 - "coordenada y não pode ser vazia", caso não seja informado valor para [yCoordinate];
 - "coordenada x deve ser um valor positivo", caso seja informado valor menor que 0 para [xCoordinate];
 - "coordenada y deve ser um valor positivo", caso seja informado valor menor que 0 para [yCoordinate];

## Listar pontos por proximidade

```sh
[GET] http://localhost:8080/points/findNear?xReference=10&yReference=10&distance=20
```
 realizar requisição POST informando:
  - xReference: valor da coordenada x do ponto de referência;
  - yReference: valor da coordenada y do ponto de referência;
  - distance: distância máxima a ser considerada na busca;

Para essa operação é esperada uma resposta no mesmo formato da listagem de pontos, contendo apenas os pontos que cumprem o critério da distância.

Caso não seja informado algum dos parâmetros, espera-se um retorno no seguinte formato:

```sh
{
  "timestamp": 1485210041716,
  "status": 400,
  "error": "Bad Request",
  "exception": "org.springframework.web.bind.MissingServletRequestParameterException",
  "message": "Required Integer parameter 'yReference' is not present",
  "path": "/points/findNear"
}
```



















