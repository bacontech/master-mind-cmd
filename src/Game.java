import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {
    private int codeSize = 4;
    private List<Color> secretCode = new ArrayList<>();

    private List<Guess> guesses = new ArrayList<>();

    public void setNextSecretCode(Color picked) {
        secretCode.add(picked);
    }

    public List<Color> getSecretCode() {
        return this.secretCode;
    }
    public List<Guess> getGuesses() {
        return this.guesses;
    }

    public Guess submitGuess(List<Color> colorGuesses) {
        Guess guess = new Guess();
        guess.setGuess(colorGuesses);

        List<GuessGrade> grade = determineGrade(this.secretCode, colorGuesses);

        guess.setGrade(grade);

        this.guesses.add(guess);
        return guess;
    }

    static List<GuessGrade> determineGrade(List<Color> secretCode, List<Color> colorGuesses) {
        List<GuessGrade> grades = new ArrayList<>();

        List<Color> copyOfSecret = new ArrayList<>();
        copyOfSecret.addAll(secretCode);
        List<Color> copyOfGuess = new ArrayList<>();
        copyOfGuess.addAll(colorGuesses);

        for (int i = 0; i < secretCode.size(); i++) {
            Color secret = secretCode.get(i);
            Color guess = colorGuesses.get(i);
            if (secret == guess) {
                grades.add(GuessGrade.RED);
                //
                copyOfSecret.remove(secret);
                copyOfGuess.remove(secret);
            }
        }

        copyOfSecret.forEach(s -> {
            if (copyOfGuess.contains(s)) {
                grades.add(GuessGrade.WHITE);
                copyOfGuess.remove(s);
            }
        });

        return grades;
    }
}
