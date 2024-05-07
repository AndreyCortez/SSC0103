import java.util.*;


public class OrderedBinarySearchTree extends BinarySearchTree {
    public OrderedBinarySearchTree(int capacity) {
        super(capacity);
    }

    @Override
    public void insert(String val) {
        super.insert(val);
        Arrays.sort(items, 0, size);
        heap = Arrays.copyOf(items, capacity);
    }

    @Override
    public boolean remove(String val) {
        boolean result = super.remove(val);
        Arrays.sort(items, 0, size);
        heap = Arrays.copyOf(items, capacity);
        return result;
    }
}
