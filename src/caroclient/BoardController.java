/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caroclient;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import java.io.FileNotFoundException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class BoardController implements Initializable {

    @FXML
    AnchorPane boardContainer;
    private Image crossSprite;
    private Image zeroSprite;
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            crossSprite = new Image(new FileInputStream("./src/assets/cross.png"));
            zeroSprite = new Image(new FileInputStream("./src/assets/zero.png"));
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public void drawMove(int col, int row, String fromPlayer) {
        ImageView imageView = new ImageView(fromPlayer.equals(Client.getId()) ? crossSprite : zeroSprite);
        imageView.setX(col * 40);
        imageView.setY(row * 40);
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        boardContainer.getChildren().add(imageView);
    }

    @FXML
    private void handleButtonAction(MouseEvent event) {
        int col = (int) (event.getX() / 40);
        int row = (int) (event.getY() / 40);
        Client.sendData("MOV:" + col + ";" + row + ";" + Client.getId());
    }

    public void showEndGameDialog(boolean isWinner) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Kết Thúc");
        alert.setHeaderText(null);
        alert.setContentText(isWinner ? "Thắng rồi! Vui quá!" : "Thua rồi! Buồn quá!");
        alert.initOwner(stage);
        alert.showAndWait();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
