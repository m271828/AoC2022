package day02;

import utility.Associations;
import utility.FileUtil;

import java.io.IOException;
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
        var lines = FileUtil.splitLinesBy(dataFile, " ");
        for (Associations.Pair<String> pair : lines) {
            Pair round = new Pair(pair.first(), pair.second(), convertAsChoice);
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
