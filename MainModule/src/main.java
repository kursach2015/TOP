/**
 * Created by just1ce on 15.03.2015.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.*;



public class main extends Application {

    public static void main(String[] args) {
        launch(args);


    }

   //dfdsfgsdfsdfsdfsdfsd
    //я люблю лолиту
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException, IOException {
        String line = null;
        FileReader fileReader =new FileReader("E:/test.txt");
        BufferedReader bufferedReader =
                new BufferedReader(fileReader);

        while((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }

        // Always close files.
        bufferedReader.close();
    }

}
