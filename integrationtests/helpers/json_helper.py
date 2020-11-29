import json
import requests
from os import getcwd
from os.path import join


def get_json_keys_on_list(json_file):
    dict_json = json_file
    list_keys = [ k for k in dict_json ]
    return list_keys

def return_json_from_url(url):
    api_url = url
    request = requests.get(api_url)
    json = request.json()    
    if len(json) == 0:
        return "Nenhum json encontrado"
    else:
        return json

def return_content_from_url(url):
    api_url = url
    request = requests.get(api_url)
    json = request.json()    
    return json if len(json) != 0 else None

def load_json_from_data_folder(json_file_name):
    project_path = getcwd()
    json_path = join(project_path, "data", json_file_name)
    with open(json_path) as j:
        json_file = json.load(j)
        return json_file

def find_key_and_value_on_json(json_file, key, value):
    data = json_file
    if isinstance(data, list):
        for i in data:
            try:
                if i[key] == value:
                    
                    return i
            except Exception:
                return f'Erro, valor passado não encontrado.'
    else:
        try:
            if data[key] == value:
                return data
        except Exception:
            return f'Erro, valor passado não encontrado.'


def find_value_on_json(json_file, key):
    data = json_file
    if isinstance(data, list):
        for i in data:
            try:
                if i[key]:
                    
                    return i[key]
            except Exception:
                return f'Erro, valor passado não encontrado.'
    else:
        try:
            if data[key]:
                return data[key]
        except Exception:
            return f'Erro, valor passado não encontrado.'


def find_correct_json(json, key, value):
    if type(json) == list:
        for data in json:
            if data[key] == value:
                return data

def write_on_json_file(json_file_name, key, value):
    project_path = getcwd()
    json_path = join(project_path, "data", json_file_name)
    with open(json_path, "r") as json_file:
        data = json.load(json_file)
        data[key] = value
    with open(json_path, "w") as json_file:
        json.dump(data, json_file)
    return data

    