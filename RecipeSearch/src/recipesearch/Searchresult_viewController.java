/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recipesearch;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import se.chalmers.ait.dat215.lab2.Recipe;
import se.chalmers.ait.dat215.lab2.RecipeDatabase;
import se.chalmers.ait.dat215.lab2.SearchFilter;

/**
 * FXML Controller class
 *
 * @author Murk
 */
public class Searchresult_viewController implements Initializable {
	@FXML
	private ChoiceBox<String> cuisineCB;
	@FXML
	private ChoiceBox<String> difficultyCB;
	@FXML
	private ChoiceBox<String> mainIngredientCB;
	@FXML
	private Slider timeSlider;
	@FXML
	private TextField maxCostField;
        @FXML
        private Button filterbutton;
	@FXML
	private TableColumn<Recipe, String> nameColumn;
	@FXML
	private TableColumn<Recipe, String> timeColumn;
	@FXML
	private TableColumn<Recipe, String> difficultyColumn;
	@FXML
	private TableColumn<Recipe, String> portionColumn;
	@FXML
	private TableColumn<Recipe, String> priceColumn;
	//@FXML
	//private TableColumn<Recipe, String> mainIngrededientColumn;

	@FXML
	private TableView<Recipe> resultTable;
	private RecipeDatabase db;
	private List<Recipe> searchResults;
	private RecipeSearchController recipeSearchController;
	@FXML
	private AnchorPane searchresult_view;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		db = RecipeDatabase.getSharedInstance();
	       
	       //Default values in filter
		cuisineCB.setItems(RecipeSearch.cuisines);
                cuisineCB.setValue("Cuisine");
		difficultyCB.setItems(RecipeSearch.levels);
                difficultyCB.setValue("Svårighetsgrad");
		mainIngredientCB.setItems(RecipeSearch.ingredients);
                mainIngredientCB.setValue("Main Ingredient");
	       
	       //Sets what attribute the column should retrieve from the Recipe objects.
	       nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
	       timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
	       difficultyColumn.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
	       portionColumn.setCellValueFactory(new PropertyValueFactory<>("servings"));
	       priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
               //mainIngrededientColumn.setCellValueFactory(new PropertyValueFactory<>("ingredients"));
	       
	       //Adds a mouselistener for the table to look for double clicks.
	       resultTable.setOnMouseClicked((MouseEvent event) -> {
	           if(event.getClickCount() > 1){
	               Recipe selected = resultTable.getSelectionModel().getSelectedItem();
	               //recipeSearchController.detailed_viewController.showRecipe(selected);
	           }
	       });

	}
	private ObservableList<Recipe> getSearchAsObservableList(){
        ObservableList<Recipe> list = FXCollections.observableArrayList();
        
        for(Recipe r : searchResults){
            list.add(r);
        }
        
        return list;
    }
    
    public void injectMainController(RecipeSearchController main){
        this.recipeSearchController = main;
    }
    
    
    @FXML
    private void filter(){
        //Gather info
        String ingredient = mainIngredientCB.getValue();
        String cuisine = cuisineCB.getValue();
        String level = difficultyCB.getValue();
        int maxTime = (int) timeSlider.getValue();
        int maxPrice = 0;
        
        String sMaxPrice = maxCostField.getText();
        if(!sMaxPrice.isEmpty())
        {
            maxPrice = Integer.parseInt(sMaxPrice); 
        }
        
        
        //Do search
        searchResults = db.search(new SearchFilter(level, maxTime, cuisine, maxPrice, ingredient));
        
        updateResults();
    }
    
    public void searchViewUpdate(List<Object> keywords, List<Recipe> recipes){
        searchResults = recipes;
        updateResults();
    }
    private void updateResults(){
        resultTable.setItems(getSearchAsObservableList());
    }
    
    private void setFilterFields(List<Object> keywords){
        
    	mainIngredientCB.setValue((String)keywords.get(3) == null ? "Main Ingredient" : (String)keywords.get(3));
        cuisineCB.setValue((String)keywords.get(1) == null ? "Cuisine" : (String)keywords.get(1));
        difficultyCB.setValue((String)keywords.get(0) == null ? "Svårighetsgrad" : (String)keywords.get(0));
        maxCostField.setText(keywords.get(2).toString());
    }
    @FXML
    public void searchEventListener(ActionEvent e){
        try
        {
        List<Object> keywords = parseString();
        
        recipeSearchController.search(keywords);
        }
        catch(IndexOutOfBoundsException f){
         //Do nothing if no text is entered   
        }
    }
    
    private List<Object> parseString(){
        String cuisine = null;
        String level = null;
        String ingredient = null;
        Integer maxPrice = 0;
        List<Object> keys = new ArrayList<>();
        
        keys.add(level);
        keys.add(cuisine);
        keys.add(maxPrice);
        keys.add(ingredient);
        return keys;
    }
    
    private String firstLetterToUpperCase(String word){
        char letter = Character.toUpperCase(word.charAt(0));
        String tmp = word.substring(1);
        word = letter + tmp;
        return word;
    }
    
    private String toNumber(String word){
        if(word.contains("kr")){
            word = word.substring(0, word.length()-2);
        }
        return word;
    }
}


