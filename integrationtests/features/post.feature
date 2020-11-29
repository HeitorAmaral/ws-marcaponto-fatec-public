Funcionalidade: Post
    Eu como desenvolvedor, quero inserir dados através de requisições tipo POST.  

Contexto:
    Dado webservice esteja em execucao

Cenário: Cadastrar um setor:
    Dado que eu carrego o arquivo JSON de nome "insert_sector.json"
    Quando envio o arquivo JSON "insert_sector.json", usando o método POST para a rota "http://ws-marcaponto.herokuapp.com/api/v1/setor"
    Então devo ter um retorno "201" do content

Cenário: Cadastrar uma pessoa:
    Dado que eu carrego o arquivo JSON de nome "insert_person.json"
    Quando envio o arquivo JSON "insert_person.json", usando o método POST para a rota "http://ws-marcaponto.herokuapp.com/api/v1/pessoa"
    Então devo ter um retorno "201" do content
    
Cenário: Cadastrar uma função responsável:
    Dado que eu carrego o arquivo JSON de nome "insert_responsible_function.json"
    Quando faço uma requisição do tipo GET na rota "http://ws-marcaponto.herokuapp.com/api/v1/setor/"
    E encontro o id do JSON com o atributo "nome": "setor_teste"
    E atualizo a chave "setorId" com o valor "id_encontrado" no JSON "insert_responsible_function.json"
    Quando envio o arquivo JSON "insert_responsible_function.json", usando o método POST para a rota "http://ws-marcaponto.herokuapp.com/api/v1/funcao"
    Então devo ter um retorno "201" do content

Cenário: Cadastrar uma função:
    Dado que eu carrego o arquivo JSON de nome "insert_function.json"
    Quando faço uma requisição do tipo GET na rota "http://ws-marcaponto.herokuapp.com/api/v1/funcao/"
    E encontro o id do JSON com o atributo "nome": "QA_responsavel"
    E atualizo a chave "funcaoResponsavelId" com o valor "id_encontrado" no JSON "insert_function.json"
    E faço uma requisição do tipo GET na rota "http://ws-marcaponto.herokuapp.com/api/v1/setor/"
    E encontro o id do JSON com o atributo "nome": "setor_teste"
    E atualizo a chave "setorId" com o valor "id_encontrado" no JSON "insert_function.json"
    Quando envio o arquivo JSON "insert_function.json", usando o método POST para a rota "http://ws-marcaponto.herokuapp.com/api/v1/funcao"
    Então devo ter um retorno "201" do content

Cenário: Cadastrar um expediente:
    Dado que eu carrego o arquivo JSON de nome "insert_expediente.json"
    Quando envio o arquivo JSON "insert_expediente.json", usando o método POST para a rota "http://ws-marcaponto.herokuapp.com/api/v1/expediente"
    Então devo ter um retorno "201" do content

Cenário: Cadastrar um colaborador:
    Dado que eu carrego o arquivo JSON de nome "insert_colaborador.json"
    Quando faço uma requisição do tipo GET na rota "http://ws-marcaponto.herokuapp.com/api/v1/pessoa/"
    E encontro o id do JSON com o atributo "nomeCompleto": "Diogo Testes"
    E atualizo a chave "pessoaId" com o valor "id_encontrado" no JSON "insert_colaborador.json"
    Quando faço uma requisição do tipo GET na rota "http://ws-marcaponto.herokuapp.com/api/v1/funcao/"
    E encontro o id do JSON com o atributo "nome": "QA_responsavel"
    E atualizo a chave "funcaoId" com o valor "id_encontrado" no JSON "insert_colaborador.json"
    Quando faço uma requisição do tipo GET na rota "http://ws-marcaponto.herokuapp.com/api/v1/expediente/"
    E encontro o id do JSON com o atributo "nome": "qa-expediente"
    E atualizo a chave "expedienteId" com o valor "id_encontrado" no JSON "insert_colaborador.json"
    Quando envio o arquivo JSON "insert_colaborador.json", usando o método POST para a rota "http://ws-marcaponto.herokuapp.com/api/v1/colaborador"
    Então devo ter um retorno "201" do content




