import random

print("How many numbers would you like to generate?")
numCount = int(input())

print("What is the minimum number?")
min = int(input())

print("What is the maximum number?")
max = int(input())

string = ""
for i in range(numCount):
    num = random.randint(min,max)
    if (i != 0 and i  % 10 == 0):
        string += "\n"
    string += str(num) + " "


outputFile = open("numbers.txt", "w")
outputFile.write(string)
