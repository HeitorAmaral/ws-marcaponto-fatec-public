Funcionalidade: Put
    Eu como desenvolvedor, quero alterar dados através de requisições tipo Put.

Contexto:
    Dado webservice esteja em execucao

Cenário: Alterar nome da função
    Dado faço uma requisição do tipo GET na rota "http://ws-marcaponto.herokuapp.com/api/v1/funcao"
    E encontro o ID do cadastro teste no Json onde o valor da chave "nome" é "QA"
    E adiciono o ID ao arquivo "insert_functionR.json"
    E acesso a rota "http://ws-marcaponto.herokuapp.com/api/v1/funcao"/id da função com o tipo GET
    Então um JSON com as seguintes chaves deve ser retornado
    |key              |
    |recCreatedOn     |
    |recCreatedBy     |
    |nome             |
    |ativo            |
    |setor            | 

Cenário: Alterar nome da pessoa
    Dado faço uma requisição do tipo GET na rota "http://ws-marcaponto.herokuapp.com/api/v1/pessoa"
    E encontro o ID do cadastro teste no Json onde o valor da chave "apelido" é "Diogo-tests"
    Quando envio o arquivo JSON "update_person.json", usando o método PUT
    E acesso a rota "http://ws-marcaponto.herokuapp.com/api/v1/pessoa"/id da função com o tipo GET
    Então um JSON com as seguintes chaves deve ser retornado
    |key              |
    |id               |
    |nomeCompleto     |
    |email            |
    |rg               |
    |cpf              |
    |ativo            |


Cenário: Alterar nome da setor
    Dado faço uma requisição do tipo GET na rota "http://ws-marcaponto.herokuapp.com/api/v1/setor"
    E encontro o ID do cadastro teste no Json onde o valor da chave "nome" é "setor_teste"
    Quando envio o arquivo JSON "update_sector.json", usando o método PUT
    E acesso a rota "http://ws-marcaponto.herokuapp.com/api/v1/setor"/id da função com o tipo GET
    Então um JSON com as seguintes chaves deve ser retornado
    |key      |
    |id       |
    |nome     |
    |ativo    |