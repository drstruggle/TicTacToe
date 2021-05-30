package com.slemmer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//original main method
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{


        Parent root = FXMLLoader.load(getClass().getResource("/screen.fxml"));
        primaryStage.setTitle("Tic Tac Toe");
//            primaryStage.setScene(new Scene(root, 350, 600));
//            primaryStage.setMinHeight(550);
//            primaryStage.setMinWidth(300);
//            primaryStage.setMaxHeight(800);
//            primaryStage.setMaxWidth(550);
//        primaryStage.show();
//        root.requestFocus();


        Scene scene = new Scene(root, 320, 550);
        primaryStage.setScene(scene);
        primaryStage.minWidthProperty().bind(scene.heightProperty().multiply(0.58));
        primaryStage.minHeightProperty().bind(scene.widthProperty().divide(0.58));
        primaryStage.setMaxHeight(800);
        primaryStage.setMaxWidth(520);
        primaryStage.show();
        root.requestFocus();
    }


    public static void main(String[] args) {
                launch(args);
    }

}

