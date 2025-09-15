'''
Data Analysis intro project
NBA gamelogs 2023-2024 season
'''

import datetime

fileName = "2023-2024 NBA Season data - 2023-2024_NBA_Gamelogs.csv"
fullStatsFileName = "2023-2024 NBA Season data - Full_Stats.csv"

# Read gamelogs file
with open(fileName, "r") as file:
    text = file.readlines()

# Read full stats file
with open(fullStatsFileName, "r") as fullFile:
    fullText = fullFile.readlines()

COLUMN_HEADERS = fullText[0].strip().split(",")

# Clean gamelogs
cleanedData = []
for line in text[1:]:
    line = line.strip().split(",")
    for i in range(4, len(line)):
        line[i] = float(line[i]) 
    cleanedData.append(line)

# Clean full stats
fullCleanedData = []
for line in fullText[1:]:
    line = line.strip().split(",")
    for i in range(5, len(line)):
        line[i] = 0 if line[i] == "-" else float(line[i]) 
    fullCleanedData.append(line)

# Game logs by player
gameLogs = {}
for line in cleanedData:
    player = line[0]
    if player not in gameLogs:
        gameLogs[player] = []
    gameLogs[player].append(line)

# Full game logs by player
fullGameLogs = {}
for line in fullCleanedData:
    player = line[0]
    if player not in fullGameLogs:
        fullGameLogs[player] = []
    fullGameLogs[player].append(line)

# Game logs by opponent
gameLogsByOpponent = {}
for line in cleanedData:
    player = line[0]
    if player not in gameLogsByOpponent:
        gameLogsByOpponent[player] = {}

    match = line[1].split(" ")
    opp = match[2]
    playerMap = gameLogsByOpponent[player]
    if opp not in playerMap:
        playerMap[opp] = []
    playerMap[opp].append(line)


# Indexes reference
'''
0 - playerName
1 - gamesPlayed
2 - wins
3 - losses
4 - homeWins
5 - homeLosses
6 - awayWins
7 - awayLosses
8 - totalMinutes
9 - totalPoints
10 - totalPointsInWins
11 - totalPointsInLosses
12 - totalHomePoints
13 - totalAwayPoints
14 - minsPerGame
15 - PPG
16 - PPM
17 - win%
18 - PPGinWins
19 - PPGinLosses
20 - AssistsToPoints
'''

statDescriptions = {
    1 : "Games Played",
    2 : "Total Wins",
    3 : "Total Losses",
    4 : "Home Wins",
    5 : "Home Losses",
    6 : "Away Wins",
    7 : "Away Losses",
    8 : "Total Minutes",
    9 : "Total Points",
    10 : "Total Points in Wins",
    11 : "Total Points in Losses",
    12 : "Total Home Points",
    13 : "Total Away Points",
    14 : "Minutes Per Game",
    15 : "Points Per Game",
    16 : "Points Per Minute",
    17 : "Win Percentage",
    18 : "Points Per Game in Wins",
    19 : "Points Per Game in Losses",
    20 : "Assists To Points"
}

def getPlayerStats(playerName):
    games = gameLogs[playerName]
    fullGames = fullGameLogs[playerName]
    
    gamesPlayed = len(games)
    wins = 0
    losses = 0
    homeWins = 0
    homeLosses = 0
    awayWins = 0
    awayLosses = 0
    totalMinutes = 0
    totalPoints = 0
    totalPointsInWins = 0
    totalPointsInLosses = 0
    totalHomePoints = 0
    totalAwayPoints = 0
    totalAssists = 0
 
    for i in range(len(games)):
        game  = games[i]
        fullGame  = fullGames[i]
        match = game[1].split(" ")
        home = match[1] != "@"

        outcome = game[3]
        if outcome == "W":
            wins += 1
            if home:
                homeWins += 1
            else:
                awayWins += 1
        else:
            losses += 1
            if home:
                homeLosses += 1
            else:
                awayLosses += 1

        totalMinutes += game[4]
        points = game[5]
        totalPoints += points

        # Check index for assists (make sure COLUMN_HEADERS[19] == "AST")
        totalAssists += fullGame[19]

        if outcome == "W":
            totalPointsInWins += points
        else:
            totalPointsInLosses += points
        if home:
            totalHomePoints += points
        else:
            totalAwayPoints += points
   
    minsPerGame = totalMinutes / gamesPlayed if gamesPlayed > 0 else 0
    ppg = totalPoints / gamesPlayed if gamesPlayed > 0 else 0
    ppm = totalPoints / totalMinutes if totalMinutes > 0 else 0
    winPercentage = wins / gamesPlayed * 100 if gamesPlayed > 0 else 0
    ppgInWins = totalPointsInWins / wins if wins > 0 else 0
    ppgInLosses = totalPointsInLosses / losses if losses > 0 else 0
    assistsToPoints = totalAssists / totalPoints if totalPoints > 0 else 0

    return [
        playerName, gamesPlayed, wins, losses, homeWins, homeLosses,
        awayWins, awayLosses, totalMinutes, totalPoints, totalPointsInWins,
        totalPointsInLosses, totalHomePoints, totalAwayPoints,
        minsPerGame, ppg, ppm, winPercentage,
        ppgInWins, ppgInLosses, assistsToPoints
    ]


def pointsPerMonth(month):
    points = 0
    for line in cleanedData:
        date = line[2]
        splitDate = date.split("/")
        if int(splitDate[0]) == month:
            points += int(line[5])
    return points


def printPPGperOpponent(playerName):
    logs = gameLogsByOpponent[playerName]
    teams = list(logs.keys())
    teams.sort()
    for team in teams:
        games = logs[team]
        totalGames = len(games)
        totalPoints = sum(game[5] for game in games)
        avgPoints = totalPoints / totalGames if totalGames > 0 else 0
        print(team, ":", avgPoints)


def printPPGperBreakLength(playerName):
    logs = gameLogs[playerName]

    points = [0,0,0]
    games = [0,0,0]

    last_date_object = None
    for game in logs:
        date = game[2].split('/')
        date_object = datetime.datetime(int(date[2]), int(date[0]), int(date[1]))

        if last_date_object is None:
            last_date_object = date_object
            day_diff = 3
        else:
            day_diff = (date_object - last_date_object).days - 1
            last_date_object = date_object
        
        if day_diff == 0:
            index = 0
        elif day_diff <= 2:
            index = 1
        else:
            index = 2
        
        points[index] += game[5]
        games[index] += 1
    
    avg = [
        points[0] / games[0] if games[0] > 0 else 0,
        points[1] / games[1] if games[1] > 0 else 0,
        points[2] / games[2] if games[2] > 0 else 0
    ]

    print(playerName + ":")
    print("0 rest days: " + str(avg[0]))
    print("1/2 rest days: " + str(avg[1]))
    print("3+ rest days: " + str(avg[2]))
    return avg


def makeAllPlayerStats():
    stats = []
    for player in gameLogs:
        stats.append(getPlayerStats(player))
    return stats


def writePlayerStats(fileName, index):
    stats = makeAllPlayerStats()
    stats.sort(key=lambda x: x[index], reverse=True)
  
    string = ""
    title = "2023-2024 NBA Season Stats - " + statDescriptions[index]
    string += title + "\n"
    string += "-" * len(title) + "\n"
    for i in range(len(stats)):
        newString = str(i+1) + ". " +  str(stats[i][0]) 
        string += newString + " " * (29 - len(newString)) + str(round(stats[i][index], 2))
        string += "\n"
    
    with open(fileName, "w") as outputFile:
        outputFile.write(string.strip())


# Example run
writePlayerStats("sorted_nba_stats.txt", 20)
print("compiles")
