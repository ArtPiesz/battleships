package com.example.battleships;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Random;

import static java.lang.Integer.parseInt;

public class MainApp extends Application {
private int shipsToPlace = 5;
public boolean orientation = true;
public int shipLength = 0;
private final Random random = new Random();
private boolean running = false;
public boolean enemyTurn = false;
private BoardView enemyBoard, playerBoard;

private final TextArea messages = new TextArea();
private final boolean isServer = false;
private final NetworkConnection connection = isServer ? createServer() : createClient();

@Override
public void init() throws Exception{
connection.startConnection();
}

private Server createServer(){
    return new Server(55555, data -> Platform.runLater(()-> messages.appendText(data.toString() + "\n")));
}

private Client createClient(){
    return new Client("127.0.0.2",55555, data-> Platform.runLater(()-> messages.appendText(data.toString() + "\n")));
}
private Parent createChat(){
    TextField input = new TextField();


    input.setOnAction(event->{
        String message = isServer ? "Server: " : "Client: ";
        message+= input.getText();
        input.clear();
        messages.appendText(message + "\n");
        try {
            connection.send(message);
        }
        catch(Exception e){
            messages.appendText("Failed to send" + "\n");
        }
    });
    VBox root = new VBox(20,messages,input);
    root.setPrefHeight(800);
    root.setAlignment(Pos.CENTER_RIGHT);
    return root;
}

 private Parent createMenu (){
     VBox vBox = new VBox();
     vBox.getChildren().add(new Text("Choose ship length:"));
     ToggleGroup menuToggle = new ToggleGroup();
     ToggleGroup orientToggle = new ToggleGroup();

     AnchorPane root = new AnchorPane();
     AnchorPane.setLeftAnchor(vBox,75.0);

     ToggleButton horizontal = new ToggleButton();
     horizontal.setPrefHeight(25);
     horizontal.setPrefWidth(75);
     horizontal.setText("---");
     horizontal.setToggleGroup(orientToggle);


     ToggleButton vertical = new ToggleButton();
     vertical.setPrefHeight(25);
     vertical.setPrefWidth(75);
     vertical.setText("|");
     vertical.setToggleGroup(orientToggle);

     vertical.setOnAction(actionEvent -> {
         orientation = false;
         System.out.println("vertical");
     });
     horizontal.setOnAction(actionEvent -> {
         orientation = true;
         System.out.println("horizontal");
     });

     for(int i=1; i < 5; i++)
     {
         ToggleButton shipType = new ToggleButton();
         shipType.setPrefHeight(25);
         shipType.setPrefWidth(75);
         shipType.setText(String.valueOf(i+1));

         shipType.setOnAction(actionEvent -> shipLength =parseInt(shipType.getText()));
         shipType.setToggleGroup(menuToggle);
         vBox.getChildren().add(shipType);
     }
     vBox.getChildren().addAll(vertical,horizontal);
     root.getChildren().add(vBox);

    return root;
}
    private Parent createContent(){
        BorderPane root = new BorderPane();
        root.setPrefSize(600, 800);
        root.setLeft(createMenu());
        root.setRight(createChat());
        enemyBoard = new BoardView(true, event -> {
            if (!running)
                return;

            Cell cell = (Cell) event.getSource();
            if (cell.wasShot)
                return;

            enemyTurn = !cell.shoot();

            if (enemyBoard.ships == 0) {
                System.out.println("YOU WIN");
                System.exit(0);
            }

            if (enemyTurn)
                enemyMove();
        });

        playerBoard = new BoardView(false, event -> {
            if (running)
                return;

            Cell cell = (Cell) event.getSource();
            if (playerBoard.placeShip(new Ship(shipLength,orientation), cell.x, cell.y)) {
                if (--shipsToPlace == 0) {
                    startGame();
                    root.setLeft(null);
                }
            }
        });

        VBox vboxBoard = new VBox(50, enemyBoard, playerBoard);
        vboxBoard.setAlignment(Pos.CENTER);
        VBox vboxMenu = new VBox(50,createMenu());
        vboxMenu.setAlignment(Pos.CENTER);
        root.setCenter(vboxBoard);
        root.setLeft(vboxMenu);

        return root;
    }
    private void enemyMove() {
        while (enemyTurn) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);
            GridPane playerGrid= playerBoard.getGridFromBoardView(playerBoard);

            Cell cell = (Cell)playerBoard.getCellFromGridPane(playerGrid,x,y);
            if (cell.wasShot)
                continue;

            enemyTurn = cell.shoot();

            if (playerBoard.ships == 0) {
                System.out.println("YOU LOSE");
                System.exit(0);
            }
        }
    }

    private void startGame() {
        // place enemy ships
        int ships = 5;

        while (ships > 0) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);

            if (enemyBoard.placeShip(new Ship(random.nextInt(1,5), Math.random() < 0.5), x, y)) {
                ships--;
            }
        }

        running = true;
    }
    @Override
    public void start(Stage stage) {
        stage.setTitle("Battleships");
        Scene scene = new Scene(createContent());
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void stop() throws Exception{
        connection.closeConnection();
    }
    public static void main(String[] args){

        launch(args);
    }


}
