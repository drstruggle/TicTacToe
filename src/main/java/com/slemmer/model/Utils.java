package com.slemmer.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

/*
* Helper class
* */
public class Utils {


    private Utils(){}
    /*
     * Makes a deep copy of any Java object that is passed.
     * This doesn't seem to work on the Board object.
     * One day I'll find out why. For now, use the other deepcopies.
     */
    public static Object getDeepCopy(Object object) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream outputStrm = new ObjectOutputStream(outputStream);
            outputStrm.writeObject(object);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
            ObjectInputStream objInputStream = new ObjectInputStream(inputStream);
            return objInputStream.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get a deep copy of the board.
     */
    public static Board getDeepCopyOfBoard (Board board)  {
        Board newBoard = new Board(State.O);

        Cell[][] newCell = new Cell[3][3];
        Cell[][] oldCell = board.getCells();
        for (int i = 0; i < oldCell.length; i++) {
            for (int j = 0; j < oldCell[0].length; j++) {
                newCell[i][j] = deepCopyOfCell(oldCell[i][j]);
            }
        }

        newBoard.setCells(newCell);
        newBoard.setGameOver(board.isGameOver());
        newBoard.setWhoWon(board.getWhoWon());
        newBoard.setMovesDone(board.getMovesDone());
        newBoard.setNextTurn(board.getNextTurn());
        newBoard.getAvailableMoves();

        return newBoard;
    }

    /*
    * Get a deep copy of a cell
    * */
    public static Cell deepCopyOfCell(Cell cell) {

        Cell newCell = new Cell(cell.getShortCode(), cell.getR(), cell.getC());
        newCell.setOwner(cell.getOwner());

        return newCell;
    }

}


