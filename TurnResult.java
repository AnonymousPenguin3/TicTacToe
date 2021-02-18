package DecemberBreakWork.TicTacToe;

public class TurnResult {
    private static TurnResult gameNotOverResult = new TurnResult(false, false, 'y');
    
    private final boolean isGameOver;
    private final boolean isThereAWinner;
    private final char winner;

    public TurnResult(boolean isGameOver, boolean isThereAWinner, char winner) {
        this.isGameOver = isGameOver;
        this.isThereAWinner = isThereAWinner;
        this.winner = winner;
    }

    public static TurnResult gameNotOver() {
        return gameNotOverResult;
    }

    public static TurnResult gameOver(char winner) {
        return new TurnResult(true, true, winner);
    }

    public static TurnResult gameOverWithNoWinner() {
        return new TurnResult(true, false, 'y');
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public boolean isThereAWinner() {
        return isThereAWinner;
    }

    public char getWinner() {
        return winner;
    }
}
