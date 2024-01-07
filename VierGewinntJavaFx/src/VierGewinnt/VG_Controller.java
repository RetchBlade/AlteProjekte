package VierGewinnt;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VG_Controller extends Application {

	private VG_Model model;
	private VG_View view;
	
	
	
	
	@Override
	public void init() {
		model = new VG_Model();
		view = new VG_View(this,model);
		
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		Scene scene = new Scene(view.getView(), 600, 600);
		stage.setScene(scene);
		stage.show();
	}
	
	public void reset() {
		model.reset();
	}
	
	public void setColumn(int pCol) {
		model.setColumn(pCol);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
