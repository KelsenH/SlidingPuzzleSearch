import java.util.*;
import java.util.Random;

class BreadthFirst {
    Board [] initial_boards;

    BreadthFirst () {
        initial_boards = new Board []{create_board (), create_board(), create_board()};
    }

    //Uses random number generation to create 3 solvable boards
    Board create_board () {

        int [] solution = {1,2,3,4,5,6,7,8,0};
        shuffle (solution);
        while (!is_solvable(solution)) {
            shuffle(solution);
        }
        int [][] spaces = new int [3][3];
        int help = 0;
        int x = 0;
        int y = 0;
        for (int i = 0; i < 3; i ++) {
            for (int j = 0; j < 3; j ++) {
                spaces [i][j] = solution [i + j + help];
                if (solution [i + j + help] == 0) {
                    x = i;
                    y = j;
                }
            }
            help += 2;
        }

        Board board = new Board (null, spaces, x, y);
        return board;
    }


    private void shuffle(int[] array) {
        int n = array.length;
        //Loop over array
        for (int i = 0; i < array.length; i++) {
            //Get a random value 
            randnum = new Random();
            int randomValue = i + randnum.nextInt(n - i);
            //Swap the random element with the present element
            int randomElement = array[randomValue];
            array[randomValue] = array[i];
            array[i] = randomElement;
        }
    }

    private boolean is_solvable (int arr []) {
        if (getInvCount(arr) % 2 == 0) {
            return true;
        }

        else {
            return false;
        }
    }

    int getInvCount(int arr[])
    {
        int inversions = 0;
        for(int i = 0; i < 8; i++){
            for(int j = i+1; j < 9; j++){
                if (arr [i] == 0 || arr [j] == 0) {
                    continue;
                }
                if(arr [j] > arr [i]) {
                    inversions++;
                }
            }
        }
        return inversions;
    }

    void solve_all () {
        Controller controller = new Controller (initial_boards [0]);
        controller.solve ();
        controller.output_solution();

        Controller controller_two = new Controller (initial_boards [1]);
        controller_two.solve ();
        controller_two.output_solution();

        Controller controller_three = new Controller (initial_boards [2]);
        controller_three.solve ();
        controller_three.output_solution();
    }



    public static void main (String args []) {
        BreadthFirst test = new BreadthFirst();
        test.solve_all();
    }
}