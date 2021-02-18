package DecemberBreakWork.TicTacToe;

/**
 * UNOGame plays well-known UNO game between a computer and human player.
 */
public class TicTacToeGame {
    private final Player firstPlayer;
    private final Player secondPlayer;
    private final Board board;

    public TicTacToeGame(boolean isSecondPlayerComputer) {
        this.board = new Board();
       // this.firstPlayer = new HumanPlayer(board);
        this.firstPlayer = new ComputerPlayer(board, 'X', 'O');
        this.secondPlayer = isSecondPlayerComputer ? new ComputerPlayer(board, 'O',
                'X') : new HumanPlayer(board, 'O');
    }

    public static void main(String[] argv) {
        System.out.println("********Welcome to DemeberBreakWork.TicTacToe.********");
        System.out.println("Here are the rules: ");
        System.out.println("1. The first person will start as player X and the second player O");
        System.out.println("2. The first player who reaches 3 marks in a row will win the game. " +
                "It can be diagonal, horizontal or vertical");
        System.out.println("3. After a spot has been use it cannot be redone until the game is over.");

        // ask to run the game in a loop
        String finishGame;
        TicTacToeGame game;
        int typeGame;
        do {
            System.out.print("Would you like to play (1)2 player or (2)against a computer");
            typeGame = TextIO.getlnInt();
            if (!(typeGame == 1 || typeGame == 2)) {
                System.out.print("You entered an invalid choice. (1) 2 player or (2) against a computer");
            } else {
                System.out.print("New game is being made");
                game = new TicTacToeGame(typeGame == 2);
                game.playTicTacToe();
            }

            System.out.print("Do you still want to play? :");
            finishGame = TextIO.getlnString();
        } while(!finishGame.startsWith("n"));

        System.out.println("You have finished playing DemeberBreakWork.TicTacToe. Thanks for playing.");
    }

    private void playTicTacToe() {
        Player currentPlayer = firstPlayer;
        TurnResult isWonResult;
        TurnResult isBoardFull;
        do {
            board.printBoard();
            currentPlayer.playTurn();
            currentPlayer = currentPlayer == firstPlayer ? secondPlayer : firstPlayer;
            isWonResult = board.checkForWin();
        } while(!isWonResult.isGameOver());

        board.printBoard();
        if (isWonResult.isThereAWinner()) {
            System.out.println("Player " + isWonResult.getWinner() + " won.");
        } else {
            System.out.print("No one won.");
        }
    }
}
