import java.util.*;

class Controller {
    ArrayList <Board> open_list = new ArrayList <Board> (); //List of nodes to visit
    ArrayList <Board> closed_list = new ArrayList <Board> (); //List of unvisited nodes
    Board current_board; //Current board that we are evaluating
    Board solution;

    Controller (Board current_board_) {
        //Set current board
        this.current_board = current_board_;
        //Set open_list to be current board
        open_list.add (current_board);
        solution = null; //Place for controller solution
    }

    /*
    * Checks for all possible tile moves into the empty space
    * Creates a new board made from that new configuration
    * If that configuration is not in the closed list, it will be added to the open list
    * to be evaluated
     */
    void get_moves () {
        //Get current_board's spaces
        int [][] spaces = current_board.get_spaces ();

        //Get current board's x and y open coordinates
        int x = current_board.empty_x;
        int y = current_board.empty_y;

        open_list.remove (current_board); //Remove the current board from the open list

        //Note: The x and y notation is confusing here, and I should not have used those names
        //X refers to row, Y refers to column. The opposite of how it should be.

        //Create board moving above tile into opening
        int above_x = x - 1;
        //If this place is on the board
        if (check_bounds (above_x)) {
            //Make new board spaces with the tile moved into the empty place
            int [][] board_spaces = swap (above_x, y, x, y);
            //Create new board with above piece slid down
            Board above = new Board (current_board, board_spaces, above_x, y);
            //Check for solution
            if (above.is_solution ()) {
                solution = above;
            }
            //Add new board configuration to open_list if it is not already in the closed list
            if (!in_closed (above) && !in_open(above)) {
                open_list.add (above);
            }

        }

        //Board with below tile in the opening
        int below_x = x + 1;
        if (check_bounds (below_x)) {
            int [][] board_spaces = swap (below_x, y, x, y);
            //Create new board with below piece slid up
            Board below = new Board (current_board, board_spaces, below_x, y);
            if (below.is_solution ()) {
                solution = below;
            }
            if (!in_closed (below) && !in_open (below)) {
                open_list.add (below);
            }
        }

        //Board with left tile in the opening
        int left_y = y - 1;
        if (check_bounds (left_y)) {
            int [][] board_spaces = swap (x, left_y, x, y);
            //Create new board with left piece slid over
            Board left = new Board (current_board, board_spaces, x, left_y);
            if (left.is_solution ()) {
                solution = left;
            }
            if (!in_closed (left) && !in_open (left)) {
                open_list.add (left);
            }
        }

        //Board with right piece in the opening
        int right_y = y + 1;
        if (check_bounds (right_y)) {
            int [][] board_spaces = swap (x, right_y, x, y);
            //Create new board with right piece slid over
            Board right = new Board (current_board, board_spaces, x, right_y);
            if (right.is_solution ()) {
                solution = right;
            }
            if (!in_closed (right) && !in_open (right)) {
                open_list.add (right);
            }
        }

        closed_list.add (current_board); //Add the current node to closed list
        if (!open_list.isEmpty()) {
            current_board = open_list.get (0); //Grab the next node from the open list to be evaluated
        }

        else {
            System.out.println ("No solution found");
            solution = current_board;
        }
    }

    //Make sure we are not checking anything out of the bounds
    boolean check_bounds (int i) {
        if (i < 0 || i > 2) {
            return false;
        }
        return true;
    }

    //Check to see if board already exist in open list
    boolean in_closed (Board test) {
        for (int i = 0; i < this.closed_list.size (); i ++) {
            if (test.equals (this.closed_list.get (i))) {
                return true;
            }
        }
        return false;
    }

    //Check to see if board already exist in open list
    boolean in_open (Board test) {
        for (int i = 0; i < this.open_list.size (); i ++) {
            if (test.equals (this.open_list.get (i))) {
                return true;
            }
        }
        return false;
    }



    //Change the open space to new_x and new_y, and set the old_x and old_y to
    //the old value
    int [][] swap (int new_x, int new_y, int old_x, int old_y) {
        int [][] spaces = current_board.get_spaces ();
        int [][] new_spaces = new int [3][3];

        for (int i = 0; i < 3; i ++) {
            for (int j = 0; j < 3; j ++) {
                new_spaces [i][j] = spaces [i][j];
            }
        }

        //Swap the values
        new_spaces [new_x] [new_y] = 0;
        new_spaces [old_x] [old_y] = spaces [new_x] [new_y];
        return new_spaces;
    }



    //Follow up the parent chain of the solution
    //Put them on a stack to reverse them
    //Print them as you pop them off to see in-order solution
    void output_solution () {
        if (this.solution != null) {
            Board spot = this.solution;
            Stack <Board> stack = new Stack <Board> ();
            int count = 0;

            while (spot.parent != null) {
                stack.push (spot);
                spot = spot.parent;
                count ++;
            }
            stack.push (spot);

            while (!stack.empty ()) {
                Board current = (Board) stack.pop ();
                current.print_board ();
            }
        }
    }

    void solve () {
        int checks = 0;
        while (this.solution == null) {
            this.get_moves ();
            checks ++;
            //System.out.println ("Open: " + open_list.size ());
            //System.out.println ("Closed: " + closed_list.size ());
        }
        System.out.println ("Nodes checked: " + checks);
    }
}