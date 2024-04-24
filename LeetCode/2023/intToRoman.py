def intToRoman(self, num: int) -> str:
        result =''
        digit = ['','I', 'II','III','IV','V', 'VI','VII','VIII', 'IX']
        ten = ['','X', 'XX','XXX','XL','L', 'LX','LXX','LXXX', 'XC']
        hund = ['','C', 'CC','CCC','CD','D', 'DC','DCC','DCCC', 'CM']
        thou = ['','M','MM','MMM']
        result = result + thou[int(num/1000)]
        remainder = num%1000
        result = result + hund[int(remainder/100)]
        remainder = remainder%100
        result = result + ten[int(remainder/10)]
        remainder = remainder%10
        result = result + digit[int(remainder/1)]
        return result

