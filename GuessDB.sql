DROP DATABASE IF EXISTS GuessGameDB;

CREATE DATABASE GuessGameDB;

USE GuessGameDB;

-- Make the game table. we set default for game status as 1 to signify the game is in progress
CREATE TABLE Game(
	GameId INT PRIMARY KEY AUTO_INCREMENT,
    Answer VARCHAR(4) NOT NULL,
    GameStatus BOOLEAN NOT NULL DEFAULT 1 
);

CREATE TABLE Round(
	RoundId INT PRIMARY KEY AUTO_INCREMENT,
    GameId INT NOT NULL,
    FOREIGN KEY fk_Game_Id (GameId)
		REFERENCES Game(GameId),
	Guess VARCHAR(4) NOT NULL,
    `Time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Result VARCHAR(7) NOT NULL
);