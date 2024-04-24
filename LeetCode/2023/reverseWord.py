def reverseWords(self, s: str) -> str:
    string_list = s.split(" ")
    result = ""
    string_list= [i for i in string_list if i !='']
    for i in range(len(string_list)-1,-1,-1):

        result = result+ string_list[i]
        if (i>0):
            result = result+" "
        
    return result