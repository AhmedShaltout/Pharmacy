package fx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class confirmExit {
    
    private static boolean answer=false;
    public static boolean display(String Title, String Message){
        Stage window=new Stage();
        window.setTitle(Title);
        /////you have to close me at first/////
        window.initModality(Modality.APPLICATION_MODAL);
        Label label=new Label(Message);
        Button yes=new Button("Yes");
        yes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                answer=true;
                window.close();
            }
        });
        Button no=new Button("No");
        no.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                answer=false;
                window.close();
            }
        });
        VBox layout=new VBox(10);
        layout.getChildren().addAll(label,yes,no);
        layout.setAlignment(Pos.CENTER);
        Scene scene=new Scene(layout,250,120);
        window.setScene(scene);
        window.showAndWait();
        return answer;
    }
    
}
