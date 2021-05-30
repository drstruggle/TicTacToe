package com.slemmer.model;

import javafx.beans.property.SimpleStringProperty;

/*
* One cell in the matrix
* Keeps track of :
* owner: who's claimed the cell
* r, c : row and column number
* shortcode: [][] to []
* */
public class Cell {

    // variables
    private final int shortCode;
    private State owner;
    private final int r;
    private final int c;
    public SimpleStringProperty visual;


    //constructor
    public Cell(int shortCode, int r, int c) {
        this.shortCode = shortCode;
        this.r = r;
        this.c = c;
        setOwner(State.EMPTY);
    }

    //getters and setters

    public int getR() {
        return r;
    }

    public int getC() {
        return c;
    }

    public int getShortCode() {
        return shortCode;
    }

    public State getOwner() {
        return owner;
    }

    public void setOwner(State owner) {
        this.owner = owner;
        this.visual = (this.owner==State.EMPTY
                ? new SimpleStringProperty(String.valueOf(this.getShortCode())) :
                new SimpleStringProperty(String.valueOf(this.getOwner())));
    }

    public String getVisual() {
        return this.visual.get();
    }

    public SimpleStringProperty visualProperty() {
        return this.visual;
    }


    //added to make a input like 9 select the cell[2][2] (by it's shortCode 9)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell)) return false;

        Cell cell = (Cell) o;
        return (this.getShortCode()== cell.getShortCode());
    }

}
