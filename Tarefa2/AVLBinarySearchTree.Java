import java.util.*;


public class AVLBinarySearchTree extends BinarySearchTree {
    public AVLBinarySearchTree(int capacity) {
        super(capacity);
    }

    @Override
    public boolean insert(String val) {
        
        if (result)
            sort_heap();
        
        return result;
    }

    @Override
    public boolean remove(String val) {
        boolean result = super.remove(val);
        if (result)
            sort_heap();
        return result;
    }

    private void sort_heap()
    {
        Arrays.sort(heap, Comparator.nullsLast(Comparator.naturalOrder()));
        String aux[] = new String[capacity];

        int cnt = 2;
        int disp = 0;
        int mean = decide_mean(size);

        while (cnt <= size + 2)
        {
            aux[cnt - 2] = heap[mean + disp];
            
            if (cnt % 2 == 0)
                disp = cnt / 2 * -1;
            else 
                disp = (cnt - 1) / 2 ;

            cnt += 1;
        }

        System.out.println(Arrays.toString(aux));
        System.arraycopy(aux, 0, heap, 0, aux.length);
    }

    private int decide_mean(int size)
    {
        if (size%2 == 0)
            return (size / 2);

        return (size + 1) / 2;
    }
}
