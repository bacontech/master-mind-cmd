import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Game {
    private int codeSize;
    private String playerName;
    private Scanner ins;

    public Game(Scanner ins, String playerName) {
        this.ins = ins;
        this.playerName = playerName;
        this.codeSize = 4;
    }

    private List<Color> secretCode = new ArrayList<>();

    private List<Guess> guesses = new ArrayList<>();

    public void start() throws InterruptedException {
        this.secretCode = generateSecretCode(this.codeSize);
        System.out.println("Let's Start a new Game!");
        System.out.print("I will pick the secret code now");
        Thread.sleep(300);
        System.out.print("\rI will pick the secret code now.");
        Thread.sleep(300);
        System.out.print("\rI will pick the secret code now..");
        Thread.sleep(300);
        System.out.println("\rI will pick the secret code now...");
        Thread.sleep(300);
        System.out.println("Okay, I am ready - let's play");

        guessLoop();
    }

    void guessLoop() {
        boolean isWinner = false;

        do {
            System.out.println("\n");
            if (this.getGuesses().size() > 0) {
                printHistory(this);
            }
            System.out.println("\n");
            System.out.println(this.playerName+ ", what is your guess?");
            System.out.println("Pick 4 colors with a space between.");
            System.out.println("White (W), Black (BK), Green (G), Red (R), Blue (BE), Yellow (Y)");
            String rawGuess = ins.nextLine();
            List<Color> colorGuesses = decodeGuess(rawGuess);
            if (colorGuesses.size() != 4) {
                System.out.println("I did not understand your guess, please guess again.");
                String understood = colorGuesses.stream().map(Enum::name).collect(Collectors.joining(", "));
                System.out.println("Understood: " + understood);
            } else {
                String understood = colorGuesses.stream().map(Enum::name).collect(Collectors.joining(", "));
                System.out.println("Guess: " + understood);
                Guess thisGuess = this.submitGuess(colorGuesses);

                String result = thisGuess.getGrade().stream().map(Enum::name).collect(Collectors.joining(", "));
                System.out.println("Your result: " + result);

                if (thisGuess.isWinner()) {
                    isWinner = true;
                }
            }
        } while (!isWinner && this.getGuesses().size() < 11);

        if (isWinner) {
            System.out.println("Great Job! The secret was found");
        } else {
            System.out.print("Game Over!");
        }
        String theCode = this.getSecretCode().stream().map(Enum::name).collect(Collectors.joining(", "));
        System.out.println("The code was: " + theCode);
    }

    public void setNextSecretCode(Color picked) {
        secretCode.add(picked);
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

    List<Color> generateSecretCode(int codeSize) {
        int minNum = 0;
        int maxNum = 5;

        List<Color> code = new ArrayList<>();
        for(int i =0; i< codeSize; i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(minNum, maxNum + 1);
            Color picked = Color.values()[randomNum];
            code.add(picked);
        }
        return code;
    }

    private static void printHistory(Game game) {
        System.out.println("History:");
        game.getGuesses().forEach(g -> {
            String print = g.guessString() + " - " + g.gradeString();
            System.out.println(print);
        });


    }

    static List<Color> decodeGuess(String rawGuess) {
        List<Color> decodedList = new ArrayList<>();
        if (rawGuess == null || rawGuess.isBlank()) {
            return decodedList;
        }
        String[] guessSplit = rawGuess.split(" ");
        for (String g : guessSplit) {
            Color decoded = decodeString(g);
            if (decoded != null) {
                decodedList.add(decoded);
            }
        }
        return decodedList;
    }

    static Color decodeString(String code) {
        String cap = code.toUpperCase(Locale.ROOT);
        Color matchedEnum = Color.valueOfWithNulls(cap);
        if (matchedEnum != null) {
            return matchedEnum;
        }


        if ("W".equals(cap)) {
            return Color.WHITE;
        } else if ("BK".equals(cap)) {
            return Color.BLACK;
        } else if ("G".equals(cap)) {
            return Color.GREEN;
        } else if ("R".equals(cap)) {
            return Color.RED;
        } else if ("BE".equals(cap)) {
            return Color.BLUE;
        } else if ("Y".equals(cap)) {
            return Color.YELLOW;
        }
        return null;
    }


    public List<Color> getSecretCode() {
        return this.secretCode;
    }
    public List<Guess> getGuesses() {
        return this.guesses;
    }
}
