/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package caroclient.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import caroclient.Client;
import caroclient.handler.RankingHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author phandungtri
 */
public class RankingController extends ControllerBase {
    private MainController container;
    @FXML
    private VBox rankVbox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handler = new RankingHandler(this);
        Client.registerHandler(handler);
        Client.sendData("RANK:rank");
    }

    @FXML
    public void goToHub() {
        container.loadHub();
    }

    public void showRank(String[] infos) {
        rankVbox.getChildren().clear();

        try {
            for (int i = 0; i < infos.length; i++) {
                String[] info = infos[i].split(",");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/caroclient/PlayerRank.fxml"));
                Node item = loader.load();
                PlayerRankController controller = loader.getController();

                controller.setInfo(info[0], info[1]);
                rankVbox.getChildren().add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setContainer(MainController container) {
        this.container = container;
    }
}
