import requests
from behave import given, then, when, step
from helpers.json_helper import get_json_keys_on_list, return_content_from_url 
from helpers.json_helper import load_json_from_data_folder, find_key_and_value_on_json, find_value_on_json
    

@step('faço uma requisição do tipo DELETE na rota + ID')
def delete_on_route(context):
    status_code = [200, 204]
    context.status = requests.delete(f'{context.api_url}/{context.id_found}')
    assert context.status.status_code in status_code, f'Não foi possível deletar, retorno {context.status.status_code}'

@step('encontro o id do colaborador teste')
def find_test_id(context):
    data = context.data_requested
    url_pessoa = "http://ws-marcaponto.herokuapp.com/api/v1/pessoa/"
    for info in data:
        pessoa_id = info['pessoaId']
        validator = False
        content = return_content_from_url(url_pessoa+str(pessoa_id))
        if content['nomeCompleto'] == 'Diogo Testes':
            context.id_found = info['id']
            validator = True
    
    assert validator, 'Id não encontrado'
        
        

