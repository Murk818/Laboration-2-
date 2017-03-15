/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recipesearch;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import se.chalmers.ait.dat215.lab2.Ingredient;
import se.chalmers.ait.dat215.lab2.Recipe;

/**
 * FXML Controller class
 *
 * @author Murk
 */
public class Detailed_viewController implements Initializable {

    @FXML
    private Button backBtn;
    @FXML
    private ImageView recipeImage;
    @FXML
    private Text descriptionField;
    @FXML
    private Text portionsField;
    @FXML
    private AnchorPane instructionsPane;
    @FXML
    private Text instructionsField;
    @FXML
    private AnchorPane ingredientsPane;
    @FXML
    private Text ingredientsField;

    private RecipeSearchController recipeSearchController;
    @FXML
    private AnchorPane detailed_view;
    @FXML
    private Text titleField;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        descriptionField.setWrappingWidth(350);
        instructionsField.setWrappingWidth(350);
        ingredientsField.setWrappingWidth(190);
        backBtn.setOnAction(e-> backToSearch());
    } 
    public void injectMainController(RecipeSearchController main){
        recipeSearchController = main;
    }
    
    public void showRecipe(Recipe recipe){
        this.recipeImage.setImage(recipe.getFXImage());
        this.titleField.setText(recipe.getName());
        this.descriptionField.setText("Huvudingrediens: " + recipe.getMainIngredient() +"\n" + recipe.getDescription());
        this.portionsField.setText("Portioner: " + recipe.getServings());
        this.instructionsField.setText(recipe.getDescription());
        this.ingredientsField.setText(getIngredientString(recipe));
        
        recipeSearchController.searchresult_view.setVisible(false);
        recipeSearchController.detailed_view.setVisible(true);
        recipeSearchController.detailed_view.toFront();
        
    }
    
    private String getIngredientString(Recipe r){
        String list = "";
        for(Ingredient i : r.getIngredients()){
            list = i.getAmount() + " " + i.getUnit() + " - " + i.getName() + "\n";
        }
        return list;
    }
    private void backToSearch(){
    	System.out.println("backToSearch");
    	recipeSearchController.detailed_view.setVisible(false);
    	recipeSearchController.searchresult_view.setVisible(true);
        recipeSearchController.detailed_view.toFront();
    }
    
}
