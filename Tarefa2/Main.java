import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        BinarySearchTree bst = new BinarySearchTree(1000);
        OrderedBinarySearchTree obst = new OrderedBinarySearchTree(1000);
        OrderedBinarySearchTree avlbst = new OrderedBinarySearchTree(1000);

        while (scanner.hasNext()) {
            String comando = scanner.next();
            String valor = scanner.next();

            if (comando.equals("i")) {
                bst.insert(valor);
                obst.insert(valor);
                avlbst.insert(valor);
            } else if (comando.equals("d")) {
                bst.remove(valor);
                obst.remove(valor);
                avlbst.remove(valor);
            } else {
                System.out.println("Comando inválido.");
            }
        }

        System.out.println(bst);
        System.out.println("");
        System.out.println(obst);
        System.out.println("");
        System.out.println(avlbst);
        System.out.println("");
    }
}
