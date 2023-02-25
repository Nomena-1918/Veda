def add(list, alphabet, size):
    if len(list)==0:
        return add(alphabet, alphabet, size)
    elif len(list[0])<size:
        newlist=[]
        for elmt in list:
            for a in alphabet:
                newlist.append(elmt+a)
        return add(newlist, alphabet, size)
    return list
    
alphabet=["a", "b", "5", "d", "e", "f", "g", "h"]
array=add([], alphabet, 8)
f=open("listpass.txt", "w")
for elmt in array:
    f.write(elmt+" ")
f.close()