/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caro;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import java.io.FileNotFoundException;
import java.util.Arrays;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class BoardController implements Initializable {

    @FXML
    AnchorPane boardContainer;
    boolean turn = false;
    int[][] move = new int[15][15];

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for (int[] row : move) {
            Arrays.fill(row, 0);
        }
    }

    @FXML
    private void handleButtonAction(MouseEvent event) {
        try {

            int col = (int) (event.getX() / 40);
            int row = (int) (event.getY() / 40);
            if (move[row][col] == 0) {
                move[row][col] = turn ? 1 : 2;

                Image image = new Image(new FileInputStream(turn ? "./src/assets/cross.png" : "./src/assets/zero.png"));
                ImageView imageView = new ImageView(image);
                imageView.setX(col * 40);
                imageView.setY(row * 40);
                imageView.setFitHeight(40);
                imageView.setFitWidth(40);
                System.out.println(col + " " + row);
                boardContainer.getChildren().add(imageView);
                turn = !turn;
                if (isWinning(col, row)) {
                    showAlertWithoutHeaderText();
                    Platform.exit();
                    System.exit(0);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean checkHorizontal(int x, int y) {
        int count = 1;
        int col = x;

        while (col < 14 && move[y][col] == move[y][col + 1]) {
            count++;
            col++;
        }

        col = x;
        while (col > 0 && move[y][col] == move[y][col - 1]) {
            count++;
            col--;
        }

        return count >= 5;
    }

    private boolean checkVertical(int x, int y) {
        int count = 1;
        int row = y;

        while (row < 14 && move[row][x] == move[row + 1][x]) {
            count++;
            row++;
        }

        row = y;
        while (row > 0 && move[row][x] == move[row - 1][x]) {
            count++;
            row--;
        }

        return count >= 5;
    }

    private boolean checkLeftDiagonal(int x, int y) {
        int count = 1;
        int row = y;
        int col = x;

        while (row < 14 && col < 14 && move[row][col] == move[row + 1][col + 1]) {
            count++;
            row++;
            col++;
        }

        row = y;
        col = x;
        while (row > 0 && col > 0 && move[row][col] == move[row - 1][col - 1]) {
            count++;
            row--;
            col--;
        }

        return count >= 5;
    }

    private boolean checkRightDiagonal(int x, int y) {
        int count = 1;
        int row = y;
        int col = x;

        while (row < 14 && col > 0 && move[row][col] == move[row + 1][col - 1]) {
            count++;
            row++;
            col--;
        }

        row = y;
        col = x;
        while (row > 0 && col < 14 && move[row][col] == move[row - 1][col + 1]) {
            count++;
            row--;
            col++;
        }

        return count >= 5;
    }

    private boolean isWinning(int x, int y) {
        return checkHorizontal(x, y) || checkVertical(x, y) || checkLeftDiagonal(x, y) || checkRightDiagonal(x, y);
    }

    private void showAlertWithoutHeaderText() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("WIN");
        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText("Player ? WIN !");
        alert.showAndWait();
    }
}
