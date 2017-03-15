
package recipesearch;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import se.chalmers.ait.dat215.lab2.Recipe;
import se.chalmers.ait.dat215.lab2.RecipeDatabase;
import se.chalmers.ait.dat215.lab2.SearchFilter;

public class RecipeSearchController implements Initializable {
    private RecipeDatabase db = RecipeDatabase.getSharedInstance();
    @FXML private MenuBar menuBar;
    @FXML public AnchorPane detailed_view;
    @FXML public AnchorPane searchresult_view;
    @FXML public StackPane stackPane;
    @FXML public Detailed_viewController detailed_viewController;
    @FXML public Searchresult_viewController searchresult_viewController;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        searchresult_viewController.injectMainController(this);
        detailed_viewController.injectMainController(this);
        
        detailed_view.setVisible(false);
        searchresult_view.setVisible(true);
    }
    
    @FXML 
    protected void openAboutActionPerformed(ActionEvent event) throws IOException{
    
        ResourceBundle bundle = java.util.ResourceBundle.getBundle("recipesearch/resources/RecipeSearch");
        Parent root = FXMLLoader.load(getClass().getResource("recipe_search_about.fxml"), bundle);
        Stage aboutStage = new Stage();
        aboutStage.setScene(new Scene(root));
        aboutStage.setTitle(bundle.getString("about.title.text"));
        aboutStage.initModality(Modality.APPLICATION_MODAL);
        aboutStage.setResizable(false);
        aboutStage.showAndWait();
    }
    
    @FXML 
    protected void closeApplicationActionPerformed(ActionEvent event) throws IOException{
        
        Stage addressBookStage = (Stage) menuBar.getScene().getWindow();
        addressBookStage.hide();
    }   
    public void search(List<Object> keywords){
        List<Recipe> recipes = db.search(new SearchFilter((String)keywords.get(0), 0, (String)keywords.get(1), (Integer)keywords.get(2), (String)keywords.get(3)));
        searchresult_viewController.searchViewUpdate(keywords, recipes);
        searchresult_view.toFront();
        searchresult_view.setVisible(true);
    }
}
