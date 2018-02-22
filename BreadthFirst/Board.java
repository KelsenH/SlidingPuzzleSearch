/*
* Class to represent the current state of the board
 */

class Board {
    public Board parent; //Parent board that derived this board
    public int  [][] spaces = new int [3][3]; //Represents the current placement of the tiles
    public int empty_x; //X position of the empty space
    public int empty_y; //Y postition of the empty space
    int level = 0;
    int misplaced;
    int f;

    //Constructor
    Board (Board parent_, int spaces_ [][], int empty_x_, int empty_y_) {
        this.parent = parent_;
        this.spaces = spaces_;
        this.empty_x = empty_x_;
        this.empty_y = empty_y_;
        if (parent != null && parent.level != 0) {
            this.level = parent.level ++;
        }
        else {
            level = 0;
        }
    }

    //Sets the specified spot on the board to be the specified value
    void set_space (int x, int y, int n) {
        spaces [x][y] = n;
    }

    //Return the current board setup
    int [][] get_spaces () {
        return spaces;
    }

    void print_board () {
        System.out.println (spaces [0][0] +  " " + spaces [0][1] + " " + spaces [0][2]);
        System.out.println (spaces [1][0] +  " " + spaces [1][1] + " " + spaces [1][2]);
        System.out.println (spaces [2][0] +  " " + spaces [2][1] + " " + spaces [2][2]);
        System.out.println ();
    }

    boolean is_solution () {
        if (spaces [0][0] == 1 && spaces [0][1] == 2 && spaces [0][2] == 3 &&
                spaces [1][0] == 4 && spaces [1][1] == 5 && spaces [1][2] == 6 &&
                spaces [2][0] == 7 && spaces [2][1] == 8 && spaces [2][2] == 0) {
            return true;
        }
        else { return false; }
    }

    boolean equals (Board test_board) {
        for (int i = 0; i < 3; i ++) {
            for (int j = 0; j < 3; j ++) {
                if (!(this.spaces [i][j] == test_board.spaces [i][j])) {
                    return false;
                }
            }
        }
        return true;
    }

    int misplaced_amt () {
        int [] solution = new int [] {1,2,3,4,5,6,7,8,9,0};
        int help = 0;
        int misplaced = 0;
        for (int i =0; i < 3; i ++) {
            for (int j = 0; j < 3; j ++) {
                if (this.spaces [i][j] != solution [i + j + help]) {
                    misplaced ++;
                }
            }
            help ++;
        }
        return misplaced;
    }

}