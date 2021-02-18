package DecemberBreakWork.TicTacToe;

/**
 * Represents a Computer Player - one that cannot give inputs.
 * All input related things have been randomized or made to be very simple AI
 */
public class ComputerPlayer implements Player {
    private final Board board;
    private final char turnSymbol;
    private final char otherPlayerSymbol;

    public ComputerPlayer(Board board, char turnSymbol, char otherPlayerSymbol) {
        this.board = board;
        this.turnSymbol = turnSymbol;
        this.otherPlayerSymbol = otherPlayerSymbol;
    }

    @Override
    public void playTurn() {
        //board.markFirstEmptyCell(turnSymbol);
        board.markNeverLose(turnSymbol, otherPlayerSymbol);
    }
}
