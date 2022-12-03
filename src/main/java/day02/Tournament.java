package day02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Tournament {
    private class Pair {
        public RockPaperScissors.Choice elf;
        public RockPaperScissors.Choice you;
        public Pair(String elf, String you, boolean convertAsChoice) {
            this.elf = RockPaperScissors.convertChoice(elf);
            if (convertAsChoice) {
                this.you = RockPaperScissors.convertChoice(you);
            } else {
                this.you = RockPaperScissors.solveCondition(this.elf, RockPaperScissors.convertResult(you));
            }
        }
    }
    private ArrayList<Pair> rounds;

    public Tournament(String dataFile, boolean convertAsChoice) throws IOException {
        rounds = new ArrayList<>();
        var lines = Files.readAllLines(Path.of(dataFile));
        for (String line : lines) {
            var choices = line.split(" ");
            Pair round = new Pair(choices[0], choices[1], convertAsChoice);
            rounds.add(round);
        }
    }

    public int runTournament() {
        int score = 0;
        for(Pair p : rounds) {
            RockPaperScissors.Winner winner = RockPaperScissors.game(p.elf, p.you);
            score += RockPaperScissors.score(p.you, winner);
        }
        return score;
    }
}
