package application;
	
import application.Main.FoodItem;
import application.Main.FoodListItem;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
  	    BorderPane bPane = new BorderPane();
        Scene scene = new Scene(bPane,1600,900);
      
        // top pane
        HBox HBoxTop = new HBox(500);
        Label title = new Label("NomNom Meal Prep Program.");
        title.setUnderline(true);
        title.setFont(new Font("Arial",20));
        title.setMinWidth(350);
        Button loadFoodButton = new Button();
        loadFoodButton.setText("Load Food");
        HBoxTop.getChildren().addAll(title,loadFoodButton);
        HBoxTop.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(title, Priority.ALWAYS);
        HBox.setHgrow(loadFoodButton, Priority.ALWAYS);
        HBoxTop.setMinHeight(50);
        bPane.setTop(HBoxTop);
      
        // right pane
        ObservableList<FoodItem> menuList = FXCollections.observableArrayList();
        menuList.add(new FoodItem("First Food", 1,2,3,4,5,"Add"));
        menuList.add(new FoodItem("Second Food", 6,7,8,9,10,"Add"));
        menuList.add(new FoodItem("Third Food", 11,12,13,14,15,"Add"));
        ListView<FoodItem> listViewRight = new ListView<>(menuList);
        
        listViewRight.setCellFactory(param -> new FoodListItem());
        //VBox VBoxRight = new VBox();
				//VBoxRight.getChildren().addAll(getHeader(),listViewRight);
        Label VBoxRightLabel = new Label("Meal");
        VBoxRightLabel.setFont(new Font("Arial",18));
        //VBoxRightLabel.setAlignment(Pos.TOP_CENTER);
        VBox VBoxRight = new VBox(10,VBoxRightLabel);
        VBoxRight.getChildren().addAll(getHeader(),listViewRight);
        VBoxRight.setAlignment(Pos.TOP_CENTER);
        VBoxRight.setMinWidth(600);
        bPane.setRight(VBoxRight);
        BorderPane.setAlignment(VBoxRight, Pos.CENTER_LEFT);
        
        
        // left pane
        ObservableList<FoodItem> foodList = FXCollections.observableArrayList();
        foodList.add(new FoodItem("First Food", 1,2,3,4,5,"Remove"));
        foodList.add(new FoodItem("Second Food", 6,7,8,9,10,"Remove"));
        foodList.add(new FoodItem("Third Food", 11,12,13,14,15,"Remove"));
        ListView<FoodItem> listViewLeft = new ListView<>(foodList);
        
        listViewLeft.setCellFactory(param -> new FoodListItem());
        //VBox VBoxLeft = new VBox();
        //VBoxLeft.getChildren().addAll(getHeader(),listViewLeft);
        Label VBoxLeftLabel = new Label("Food List");
        VBoxLeftLabel.setFont(new Font("Arial",18));
        //VBoxLeftLabel.setAlignment(Pos.TOP_CENTER);
        VBox VBoxLeft = new VBox(10, VBoxLeftLabel);
        VBoxLeft.getChildren().addAll(getHeader(),listViewLeft);
        VBoxLeft.setAlignment(Pos.TOP_CENTER);
        VBoxLeft.setMinWidth(600);
        bPane.setLeft(VBoxLeft);
  
        /* bottom pane
         * This will have 3 sections:
         * 1) Add Item Box.
         * 2) Filters Box.
         * 3) Additional Resources
        */
        
        HBox HBoxBottom = new HBox(200);
        
        //Begin Code on Item Details Box
        
        GridPane ItemDetailsBox = new GridPane();
        constructItemDetailsBox(ItemDetailsBox);
             
        HBoxBottom.getChildren().add(ItemDetailsBox);        
        
        // Filter Options
        Label filterLabel = new Label("Filter List Options");
        filterLabel.setFont(new Font("Arial",18));
        VBox bottomCenter = new VBox(10, filterLabel);  //Add to the bottom center of the screen
        
        bottomCenter.setAlignment(Pos.CENTER);
        GridPane filterArea = new GridPane();
        HBox filter = new HBox(10);
        ComboBox<String> macroSelect = new ComboBox<String>();
        macroSelect.getItems().addAll("Carbs", "Calories", "Fat", "Fiber", "Protein");
        macroSelect.setPromptText("Macro");
        macroSelect.setMinWidth(100);
        ComboBox<String> comparatorSelect = new ComboBox<String>();
        comparatorSelect.getItems().addAll("=", ">", "<", ">=", "<=");
        comparatorSelect.setPromptText("Comparator");
        TextField value = new TextField("Enter value");
        
        //Nutrition Links Box
        VBox NutritionLinksBox = new VBox();
        Label NutritionLinksLabel = new Label("Nutrition Links");
        NutritionLinksLabel.setFont(new Font("Arial",18));
        NutritionLinksBox.getChildren().add(NutritionLinksLabel);
        Hyperlink link = new Hyperlink("https://tinyurl.com/yccjpbok");
       NutritionLinksBox.getChildren().add(new HBox(link));

        
        
        
        
        
        
        filter.getChildren().addAll(macroSelect, comparatorSelect, value);
        Button filterButton = new Button("Add Filter");
        filterButton.setMinWidth(100);
        Button clearButton = new Button("Clear Filters");
        clearButton.setMinWidth(100);
        Button applyButton = new Button ("Apply Filters");
        applyButton.setMinWidth(100);
        filterArea.add(filter, 0, 0);
        ColumnConstraints cc = new ColumnConstraints();
        filterArea.add(filterButton, 1, 0);
        filterArea.add(clearButton, 1, 1);
        filterArea.add(applyButton, 1, 2);
        filterArea.setHgap(10);
        filterArea.setVgap(10);
        bottomCenter.getChildren().add(filterArea);
        HBoxBottom.getChildren().addAll(bottomCenter, NutritionLinksBox);
        bPane.setBottom(HBoxBottom);
        VBox appliedFilters = new VBox(5);
        appliedFilters.setAlignment(Pos.CENTER);
        filterButton.setOnAction( new EventHandler<ActionEvent>(){
        	@Override
            public void handle(ActionEvent e) {
        	applyFilter(macroSelect, comparatorSelect, value, appliedFilters);
        }});
        
        clearButton.setOnAction( new EventHandler<ActionEvent>(){
        	@Override
            public void handle(ActionEvent e) {
        	appliedFilters.getChildren().clear();
        }});
        
        applyButton.setOnAction( new EventHandler<ActionEvent>(){
        	@Override
            public void handle(ActionEvent e) {
        	System.out.println("Not implemented yet");
        }});
        filterArea.add(appliedFilters, 0, 1);
        //RowConstraints rr = new RowConstraints();
        
        
        
  
        primaryStage.setTitle("NomNomNom");
        //show stage
        primaryStage.setScene(scene);
        primaryStage.show();
	}
  /**
   * @param ItemDetailsBox
   */
  private void constructItemDetailsBox(GridPane ItemDetailsBox) {
    ColumnConstraints cc = new ColumnConstraints();
    //Column 1 setup
    cc.setPercentWidth(30);
    cc.setHalignment(HPos.LEFT);
    ItemDetailsBox.getColumnConstraints().add(cc);
    
  //Column 2 setup
    cc.setPercentWidth(70);
    cc.setHalignment(HPos.CENTER);
    ItemDetailsBox.getColumnConstraints().add(cc);
    
//  //Column 3 setup
//    cc.setPercentWidth(20);
//    cc.setHalignment(HPos.LEFT);
//    ItemDetailsBox.getColumnConstraints().add(cc);
    
    Button AddItemButton = new Button("Add Item");
    ItemDetailsBox.add(AddItemButton, 0, 1);

   // page.add(Node, colIndex, rowIndex, colSpan, rowSpan):
    Label ItemDetailsBoxLabel = new Label("Item Details");
    ItemDetailsBoxLabel.setFont(new Font("Arial",18));
    ItemDetailsBox.add(ItemDetailsBoxLabel, 0, 0, 2, 1);
    
    String LabelString;
    TextField LabelField;
    int row = 1;
    
    //Name Field Build
    LabelString = "Name";
    TextField NameField = new TextField("Enter " + LabelString);
    LabelField = NameField;
    row++;
            
    addItemDetailsRow(ItemDetailsBox, LabelString, LabelField, row);
    
    
    //Calories Field Build
    LabelString = "Calories";
    TextField CaloriesField = new TextField("Enter " + LabelString);
    LabelField = CaloriesField;
    row++;
            
    addItemDetailsRow(ItemDetailsBox, LabelString, LabelField, row);
    
    //Fats Field Build
    LabelString = "Fats";
    TextField FatsField = new TextField("Enter " + LabelString);
    LabelField = FatsField;
    row++;
            
    addItemDetailsRow(ItemDetailsBox, LabelString, LabelField, row);
    
    //Carbs Field Build
    LabelString = "Carbs";
    TextField CarbsField = new TextField("Enter " + LabelString);
    LabelField = CarbsField;
    row++;
            
    addItemDetailsRow(ItemDetailsBox, LabelString, LabelField, row);

    //Fiber Field Build
    LabelString = "Fats";
    TextField FiberField = new TextField("Enter " + LabelString);
    LabelField = FiberField;
    row++;
            
    addItemDetailsRow(ItemDetailsBox, LabelString, LabelField, row);
    
    //Protein Field Build
    LabelString = "Protein";
    TextField ProteinField = new TextField("Enter " + LabelString);
    LabelField = ProteinField;
    row++;
            
    addItemDetailsRow(ItemDetailsBox, LabelString, LabelField, row);
    
  }
  /**
   * @param ItemDetailsBox
   * @param LabelString
   * @param LabelField
   * @param row
   */
  private void addItemDetailsRow(GridPane ItemDetailsBox, String LabelString, TextField LabelField, int row) {
    ItemDetailsBox.add(new Label(LabelString), 0, row);
    ItemDetailsBox.add(LabelField, 1, row);
  }
	public Node getHeader() {
		GridPane gPane = new GridPane();
		int numCols=7;
		for (int col = 0 ; col < numCols; col++ ) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(100/(numCols*1.0));
            //cc.setFillWidth(true);
            //cc.setHgrow(Priority.ALWAYS);
            gPane.getColumnConstraints().add(cc);
		}
		gPane.add(new Label("Name"), 0, 0);
		gPane.add(new Label("Cals"), 1, 0);
		gPane.add(new Label("Fat"), 2, 0);
		gPane.add(new Label("Carbs"), 3, 0);
		gPane.add(new Label("Fiber"), 4, 0);
		gPane.add(new Label("Protien"), 5, 0);
		gPane.add(new Label(""), 6, 0);
		return gPane;
	}
	
	public void applyFilter(ComboBox<String> macro, ComboBox<String> comparator, TextField value, VBox filters) {
		int filterValue;
		String filter;
		if (macro.getValue() == null || macro.getValue().toString().isEmpty() || comparator.getValue() == null || comparator.getValue().toString().isEmpty()){
                    System.out.println("No value selected for macro or comparator");
                    return;
		}
        try {
        	filterValue = Integer.parseInt(value.getText());
        } catch(NumberFormatException e) {
        	System.out.println("Filter value must be numeric");
        	return;
        }
        
        if (filterValue < 0) {
        	System.out.println("Negative values not allowed");
        	return;
        }
        filter = macro.getValue() + " " + comparator.getValue() + " " + filterValue;
        filters.getChildren().add(new Label(filter));
        
		
	}
	public static class FoodItem {
		String name;
		int cals;
		int fat;
		int carbs;
		int fiber;
		int protien;
		String buttonText;
		FoodItem(String s, int cal, int fa , int carb,int fib,int pro,String b){
			name=s;
			cals=cal;
			fat = fa;
			carbs = carb;
			fiber = fib;
			protien = pro;
			buttonText=b;
		}
		public String getName() {
			return name;
		}
		public int getCals() {
			return cals;
		}
		public int getFat() {
			return fat;
		}
		public int getCarbs() {
			return carbs;
		}
		public int getFiber() {
			return fiber;
		}
		public int getProtien() {
			return protien;
		}
		public String getButtonText() {
			return buttonText;
		}
		
	}
	public static class FoodListItem extends ListCell<FoodItem>{
		//HBox hBox = new HBox();
		GridPane gPane = new GridPane();
		Label nameLabel = new Label();
		Label calsLabel = new Label();
		Label fatLabel = new Label();
		Label carbsLabel = new Label();
		Label fiberLabel = new Label();
		Label protienLabel = new Label();
		Button addButton = new Button();
		public FoodListItem () {
			super();
			//hBox.getChildren().addAll(nameLabel,calsLabel,addButton);
			int numCols=7;
			for (int col = 0 ; col < numCols; col++ ) {
	            ColumnConstraints cc = new ColumnConstraints();
	            cc.setPercentWidth(100/(numCols*1.0));
	            //cc.setFillWidth(true);
	            //cc.setHgrow(Priority.ALWAYS);
	            gPane.getColumnConstraints().add(cc);
			}
			gPane.add(nameLabel, 0, 0);
			gPane.add(calsLabel, 1, 0);
			gPane.add(fatLabel, 2, 0);
			gPane.add(carbsLabel, 3, 0);
			gPane.add(fiberLabel, 4, 0);
			gPane.add(protienLabel, 5, 0);
			gPane.add(addButton, 6, 0);
		}
		@Override
        protected void updateItem(FoodItem item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);  // No text in label of super class
            if (empty) {
                setGraphic(null);
            } else {
                nameLabel.setText(item.getName()!=null ? item.getName() : "<null>");
                calsLabel.setText(item.getCals()+"");
                fatLabel.setText(item.getFat()+"");
                carbsLabel.setText(item.getCarbs()+"");
                fiberLabel.setText(item.getFiber()+"");
                protienLabel.setText(item.getProtien()+"");
                addButton.setText("Add");
                //setGraphic(hBox);
                setGraphic(gPane);
            }
        }
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
