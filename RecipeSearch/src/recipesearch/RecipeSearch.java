
package recipesearch;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RecipeSearch extends Application {
    
    public static final ObservableList<String> cuisines = FXCollections.observableArrayList("Cuisine","Sverige", "Grekland", "Asien", "Afrika", "Frankrike", "Indien");
    public static final ObservableList<String> levels = FXCollections.observableArrayList("Svårighetsgrad","Lätt", "Mellan", "Svår");
    public static final ObservableList<String> ingredients = FXCollections.observableArrayList("Main Ingredient","Kött", "Fisk", "Kyckling", "Vegetarisk");
    
    @Override
    public void start(Stage stage) throws Exception {
        
        ResourceBundle bundle = java.util.ResourceBundle.getBundle("recipesearch/resources/RecipeSearch");
        
        Parent root = FXMLLoader.load(getClass().getResource("recipe_search.fxml"), bundle);
        
        Scene scene = new Scene(root, 600, 400);
        
        stage.setTitle(bundle.getString("application.name"));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
