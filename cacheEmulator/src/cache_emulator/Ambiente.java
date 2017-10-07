package cache_emulator;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Ambiente extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Pane raiz = FXMLLoader.load(getClass().getResource("cache.fxml"));
            Scene cena = new Scene(raiz);
            primaryStage.setTitle("LRU teste");
            primaryStage.setScene(cena);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(Ambiente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
