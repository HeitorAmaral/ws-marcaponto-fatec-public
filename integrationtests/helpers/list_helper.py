def create_list_with_duplicated_elements_of_two_lists(list_1, list_2):
    final_list = []
    bigger_list = list_1 if len(list_1) >= len(list_2) else list_2
    smaller_list = list_2 if len(list_1) >= len(list_2) else list_1
    try: 
        for e in bigger_list:
            for el in smaller_list:
                if e == el:
                    final_list.append(e)
        return final_list
    
    except Exception:
        return 'Listas passadas não tem elementos duplicados'