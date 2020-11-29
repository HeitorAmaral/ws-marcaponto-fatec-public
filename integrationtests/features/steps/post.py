import requests
from time import sleep
from behave import given, then, when, step
from helpers.json_helper import get_json_keys_on_list, return_json_from_url, find_correct_json, write_on_json_file
from helpers.json_helper import load_json_from_data_folder


@given('que eu carrego o arquivo JSON de nome "{name_json_file}"')
def load_json(context, name_json_file):
    context.json_file = load_json_from_data_folder(name_json_file)

@when('envio o arquivo JSON "{json_file}", usando o método POST para a rota {url}')
def send_json_to_post_route(context, json_file, url):
    url = str.replace(url, '"', '')
    context.json_file = load_json_from_data_folder(json_file)
    context.status = requests.post(url, json=context.json_file)

@then('devo ter um retorno "{code}" do content')
def request_on_route(context, code):
    behave_status_code = int(code)
    sleep(1)
    assert behave_status_code == context.status.status_code, f'O retorno não foi {code} e sim {context.status.status_code}'

@step('encontro o id do JSON com o atributo "{key}": "{value}"')
def find_id_by_key_and_value(context, key, value):
    json_data = context.data_requested
    json_data = find_correct_json(json_data, key, value)
    context.id_found = json_data['id']
    assert context.id_found / 1 == context.id_found, "Id não encontrado"

@step('atualizo a chave "{key}" com o valor "{value}" no JSON "{json_file}"')
def update_json(context, key, value, json_file):
    if value == 'id_encontrado':
        value = context.id_found
        new_json = write_on_json_file(json_file, key, value)
    else:
        new_json = write_on_json_file(json_file, key, value)
    assert new_json[key] == value, 'Json não foi atualizado'
