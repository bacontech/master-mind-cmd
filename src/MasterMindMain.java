import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class MasterMindMain {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Master Mind - One - Command Line");

        Scanner ins = new Scanner(System.in);
        System.out.println("What is your name?");

        String name = ins.nextLine();

        System.out.println("Hello " + name);
        System.out.println("Let's Start a new Game!");
        Game game = new Game();
        System.out.print("I will pick the secret code now");
        Thread.sleep(300);
        System.out.print("\rI will pick the secret code now.");
        Thread.sleep(300);
        System.out.print("\rI will pick the secret code now..");
        Thread.sleep(300);
        System.out.println("\rI will pick the secret code now...");
        Thread.sleep(300);

        int minNum = 0;
        int maxNum = 5;
        for(int i =0; i<4; i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(minNum, maxNum + 1);
            Color picked = Color.values()[randomNum];
            game.setNextSecretCode(picked);
        }
        System.out.println("Okay, I am ready - let's play");

        boolean isWinner = false;

        do {
            System.out.println("\n");
            if (game.getGuesses().size() > 0) {
                printHistory(game);
            }
            System.out.println("\n");
            System.out.println(name + ", what is your guess?");
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
                Guess thisGuess = game.submitGuess(colorGuesses);

                String result = thisGuess.getGrade().stream().map(Enum::name).collect(Collectors.joining(", "));
                System.out.println("Your result: " + result);

                if (thisGuess.isWinner()) {
                    isWinner = true;
                }
            }
        } while (!isWinner && game.getGuesses().size() < 11);

        if (isWinner) {
            System.out.println("Great Job! The secret was found");
        } else {
            System.out.print("Game Over!");
        }
        String theCode = game.getSecretCode().stream().map(Enum::name).collect(Collectors.joining(", "));
        System.out.println("The code was: " + theCode);

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
        Color matchedEnum = Color.valueOfWithNulls(code);
        if (matchedEnum != null) {
            return matchedEnum;
        }


        String cap = code.toUpperCase(Locale.ROOT);
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
}
