package guickie;

import java.util.HashMap;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Mads S. Mogensen
 */
public abstract class GuickieBuilder extends Application {

    private HashMap<String, Object> idList = new HashMap<>();
    private Pane root = root();
    private Object currentLocation = root;
    private Object currentElement;
    private Object lastEdit;

    public HashMap<String, Object> getIdList() {
        return idList;
    }

    public abstract void build();

    private Pane root() {
        Pane newRoot = new VBox();
        newRoot.setId("root");
        idList.put(newRoot.getId(), newRoot);
        return newRoot;
    }

    public GuickieBuilder vertical(String ID) {
        if (idList.containsKey(ID)) {
            currentElement = idList.get(ID);
        } else {
            VBox newVertical = new VBox();
            newVertical.setId(ID);
            currentElement = newVertical;
            idList.put(ID, currentElement);
        }
        lastEdit = currentElement;
        addElementToParent();
        return this;
    }

    public GuickieBuilder horizontal(String ID) {
        if (idList.containsKey(ID)) {
            currentElement = idList.get(ID);
        } else {
            HBox newVertical = new HBox();
            newVertical.setId(ID);
            currentElement = newVertical;
            idList.put(ID, currentElement);
        }
        lastEdit = currentElement;
        addElementToParent();
        return this;
    }

    public GuickieBuilder tabpane(String ID) {
        if (idList.containsKey(ID)) {
            currentElement = idList.get(ID);
        } else {
            TabPane newTabPane = new TabPane();
            newTabPane.setId(ID);
            currentElement = newTabPane;
            idList.put(ID, currentElement);
        }
        addElementToParent();
        return this;
    }

    public GuickieBuilder tab(String ID) {
        if (idList.containsKey(ID)) {
            currentLocation = idList.get(ID);
        } else {
            Tab tab = new Tab(ID);
            tab.setId(ID);
            tab.setClosable(false);
            tab.setContent(new VBox());
            currentElement = tab;
            idList.put(ID, currentElement);
            if (currentLocation.getClass().equals(Tab.class)) {
                Tab otherTab = (Tab) currentLocation;
                currentLocation = otherTab.getTabPane();
            }
            addElementToParent();
        }
        return this;
    }

    public GuickieBuilder label(String ID) {
        if (idList.containsKey(ID)) {
            currentElement = idList.get(ID);
        } else {
            Label label = new Label(ID);
            label.setId(ID);
            currentElement = label;
            idList.put(ID, currentElement);

        }
        addElementToParent();
        return this;
    }

    public GuickieBuilder button(String ID) {
        if (idList.containsKey(ID)) {
            currentElement = idList.get(ID);
        } else {
            Button button = new Button(ID);
            button.setId(ID);
            currentElement = button;
            idList.put(ID, currentElement);

        }
        addElementToParent();
        return this;
    }

    public GuickieBuilder textfield(String ID) {
        if (idList.containsKey(ID)) {
            currentElement = idList.get(ID);
        } else {
            TextField textField = new TextField();
            textField.setPromptText(ID);
            textField.setId(ID);
            currentElement = textField;
            idList.put(ID, currentElement);
        }
        addElementToParent();
        return this;
    }

    public GuickieBuilder text(String text) {
        switch (currentElement.getClass().toString()) {
            case "class javafx.scene.control.Label":
                Label label = (Label) currentElement;
                label.setText(text);
                break;
            case "class javafx.scene.control.Button":
                Button button = (Button) currentElement;
                button.setText(text);
                break;
            case "class javafx.scene.control.TextField":
                TextField textField = (TextField) currentElement;
                textField.setPromptText(text);
                break;
            default:
                break;
        }
        lastEdit = currentElement;
        return this;
    }

    public GuickieBuilder width(int size) {
        switch (lastEdit.getClass().toString()) {
            case "class javafx.scene.control.Label":
            case "class javafx.scene.control.Button":
            case "class javafx.scene.control.TextField":
                Control control = (Control) lastEdit;
                control.setMinWidth(size);
                break;
            case "class javafx.scene.control.Tab":
                Tab tab = (Tab) lastEdit;
                TabPane parentTabPane = tab.getTabPane();
                parentTabPane.setTabMinWidth(size);
                break;
            case "class javafx.scene.control.TabPane":
                TabPane tabPane = (TabPane) lastEdit;
                tabPane.setMinWidth(size);
                break;
            case "class javafx.scene.layout.VBox":
            case "class javafx.scene.layout.HBox":
                Pane pane = (Pane) lastEdit;
                pane.setMinWidth(size);
                break;
            default:
                break;
        }
        return this;
    }

    public GuickieBuilder height(int size) {
        switch (lastEdit.getClass().toString()) {
            case "class javafx.scene.control.Label":
            case "class javafx.scene.control.Button":
            case "class javafx.scene.control.TextField":
                Control control = (Control) lastEdit;
                control.setMinHeight(size);
                break;
            case "class javafx.scene.control.Tab":
                Tab tab = (Tab) lastEdit;
                TabPane parentTabPane = tab.getTabPane();
                parentTabPane.setTabMinHeight(size);
                break;
            case "class javafx.scene.control.TabPane":
                TabPane tabPane = (TabPane) lastEdit;
                tabPane.setMinHeight(size);
                break;
            case "class javafx.scene.layout.VBox":
            case "class javafx.scene.layout.HBox":
                Pane pane = (Pane) lastEdit;
                pane.setMinHeight(size);
                break;
            default:
                break;
        }
        return this;
    }

    public Object custom() {
        return currentElement;
    }

    public GuickieBuilder addElementToParent() {
        //null check
        if (currentElement != null) {
            //check element type
            switch (currentElement.getClass().toString()) {
                case "class javafx.scene.control.Label":
                case "class javafx.scene.control.Button":
                case "class javafx.scene.control.TextField":
                    Control element = (Control) currentElement;
                    if (!currentLocation.getClass().equals(TabPane.class)) {
                        if (currentLocation.getClass().equals(Tab.class)) {
                            Tab location = (Tab) currentLocation;
                            VBox tabContent = (VBox) location.getContent();
                            tabContent.getChildren().add(element);
                            location.setContent(tabContent);
                        } else {
                            Pane location = (Pane) currentLocation;
                            location.getChildren().add(element);
                        }
                        currentElement = element;
                        lastEdit = currentElement;
                    }
                    break;
                case "class javafx.scene.control.Tab":
                    Tab tab = (Tab) currentElement;
                    if (currentLocation.getClass().equals(TabPane.class)) {
                        TabPane tabPane = (TabPane) currentLocation;
                        tabPane.getTabs().add(tab);
                        currentLocation = tab;
                        lastEdit = currentLocation;
                    } else {
                        System.out.println("not a tabpane");
                    }
                    break;
                case "class javafx.scene.layout.VBox":
                case "class javafx.scene.layout.HBox":
                case "class javafx.scene.control.TabPane":
                    Node newLocation = (Node) currentElement;
                    if (currentLocation.getClass().equals(Tab.class)) {
                        Tab currentTab = (Tab) currentLocation;
                        TabPane parentLocation = (TabPane) currentTab.getTabPane();
                        currentLocation = currentTab.getContent();
                    }
                    Pane parentLocation = (Pane) currentLocation;
                    parentLocation.getChildren().add(newLocation);
                    currentLocation = newLocation;
                    lastEdit = currentLocation;
                    break;
                default:
                    System.out.println("default????");
                    break;
            }
            if (currentLocation.getClass().equals(VBox.class) || currentLocation.getClass().equals(HBox.class)) {

            }
        }
        return this;
    }

    public GuickieBuilder clearSelection() {
        currentLocation = root;
        currentElement = null;
        lastEdit = null;
        return this;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
