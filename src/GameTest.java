import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    @ParameterizedTest
    @CsvSource({
        "W, WHITE",
        "WHITE, WHITE",
        "yellow, YELLOW",
        "y, YELLOW",
        "Trash,",
    })
    void testDecodeString(String input, Color expected) {
        Color result = Game.decodeString(input);
        assertEquals(expected, result);
    }

    @Nested
    class DetermineGrade {
        @Test
        void allRight() {
            List<Color> secretCode = List.of(Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE);
            List<Color> colorGuesses = List.of(Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE);

            List<GuessGrade> guessGrades = Game.determineGrade(secretCode, colorGuesses);
            assertNotNull(guessGrades);
            assertEquals(4, guessGrades.size());
            List<GuessGrade> reds = guessGrades.stream().filter(GuessGrade.RED::equals).toList();
            List<GuessGrade> whites = guessGrades.stream().filter(GuessGrade.WHITE::equals).toList();
            assertEquals(4, reds.size());
            assertEquals(0, whites.size());
        }
        @Test
        void allRight_two() {
            List<Color> secretCode = List.of(Color.BLUE, Color.WHITE, Color.BLACK, Color.BLUE);
            List<Color> colorGuesses = List.of(Color.BLUE, Color.WHITE, Color.BLACK, Color.BLUE);

            List<GuessGrade> guessGrades = Game.determineGrade(secretCode, colorGuesses);
            assertNotNull(guessGrades);
            assertEquals(4, guessGrades.size());
            List<GuessGrade> reds = guessGrades.stream().filter(GuessGrade.RED::equals).toList();
            List<GuessGrade> whites = guessGrades.stream().filter(GuessGrade.WHITE::equals).toList();
            assertEquals(4, reds.size());
            assertEquals(0, whites.size());
        }
        @Test
        void none_right() {
            List<Color> secretCode = List.of(Color.RED, Color.RED, Color.RED, Color.RED);
            List<Color> colorGuesses = List.of(Color.BLUE, Color.WHITE, Color.BLACK, Color.BLUE);

            List<GuessGrade> guessGrades = Game.determineGrade(secretCode, colorGuesses);
            assertNotNull(guessGrades);
            assertEquals(0, guessGrades.size());
            List<GuessGrade> reds = guessGrades.stream().filter(GuessGrade.RED::equals).toList();
            List<GuessGrade> whites = guessGrades.stream().filter(GuessGrade.WHITE::equals).toList();
            assertEquals(0, reds.size());
            assertEquals(0, whites.size());
        }
        @Test
        void three_whites() {
            List<Color> secretCode = List.of(Color.WHITE, Color.BLUE, Color.BLACK, Color.RED);
            List<Color> colorGuesses = List.of(Color.BLUE, Color.WHITE, Color.BLUE, Color.BLACK);

            List<GuessGrade> guessGrades = Game.determineGrade(secretCode, colorGuesses);
            assertNotNull(guessGrades);
            assertEquals(3, guessGrades.size());
            List<GuessGrade> reds = guessGrades.stream().filter(GuessGrade.RED::equals).toList();
            List<GuessGrade> whites = guessGrades.stream().filter(GuessGrade.WHITE::equals).toList();
            assertEquals(0, reds.size());
            assertEquals(3, whites.size());
        }
    }
}