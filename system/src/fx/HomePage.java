package fx;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pharmacy.Manager;
import pharmacy.Medicine;
import pharmacy.Person;

public class HomePage{
	/////////////////////Objects Will Be Created/////////////////////////
	static short wrong=0;
	Stage window;
	Scene scene;
	Button searchButton, howTo, signIn, login, search;
	TextField searchBox;
	PasswordField password;
	TextArea textArea;
	Label hints, about;
	GridPane glass;
	BorderPane layout;
	HBox header, footer;
	ImageView layoutBackground;
	RadioButton radio1, radio2;
	ToggleGroup group;
	CheckBox changePassword;
	/////////////////////SearchGlass Objects/////////////////////////////
	GridPane searchGlass;
	TableView <Medicine>searchTable=new TableView<Medicine>();
	TableColumn<Medicine, Integer>idColumn;
	TableColumn<Medicine, String>nameColumn;
	TableColumn<Medicine, String>formulaColumn;
	TableColumn<Medicine, String>indicationsColumn;
	TableColumn<Medicine, String>allowedPersonsColumn;
	TableColumn<Medicine, Float>priceColumn;
	////////////////////ChangePasswordGlass Objects////////////////////
	GridPane changePasswordGlass;
	PasswordField oldPassword, newPassword,confirmPassword;
	Label oldPasswordLabel, newPasswordLabel,confirmPasswordLabel;
	Button done;
	/////////////////HowToUse Glass Objects//////////////////////////////
	GridPane howToUseGlass;

	@SuppressWarnings("unchecked")
	public HomePage(){
		/////////////////////Header Components/////////////////////////
		howTo=new Button("How To Use?");
		howTo.setId("howTo");
		howTo.setPadding(new Insets(5,7,10,7));
		howTo.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				howToUseGlass.setVisible(true);
				hints.setVisible(true);
				layout.setCenter(howToUseGlass);
			}
		});
		
		searchBox=new TextField();
		searchBox.setPrefWidth(310);
		searchBox.setPrefHeight(33);
		searchBox.setPromptText("type here then click enter");
		search= new Button();
		
		ImageView buttonView=new  ImageView(new Image(getClass().getResourceAsStream("ss.png")));
		buttonView.setFitHeight(25);
		buttonView.setFitWidth(25);
		search.setGraphic(buttonView);
		search.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				boolean name=radio1.isSelected() ;
				boolean illness=radio2.isSelected();
				if(name||illness){
					ArrayList<Medicine>foundList=new ArrayList<>();
					try{
						Exceptions.isString(searchBox.getText());
						String search=searchBox.getText();
						if(name){
								Medicine m;
								if((m=Person.searchMedicineByName(search))!=null)
									foundList.add(m);
								foundList=compare(foundList, Person.searchMedicineByLetters(search));
								if(!foundList.isEmpty())
									foundList=compare(foundList, Person.searchMedicineByFormula(foundList.get(0).getFormula()));
						}
						else if(illness){
							foundList.addAll(Person.searchMedicineByIllness(search));
						}
					}catch(Exception e){
						Exceptions.invalidInputWarning(e.getMessage());
					}finally{
						searchGlass.setVisible(true);
						layout.setCenter(searchGlass);
						searchTable.setItems(FXCollections.observableList(foundList));
						foundList=new ArrayList<>();
						hints.setText("1- You can choose to search for illness\n     or medicine by typing the name\n2- Please keep the ID of the needed\n\t     medicine to buy with\n      * Thanks for useing our serveses *");
					}
				}
				else
				Exceptions.invalidInputWarning("Choose illness or Medicine please");
			}
		});
		signIn=new Button("sign In");
		signIn.setId("howTo");
		signIn.setPadding(new Insets(5,0,10,7));
		signIn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				hints.setText("1- You can add medicine, make bills,\nWatch low quantity medicine or see\nActions in last 7 days from the buttons in\n\t\t\tThe header\n2- The only ways that you can update or\nDelete medicine is to search about it and\nChoose to update or del or from low\n\t\t\tQuantity tab");
				glass.setVisible(true);
				password.setVisible(true);
				login.setVisible(true);
				changePassword.setVisible(true);
				layout.setCenter(glass);
			}
		});
		
		group=new ToggleGroup();
		radio1=new RadioButton("search by medicine");
		radio1.setToggleGroup(group);
		radio2=new RadioButton("search by illness");
		radio2.setToggleGroup(group);
		radio1.setTextFill(Color.WHITE);
		radio1.setFont(Font.font("serif", 18));
		radio2.setTextFill(Color.WHITE);
		radio2.setFont(Font.font("serif", 18));
		header=new HBox();
		header.getChildren().addAll(howTo, radio1, radio2,searchBox,search,signIn);
		header.setPrefWidth(1060);
		header.setPadding(new Insets(10,7,3,7));
		header.setSpacing(35);
		header.setId("header");
		//////////////////////Glass Components///////////////////////////
		password=new PasswordField();
		password.setPromptText("enter your password");
		password.setPrefWidth(300);
		password.setPrefHeight(30);
		password.setVisible(false);
		
		login=new Button("Login");
		login.setId("howTo");
		login.setVisible(false);
		login.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(Manager.login(password.getText())){
					window.close();
					new Admin();
				}
				else{
					Exceptions.invalidInputWarning("Wrong password");
					if(++wrong==3)
						window.close();
				}
			}
		});
		changePassword=new CheckBox("Change password");
		changePassword.setId("changePassword");
		changePassword.setVisible(false);
		changePassword.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(changePassword.isSelected()){
					layout.setCenter(changePasswordGlass);
					changePasswordGlass.setVisible(true);
					changePassword.setSelected(false);
					
				}
			}
		});			
		glass=new GridPane();
		glass.setMaxWidth(485);
		glass.setMaxHeight(380);
		glass.setId("glass");
		glass.getChildren().addAll(password, login, changePassword);
		glass.setPadding(new Insets(50,10,10,30));
		glass.setHgap(10);
		glass.setVgap(20);
		glass.setVisible(false);
		GridPane.setConstraints(password, 0, 0);
		GridPane.setConstraints(login, 1, 0);
		GridPane.setConstraints(changePassword, 0, 1);
		////////////////////////Search Glass Components////////////////
		searchTable=new TableView<>();
		idColumn=new TableColumn<>("Id");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
		idColumn.setMinWidth(100);
		nameColumn=new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
		nameColumn.setMinWidth(150);
		formulaColumn=new TableColumn<>("Formula");
		formulaColumn.setCellValueFactory(new PropertyValueFactory<>("Formula"));
		formulaColumn.setMinWidth(250);
		indicationsColumn=new TableColumn<>("Indications");
		indicationsColumn.setCellValueFactory(new PropertyValueFactory<>("Indications"));
		indicationsColumn.setMinWidth(240);
		allowedPersonsColumn=new TableColumn<>("Allowed Persons");
		allowedPersonsColumn.setCellValueFactory(new PropertyValueFactory<>("AllowedPerson"));
		allowedPersonsColumn.setMinWidth(220);
		priceColumn=new TableColumn<>("Price");
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
		priceColumn.setMinWidth(90);
		searchTable.getColumns().addAll(idColumn, nameColumn, formulaColumn, indicationsColumn, allowedPersonsColumn, priceColumn);
		searchGlass=new GridPane();
		GridPane.setConstraints(searchTable,0,0);
		searchGlass.getChildren().add(searchTable);
		searchGlass.setMinWidth(485);
		searchGlass.setId("glass");
		searchGlass.setPadding(new Insets(10,10,10,10));
		searchGlass.setVisible(false);
		
		///////////////////////ChangePasswordGlass Components////////////////
				
		oldPassword=new PasswordField();
		oldPasswordLabel=new Label("Old Password: ");
		oldPasswordLabel.setId("name");
		newPassword=new PasswordField();
		newPasswordLabel=new Label("New Password: ");
		newPasswordLabel.setId("name");
		newPassword.setPrefWidth(200);
		confirmPassword=new PasswordField();
		confirmPasswordLabel=new Label("Confirm Password: ");
		confirmPasswordLabel.setId("name");
		oldPasswordLabel.setId("name");
		done=new Button("Done");
		done.setId("howTo");
		done.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try{
					Exceptions.isString(oldPassword.getText());
					Exceptions.isString(newPassword.getText());
					Exceptions.isString(confirmPassword.getText());
					String old=oldPassword.getText();
					String now=newPassword.getText();
					String confirm=confirmPassword.getText();
					if(Manager.login(old)){
						if(now.equals(confirm)){
							Manager.changePassword(now);
							Exceptions.success("Password changed successfly");
							oldPassword.setText("");
							newPassword.setText("");
							confirmPassword.setText("");
							layout.setCenter(null);
							hints.setText("1- You can choose to search for illness\n     or medicine by typing the name\n2- Please keep the ID of the needed\n\t     medicine to buy with\n      * Thanks for useing our serveses *");
						}
						else
							throw new Exception("new password and confirmation don't match");
					}else
						throw new Exception("old password isn't correct");
				}catch(Exception e){
					Exceptions.invalidInputWarning(e.getMessage());
				}
			}
		});
		changePasswordGlass=new GridPane();
		changePasswordGlass.setMaxWidth(485);
		changePasswordGlass.setMaxHeight(380);
		changePasswordGlass.setId("glass");
		changePasswordGlass.getChildren().addAll(oldPassword, oldPasswordLabel, newPassword,confirmPasswordLabel,confirmPassword, newPasswordLabel, done);
		changePasswordGlass.setPadding(new Insets(30,10,10,10));
		changePasswordGlass.setHgap(10);
		changePasswordGlass.setVgap(20);
		changePasswordGlass.setVisible(false);
		GridPane.setConstraints(oldPasswordLabel, 0, 0);
		GridPane.setConstraints(oldPassword, 1, 0);
		GridPane.setConstraints(newPasswordLabel, 0, 1);
		GridPane.setConstraints(newPassword, 1, 1);
		GridPane.setConstraints(confirmPasswordLabel,0,2);
		GridPane.setConstraints(confirmPassword, 1, 2);
		GridPane.setConstraints(done, 0, 3);
		
		////////////////////HowToUse Glass Components/////////////////
		howToUseGlass=new GridPane();
		howToUseGlass.setId("glass");
		
		hints=new Label();
		hints.setText("1- You can choose to search for illness\n     or medicine by typing the name\n2- Please keep the ID of the needed\n\t     medicine to buy with\n      * Thanks for useing our serveses *");
		hints.setPrefHeight(320);
		hints.setId("hints");
		hints.setVisible(false);
		
		howToUseGlass.setMaxWidth(485);
		howToUseGlass.setMaxHeight(380);
		howToUseGlass.setPadding(new Insets(30,30,30,30));
		howToUseGlass.setVisible(false);
		GridPane.setConstraints(hints, 0, 0);
		howToUseGlass.getChildren().add(hints);
		////////////////////Footer Components/////////////////////////
		about=new Label("TOIN PHARMACY.\nYou can find us on locations: - elsafa street mobarah mm elmataria in cairo."
				+ "\n						- rohet st below GEN-LAB in trolli c."
				+"\n you can find us on: http://www.toin-pharmacy.com..."
				+ "\nPhone Numbers: 22621511/  22658978/  01065235154\n"
				+ "														We are available for 24 hours a day.");
		about.setId("about");
		about.setPadding(new Insets(5,5,0,20));
		footer=new HBox();
		footer.setId("footer");
		footer.setPrefHeight(130);
		footer.setMaxWidth(1070);
		footer.setPadding(new Insets(5,3,5,3));
		footer.getChildren().addAll(about);
		//////////////////////Layout components//////////////////////
		layoutBackground=new ImageView(new Image(getClass().getResourceAsStream("mov.jpg")));
		layoutBackground.setFitHeight(600);
		layoutBackground.setFitWidth(1070);
		
		layout=new BorderPane();
		layout.getChildren().add(layoutBackground);
		layout.setTop(header);
		layout.setBottom(footer);
		layout.setMinHeight(600);
		/////////////////////Window Components/////////////////////////
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

	protected static ArrayList<Medicine> compare(ArrayList<Medicine> f, ArrayList<Medicine> s) {
		if(f.isEmpty())
			return s;
		else if(s.isEmpty())
			return f;
		else{
			f.removeAll(s);
			f.addAll(s);
			return f;
		}
	}

    public void closeProgram(){
        boolean result=confirmExit.display("Exit", "Close the Application?!");
        if(result)
            window.close();
    }
}
