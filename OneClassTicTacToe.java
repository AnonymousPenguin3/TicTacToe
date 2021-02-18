package DecemberBreakWork.TicTacToe;

public class OneClassTicTacToe {
    private char[][] board;
    private char currentPlayerMark;
    private char winner;

    public static void main(String[] argv) {
        // ask to run the game in a loop
        String finishGame;
        OneClassTicTacToe game;
        do {
            game = new OneClassTicTacToe();
            game.playTicTacToe();
            game.printBoard();
            System.out.println("Player " + game.winner + " won.");
            System.out.print("Do you want to play again? :");
            finishGame = TextIO.getlnString();
        } while(!finishGame.startsWith("n"));

        System.out.println("You have finished playing DemeberBreakWork.TicTacToe. Thanks for playing.");
    }

    private OneClassTicTacToe() {
        board = new char[3][3];
        currentPlayerMark = 'X'; // First player default is X. Could probably ask for it but I am lazy
        initializeBoard();
    }

    private void playTicTacToe() {
        System.out.println("********Welcome to DemeberBreakWork.TicTacToe.********");
        System.out.println("Here are the rules: ");
        System.out.println("1. The first person will start as player X and the second player O");
        System.out.println("2. The first player who reaches 3 marks in a row will win the game. " +
                "It can be diagonal, horizontal or vertical");
        System.out.println("3. After a spot has been use it cannot be redone until the game is over.");
        int typeGame;
        do {
            System.out.print("Would you like to play (1)2 player or (2)against a computer");
            typeGame = TextIO.getlnInt();
            if (!(typeGame == 1 || typeGame == 2)) {
                System.out.print("Please re-enter a valid input. (1) 2 player or (2) against a computer");
            } else if(typeGame == 1) {
                humanTwoPlayerGame();
            } else {
                computerOpponentGame();
            }
        } while(!checkForWin() || isBoardFull());
    }

    // Set/Reset the board back to all empty values.
    private void initializeBoard() {
        // Loop through rows
        for (int i = 0; i < 3; i++) {
            // Loop through columns
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    private void printBoard() {
        System.out.println("-------------");
        for (int row = 0; row < 3; row++) {
            System.out.print("|");
            for (int col = 0; col < 3; col++) {
                System.out.print(board[row][col] + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }
    // Loop through all cells of the board and if one is found to be empty (contains char '-') then return false.
    // Otherwise the board is full.
    private boolean isBoardFull() {
        boolean isFull = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    isFull = false;
                }
            }
        }
        return isFull;
    }
    // Returns true if there is a win, false otherwise.
    // This calls the other win check functions to check the entire board.
    private boolean checkForWin() {
        return (checkRowsForWin() || checkColumnsForWin() || checkDiagonalsForWin());
    }

    // Loop through rows and see if any are winners by rows.

    private boolean checkRowsForWin() {
        for (int i = 0; i < 3; i++) {
            if (checkRowCol(board[i][0], board[i][1], board[i][2])) {
                winner = board[i][0];
                return true;
            }
        }
        return false;
    }

    // Loop through columns and see if any are winners by columns.
    private boolean checkColumnsForWin() {
        for (int i = 0; i < 3; i++) {
            if (checkRowCol(board[0][i], board[1][i], board[2][i])) {
                winner = board[0][i];
                return true;
            }
        }
        return false;
    }
    // Check the two diagonals to see if either is a win. Return true if either wins.
    private boolean checkDiagonalsForWin() {
        if (checkRowCol(board[0][0], board[1][1], board[2][2])) {
            winner = board[0][0];
            return true;
        }
        if (checkRowCol(board[0][2], board[1][1], board[2][0])) {
            winner = board[0][2];
            return true;
        }
        return false;
    }
    // Check to see if all three values are the same (and not empty) indicating a win.
    private boolean checkRowCol(char c1, char c2, char c3) {
        return ((c1 != '-') && (c1 == c2) && (c2 == c3));
    }
    // Change player marks back and forth.
    private void changePlayer() {
        if (currentPlayerMark == 'X') {
            System.out.println("Player O's turn.");
            currentPlayerMark = 'O';
        } else {
            System.out.println("Player X's turn.");
            currentPlayerMark = 'X';
        }
    }

    // Places a mark at the cell specified by row and col with the mark of the current player.
    private void placeMark(int row, int col) {
        // Make sure that row and column are in bounds of the board.
        if ((row >= 0) && (row < 3)) {
            if ((col >= 0) && (col < 3)) {
                if (board[row][col] == '-') {
                    board[row][col] = currentPlayerMark;
                    return;
                }
            }
            System.out.println("This is not a valid spot. Please choose a valid column between 1 and 3");
        }
        System.out.println("This is not a valid spot. Please choose a valid row between 1 and 3");
    }

    private void humanTurn() {
        int inputRow;
        int inputColumn;
        System.out.println("Here is the current board:");
        printBoard();
        System.out.print("Which row would you like to place your mark? : ");
        inputRow = TextIO.getlnInt();
        System.out.print("Which column would you like to place your mark? : ");
        inputColumn = TextIO.getlnInt();
        placeMark(inputRow - 1, inputColumn - 1);
    }

    private void computerTurn() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == '-') {
                    board[row][col] = 'O';
                    System.out.println("My turn! I played O on row " + row + ", column " + col);
                    return;
                }
            }
        }
    }

    private void humanTwoPlayerGame() {
        do {
            changePlayer();
            humanTurn();
        } while(!checkForWin() || isBoardFull());
    }

    private void computerOpponentGame() {
        boolean isHumanTurn = true;
        do {
            if (isHumanTurn) {
                humanTurn();
                isHumanTurn = false;
            } else {
                computerTurn();
                isHumanTurn = true;
            }
        } while(!checkForWin() || isBoardFull());
    }
}
