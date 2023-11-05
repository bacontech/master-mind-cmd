import java.util.List;
import java.util.stream.Collectors;

public class Guess {
    private List<Color> guess;
    private List<GuessGrade> grade;

    public List<Color> getGuess() {
        return guess;
    }

    public void setGuess(List<Color> guess) {
        this.guess = guess;
    }

    public List<GuessGrade> getGrade() {
        return grade;
    }

    public String guessString() {
        return this.guess.stream().map(Color::name).collect(Collectors.joining(", "));
    }

    public String gradeString() {
        return this.grade.stream().map(GuessGrade::name).collect(Collectors.joining(", "));
    }

    public boolean isWinner() {
        return this.grade.size() == 4 && this.grade.stream().allMatch(g -> g == GuessGrade.RED);
    }

    public void setGrade(List<GuessGrade> grade) {
        this.grade = grade;
    }
}
