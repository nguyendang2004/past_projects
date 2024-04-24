def longestCommonPrefix(self, strs: List[str]) -> str:
    if len(strs) == 1:
        return strs[0]
    strs=sorted(strs)
    fir  = strs[0]
    sec= strs[-1]
    prefix =""
    for i in range(0, min(len(fir),len(sec))):
        if (fir[i]==sec[i]):
            prefix = prefix + sec[i]
        else:
            break;
    return prefix