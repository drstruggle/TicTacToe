package com.slemmer.model;

import java.util.List;

/*
* This is where the magic happens
* This is the minimax algorithm
* */
public class Computer {

    // variables
    private static double maxPly;
    private Cell bestMove;

    //constructor
    public Computer() {}

    /**
     * Execute the algorithm.
     * @param player        the player that the AI will identify as
     * @param board         the Tic Tac Toe board to play on
     * @param maxPly        the maximum depth
     */
    public void run (State player, Board board, double maxPly) {
        if (maxPly < 1) {
            throw new IllegalArgumentException("Maximum depth must be greater than 0.");
        }

        Computer.maxPly = maxPly;
        miniMax(player, board, 0);
    }

    /**
     * The meat of the algorithm.
     * @param player        the player that the AI will identify as
     * @param board         the Tic Tac Toe board to play on
     * @param currentPly    the current depth
     * @return              the score of the board
     */
    private int miniMax (State player, Board board, int currentPly) {
        if (currentPly++ == maxPly || board.isGameOver()) {
            return score(player, board);
        }

        if (board.getNextTurn() == player) {
            return getMax(player, board, currentPly);
        } else {
            return getMin(player, board, currentPly);
        }

    }

    /**
     * Play the move with the highest score.
     * @param player        the player that the AI will identify as
     * @param board         the Tic Tac Toe board to play on
     * @param currentPly    the current depth
     * @return              the score of the board
     */
    private  int getMax (State player, Board board, int currentPly) {
        double bestScore = Double.NEGATIVE_INFINITY;
        Cell indexOfBestMove = null;
        List<Cell> availableMoves = board.getAvailableMoves();
        //System.out.println("BOARD AVAIL MOVE "+ board.getAvailableMoves());

        for (Cell theMove : availableMoves) {

            //System.out.println("MAX CHECKING THIS MOVE "+ theMove.getVisual());
            Board modifiedBoard = Utils.getDeepCopyOfBoard(board);

            modifiedBoard.move(theMove);

            int score = miniMax(player, modifiedBoard, currentPly);

            if (score >= bestScore) {
                bestScore = score;
                indexOfBestMove = theMove;
            }

        }
        board.move(indexOfBestMove);
        this.bestMove = indexOfBestMove;
        //System.out.println("CHECK BESTSCORE IN GETMAX "+ bestScore);
        return (int)bestScore;
    }

    /**
     * Play the move with the lowest score.
     * @param player        the player that the AI will identify as
     * @param board         the Tic Tac Toe board to play on
     * @param currentPly    the current depth
     * @return              the score of the board
     */
    private int getMin (State player, Board board, int currentPly) {
        double bestScore = Double.POSITIVE_INFINITY;
        Cell indexOfBestMove = null;

        for (Cell theMove : board.getAvailableMoves()) {
            //System.out.println("MIN CHECKING THIS MOVE "+ theMove.getVisual());

            Board modifiedBoard = Utils.getDeepCopyOfBoard(board);
            modifiedBoard.move(theMove);

            int score = miniMax(player, modifiedBoard, currentPly);

            if (score <= bestScore) {
                bestScore = score;
                indexOfBestMove = theMove;
            }

        }

        board.move(indexOfBestMove);
        this.bestMove = indexOfBestMove;
        return (int)bestScore;
    }

    /**
     * Get the score of the board.
     * @param player        the play that the AI will identify as
     * @param board         the Tic Tac Toe board to play on
     * @return              the score of the board
     */
    private int score (State player, Board board) {

        State opponent = (player == State.X) ? State.O : State.X;

        if (board.isGameOver() && board.getWhoWon() == player) {
            return 10;
        } else if (board.isGameOver() && board.getWhoWon() == opponent) {
            return -10;
        } else {
            return 0;
        }
    }

    public Cell getBestMove() {
        return this.bestMove;
    }
}
