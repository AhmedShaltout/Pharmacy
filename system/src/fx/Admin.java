package fx;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pharmacy.Activity;
import pharmacy.Manager;
import pharmacy.Medicine;

public class Admin {
	private static boolean stop=false;
	private static Medicine U_medicine;
	private static float totalPrice=0;
	//////////////////Objects Will Be Created///////////////////
	Stage window;
	Scene scene;
	BorderPane layout;
	GridPane glass;
	static Button searchButton, updateButton, addButton, makeBillButton, lowQuantityButton, weeklyreportButton, add;
	TextField searchBox, nameField, locationField, formulaField, indicationsField, allowedField, idField, priceField, quantityField;
	Label name, location, formula, indications, allowedPersons, id, price, quantity;
	ImageView firstView, secondView;
	HBox header;
	/////////////UpdateGlass Objects/////////////////////////
	GridPane updateGlass;
	Label U_name, U_location, U_quantity, U_price;
	TextField U_nameField, U_locationField, U_quantityField, U_priceField;
	Button update;
	////////////MakeBillGlass Objects///////////////////////
	GridPane makeBillGlass;
	Label M_id, M_quantity, bill;
	TextField M_idField, M_quantityField;
	Button calculate, M_add;
	TextArea M_area;
	//////////////LowQuantityGlass Objects//////////////////////
	GridPane lowQuantityGlass;
	static TableView<Medicine>table=new TableView<>();
	TableColumn<Medicine, Integer>idColumn;
	TableColumn<Medicine, String>nameColumn;
	TableColumn<Medicine, Short>quantityColumn;
	TableColumn<Medicine, String>updateColumn;
	TableColumn<Medicine, String>deleteColumn;
	///////////////WeeklyReportGlass Objects///////////////////////////////////////
	GridPane weeklyReportGlass;
	static TableView<Activity>weeklyReportTable;
	TableColumn<Activity, String>actionColumn;
	TableColumn<Activity, Short>dateColumn;
	/////////////////////SearchResultGlass Objects/////////////////////////////////
	GridPane searchResultGlass;
	static TableView <Medicine>searchResultTable;
	TableColumn<Medicine, Integer>idSearchColumn;
	TableColumn<Medicine, String>nameSearchColumn;
	TableColumn<Medicine, String>formulaSearchColumn;
	TableColumn<Medicine, String>indicationsSearchColumn;
	TableColumn<Medicine, String>allowedPersonsSearchColumn;
	TableColumn<Medicine, Float>priceSearchColumn;
	TableColumn<Medicine, Short>locationSearchColumn;
	TableColumn<Medicine, Short>quantitySearchColumn;
	TableColumn<Medicine, String>deleteSearchColumn;
	TableColumn<Medicine, String>updateSearchColumn;
	@SuppressWarnings("unchecked")
	public Admin(){
		Manager.QuantityCheckerForAdmin();
		//////////////////Header Components///////////////////
		searchBox=new TextField();
		searchBox.setPromptText("search about specific medicine");
		searchBox.setPrefHeight(30);
		searchBox.setPrefWidth(200);
		
		searchButton=new Button("search");
		searchButton.setId("howTo22");
		searchButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ArrayList<Medicine> list=new ArrayList<>();
				try{
					if(Exceptions.isString(searchBox.getText())||Exceptions.isNumber(searchBox.getText())){
						String search=searchBox.getText();
						Medicine m;
						if((m=Manager.searchMedicineByName(search))!=null)
							list.add(m);
						list=HomePage.compare(list, Manager.searchMedicineByLetters(search));
						Integer ID=Integer.parseInt(searchBox.getText());
						if((m=Manager.searchMedicine(ID))!=null){
							ArrayList<Medicine>me=new ArrayList<>();
							me.add(m);
							list=HomePage.compare(list, me);
						}
					}
				}catch(Exception e){}
				finally {
					layout.setCenter(searchResultGlass);
					searchResultTable.setItems(FXCollections.observableList(list));
					list=new ArrayList<>();
					searchBox.setText("");
				}
			}
		});
		
		addButton= new Button("Add Medicine");
		addButton.setId("howTo2");
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				layout.setCenter(glass);
				Manager.QuantityCheckerForAdmin();
			}
		});
		
		updateButton=new Button("Update Medicine");
		updateButton.setId("howTo2");
		makeBillButton=new Button("Make Bill");
		makeBillButton.setId("howTo2");
		makeBillButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				layout.setCenter(makeBillGlass);
				Manager.QuantityCheckerForAdmin();
			}
		});
		
		lowQuantityButton=new Button("Low Quantity");
		lowQuantityButton.setId("howTo2");
		lowQuantityButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				layout.setCenter(lowQuantityGlass);
				Manager.QuantityCheckerForAdmin();
			}
		});
		weeklyreportButton=new Button("weekly Report");
		weeklyreportButton.setId("howTo2");
		weeklyreportButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				layout.setCenter(weeklyReportGlass);
				weeklyReportTable.setItems(FXCollections.observableList(Manager.getWeeklyReport()));
				Manager.QuantityCheckerForAdmin();
			}
		});
		
		header=new HBox();
		header.setPrefWidth(1060);
		header.setId("header2");
		header.getChildren().addAll(searchBox, searchButton, addButton, updateButton, makeBillButton, lowQuantityButton, weeklyreportButton);
		header.setPadding(new Insets(10,10,5,20));
		header.setSpacing(10);
		/////////////////////////AddGlass Components///////////////////////////
		name=new Label("Medicine Name: ");
		location= new Label("Medicine Location: ");
		formula=new Label("Medicine Formula: ");
		indications=new Label("Medicine Indications: ");
		allowedPersons=new Label("Allowed Persons For This Medicine: ");
		id=new Label("Medicine ID: ");
		price=new Label("Medicine Price: ");
		quantity=new Label("Medicine Quantity: ");
		name.setId("name");
		location.setId("name");
		formula.setId("name");
		indications.setId("name");
		allowedPersons.setId("name");
		id.setId("name");
		price.setId("name");
		quantity.setId("name");
		nameField=new TextField();
		locationField=new TextField();
		formulaField=new TextField();
		indicationsField=new TextField();
		allowedField=new TextField();
		idField=new TextField();
		priceField=new TextField();
		quantityField=new TextField();
		nameField.setId("nameField");
		locationField.setId("nameField");
		formulaField.setId("nameField");
		indicationsField.setId("nameField");
		allowedField.setId("nameField");
		idField.setId("nameField");
		priceField.setId("nameField");
		quantityField.setId("nameField");
		nameField.setPrefWidth(300);
		add=new Button("Add");
		add.setId("add");
		idField.textProperty().addListener(new ChangeListener<String>() {
		        @Override
		        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		            if (!newValue.matches("\\d*")) {
		            	idField.setText(newValue.replaceAll("[^\\d]", ""));
		            }
		        }
		        });
		quantityField.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (!newValue.matches("\\d*")) {
	            	quantityField.setText(newValue.replaceAll("[^\\d]", ""));
	            }
	        }
	        });
		locationField.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (!newValue.matches("\\d*")) {
	            	locationField.setText(newValue.replaceAll("[^\\d]", ""));
	            }
	        }
	        });
		add.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try{
					Exceptions.isString(nameField.getText());
					Exceptions.isString(formulaField.getText());
					Exceptions.isString(indicationsField.getText());
					Exceptions.isString(allowedField.getText());
					Exceptions.isInRange(locationField.getText());
					Exceptions.isInRange(quantityField.getText());
					Exceptions.isFloat(priceField.getText());
					if(Manager.add(new Medicine(Integer.parseInt(idField.getText()),
							Short.parseShort(locationField.getText()), 
							nameField.getText(),
							formulaField.getText(),
							indicationsField.getText(),
							allowedField.getText(),
							Short.parseShort(quantityField.getText()),
							Float.parseFloat(priceField.getText())))){
						Exceptions.success("The new medicine is added successfully");
						layout.setCenter(null);
						idField.setText("");locationField.setText("");nameField.setText("");
						formulaField.setText("");indicationsField.setText("");allowedField.setText("");
						quantityField.setText("");priceField.setText("");
					}
					else
						Exceptions.invalidInputWarning("There is medicine with this ID in the pharmacy!");
					Manager.QuantityCheckerForAdmin();
				} catch(Exception e){
					Exceptions.invalidInputWarning(e.getMessage());
				}
			}
		});
		glass=new GridPane();
		glass.setId("glass2");
		glass.setHgap(10);
		glass.setVgap(15);
		glass.setPadding(new Insets(100,20,20,20));
		GridPane.setConstraints(name,0,0);			GridPane.setConstraints(nameField,1,0);
		GridPane.setConstraints(location,0,1);		GridPane.setConstraints(locationField,1,1);
		GridPane.setConstraints(formula,0,2);		GridPane.setConstraints(formulaField,1,2);
		GridPane.setConstraints(indications,0,3);	GridPane.setConstraints(indicationsField,1,3);
		GridPane.setConstraints(allowedPersons,0,4);GridPane.setConstraints(allowedField,1,4);
		GridPane.setConstraints(id,0,5);			GridPane.setConstraints(idField,1,5);
		GridPane.setConstraints(price,0,6);			GridPane.setConstraints(priceField,1,6);
		GridPane.setConstraints(quantity,0,7);		GridPane.setConstraints(quantityField,1,7);
		GridPane.setConstraints(add,2,8);
		
		glass.getChildren().addAll(name, nameField, location, locationField, formula, formulaField, indications,
				indicationsField, allowedPersons, allowedField, id, idField, price, priceField, quantity, quantityField, add);
		////////////////MakeBillGlass Components//////////////////
		updateGlass=new GridPane();
		updateGlass.setHgap(30);
		updateGlass.setVgap(45);
		updateGlass.setPadding(new Insets(200,20,100,20));
		
		U_name=new Label("Name: ");
		U_location=new Label("New Location: ");
		U_quantity=new Label("New Quantity: ");
		U_price=new Label("New Price: ");
		U_name.setId("name");
		U_location.setId("name");
		U_quantity.setId("name");
		U_price.setId("name");
		U_nameField=new TextField();
		U_locationField=new TextField();
		U_quantityField=new TextField();
		U_priceField=new TextField();
		U_nameField.setDisable(true);
		U_nameField.setPrefWidth(235);
		U_locationField.setPrefWidth(235);
		update=new Button("Update");
		update.setId("add");
		updateButton.setDisable(true);
		update.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				try{
					U_quantityField.getText();
					U_locationField.getText();
					U_priceField.getText();
					Exceptions.isInRange(U_quantityField.getText());
					Exceptions.isInRange(U_locationField.getText());
					Exceptions.isFloat(U_priceField.getText());
					U_medicine.setLocation(Short.parseShort(U_locationField.getText()));
					U_medicine.setPrice(Float.parseFloat(U_priceField.getText()));
					U_medicine.setQuantity(Short.parseShort(U_quantityField.getText()));
					if(Manager.update(U_medicine)){
						Exceptions.success("medicine updated successfly");
						layout.setCenter(null);
						Manager.QuantityCheckerForAdmin();
					}
					else{
						Exceptions.invalidInputWarning("somthing went wrong please try again");
						layout.setCenter(null);
					}
				}catch(Exception e){
					Exceptions.invalidInputWarning(e.getMessage());
				}
			}
		});
		U_locationField.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (!newValue.matches("\\d*")) {
	            	U_locationField.setText(newValue.replaceAll("[^\\d]", ""));
	            }
	        }
	        });
		U_quantityField.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
	            if (!newValue.matches("\\d*")) {
	            	U_quantityField.setText(newValue.replaceAll("[^\\d]", ""));
	            }
	        }
	        });
		U_nameField.setId("U_nameField");
		U_locationField.setId("U_locationField");
		U_priceField.setId("U_locationField");
		U_quantityField.setId("U_locationField");
		
		GridPane.setConstraints(U_name,0,0);		GridPane.setConstraints(U_nameField,1,0);
		GridPane.setConstraints(U_location,2,0);	GridPane.setConstraints(U_locationField,3,0);
		GridPane.setConstraints(U_quantity,0,1);	GridPane.setConstraints(U_quantityField,1,1);
		GridPane.setConstraints(U_price,2,1);		GridPane.setConstraints(U_priceField,3,1);
		GridPane.setConstraints(update,4,2);
		updateGlass.getChildren().addAll(U_name, U_location, U_quantity, U_price, U_nameField, U_locationField,
											U_quantityField, U_priceField, update);
		///////////////////MakeBillGlass Components////////////////////////
		makeBillGlass= new GridPane();
		makeBillGlass.setHgap(30);
		makeBillGlass.setVgap(45);
		makeBillGlass.setPadding(new Insets(200,20,100,20));
		
		M_id=new Label("Medicine ID: ");
		M_quantity=new Label("Number Of Backets: ");
		bill=new Label("Bill: 0.0 $");
		M_id.setId("name");
		M_quantity.setId("name");
		bill.setId("name");
		M_idField=new TextField();
		M_quantityField=new TextField();
		calculate=new Button("Save");
		M_area=new TextArea();
		M_area.setPrefHeight(400);
		M_area.setPrefWidth(450);
		calculate.setId("add");
		calculate.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				Exceptions.success("Last Bill was "+totalPrice+" $");
				bill.setText("Bill: 0.0 $");
				totalPrice=0;
				M_area.setText("");
				layout.setCenter(null);
			}
		});
		M_idField.setId("U_nameField");
		M_quantityField.setId("U_nameField");
		M_area.setId("U_nameField");
		M_add=new Button("Add");
		M_add.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try{
					Exceptions.isInRange(M_quantityField.getText());
					Short s=Short.parseShort(M_quantityField.getText());
					Integer i=Integer.parseInt(M_idField.getText());
					Medicine medicine=Manager.bringN_P_QC(s,i);
					if(medicine!=null){
						float price=s*medicine.getPrice();
						M_area.setText(""+M_area.getText()+"Name: "+medicine.getName()+" // Price: "+price+" $\n");
						totalPrice+=price;
						bill.setText("Bill: "+totalPrice+" $");
					}
					else{
						Exceptions.invalidInputWarning("No enough quantity");
					}
					Manager.QuantityCheckerForAdmin();
					M_quantityField.setText("");
					M_idField.setText("");
				}catch(Exception e){
					Exceptions.invalidInputWarning("not found");
				}
			}
		});
		M_area.setEditable(false);
		M_add.setId("add");
		GridPane.setConstraints(M_id, 0,0);				GridPane.setConstraints(M_idField, 1,0);	
		GridPane.setConstraints(M_quantity, 0,1);		GridPane.setConstraints(M_quantityField, 1,1);
		GridPane.setConstraints(M_area, 2, 0, 2, 3);	GridPane.setConstraints(M_add, 1,2);
		GridPane.setConstraints(calculate, 2,3);		GridPane.setConstraints(bill, 3,3);
		
		makeBillGlass.getChildren().addAll(M_id, M_idField, M_quantity, M_quantityField, M_area, calculate, bill, M_add);
		/////////////////////////////LowQuantityGlass Components//////////////////
		idColumn=new TableColumn<>("ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
		nameColumn=new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
		quantityColumn=new TableColumn<>("Quantity");
		quantityColumn.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
		updateColumn=new TableColumn<>("update");
		updateColumn.setCellValueFactory(new PropertyValueFactory<>("Update"));
		deleteColumn= new TableColumn<>("Delete");
		deleteColumn.setCellValueFactory(new PropertyValueFactory<>("Delete"));
		table.getColumns().addAll(idColumn, nameColumn, quantityColumn, updateColumn, deleteColumn);
		table.getSelectionModel().setCellSelectionEnabled(true);
		ObservableList selectedCells = table.getSelectionModel().getSelectedCells();
		selectedCells.addListener(new ListChangeListener() {
		    @Override
		    public void onChanged(Change c) {
		    	try{
			        TablePosition tablePosition = (TablePosition) selectedCells.get(0);
			        Object val = tablePosition.getTableColumn().getCellData(tablePosition.getRow());
			        if(val.toString().equals("Upadte"))
			        {
			        	U_medicine =table.getSelectionModel().getSelectedItem();
	    				layout.setCenter(updateGlass);
	    				U_nameField.setPromptText(U_medicine.getName());
	    				U_locationField.setText(U_medicine.getLocation().toString());
	    				U_priceField.setText(U_medicine.getPrice().toString());
	    				U_quantityField.setText(U_medicine.getQuantity().toString());
	    				Manager.QuantityCheckerForAdmin();
			        }
			        else if(val.toString().equals("Delete")){
			        	if(Manager.delete(table.getSelectionModel().getSelectedItem().getID())){
			        		Exceptions.success("medicine deleted successfly");
			        		layout.setCenter(null);
			        		Manager.QuantityCheckerForAdmin();
			        	}
			        	else
			        		Exceptions.invalidInputWarning("Something went wrong please try again");
			        }
		    	}catch(Exception e){
		    		
		    	}
		    }
		});
		table.setMinHeight(558);
		idColumn.setMinWidth(100);
		nameColumn.setMinWidth(200);
		quantityColumn.setMinWidth(100);
		updateColumn.setMinWidth(100);
		deleteColumn.setMaxWidth(100);
		
		lowQuantityGlass=new GridPane();
		lowQuantityGlass.setPadding(new Insets(0, 200, 0, 250));
		GridPane.setConstraints(table, 0, 0);
		lowQuantityGlass.getChildren().add(table);
		//////////////////WeeklyReportGlass Components////////////////	
		actionColumn=new TableColumn<>("Action");
		actionColumn.setCellValueFactory(new PropertyValueFactory<>("action"));
		dateColumn=new TableColumn<>("Date");
		dateColumn.setMinWidth(100);
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
		actionColumn.setMinWidth(900);
		weeklyReportTable=new TableView<>();
		weeklyReportTable.getColumns().addAll(dateColumn, actionColumn);
		weeklyReportTable.setMinHeight(558);
		weeklyReportTable.setItems(FXCollections.observableList(Manager.getWeeklyReport()));
		weeklyReportGlass=new GridPane();
		weeklyReportGlass.setPadding(new Insets(0, 30, 0, 30));
		GridPane.setConstraints(weeklyReportTable, 0, 0);
		weeklyReportGlass.getChildren().add(weeklyReportTable);
		//////////////////////SearchResultGlass Components//////////////////
		searchResultTable=new TableView<>();
		idSearchColumn=new TableColumn<>("ID");
		idSearchColumn.setMinWidth(100);
		idSearchColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
		nameSearchColumn=new TableColumn<>("Name");
		nameSearchColumn.setMinWidth(150);
		nameSearchColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
		formulaSearchColumn=new TableColumn<>("Formula");
		formulaSearchColumn.setMinWidth(150);
		formulaSearchColumn.setCellValueFactory(new PropertyValueFactory<>("Formula"));
		indicationsSearchColumn=new TableColumn<>("Indications");
		indicationsSearchColumn.setMinWidth(150);
		indicationsSearchColumn.setCellValueFactory(new PropertyValueFactory<>("Indications"));
		allowedPersonsSearchColumn=new TableColumn<>("Allowed Persons");
		allowedPersonsSearchColumn.setMinWidth(150);
		allowedPersonsSearchColumn.setCellValueFactory(new PropertyValueFactory<>("AllowedPerson"));
		priceSearchColumn=new TableColumn<>("Price");
		priceSearchColumn.setMinWidth(100);
		priceSearchColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
		locationSearchColumn=new TableColumn<>("Locations");
		locationSearchColumn.setMinWidth(100);
		locationSearchColumn.setCellValueFactory(new PropertyValueFactory<>("Location"));
		quantitySearchColumn=new TableColumn<>("Quantity");
		quantitySearchColumn.setMinWidth(100);
		quantitySearchColumn.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
		updateSearchColumn=new TableColumn<>("Update");
		updateSearchColumn.setMinWidth(50);
		updateSearchColumn.setCellValueFactory(new PropertyValueFactory<>("Update"));
		deleteSearchColumn=new TableColumn<>("Delete");
		deleteSearchColumn.setMinWidth(50);
		deleteSearchColumn.setCellValueFactory(new PropertyValueFactory<>("Delete"));
		
		searchResultTable.getColumns().addAll(idSearchColumn, nameSearchColumn, formulaSearchColumn,
		indicationsSearchColumn, allowedPersonsSearchColumn, priceSearchColumn, locationSearchColumn, quantitySearchColumn ,updateSearchColumn, deleteSearchColumn);
		searchResultTable.getSelectionModel().setCellSelectionEnabled(true);
		ObservableList selected = searchResultTable.getSelectionModel().getSelectedCells();
		selected.addListener(new ListChangeListener() {
		    @Override
		    public void onChanged(Change c) {
		    	try{
			        TablePosition tablePosition = (TablePosition) selected.get(0);
			        Object val = tablePosition.getTableColumn().getCellData(tablePosition.getRow());
			        if(val.toString().equals("Upadte"))
			        {
			        	U_medicine =searchResultTable.getSelectionModel().getSelectedItem();
	    				layout.setCenter(updateGlass);
	    				U_nameField.setPromptText(U_medicine.getName());
	    				U_locationField.setText(U_medicine.getLocation().toString());
	    				U_priceField.setText(U_medicine.getPrice().toString());
	    				U_quantityField.setText(U_medicine.getQuantity().toString());
	    				Manager.QuantityCheckerForAdmin();
			        }
			        else if(val.toString().equals("Delete")){
			        	if(Manager.delete(searchResultTable.getSelectionModel().getSelectedItem().getID())){
			        		Exceptions.success("medicine deleted successfly");
			        		layout.setCenter(null);
			        		Manager.QuantityCheckerForAdmin();
			        	}
			        }
		    	}catch(Exception e){
		    		
		    	}
		    }
		});
		searchResultGlass=new GridPane();
		GridPane.setConstraints(searchResultTable, 0, 0);
		searchResultGlass.getChildren().add(searchResultTable);

		
		/////////////////////Layout Components/////////////////////////////
		firstView=new ImageView(new Image(getClass().getResourceAsStream("medical-wallpaper-HD10.jpg")));
		firstView.setFitHeight(610);
		firstView.setFitWidth(1070);
		
		layout=new BorderPane();
		layout.getChildren().add(firstView);
		layout.setTop(header);
		layout.setMinHeight(600);
		///////////////////Window Components///////////////
		scene=new Scene(layout);
		scene.getStylesheets().add(this.getClass().getResource("Pharmacy.css").toExternalForm());
		window=new Stage();
		window.setScene(scene);
		window.show();
		window.setResizable(false);
		window.setOnCloseRequest(new EventHandler<WindowEvent>() {
					
					@Override
					public void handle(WindowEvent event) {
						event.consume();
						closeProgram();
					}
				});
		
	}
	public static void getMedicine(ArrayList<Medicine> lst){
		ObservableList<Medicine>list= FXCollections.observableList(lst);
		table.setItems(list);
	}
	public static void colorLowQuantity(boolean x) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				stop=true;
				while(stop){
					try {
						lowQuantityButton.setId("howTo3");
						Thread.sleep(700);
						lowQuantityButton.setId("howTo2");
						Thread.sleep(700);
					} catch (Exception e){
					}
				}
			}
		}).start();
	}
	public static void stopColoringAndDelTable(ArrayList<Medicine> empty,boolean False) {
		lowQuantityButton.setId("howTo2");
		stop=False;
		table.setItems(FXCollections.observableList(empty));
	}
	public void closeProgram(){
        boolean result=confirmExit.display("Exit", "Close the Application?!");
        if(result)
            window.close();
    }

}