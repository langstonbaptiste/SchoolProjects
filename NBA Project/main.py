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

# Human-readable stat descriptions
statDescriptions = {
    "GamesPlayed": "Games Played",
    "Wins": "Total Wins",
    "Losses": "Total Losses",
    "HomeWins": "Home Wins",
    "HomeLosses": "Home Losses",
    "AwayWins": "Away Wins",
    "AwayLosses": "Away Losses",
    "TotalMinutes": "Total Minutes",
    "TotalPoints": "Total Points",
    "TotalPointsInWins": "Total Points in Wins",
    "TotalPointsInLosses": "Total Points in Losses",
    "TotalHomePoints": "Total Home Points",
    "TotalAwayPoints": "Total Away Points",
    "MinutesPerGame": "Minutes Per Game",
    "PPG": "Points Per Game",
    "PPM": "Points Per Minute",
    "Win%": "Win Percentage",
    "PPGinWins": "Points Per Game in Wins",
    "PPGinLosses": "Points Per Game in Losses",
    "Assists": "Total Assists",
    "AssistsPerGame": "Assists Per Game",
    "AssistsToPoints": "Assists to Points Ratio"
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

        # Assists are in COLUMN_HEADERS[19] of full stats
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
    winPercentage = wins / gamesPlayed if gamesPlayed > 0 else 0
    ppgInWins = totalPointsInWins / wins if wins > 0 else 0
    ppgInLosses = totalPointsInLosses / losses if losses > 0 else 0
    assistsToPoints = totalAssists / totalPoints if totalPoints > 0 else 0

    return {
        "GamesPlayed": gamesPlayed,
        "Wins": wins,
        "Losses": losses,
        "HomeWins": homeWins,
        "HomeLosses": homeLosses,
        "AwayWins": awayWins,
        "AwayLosses": awayLosses,
        "TotalMinutes": totalMinutes,
        "TotalPoints": totalPoints,
        "TotalPointsInWins": totalPointsInWins,
        "TotalPointsInLosses": totalPointsInLosses,
        "TotalHomePoints": totalHomePoints,
        "TotalAwayPoints": totalAwayPoints,
        "MinutesPerGame": minsPerGame,
        "PPG": ppg,
        "PPM": ppm,
        "Win%": winPercentage,
        "PPGinWins": ppgInWins,
        "PPGinLosses": ppgInLosses,
        "Assists": totalAssists,
        "AssistsPerGame": totalAssists / gamesPlayed if gamesPlayed > 0 else 0,
        "AssistsToPoints": assistsToPoints
    }

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

def writePlayerStats(fileName, statKey):
    stats = makeAllPlayerStats()

    n = len(stats)
    for i in range(n):
        for j in range(0, n-i-1):
            if stats[j][statKey] < stats[j+1][statKey]:
                stats[j], stats[j+1] = stats[j+1], stats[j]

    string = ""
    title = "2023-2024 NBA Season Stats - " + statDescriptions.get(statKey, statKey)
    string += title + "\n"
    string += "-" * len(title) + "\n"

    players = list(gameLogs.keys())
    for i in range(len(stats)):
        newString = str(i+1) + ". " + players[i]
        string += newString + " " * (29 - len(newString)) + str(round(stats[i][statKey], 2))
        string += "\n"

    with open(fileName, "w") as f:
        f.write(string)

def computeMVP():
    players = list(gameLogs.keys())
    stats = [getPlayerStats(p) for p in players]

    # choose which stats to include in MVP calculation
    mvpStats = ["PPG", "PPM", "Win%", "AssistsPerGame",
                "PPGinWins", "PPGinLosses", "MinutesPerGame", "AssistsToPoints"]

    def normalize(lst):
        lo, hi = min(lst), max(lst)
        return [(x - lo) / (hi - lo) if hi > lo else 0 for x in lst]


    normalized = {}
    for stat in mvpStats:
        values = [s[stat] for s in stats]
        normalized[stat] = normalize(values)


    scores = []
    for i, player in enumerate(players):
        score = sum(normalized[stat][i] for stat in mvpStats) / len(mvpStats)
        scores.append([player, score])

    n = len(scores)
    for i in range(n):
        for j in range(0, n-i-1):
            if scores[j][1] < scores[j+1][1]:
                scores[j], scores[j+1] = scores[j+1], scores[j]


    print("\n🏀 MVP Leaderboard (Comprehensive):")
    for i in range(min(10, len(scores))):
        player, score = scores[i]
        print(str(i+1) + ". " + player + " : " + str(round(score, 3)))

# Example run
writePlayerStats("sorted_nba_stats.txt", "PPG")
computeMVP()
print("compiles")
