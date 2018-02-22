import java.util.*;
import java.util.Random;

/**
 * AStar class to find the solution to three randomly generated 8-tile boards
 */
class AStar {
    Board [] initial_boards; //Array of boards to be solved
    Random randnum;

    AStar () {
        initial_boards = new Board []{create_board (), create_board(), create_board()};
    }

    //Uses random number generation to create 3 solvable boards
    Board create_board () {

        int [] solution = {1,2,3,4,5,6,7,8,0}; //Array to be shuffled
        shuffle (solution);

        //Create new combos until solvable
        while (!is_solvable(solution)) {
            shuffle(solution);
        }
        int [][] spaces = new int [3][3];
        int help = 0; //Used to help place into the matrix correctly 
        int x = 0;
        int y = 0;
        //Place the shuffled array into a matrix of spaces
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

        //Create and return the board
        Board board = new Board (null, spaces, x, y);
        return board;
    }


    //Randomly shuffles an array
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

    //Checks if it is solvable by whether or not the amt of inversions is even
    private boolean is_solvable (int arr []) {
        if (getInvCount(arr) % 2 == 0) {
            return true;
        }

        else {
            return false;
        }
    }

    //Checks how many inversions exist
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

    //Creates new controllers for each created board, solves them, outputs the found solution
    void solve_all () {
        AStarController controller = new AStarController (initial_boards [0]);
        controller.solve ();
        controller.output_solution(); 

        AStarController controller_two = new AStarController (initial_boards [1]);
        controller_two.solve ();
        controller_two.output_solution();

        AStarController controller_three = new AStarController (initial_boards [2]);
        controller_three.solve ();
        controller_three.output_solution(); 
    }



    public static void main (String args []) {
        AStar test = new AStar ();
        test.solve_all();
    }
}