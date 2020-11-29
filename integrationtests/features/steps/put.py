import requests
import json
from time import sleep
from behave import given, then, when, step
from helpers.json_helper import return_json_from_url, load_json_from_data_folder

@step('envio o arquivo JSON "{json_file}", usando o método PUT')
def send_json_to_put_route(context, json_file):
    status_positive = [200,204]
    json_file = load_json_from_data_folder(json_file)
    url = f'{context.api_url}/{context.json_id}'
    import ipdb; ipdb.sset_trace()
    context.status = requests.put(url, json=json_file)
    assert context.status.status_code in status_positive, f'Não foi possível alterar o valor da chave \n Status: {context.status}\n Mensagem: {context.status.text}'

@step(u'acesso a rota "{url}"/id da função com o tipo GET')
def request_on_route_with_id(context, url):
    id = context.json_id
    url = f'{url}/{id}'
    context.data_requested = return_json_from_url(context.api_url)
    assert context.data_requested != type(list), "Não foi possível retornar o JSON"
