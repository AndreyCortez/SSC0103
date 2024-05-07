import java.util.*;

public class BinarySearchTree {
    protected String[] heap;
    protected int size;
    protected int capacity;
    protected int heigth;

    public BinarySearchTree(int capacity) {
        this.heap = new String[capacity];
        this.size = 0;
        this.capacity = capacity;
    }

    public boolean insert(String val) {
        if (size == capacity) {
            System.out.println("Heap is full. Cannot insert more elements.");
            return false;
        }
        int ind = 0;
        boolean inserted = insert_aux(ind, val);

        while (ind <= capacity && !inserted) 
        {
            int comp = val.compareTo(heap[ind]);

            if (comp > 0)
                ind = (ind + 1) * 2;
            else if (comp < 0)
                ind = (ind * 2) + 1;
            else
                return false;
            
            inserted = insert_aux(ind, val);
        }
        

        if (inserted)
        {
            size += 1;
            return true;
        }

        return false;
    }

    private boolean insert_aux(int index, String val)
    {
        if (heap[index] == null)
        {
            heap[index] = val;
            return true;
        }
        return false;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public boolean remove(String val) {
        int index = findIndex(val);
        if (index == -1) {
            return false;
        }
        
        int aux_ind = index;
        while (heap[aux_ind] != null)
        {
            heap[aux_ind] = heap[leftDown(aux_ind)];
            aux_ind = leftDown(aux_ind);
        }

        size -= 1;
        return true;
    }

    protected int downMin(int initial_index)
    {
        int min_ind = leftDown(initial_index);
        while (heap[min_ind] != null)
        {
            min_ind = leftDown(initial_index);
        }
        return up(min_ind);
    }

    protected int downMax(int initial_index)
    {
        int min_ind = rigthDown(initial_index);
        while (heap[min_ind] != null)
        {
            min_ind = rigthDown(initial_index);
        }
        return up(min_ind);
    }

    protected int rigthDown(int index)
    {
        return index * 2 + 2;
    }

    protected int leftDown(int index)
    {
        return index * 2 + 1;
    }

    protected int up(int index)
    {
        if (index == 0)
            return -1;
        if (index%2 == 0)
            return index/2 - 1;
        return (index - 1)/2;
    }

    private int findIndex(String val) {
        for (int i = 0; i < size; i++) {
            if (heap[i] == null)
                continue;
            if (heap[i].equals(val)) 
                return i;
        }
        return -1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph {\n");

        if (size == 1 && heap[0] != null)
        {
            sb.append("\"" + 0 + " " + heap[0] + "\" ");
            sb.append("}");
            return sb.toString();
        }

        for (int i = 0; i < capacity; i++) {
            int leftChildIndex = 2 * i + 1;
            int rightChildIndex = 2 * i + 2;

            if (leftChildIndex >= capacity || rightChildIndex >= capacity)
            {
                break;
            }

            if (heap[leftChildIndex] != null) {
                sb.append("\"" + i + " " + heap[i] + "\" -> \"" + leftChildIndex + " " + heap[leftChildIndex] + "\"\n");
            }
            if (heap[rightChildIndex] != null) {
                sb.append("\"" + i + " " + heap[i] + "\" -> \"" + rightChildIndex + " " + heap[rightChildIndex] + "\"\n");
            }
        }

        sb.append("}");
        return sb.toString();
    }
}
