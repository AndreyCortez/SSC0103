import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[] initialConfig = Arrays.stream(scanner.nextLine().split("\\s+"))
                                     .mapToInt(Integer::parseInt)
                                     .toArray();

        String moves = scanner.nextLine();

        scanner.close();

        Board board = new Board(initialConfig);
        System.out.println(board);

        for (char move : moves.toCharArray()) {
            board.move(move);
            System.out.println(board);
        }

        System.out.println("Posicao final: " + board.isSolved());
    }
}
