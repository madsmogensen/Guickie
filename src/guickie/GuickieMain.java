package guickie;

import java.util.HashMap;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author Mads S. Mogensen
 */
public class GuickieMain extends Application {

    Guickie gui = new Guickie();
    HashMap<String,Object> guiElements = new HashMap<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        gui.build();
        
        guiElements = gui.getIdList();
        Button btn = (Button)guiElements.get("buttonID");
        btn.setOnAction((a) -> {
            //DO STUFF HERE
        });
        
        gui.start(primaryStage);
    }
}
