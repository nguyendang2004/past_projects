def fullJustify(self, words: List[str], maxWidth: int) -> List[str]:
            
    result = []
    line=""
    sum_length = 0
    space = 0
    per_word_space = 0
    spare_space =0
    word_num =0 
    start_index=0
    h=0
    for i in range(0, len(words)):
        if word_num !=0:
            sum_length= sum_length +1
        sum_length= sum_length + len(words[i])
        word_num = word_num+1 
        if (i+1==len(words)):
            h = maxWidth+1
        else:
            h=  sum_length + len(words[i+1])+1


        if h > maxWidth or i==len(words)-1:
            line=""
            space = maxWidth -sum_length
            if i == len(words)-1 or word_num ==1:
                per_word_space= 0
                spare_space = 0
            else:
                per_word_space= int(space /(word_num-1))
                spare_space = space %(word_num-1)

            
            for b in range (start_index,i+1,1):
            
                line = line+ words[b]
                if b!= i:
                    line=line+" "*(per_word_space+1)
                    if spare_space >0:
                        spare_space=spare_space-1
                        line=line+" "

            if i==len(words)-1 or word_num==1:
                line=line+" "*space
            start_index = i+1
            result.append(line)
            word_num=0
            sum_length=0
            h=0
        
    return result