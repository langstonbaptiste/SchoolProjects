inputFile = open("numbers.txt", "r")
input = inputFile.read()

outputFile = open("numbers2.txt", "w")
outputFile.write("")
outputFile.close()
outputFile = open("numbers2.txt", "a")


lines = input.splitlines()
for line in lines:
    string  = ""
    nums = line.strip().split(" ")
    total = 0
    for num in nums:
        intNum = int(num) + 10
        total += intNum
        string += str(intNum) + " "
    string += "rowSum = " + str(total) + "\n"
    outputFile.write(string)