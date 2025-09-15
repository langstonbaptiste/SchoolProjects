import random

print("How many numbers would you like to generate?")
numCount = int(input())

print("What is the minimum number?")
min = int(input())

print("What is the maximum number?")
max = int(input())

outputFile = open("numbers.txt", "w")
outputFile.write("")
outputFile.close()
outputFile = open("numbers.txt", "a")

string = ""
for i in range(numCount):
    num = random.randint(min,max)
    if (i != 0 and i  % 10 == 0):
        outputFile.write(string + "\n")
        string  = ""
    string += str(num) + " "
outputFile.write(string)

