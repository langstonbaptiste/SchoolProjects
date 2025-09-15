#PracticeSort.py

testList = [['az', 5, -1.2], ['cy', 7, -1.4], ['bx', 6, -1.6]]

def customSort(aList):
    for i in range(len(aList)):
        for j in range(len(aList) - 1):
            if aList[j][0][1] > aList[j + 1][0][1]:
                aList[j], aList[j + 1] = aList[j + 1], aList[j]
    return aList


print(customSort(testList))