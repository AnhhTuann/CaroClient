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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for (int[] row : move) {
            Arrays.fill(row, 0);
        }
    }
    
    private void log() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                System.out.print(move[i][j] + " ");
            }
            System.out.println();
        }
    }

    @FXML
    private void handleButtonAction(MouseEvent event) {
        try {
            
            int x = (int) (event.getX() / 40);
            int y = (int) (event.getY() / 40);
            if (move[y][x]== 0){
            move[y][x] = turn ? 1 : 2;
    
            Image image = new Image(new FileInputStream(turn ? "./src/assets/cross.png" : "./src/assets/zero.png"));
            ImageView imageView = new ImageView(image);
            imageView.setX(x * 40);
            imageView.setY(y * 40);
            imageView.setFitHeight(40);
            imageView.setFitWidth(40);
            System.out.println(x + " " + y);
            boardContainer.getChildren().add(imageView);
            turn = !turn;
            if (checkWin(move)){
                showAlertWithoutHeaderText();
                Platform.exit();
                System.exit(0);
            }
            }
            log();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    public boolean checkWin(int[][] matrix)
    {
        int cotTmp=0;
        int hangTmp=0;
        int diem=0;
       for(int hang=0;hang<15;hang++)
       {
           for(int cot=0;cot<15;cot++)
           {
               diem=0;
               cotTmp=cot;
               while(cotTmp<15&&(matrix[hang][cotTmp]==matrix[hang][cot]) && matrix[hang][cotTmp]!=0)
               {
                   diem++;
                   cotTmp++;
               }
               if(diem==5)
               {
                   return true;
               }
               diem=0;
               hangTmp=hang;
               while(hangTmp<15&&(matrix[hangTmp][cot]==matrix[hang][cot] && matrix[hangTmp][cot]!=0))
               {
                   diem++;
                   hangTmp++;
               }
               if(diem==5)
               {
                   return true;
               }
               //CheoCongCong(dauHuyen)
               diem=0;
               hangTmp=hang;
               cotTmp=cot;
               while(hangTmp<15&&cotTmp<15&&(matrix[hangTmp][cotTmp]==matrix[hang][cot])&&matrix[hangTmp][cotTmp]!=0)
               {
                   diem++;
                   hangTmp++;
                   cotTmp++;
               }
                if(diem==5)
               {
                   return true;
               }
               diem=0;
               hangTmp=hang;
               cotTmp=cot;
               while(hangTmp<15&&cotTmp>=0&&(matrix[hangTmp][cotTmp]==matrix[hang][cot])&&matrix[hangTmp][cotTmp]!=0)
               {
                   diem++;
                   hangTmp++;
                   cotTmp--;
               }
                if(diem==5)
               {
                   return true;
               }

               

               
           }
       }
       return false;
    }
        
    private void showAlertWithoutHeaderText() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("WIN");
        // Header Text: null
        alert.setHeaderText(null);
        alert.setContentText("Win roii !");
        alert.showAndWait();
    }
}
