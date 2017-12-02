from itertools import product

sum = 0
file = open("input.txt","r")
for line in file.readlines():
    values = [ int(v) for v in line.split() ]
    sum += ( max(values) - min( values ) )
file.close()

print( sum )

sum = 0

def checksum( values ):
    chk = 0
    for p in values:
        for s in values:
            if p != s and p % s == 0:
                chk = p / s
    return chk
    
file = open("input.txt","r")
for line in file.readlines():
    sum += checksum( [ int(v) for v in line.split() ] )
    
file.close()
        
print( sum )
