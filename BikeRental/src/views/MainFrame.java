package views;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.LocaleStore;

import java.io.IOException;

public class MainFrame extends Application {


    private Stage stage;

    private static MainFrame mInstance;

    static public MainFrame getInstance() {
        return mInstance;
    }

    public MainFrame() {
        mInstance = this;
    }

    public static void start(String[] args) {
        launch(args);
    }

    public Parent replaceScene(String fxmlPath) {
        try {
            Parent page = FXMLLoader.load(MainFrame.class.getResource(fxmlPath),
                    LocaleStore.getLocale().getResourceBundle(),
                    new JavaFXBuilderFactory());
            System.err.println(fxmlPath);
            Scene scene = stage.getScene();
            if (scene == null) {
                scene = new Scene(page, 700, 450);
                stage.setScene(scene);
            } else {
                stage.getScene().setRoot(page);
            }
            stage.sizeToScene();
            return page;
        } catch (IOException e) {
            System.err.println(e);
            return null;
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            stage = primaryStage;
            replaceScene("/views/Login.fxml");
            primaryStage.setTitle("Bike Rental");
            primaryStage.show();
        } catch (Exception e) {
            System.err.println(e);
            throw e;
        }
    }

}



