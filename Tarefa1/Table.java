import java.util.Arrays;
import java.util.Scanner;

class Board {
    private int[] board;
    private int size;

    public Board(int[] initialConfig) {
        size = (int) Math.sqrt(initialConfig.length);
        board = Arrays.copyOf(initialConfig, initialConfig.length);
    }

    public void move(char direction) {
        int zeroIndex = findZeroIndex();
        int row = zeroIndex / size;
        int col = zeroIndex % size;

        switch (direction) {
            case 'd':
                if (row > 0) {
                    swap(zeroIndex, zeroIndex - size);
                }
                break;
            case 'u':
                if (row < size - 1) {
                    swap(zeroIndex, zeroIndex + size);
                }
                break;
            case 'r':
                if (col > 0) {
                    swap(zeroIndex, zeroIndex - 1);
                }
                break;
            case 'l':
                if (col < size - 1) {
                    swap(zeroIndex, zeroIndex + 1);
                }
                break;
        }
    }

    private int findZeroIndex() {
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0) {
                return i;
            }
        }
        return -1;
    }

    private void swap(int i, int j) {
        int temp = board[i];
        board[i] = board[j];
        board[j] = temp;
    }

    public boolean isSolved() {
        for (int i = 0; i < board.length - 1; i++) {
            if (board[i] != i) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++)
        {
            sb.append("+------");
        }
        
        sb.append("+\n");
        for (int i = 0; i < board.length; i++) {
            sb.append("|");
            
            for (int j = 0; j < 3 - (int)(board[i]/10); j++)
	    {
		sb.append(" ");
	    }
	    
            sb.append(board[i]);
            sb.append("  ");
            if ((i + 1) % size == 0) {
                sb.append("|\n");
                for (int j = 0; j < size; j++)
        	{
            	    sb.append("+------");
        	}
        	sb.append("+\n");
            }
        }
        return sb.toString();
    }
}
