package com.slemmer.controller;


import com.slemmer.model.*;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.LinkedHashMap;
import java.util.Map;

public class Controller {


    @FXML
    private GridPane masterPane, cellBox, buttonBox, statisticsBox;
    @FXML
    private Button button1, button2, button3, button4, button5, button6, button7, button8, button9;
    @FXML
    private Button startNewGame, startNewPlayer;
    @FXML
    private Label lastResult, gameNo, playerWins, computerWins, ties;

    private ObservableList<Button> activeButtons = FXCollections.observableArrayList();
    private Map<Button, Cell> mapButtonCell = new LinkedHashMap<>();

    private DoubleProperty fontSize = new SimpleDoubleProperty(14);

    //Game variables
    private Board currentBoard;
    private Computer computer;
    private Player player;
    private int games;

    @FXML
    public void initialize() {
        player = new Player("Player");
        computer = new Computer();

        //bind fontsize
        cellBox.minWidthProperty().bind(cellBox.minHeightProperty());
        fontSize.bind(masterPane.widthProperty().divide(24));
        buttonBox.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));
        statisticsBox.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString(), ";"));
        cellBox.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.multiply(3).asString(), ";"));
    }

    //add all the buttons to the 'active buttons' list
    //this is done when initializing a new game
    public void activateAllButtons(){
        //map buttons to cells
        mapButtonCell.put(button1, currentBoard.getCells()[0][0]);
        mapButtonCell.put(button2, currentBoard.getCells()[0][1]);
        mapButtonCell.put(button3, currentBoard.getCells()[0][2]);
        mapButtonCell.put(button4, currentBoard.getCells()[1][0]);
        mapButtonCell.put(button5, currentBoard.getCells()[1][1]);
        mapButtonCell.put(button6, currentBoard.getCells()[1][2]);
        mapButtonCell.put(button7, currentBoard.getCells()[2][0]);
        mapButtonCell.put(button8, currentBoard.getCells()[2][1]);
        mapButtonCell.put(button9, currentBoard.getCells()[2][2]);

        //add all buttons to activebuttons
        for (Map.Entry<Button, Cell> set : mapButtonCell.entrySet()) {
            activeButtons.add(set.getKey());
        }
    }

    //disable all the (remaining) active buttons
    //this is done before or after a move
    public void disableActiveButtons(){

        for(Button b: activeButtons){
            b.setDisable(true);
        }
    }

    //enable all the (remaining) active buttons
    //this is done before or after a move
    public void enableActiveButtons(){

        for(Button b: activeButtons){
            b.setDisable(false);
        }
    }

    //update statistics on the screen
    //display gameno, wins, losses, ties
    public void updateStatistics(){

        gameNo.setText(String.valueOf(games));
        computerWins.setText(String.valueOf(player.getWinsComputer()));
        playerWins.setText(String.valueOf(player.getWinsPlayer()));
        ties.setText(String.valueOf(player.getTies()));
    }

    //update last result exclamation (win/lose/tie) on the screen
    public void updateLastResult(String message){

        lastResult.setText(message);
    }

    //update buttons (with the correct owner) on the screen.
    //disable buttons that are owned from activeButtons list.
    public void updateVisuals(){

        //update the button text
        for (Map.Entry<Button, Cell> set : mapButtonCell.entrySet()) {
            //set textfield button to cell.getvisual
            set.getKey().setText(set.getValue().getVisual());
            //remove button from activebutton and disable when owned
            if(set.getValue().getOwner()!=State.EMPTY){
                disableAndRemoveButton(set.getKey());
            }
        }

        //remove focus from button(s)
        masterPane.requestFocus();
    }

    //helper. Disable button and remove from activeButton list.
    public void disableAndRemoveButton(Button button){
        button.setDisable(true);
        activeButtons.remove(button);
    }

    //take screen input from the player (player is pushing button).
    @FXML
    public void onButtonClicked(ActionEvent e){

        //check if it's the players turn.
        if(this.currentBoard.getNextTurn()==State.X) {
            Cell move=null;
            //make the move

            for (Map.Entry<Button, Cell> set : mapButtonCell.entrySet()) {
                if (e.getSource().equals(set.getKey())) move = set.getValue();
            }

            currentBoard.move(move);

            //update the buttons on the screen
            updateVisuals();

            //disable all (remaining) active buttons (it's the computers turn now)
            disableActiveButtons();

            //next players turn
            nextMove();
        }
    }

    //player wants to play a game
    @FXML
    public void onStartNewGamePressed(){


        //computer = new Computer();

        //determine who may have the first move
        if (games % 2 == 0) {
            currentBoard = new Board(State.O);
        } else {
            currentBoard = new Board(State.X);
        }
        //add game count
        games++;

        //activate all buttons (because it's a new game)
        activateAllButtons();

        //update the pane
        updateVisuals();

        //set last result (win, lose etc to nothing)
        updateLastResult("");

        //update the statistics
        updateStatistics();

        //give the first player the room
        nextMove();
    }

    //player wants to empty the statistics (new player)
    @FXML
    public void onStartNewPlayerPressed(ActionEvent actionEvent) {

        //create a new player (with no existing results)
        this.player = new Player("Player");
        games=0;

        //update the statistic on screen
        updateStatistics();

        //update the last result on screen
        updateLastResult("");
    }

    //determine who's turn it is now.
    public void nextMove() {

        //determine who's turn it is
        if (currentBoard.getNextTurn() == State.O) {
            //let the computer have the room
            computerMove();
        } else {
            //let the player have the room
            playerMove();
        }

        //check if the game is over
        checkIfGameIsOver();

    }

    //players turn (activate remaining buttons - open for input)
    private void playerMove() {
        enableActiveButtons();
    }

    //computers turn - make the computer calculate a move
    public void computerMove(){

        //here comes the boom.. this is the AI at work
        //give a copy of the current board, so that the computer can
        //determine the best move.
        Board tempBoard = Utils.getDeepCopyOfBoard(this.currentBoard);
        computer.run(State.O, tempBoard, Double.POSITIVE_INFINITY);

        //move the computers choice of moves
        currentBoard.move(computer.getBestMove());

        //update the buttons on screen
        updateVisuals();

        //activate buttons, now it's the players turn
        enableActiveButtons(); //not neccessary here?

    }

    //check if the game is over (won, lost, tie)
    public void checkIfGameIsOver(){

        //check if game is over
        if(currentBoard.isGameOver()){

            //game is, so deactivate all buttons
            disableActiveButtons();

            //check if someone won
            if(currentBoard.isGameWon()){

                //someone won. who won?
                if(currentBoard.getWhoWon() == State.X){
                    //show result on screen.
                    updateLastResult("You win!!!");
                    player.setWinsPlayer(player.getWinsPlayer()+1);
                }else{
                    player.setWinsComputer(player.getWinsComputer()+1);
                    //show result on screen.
                    updateLastResult("Computer wins.");
                }
            //nobody won.
            }else{
                player.setTies(player.getTies()+1);

                //exclaim the result on screen.
                updateLastResult("It's a tie.");
            }

            //update the statistics on screen.
            updateStatistics();
        }
    }

}

