import java.util.Scanner;

public class MasterMindMain {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Master Mind - One - Command Line");

        Scanner ins = new Scanner(System.in);
        System.out.println("What is your name?");

        String name = ins.nextLine();
        System.out.println("Hello " + name);
        Game game = new Game(ins, name);
        game.start();

    }






}
