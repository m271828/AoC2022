package day02;

public class RockPaperScissors {
    public enum Choice {
        ROCK,
        PAPER,
        SCISSORS
    }

    public enum Winner {
        ELF,
        YOU,
        DRAW
    }

    public static Choice convertChoice(String str) {
        if (str.equalsIgnoreCase("a") || str.equalsIgnoreCase("x")) {
            return Choice.ROCK;
        } else if (str.equalsIgnoreCase("b") || str.equalsIgnoreCase("y")) {
            return Choice.PAPER;
        } else if (str.equalsIgnoreCase("c") || str.equalsIgnoreCase("z")) {
            return Choice.SCISSORS;
        } else {
            throw new RuntimeException("Invalid Rock, Paper, Scissors choice: " + str);
        }
    }

    public static Winner convertResult(String str) {
        if (str.equalsIgnoreCase("x")) {
            return Winner.ELF;
        } else if (str.equalsIgnoreCase("y")) {
            return Winner.DRAW;
        } else if (str.equalsIgnoreCase("z")) {
            return Winner.YOU;
        } else {
            throw new RuntimeException("Invalid Rock, Paper, Scissors choice: " + str);
        }
    }

    public static Choice solveCondition(Choice elf, Winner result) {
        if (result == Winner.DRAW) {
            return elf;
        } else if (elf == Choice.ROCK) {
            if (result == Winner.ELF) {
                return Choice.SCISSORS;
            } else {
                return Choice.PAPER;
            }
        } else if (elf == Choice.PAPER) {
            if (result == Winner.ELF) {
                return Choice.ROCK;
            } else {
                return Choice.SCISSORS;
            }
        } else { // elf == Choice.SCISSORS
            if (result == Winner.ELF) {
                return Choice.PAPER;
            } else {
                return Choice.ROCK;
            }
        }
    }

    public static Winner game(Choice playerOne, Choice playerTwo) {
        if (playerOne == playerTwo) {
            return Winner.DRAW;
        } else if (playerOne == Choice.ROCK) {
            if (playerTwo == Choice.PAPER) {
                return Winner.YOU;
            } else {
                return Winner.ELF;
            }
        } else if (playerOne == Choice.PAPER) {
            if (playerTwo == Choice.ROCK) {
                return Winner.ELF;
            } else {
                return Winner.YOU;
            }
        } else { // playerOne == Choice.SCISSORS
            if (playerTwo == Choice.ROCK) {
                return Winner.YOU;
            } else {
                return Winner.ELF;
            }
        }
    }

    public static int score(Choice object, Winner outcome) {
        int val = 0;

        if (object == Choice.ROCK) {
            val += 1;
        } else if (object == Choice.PAPER) {
            val += 2;
        } else if (object == Choice.SCISSORS) {
            val +=3;
        }

        if (outcome == Winner.ELF) {
            val += 0;
        } else if (outcome == Winner.DRAW) {
            val += 3;
        } else if (outcome == Winner.YOU) {
            val += 6;
        }

        return val;
    }
}
