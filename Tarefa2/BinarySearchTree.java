public class BinarySearchTree {
    protected String[] heap;
    protected String[] items;
    protected int size;
    protected int capacity;

    public BinarySearchTree(int capacity) {
        this.heap = new String[capacity];
        this.items = new String[capacity];
        this.size = 0;
        this.capacity = capacity;
    }

    public void insert(String val) {
        if (size == capacity) {
            System.out.println("Heap is full. Cannot insert more elements.");
            return;
        }
        int ind = 0;
        boolean inserted = insert_aux(ind, val);

        while (ind <= capacity && !inserted) 
        {
            int comp = val.compareTo(heap[ind]);

            if (comp > 0)
                ind = (ind + 1) * 2;
            else
                ind = (ind * 2) + 1;
            
            inserted = insert_aux(ind, val);
        }

        if (inserted)
        {
            size += 1;
        }
    }

    private boolean insert_aux(int index, String val)
    {
        if (heap[index] == null)
        {
            heap[index] = val;
            items[size] = val;
            return true;
        }
        return false;
    }

    public String extractMax() {
        if (size == 0) {
            return null;
        }
        String max = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapifyDown(0);
        return max;
    }

    private void heapifyDown(int index) {
        int left = 2 * index + 1;
        int right = 2 * index + 2;
        int largest = index;

        if (left < size && heap[left].compareTo(heap[largest]) > 0) {
            largest = left;
        }

        if (right < size && heap[right].compareTo(heap[largest]) > 0) {
            largest = right;
        }

        if (largest != index) {
            String temp = heap[index];
            heap[index] = heap[largest];
            heap[largest] = temp;
            heapifyDown(largest);
        }
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
        heap[index] = heap[size - 1];
        size--;
        heapifyDown(index);
        return true;
    }

    private int findIndex(String val) {
        for (int i = 0; i < size; i++) {
            if (heap[i].equals(val)) {
                return i;
            }
        }
        return -1;
    }

    public boolean removeByValue(String val) {
        for (int i = 0; i < size; i++) {
            if (heap[i].equals(val)) {
                heap[i] = heap[size - 1];
                size--;
                heapifyDown(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph {\n");

        if (size == 1)
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
