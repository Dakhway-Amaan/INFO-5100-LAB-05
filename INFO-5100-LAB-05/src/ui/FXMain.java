/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author amaan
 */
public class FXMain extends Application  {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainFXML.fxml"));
        Scene scene = new Scene(root, 640, 520);

        primaryStage.setTitle("School Registration Form");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main (String args[]){
        launch(args);
    }
    
}
