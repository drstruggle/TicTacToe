package com.slemmer.model;

import java.util.ArrayList;
import java.util.List;

/*
* The board is the matrix of cells, and keeps track
* of status:
* cells : collection of all cells
* availableMoves: which slots are available
* gameOver: is the game over?
* gameWon : is there a winner?
* whoWon  : who won
* movesDone: how many turns have been done
* nextTern: who's turn is it
* */
public class Board {

    // variables
    private Cell[][] cells;
    private List<Cell> availableMoves;
    private boolean gameWon;
    private boolean gameOver;
    private State whoWon;
    private int movesDone;
    public State nextTurn;

    public Board(State nextTurn) {
        cells = new Cell[3][3];
        cells[0][0] = new Cell(1,0,0);
        cells[0][1] = new Cell(2,0,1);
        cells[0][2] = new Cell(3,0,2);
        cells[1][0] = new Cell(4,1,0);
        cells[1][1] = new Cell(5,1,1);
        cells[1][2] = new Cell(6,1,2);
        cells[2][0] = new Cell(7,2,0);
        cells[2][1] = new Cell(8,2,1);
        cells[2][2] = new Cell(9,2,2);
        this.gameWon=false;
        this.gameOver=false;
        this.movesDone=0;
        this.nextTurn = nextTurn;
        this.availableMoves = getAvailableMoves();
    }

    //make a move, make nextTurn owner of the cell

    public void move(Cell movedCell){

        //System.out.println("cell " + movedCell.getShortCode()+" to "+ getNextTurn());
        //set the owner
        this.cells[movedCell.getR()][movedCell.getC()].setOwner(getNextTurn());

        //set remaining available moves
        getAvailableMoves();

        //add one moveDone
        setMovesDone(getMovesDone()+1);

        //set next player
        setNextTurn((getNextTurn()==State.X ? State.O : State.X));

        //check if the game is over
        checkIfGameIsOver();

    }

    //get all available cells left
    public List<Cell> getAvailableMoves() {

        //get free slots
        List<Cell> availableMoves = new ArrayList<>();
        for (int r = 0; r < getCells().length; r++) {
            for (int c = 0; c < getCells()[0].length; c++) {
                if (this.cells[r][c].getOwner()== State.EMPTY) {
                    availableMoves.add(this.cells[r][c]);
                }
            }
        }
        this.availableMoves = availableMoves;
        return availableMoves;
    }

    //check if there's a winner, or all slots are owned
    public void checkIfGameIsOver(){

        //check for winners

        // horizontal
        for (int r = 0; r < cells.length; r++) {
            if (isThisARow(cells[r][0], cells[r][1], cells[r][2])) {
                this.gameWon = true;
                this.gameOver = true;
                this.whoWon = cells[r][0].getOwner();
            }
        }

        //vertical
        for (int c = 0; c < cells[0].length; c++) {
            if (isThisARow(cells[0][c], cells[1][c], cells[2][c])) {
                this.gameWon = true;
                this.gameOver = true;
                this.whoWon = cells[0][c].getOwner();
            }
        }

        //diagonal
        if (isThisARow(cells[0][0], cells[1][1], cells[2][2])) {
            this.gameWon = true;
            this.gameOver = true;
            this.whoWon = cells[0][0].getOwner();
        }
        if (isThisARow(cells[2][0], cells[1][1], cells[0][2])) {
            this.gameWon = true;
            this.gameOver = true;
            this.whoWon = cells[2][0].getOwner();
        }

        //check if all slots are filled
        if(isBordIsVol()){
            this.gameWon = false;
            this.gameOver = true;
        }
    }

    //helper for 3-in-a-row
    public boolean isThisARow(Cell pos1, Cell pos2, Cell pos3){
        return (pos1.getOwner()!=State.EMPTY && (pos1.getOwner() == pos2.getOwner() && pos2.getOwner() == pos3.getOwner()));
    }

    //getters and setters

    public boolean isBordIsVol(){
        return (getAvailableMoves().size()<=0);
    }

    public void setGameWon(boolean gameWon) {
        this.gameWon = gameWon;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public void setWhoWon(State whoWon) {
        this.whoWon = whoWon;
    }

    public State getWhoWon() {
        return whoWon;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public int getMovesDone() {
        return movesDone;
    }

    public void setMovesDone(int movesDone) {
        this.movesDone = movesDone;
    }

    public void setNextTurn(State nextTurn) {
        this.nextTurn = nextTurn;
    }

    public State getNextTurn() {
        return nextTurn;
    }

}
