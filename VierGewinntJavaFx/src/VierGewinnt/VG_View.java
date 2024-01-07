package VierGewinnt;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class VG_View implements VG_Observer {

	private Button[][] btnCoins;
	private Button btnReset;
	private Label lblStatus;
	private String styleBtn = "";
	private VG_Observable model;
	private GridPane gp;
	private VG_Controller controller;
	


	public VG_View(VG_Controller myController, VG_Observable pModel) {
		controller = myController;
		this.model = pModel;
		model.registerObserver(this);
		gp = new GridPane();
		lblStatus = new Label("Player 1/2");
		btnReset = new Button("Reset game");
		
		//testconf um was zu testen
		/*Button testconf = new Button("set testconfig");
		testconf.setOnAction(ae -> {
			// quick&dirty� - please do not adopt for future projects.
			((VG_Model) model).setTestConfiguration();
		});*/
		
		btnReset.setOnAction(ae -> {
			controller.reset();
		});

		gp.setPadding(new Insets(10));

		btnCoins = new Button[6][7];
		for (int s = 0; s < 7; s++) {
			for (int z = 0; z < 6; z++) {
				btnCoins[z][s] = new Button("free");
				btnCoins[z][s].setPrefSize(75, 75);
				btnCoins[z][s].setStyle(styleBtn);
				btnCoins[z][s].setStyle("-fx-background-radius: 5em; " + "-fx-color: white" );
				int spalte = s;
				gp.add(btnCoins[z][s], s, z + 1);
				
				btnCoins[z][s].setOnAction(ae -> {
					controller.setColumn(spalte);
				});
			}
			
		}

		gp.add(lblStatus, 0, 0, 5, 1);
		gp.add(btnReset, 3, 7, 2, 1);
		//testconf um was zu testen
		//gp.add(testconf, 5, 7, 2, 1);

	}

	@Override
	public void update() {
		int[][] gameBoard = model.getGameBoard();
		// Idee: die beiden Arrays arr und btnCoins "uebereinanderlegen"
		for (int spalte = 0; spalte < 7; spalte++) {
			for (int zeile = 0; zeile < 6; zeile++) {
				if (gameBoard[zeile][spalte] == 1) {
					btnCoins[zeile][spalte].setText("X");
					btnCoins[zeile][spalte].setStyle("-fx-background-radius: 5em; " + "-fx-color: blue");
				} else if (gameBoard[zeile][spalte] == 2) {
					btnCoins[zeile][spalte].setText("O");
					btnCoins[zeile][spalte].setStyle("-fx-background-radius: 5em; " + "-fx-color: red");
				} else {
					btnCoins[zeile][spalte].setText("free");
					btnCoins[zeile][spalte].setStyle("-fx-background-radius: 5em; " + "-fx-color: white");
				}

			}
		}
		lblStatus.setText(model.getStatusText());
	}

	public Parent getView() {
		return gp;
	}

}