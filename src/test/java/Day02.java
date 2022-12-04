import day02.RockPaperScissors;
import day02.Tournament;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day02 {
    public static RockPaperScissors.Choice ROCK = RockPaperScissors.Choice.ROCK;
    public static RockPaperScissors.Choice PAPER = RockPaperScissors.Choice.PAPER;
    public static RockPaperScissors.Choice SCISSORS = RockPaperScissors.Choice.SCISSORS;

    @Test
    public void rockRockTest() {
        RockPaperScissors.Winner winner = RockPaperScissors.game(ROCK, ROCK);
        assertEquals(4, RockPaperScissors.score(ROCK, winner));
    }

    @Test
    public void rockPaperTest() {
        RockPaperScissors.Winner winner = RockPaperScissors.game(ROCK, PAPER);
        assertEquals(8, RockPaperScissors.score(PAPER, winner));
    }

    @Test
    public void rockScissorsTest() {
        RockPaperScissors.Winner winner = RockPaperScissors.game(ROCK, SCISSORS);
        assertEquals(3, RockPaperScissors.score(SCISSORS, winner));
    }

    @Test
    public void paperRockTest() {
        RockPaperScissors.Winner winner = RockPaperScissors.game(PAPER, ROCK);
        assertEquals(1, RockPaperScissors.score(ROCK, winner));
    }

    @Test
    public void paperPaperTest() {
        RockPaperScissors.Winner winner = RockPaperScissors.game(PAPER, PAPER);
        assertEquals(5, RockPaperScissors.score(PAPER, winner));
    }

    @Test
    public void paperScissorsTest() {
        RockPaperScissors.Winner winner = RockPaperScissors.game(PAPER, SCISSORS);
        assertEquals(9, RockPaperScissors.score(SCISSORS, winner));
    }

    @Test
    public void scissorsRockTest() {
        RockPaperScissors.Winner winner = RockPaperScissors.game(SCISSORS, ROCK);
        assertEquals(7, RockPaperScissors.score(ROCK, winner));
    }

    @Test
    public void scissorsPaperTest() {
        RockPaperScissors.Winner winner = RockPaperScissors.game(SCISSORS, PAPER);
        assertEquals(2, RockPaperScissors.score(PAPER, winner));
    }

    @Test
    public void scissorsScissorsTest() {
        RockPaperScissors.Winner winner = RockPaperScissors.game(SCISSORS, SCISSORS);
        assertEquals(6, RockPaperScissors.score(SCISSORS, winner));
    }

    @Test
    public void conversionTest() {
        assertEquals(ROCK, RockPaperScissors.convertChoice("A"));
        assertEquals(ROCK, RockPaperScissors.convertChoice("X"));
        assertEquals(PAPER, RockPaperScissors.convertChoice("B"));
        assertEquals(PAPER, RockPaperScissors.convertChoice("Y"));
        assertEquals(SCISSORS, RockPaperScissors.convertChoice("C"));
        assertEquals(SCISSORS, RockPaperScissors.convertChoice("Z"));
    }

    @Test
    public void resultConversionTest() {
        assertEquals(RockPaperScissors.Winner.ELF, RockPaperScissors.convertResult("X"));
        assertEquals(RockPaperScissors.Winner.DRAW, RockPaperScissors.convertResult("Y"));
        assertEquals(RockPaperScissors.Winner.YOU, RockPaperScissors.convertResult("Z"));
    }

    @Test void rockConditionsTest() {
        assertEquals(SCISSORS, RockPaperScissors.solveCondition(ROCK, RockPaperScissors.Winner.ELF));
        assertEquals(ROCK, RockPaperScissors.solveCondition(ROCK, RockPaperScissors.Winner.DRAW));
        assertEquals(PAPER, RockPaperScissors.solveCondition(ROCK, RockPaperScissors.Winner.YOU));
    }

    @Test void paperConditionsTest() {
        assertEquals(ROCK, RockPaperScissors.solveCondition(PAPER, RockPaperScissors.Winner.ELF));
        assertEquals(PAPER, RockPaperScissors.solveCondition(PAPER, RockPaperScissors.Winner.DRAW));
        assertEquals(SCISSORS, RockPaperScissors.solveCondition(PAPER, RockPaperScissors.Winner.YOU));
    }

    @Test void scissorsConditionsTest() {
        assertEquals(PAPER, RockPaperScissors.solveCondition(SCISSORS, RockPaperScissors.Winner.ELF));
        assertEquals(SCISSORS, RockPaperScissors.solveCondition(SCISSORS, RockPaperScissors.Winner.DRAW));
        assertEquals(ROCK, RockPaperScissors.solveCondition(SCISSORS, RockPaperScissors.Winner.YOU));
    }

    @Test
    public void runTournamentTest() throws IOException {
        Tournament tournament = new Tournament("src/main/resources/day_02_input.txt", true);
        int score = tournament.runTournament();
        System.out.println("Convert as Choice Score is: " + score);
        assertEquals(15632, score);
    }

    @Test
    public void runTournamentWithWinConditionTest() throws IOException {
        Tournament tournament = new Tournament("src/main/resources/day_02_input.txt", false);
        int score = tournament.runTournament();
        System.out.println("Convert as Result Score is: " + score);
        assertEquals(14416, score);
    }
}
