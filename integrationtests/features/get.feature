Funcionalidade: Get
  Eu como desenvolvedor, quero fazer requisições do tipo GET.  

Contexto:
  Dado webservice esteja em execucao

Cenário: Retornar as funções cadastradas:
  Quando faço uma requisição do tipo GET na rota "http://ws-marcaponto.herokuapp.com/api/v1/funcao"
  Então um JSON com as seguintes chaves deve ser retornado
    |key                  |
    |recCreatedOn         |
    |recCreatedBy         |
    |recModifiedOn        |
    |recModifiedBy        |
    |id                   |
    |nome                 |
    |funcaoResponsavelId  |
    |responsavel          |
    |setorId              |
    |ativo                |

Cenário: Retornar as pessoas cadastradas:
  Quando faço uma requisição do tipo GET na rota "http://ws-marcaponto.herokuapp.com/api/v1/pessoa"
  Então um JSON com as seguintes chaves deve ser retornado
    |key              |
    |recCreatedOn     |
    |recCreatedBy     |
    |recModifiedOn    |
    |recModifiedBy    |
    |id               |
    |nomeCompleto     |
    |apelido          |
    |email            |
    |rg               |
    |cpf              |
    |dataNascimento   |
    |ativo            |

Cenário: Retornar os setores cadastrados:
  Quando faço uma requisição do tipo GET na rota "http://ws-marcaponto.herokuapp.com/api/v1/setor"
  Então um JSON com as seguintes chaves deve ser retornado
  |key              |
  |recCreatedOn     |
  |recCreatedBy     |
  |recModifiedOn    |
  |recModifiedBy    |
  |id               |
  |nome             |
  |ativo            |


Cenário: Retornar uma função cadastrada
  Quando faço uma requisição do tipo GET na rota "http://ws-marcaponto.herokuapp.com/api/v1/funcao/"
  E encontro o id do JSON com o atributo "nome": "QA"
  Então um JSON com as seguintes chaves deve ser retornado
    |key                    |
    |recCreatedOn           |
    |recCreatedBy           |
    |recModifiedOn          |
    |recModifiedBy          |
    |id                     |
    |nome                   |
    |funcaoResponsavelId    |
    |responsavel            |
    |setorId                |
    |ativo                  |

Cenário: Retornar uma pessoa cadastrada
  Quando faço uma requisição do tipo GET na rota "http://ws-marcaponto.herokuapp.com/api/v1/pessoa/"
  E encontro o id do JSON com o atributo "apelido": "Diogo-tests"
  Então um JSON com as seguintes chaves deve ser retornado
    |key              |
    |recCreatedOn     |
    |recCreatedBy     |
    |recModifiedOn    |
    |recModifiedBy    |
    |id               |
    |nomeCompleto     |
    |apelido          |
    |email            |
    |rg               |
    |cpf              |
    |dataNascimento   |
    |ativo            |

Cenário: Retornar um setor cadastrado
  Quando faço uma requisição do tipo GET na rota "http://ws-marcaponto.herokuapp.com/api/v1/setor/"
  E encontro o id do JSON com o atributo "nome": "setor_teste"
  Então um JSON com as seguintes chaves deve ser retornado
  |key              |
  |recCreatedOn     |
  |recCreatedBy     |
  |recModifiedOn    |
  |recModifiedBy    |
  |id               |
  |nome             |
  |ativo            |


Cenário: Retornar uma função ativa
  Quando faço uma requisição do tipo GET na rota "http://ws-marcaponto.herokuapp.com/api/v1/funcao/"
  E encontro o id do JSON com o atributo "nome": "QA"
  Quando faço uma requisição do tipo GET na rota "http://ws-marcaponto.herokuapp.com/api/v1/funcao/status?ativo=true"
  Então um JSON com as seguintes chaves deve ser retornado
  |key              |
  |recCreatedOn     |
  |recCreatedBy     |
  |recModifiedOn    |
  |recModifiedBy    |
  |id               |
  |nome             |
  |ativo            |

Cenário: Retornar um setor ativo
  Quando faço uma requisição do tipo GET na rota "http://ws-marcaponto.herokuapp.com/api/v1/setor/status?ativo=true"
  Então um JSON com as seguintes chaves deve ser retornado
  |key      |
  |id       |
  |nome     |
  |ativo    |
