import os

file = open("text.txt","r")

data = file.read()
data = os.linesep.join([s for s in data.splitlines() if s])
list = data.splitlines()
count = 0;

for line in list:
    list[count] = line[line.find('[')+1:line.find(']')]
    count += 1;

newlist = []

for line in list:
    if len(line) > 0:
        newlist.append(line)

print(newlist)
 
file.close() 
