public enum Color {
    WHITE, BLACK, GREEN, RED, BLUE, YELLOW, EMPTY;
    public static Color valueOfWithNulls(String v) {
        try {
            return Color.valueOf(v);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
