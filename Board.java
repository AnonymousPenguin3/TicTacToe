package DecemberBreakWork.TicTacToe;

import java.util.ArrayList;
import java.util.Random;

public class Board {
    private final char[][] board;
    
    public Board(int size) {
        this.board = new char[size][size];
        initializeBoard();
    }

    public Board() {
        this(3);

    }

    public Board(Board tictactoe) {
        this.board = new char[tictactoe.board.length][tictactoe.board.length];
        for (int row = 0; row < board.length; row++) {
            // Loop through columns
            for (int col = 0; col < board.length; col++) {
                board[row][col] = tictactoe.board[row][col];
            }
        }
    }

    // Set/Reset the board back to all empty values.
    public void initializeBoard() {
        // Loop through rows
        for (int row = 0; row < board.length; row++) {
            // Loop through columns
            for (int col = 0; col < board.length; col++) {
                board[row][col] = '-';
            }
        }
    }

    public void printBoard() {
        System.out.println("-------------");
        for (int row = 0; row < board.length; row++) {
            System.out.print("|");
            for (int col = 0; col < board.length; col++) {
                System.out.print(board[row][col] + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }
    
    // Loop through all cells of the board and if one is found to be empty (contains char '-') then return false.
    // Otherwise the board is full.
    public TurnResult isBoardFull() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == '-') {
                    return TurnResult.gameNotOver();
                }
            }
        }
        return TurnResult.gameOverWithNoWinner();
    }

    public void placeMark(int row, int col, char playerMark) {
        // Make sure that row and column are in bounds of the board.
        if ((row >= 0) && (row < 3)) {
            if ((col >= 0) && (col < 3)) {
                if (board[row][col] == '-') {
                    board[row][col] = playerMark;
                    return;
                }
            }
            System.out.println("This is not a valid spot. Please choose a valid column between 1 and 3");
        }
        System.out.println("This is not a valid spot. Please choose a valid row between 1 and 3");
    }

//    public TurnResult isGameOver() {
//        TurnResult result = checkForWin();
//        if (!result.isGameOver()) {
//            result = isBoardFull();
//        }
//        return result;
//    }
    
    public void markFirstEmptyCell(char turnSymbol) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == '-') {
                    board[row][col] = turnSymbol;
                    System.out.println("My turn! I played O on row " + row + ", column " + col);
                    return;
                }
            }
        }
    }

    // Returns true if there is a win, false otherwise.
    // This calls the other win check functions to check the entire board.
    TurnResult checkForWin() {
        TurnResult result = checkRowsForWin();
        if (!result.isGameOver()) {
            result = checkColumnsForWin();
        }
        if (!result.isGameOver()) {
            result = checkDiagonalsForWin();
        }
        
        return result.isGameOver() ? result : isBoardFull();
    }

    // Loop through rows and see if any are winners by rows.
    private TurnResult checkRowsForWin() {
        for (int row = 0; row < board.length; row++) {
            if (checkWin(board[row][0], board[row][1], board[row][2])) {
                return TurnResult.gameOver(board[row][0]);
            }
        }
        return TurnResult.gameNotOver();
    }


    // Loop through columns and see if any are winners by columns.
    private TurnResult checkColumnsForWin() {
        for (int i = 0; i < 3; i++) {
            if (checkWin(board[0][i], board[1][i], board[2][i])) {
                return TurnResult.gameOver(board[0][i]);
            }
        }
        return TurnResult.gameNotOver();
    }

    // Check the two diagonals to see if either is a win. Return true if either wins.
    private TurnResult checkDiagonalsForWin() {
        if (checkWin(board[0][0], board[1][1], board[2][2])) {
            return TurnResult.gameOver(board[0][0]);
        }
        if (checkWin(board[0][2], board[1][1], board[2][0])) {
            return TurnResult.gameOver(board[0][2]);
        }
        return TurnResult.gameNotOver();
    }

    // Check to see if all three values are the same (and not empty) indicating a win.
    private boolean checkWin(char c1, char c2, char c3) {
        return ((c1 != '-') && (c1 == c2) && (c2 == c3));
    }

    class Pair {
        int row;
        int col;

        public Pair(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    public void markRandomEmptyCell(char turnSymbol) {
        ArrayList<Pair> emptyCells = new ArrayList<Pair>();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == '-') {
                    emptyCells.add(new Pair(row, col));
                }
            }
        }
        
        Random random = new Random();
        Pair pair = emptyCells.get(random.nextInt(emptyCells.size()));
        board[pair.row][pair.col] = turnSymbol;
    }

    public void markNeverLose(char currentPlayerSymbol, char otherPlayerSymbol) {
        MoveResult moveResult = markNeverLose2(this, true, currentPlayerSymbol, otherPlayerSymbol);
        System.out.println("I played on: " + moveResult.lastMoveRow + " " + moveResult.lastMoveCol);
        board[moveResult.lastMoveRow][moveResult.lastMoveCol] = currentPlayerSymbol;
    }

//    public int markNeverLose3(Board tictactoe, boolean isFirstPlayer,
//                              char firstPlayerSymbol, char secondPlayerSymbol) {
//        int zeroResult = -1;
//        for (int row = 0; row < 3; row++) {
//            for (int col = 0; col < 3; col++) {
//                if (tictactoe.board[row][col] == '-') {
//                    Board newBoard = new Board(tictactoe);
//                    newBoard.board[row][col] = isFirstPlayer ? firstPlayerSymbol : secondPlayerSymbol;
//                    TurnResult result = newBoard.checkForWin();
//                    if (result.isGameOver()) {
//                        if (result.isThereAWinner()) {
//                            return isFirstPlayer ? -1 : 1;
//                        } else {
//                            return 0;
//                        }
//                    }
//
//                    int thisMoveResult = markNeverLose3(newBoard, !isFirstPlayer,
//                            firstPlayerSymbol, secondPlayerSymbol);
//                    if ((isFirstPlayer && thisMoveResult == -1) ||
//                            (!isFirstPlayer && thisMoveResult == 1)) {
//                        return isFirstPlayer ? -1 : 1;
//                    }
//                    if (thisMoveResult == 0) {
//                        zeroResult = 0;
//                    }
//                }
//            }
//        }
//
//
//        if (zeroResult != -1) {
//            return 0;
//        }
//
//        return isFirstPlayer ? 1 : -1;
//    }

    class MoveResult {
        int result;
        int lastMoveRow;
        int lastMoveCol;

        public MoveResult(int result, int lastMoveRow, int lastMoveCol) {
            this.result = result;
            this.lastMoveRow = lastMoveRow;
            this.lastMoveCol = lastMoveCol;
        }
    }
    public MoveResult markNeverLose2(Board tictactoe, boolean isFirstPlayer,
                                    char firstPlayerSymbol, char secondPlayerSymbol) {
        int anEmptyRow = -1;
        int anEmptyCol = -1;
        int zeroResultRow = -1;
        int zeroResultCol = -1;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (tictactoe.board[row][col] == '-') {
                    anEmptyRow = row;
                    anEmptyCol = col;
                    Board newBoard = new Board(tictactoe);
                    newBoard.board[row][col] = isFirstPlayer ? firstPlayerSymbol : secondPlayerSymbol;
                    TurnResult result = newBoard.checkForWin();
                    if (result.isGameOver()) {
                        if (result.isThereAWinner()) {
                            //System.out.println("game won isFirst, wonRow, wonCol " + isFirstPlayer + " " + row +" " + col);
                            return new MoveResult(isFirstPlayer ? -1 : 1, row, col);
                        } else {
                            //System.out.println("game full isFirst, wonRow, wonCol " + isFirstPlayer + " " + row +" " + col);
                            return new MoveResult(0, row, col);
                        }
                    }

                    MoveResult thisMoveResult = markNeverLose2(newBoard, !isFirstPlayer,
                            firstPlayerSymbol, secondPlayerSymbol);
                    if ((isFirstPlayer && thisMoveResult.result == -1) ||
                            (!isFirstPlayer && thisMoveResult.result == 1)) {
                        //System.out.println("game won later isFirst, wonRow, wonCol " + isFirstPlayer + " " + row +" " + col);
                        return new MoveResult(isFirstPlayer ? -1 : 1, row, col);
                    }
                    if (thisMoveResult.result == 0) {
                        zeroResultRow = row;
                        zeroResultCol = col;
                    }
                }
            }
        }

        if (zeroResultCol != -1) {
            //System.out.println("game zero later isFirst, wonRow, wonCol " + isFirstPlayer + " " + zeroResultRow +" " + zeroResultCol);
            return new MoveResult(0, zeroResultRow, zeroResultCol);
        }

        //System.out.println("game lost later isFirst, wonRow, wonCol " + isFirstPlayer + " " + anEmptyRow +" " + anEmptyCol);
        return new MoveResult(isFirstPlayer ? 1 : -1, anEmptyRow, anEmptyCol);
    }

    public MoveResult markNeverLose(Board tictactoe, boolean isFirstPlayer,
                                    char firstPlayerSymbol, char secondPlayerSymbol) {
        int anEmptyRow = -1;
        int anEmptyCol = -1;
        int zeroResultRow = -1;
        int zeroResultCol = -1;
        int lostResultRow = -1;
        int lostResultCol = -1;
        int wonResultRow = -1;
        int wonResultCol = -1;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (tictactoe.board[row][col] == '-') {
                    anEmptyRow = row;
                    anEmptyCol = col;
                    Board newBoard = new Board(tictactoe);
                    newBoard.board[row][col] = isFirstPlayer ? firstPlayerSymbol : secondPlayerSymbol;
                    TurnResult result = newBoard.checkForWin();
                    if (result.isGameOver()) {
                        if (result.isThereAWinner()) {
//                            wonResultRow = row;
//                            wonResultCol = col;
//                            System.out.println("game won isFirst, wonRow, wonCol " + isFirstPlayer + " " + row +" " + col);
                            return new MoveResult(isFirstPlayer ? -1 : 1, row, col);
                        }
                    }

                    result = newBoard.isBoardFull();
                    if(result.isGameOver()) {
//                        zeroResultRow = row;
//                        zeroResultCol = col;
//                        System.out.println("game full isFirst, wonRow, wonCol " + isFirstPlayer + " " + row +" " + col);
                        return new MoveResult(0, row, col);
                    }

                    MoveResult thisMoveResult = markNeverLose(newBoard, !isFirstPlayer,
                            firstPlayerSymbol, secondPlayerSymbol);
                    if (isFirstPlayer && thisMoveResult.result == -1) {
                        wonResultRow = row;
                        wonResultCol = col;
                    }
                    if (!isFirstPlayer && thisMoveResult.result == 1) {
                        wonResultRow = row;
                        wonResultCol = col;
                    }
//                    if (isFirstPlayer && thisMoveResult.result == 1) {
//                        lostResultRow = thisMoveResult.lastMoveRow;
//                        lostResultCol = thisMoveResult.lastMoveCol;
//                    }
//                    if (!isFirstPlayer && thisMoveResult.result == -1) {
//                        lostResultRow = thisMoveResult.lastMoveRow;
//                        lostResultCol = thisMoveResult.lastMoveCol;
//                    }
                    if (thisMoveResult.result == 0) {
                        zeroResultRow = row;
                        zeroResultCol = col;
                    }
                }
            }
        }

//        if (lostResultCol != -1) {
//            return new MoveResult(isFirstPlayer ? 1 : -1, lostResultRow, lostResultCol);
//        }

        if (wonResultCol != -1) {
//            System.out.println("game won later isFirst, wonRow, wonCol " + isFirstPlayer + " " + wonResultRow +" " + wonResultCol);
            return new MoveResult(isFirstPlayer ? -1 : 1, wonResultRow, wonResultCol);
        }

        if (zeroResultCol != -1) {
//            System.out.println("game zero later isFirst, wonRow, wonCol " + isFirstPlayer + " " + zeroResultRow +" " + zeroResultCol);
            return new MoveResult(0, zeroResultRow, zeroResultCol);
        }

//        System.out.println("game lost later isFirst, wonRow, wonCol " + isFirstPlayer + " " + anEmptyRow +" " + anEmptyCol);
        return new MoveResult(isFirstPlayer ? 1 : -1, anEmptyRow, anEmptyCol);
    }
}
