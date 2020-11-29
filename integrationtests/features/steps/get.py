from behave import given, then, when, step
from helpers.json_helper import get_json_keys_on_list, return_json_from_url, find_value_on_json, find_correct_json
from helpers.list_helper import create_list_with_duplicated_elements_of_two_lists
import requests
import json

@given('webservice esteja em execucao')
def check_content_status_code(context, status=200):
    ws_url = requests.get('http://ws-marcaponto.herokuapp.com/swagger-ui.html')
    http_status = ws_url.status_code
    assert http_status == status, 'O ws-service não está disponível'

@step('encontro o id na url')
def get_id_from_url(context):
    context.url_id = find_value_on_json(context.data_requested, 'id')
    assert context.url_id / 1 == context.url_id, "Id não encontrado"

@step('atualizo o status para "{condition}"')    
def update_status(context, condition):
    data = context.data_requested
    cond = bool(condition)
    data['ativo'] = cond
    post_status = requests.post(context.new_url, json=data)
    assert data['ativo'] == cond, "Erro ao atualizar"

@step('faço uma requisição do tipo GET na rota "{url}"')
def request_on_route(context, url):
    context.api_url = url
    request = requests.get(url)
    context.data_requested = request.json()
    assert request.status_code != 404, "Requisição inválida"


@then('um JSON com as seguintes chaves deve ser retornado')
def get_json_of_request(context):
    table = [element.cells[0] for element in context.table]
    function = context.data_requested
    function = function[0] if type(function) == list else function
    json_keys_list = get_json_keys_on_list(function)
    list_same_keys = create_list_with_duplicated_elements_of_two_lists(json_keys_list, table)
    assert  list_same_keys == table, "As chaves passadas não foram encontradas"
