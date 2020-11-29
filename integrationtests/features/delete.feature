Funcionalidade: Delete
    Eu como desenvolvedor, quero deletar dados através de requisições tipo Delete.

Contexto:
    Dado webservice esteja em execucao


Cenário: Deletar um colaborador 
    Dado faço uma requisição do tipo GET na rota "http://ws-marcaponto.herokuapp.com/api/v1/colaborador"
    E encontro o id do colaborador teste
    Quando faço uma requisição do tipo DELETE na rota + ID
    Então devo ter um retorno "200" do content

Cenário: Deletar uma pessoa
    Dado faço uma requisição do tipo GET na rota "http://ws-marcaponto.herokuapp.com/api/v1/pessoa"
    E encontro o id do JSON com o atributo "apelido": "Diogo-tests"
    Quando faço uma requisição do tipo DELETE na rota + ID
    Então devo ter um retorno "200" do content

Cenário: Deletar uma função 
    Dado faço uma requisição do tipo GET na rota "http://ws-marcaponto.herokuapp.com/api/v1/funcao"
    E encontro o id do JSON com o atributo "nome": "QA"
    Quando faço uma requisição do tipo DELETE na rota + ID
    Então devo ter um retorno "200" do content

Cenário: Deletar uma função Responsável
    Dado faço uma requisição do tipo GET na rota "http://ws-marcaponto.herokuapp.com/api/v1/funcao"
    E encontro o id do JSON com o atributo "nome": "QA_responsavel"
    Quando faço uma requisição do tipo DELETE na rota + ID
    Então devo ter um retorno "200" do content


Cenário: Deletar um setor
    Dado faço uma requisição do tipo GET na rota "http://ws-marcaponto.herokuapp.com/api/v1/setor"
    E encontro o id do JSON com o atributo "nome": "setor_teste"
    Quando faço uma requisição do tipo DELETE na rota + ID
    Então devo ter um retorno "200" do content


Cenário: Deletar um expediente
    Dado faço uma requisição do tipo GET na rota "http://ws-marcaponto.herokuapp.com/api/v1/expediente"
    E encontro o id do JSON com o atributo "nome": "qa-expediente"
    Quando faço uma requisição do tipo DELETE na rota + ID
    Então devo ter um retorno "200" do content
