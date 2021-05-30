package com.slemmer.model;

/*
* Player class, holding the name and scores
* winsPlayer: how many he won
* winsComputer: how many AI won
* ties: how many were ties
* */
public class Player {

    // variables
    private final String name;
    private int winsPlayer;
    private int winsComputer;
    private int ties;

    //constructor
    public Player(String name) {
        this.name = name;
        this.winsPlayer = 0;
        this.winsComputer= 0;
        this.ties= 0;
   }

    //getters and setters

    public String getName() {
        return name;
    }

    public int getWinsPlayer() {
        return winsPlayer;
    }

    public void setWinsPlayer(int winsPlayer) {
        this.winsPlayer = winsPlayer;
    }

    public int getWinsComputer() {
        return winsComputer;
    }

    public void setWinsComputer(int winsComputer) {
        this.winsComputer = winsComputer;
    }

    public int getTies() {
        return ties;
    }

    public void setTies(int ties) {
        this.ties = ties;
    }
}
