package DecemberBreakWork.TicTacToe;

/**
 * Represents a human player - asks for human inputs
 */
public class HumanPlayer implements Player {
    private final Board board;
    private final char currentPlayerMark;

    public HumanPlayer(Board board, char currentPlayerMark) {
        this.board = board;
        this.currentPlayerMark = currentPlayerMark;
    }

    @Override
    public void playTurn() {
        int inputRow;
        int inputColumn;
        System.out.println("Here is the current board:");
        board.printBoard();
        System.out.print("Which row would you like to place your mark? : ");
        inputRow = TextIO.getlnInt();
        System.out.print("Which column would you like to place your mark? : ");
        inputColumn = TextIO.getlnInt();
        board.placeMark(inputRow - 1, inputColumn - 1, currentPlayerMark);
    }
}
