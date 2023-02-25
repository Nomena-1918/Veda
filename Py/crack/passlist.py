def add(list, alphabet, size):
    if len(list)==0:
        return add(alphabet, alphabet, size)
    elif len(list[0])<size:
        newlist=[]
        for elmt in list:
            for a in alphabet:
                newlist.append(elmt+a)
        return add(newlist, alphabet, size)
    else:
        return list
    
alphabet=["a", "b", "c", "d", "e", "f"]
array=add([], alphabet, 6)
print(array)