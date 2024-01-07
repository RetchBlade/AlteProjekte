package VierGewinnt;
import java.util.LinkedList;

public class VG_Model implements VG_Observable {
	/**
	 * Representation of the Connect-Four-Gameboard. This array has dimensions of 6
	 * rows and 7 columns. gameBoard[rows][colums] The Values have the following
	 * meanings: 0: not selected cell 1: selected by player 1 2: selected by player
	 * 2 3: winning entry of player 1 4: winning entry of player 2
	 */
	private int[][] gameBoard;
	private int activePlayer;
	private String statusText;
	private LinkedList<VG_Observer> observerList;

	public VG_Model() {

		gameBoard = new int[6][7];
		activePlayer = 1;
		statusText = "Spieler 1 beginnt.";
		observerList = new LinkedList<>();
	}

	// testconf um was zu testen
	/*
	 * public void setTestConfiguration() { int[][] testBoard = { { 1, 1, 0, 1, 2,
	 * 1, 0 }, { 0, 0, 1, 0, 0, 1, 0 }, { 2, 2, 1, 2, 1, 1, 0 }, { 1, 1, 1, 2, 2, 1,
	 * 0 }, { 2, 2, 2, 1, 1, 1, 0 }, { 1, 2, 1, 2, 1, 2, 2 } };
	 * 
	 * gameBoard = testBoard; notifyObserver(); }
	 */

	@Override
	public void registerObserver(VG_Observer pObs) {
		observerList.add(pObs);
	}

	@Override
	public void notifyObserver() {
		// Alle Observer werden kontaktiert, wenn eine
		// Änderung stattgefunden hat
		for (VG_Observer observer : observerList) {
			observer.update();
		}
	}

	@Override
	public void removeObserver(VG_Observer pObs) {
		observerList.remove(pObs);
	}

	@Override
	public int getCurrentPlayer() {
		return activePlayer;
	}

	@Override
	public int[][] getGameBoard() {
		return gameBoard;
	}

	@Override
	public String getStatusText() {
		return statusText;
	}

	// Methode, die eine Spalte entgegennimmt
	/**
	 * @param columnNumber Spaltennummer zwischen 0 und 6
	 */
	public void setColumn(int columnNumber) {
		// Spielablauf:
		// 1. passende Zeile finden (reagieren falls Spalte schon voll)
		System.out.println("Spieler " + activePlayer + " wirf in Spalte " + columnNumber);
		int rowNumber = -42;

		/*
		 * ✓ Gehe das Spielfeld zeilenweise von 5 bis 0 durch (for-Schleife) ✓ Prüfe, ob
		 * an der Position [zeile][columnNumber] eine 0 steht ✓ falls ja: Höre auf zu
		 * suchen, die Einfügezeile wurde gefunden ✓ falls nein: suche in der nächsten
		 * Zeile weiter
		 */
		for (int row = 5; row >= 0; row--) {
			if (gameBoard[row][columnNumber] == 0) {
				// falls ja: Höre auf zu suchen, die Einfügezeile wurde gefunden
				rowNumber = row;
				break;
			}
		}

		// Variante 2:
		if (rowNumber == -42) {
			statusText = "ungültiger Spielzug";
			notifyObserver();
			return;
		}

		System.out.println("Die Zeilennummer dazu lautet: " + rowNumber);

		// 2. Platz (im gameBoard) mit dem aktuellen Spieler besetzen
		gameBoard[rowNumber][columnNumber] = activePlayer;

		// 3. Überprüfen, ob aktueller Spieler durch den
		// Zug gewonnen hat
		if (hasWon(rowNumber, columnNumber)) {
			statusText = "Spieler " + activePlayer + " hat gewonnen";
			notifyObserver();
			return;
		}
		;

		// 4. Prüfen ob Unentschieden ist
		// Idee: Wenn in der obersten Zeile jedes Feld belegt ist, dann ist
		// unentschieden

		for (int col = 0; col < 7; col++) {
			if (gameBoard[0][col] == 0) {
				break;
			} else {
				if (col == 6) {
					statusText = "Unentschieden.";
					// TODO: hier gibts noch was zu tun
					notifyObserver();
					return;
				}
			}
		}
		// 5. Spieler wechseln
		if (activePlayer == 1) {
			activePlayer = 2;
		} else {
			activePlayer = 1;
		}

		// 6. StatusText anpassen
		// "Spieler 2 ist dran" "Spieler 1 ist dran"
		statusText = "Spieler " + activePlayer + " ist dran.";

		// 7. Bescheid geben, dass sich was geändert hat
		notifyObserver();
	}

	private boolean hasWon(int pRow, int pCol) {
		// 4 ARten zu gewinnen
		// 1. Vertikal 4 Felder in einer Spalte
		if (pRow <= 2) {
			if (gameBoard[pRow][pCol] == activePlayer 
					&& gameBoard[pRow + 1][pCol] == activePlayer
					&& gameBoard[pRow + 2][pCol] == activePlayer 
					&& gameBoard[pRow + 3][pCol] == activePlayer) {
				return true;

			}
		}

		// 2. Horinzontal 4 Gleicher Felder in Zeile
		// Soll bis 4 (0-3) Zählen
		for (int s = 0; s <= 3; s++) {
			// Wenn alle vier Bausteine den gleichen activePlayer haben, dann soll return
			// true gegeben werden.
			if (gameBoard[pRow][s] == activePlayer 
					&& gameBoard[pRow][s + 1] == activePlayer
					&& gameBoard[pRow][s + 2] == activePlayer 
					&& gameBoard[pRow][s + 3] == activePlayer) {
				return true;
			}
		}
		
		/*
		
		// Speichert in der erstellten Int Variable die 0 ab
		  int hcounter = 0; 
		// Zählt die Zeilen durch 
		  for (int s = 0; s < 7; s++) { 
		// Wenn ein Feld vom selben aktiven Spieler belegt wurde, dann soll der counter sich um eins erhöhen. 
		  if (gameBoard[pRow][s] == activePlayer) { 
			  hcounter++;
		// Wenn nicht der Fall eintrifft, dann soll der zähler wieder auf Null gesetzt werden 
		  } else { hcounter = 0; 
		// Wenn der counter auf vier ist, soller einen true Wert abgeben. 
		  } if (hcounter == 4){ return true; } 
		  }
		
		*/
		  		
		// 3. Diagonal down (linksOben->RechtsUnten)
		// Insperation aus Horizontalen und Vertikalen Abfrage
		// Zählt bis 3, weil sonst bei der If bedingung über die Buttons zählt
		for (int s = 0; s <= 3; s++){
			// Zählt bis 2, weil sonst bei der If bedingung über die Buttons zählt
			for (int z = 0; z <= 2; z++){
				// Wenn alle vier Bausteine den gleichen activePlayer haben, dann soll return
				// true gegeben werden.
				if(gameBoard[z][s] == activePlayer
						&& gameBoard[z + 1][s + 1] == activePlayer
						&& gameBoard[z + 2][s + 2] == activePlayer
						&& gameBoard[z + 3][s + 3] == activePlayer){
							return true;
						}
			}
		}
		
		/*
		// von 3 links über dem eingeworfenen Stein bis 3 rechts unter dem
		// eingeworfen Stein
		int downCounter = 0;
		for(int abstand =-3; abstand <=3; abstand++) {
			int row = pRow+abstand;
			int col = pCol+abstand;
			//Indizes die außerhalbb des Spielfeldes liegen mit if(...) überspringen
			if(row >= 0 
				&& row <= 5 
				&& col >= 0 
				&& col <= 6){
			// zählen wie viele in EinerReihe sind (s.o)
			// Wenn ein Feld vom selben aktiven Spieler belegt wurde, dann soll der counter sich um eins erhöhen. 
			if (gameBoard[row][col] == activePlayer) { 
				  downCounter++;
			// Wenn nicht der Fall eintrifft, dann soll der zähler wieder auf Null gesetzt werden 
			  } else { downCounter = 0; 
			// Wenn der counter auf vier ist, soller einen true Wert abgeben. 
			  } if (downCounter == 4){ 
				  return true; 
			  } 
			}
		}
		
		*/
		
		// 4. Diagonal up (linksUnten->RechtsOben)
		// Insperation aus Horizontalen und Vertikalen Abfrage
		// Zählt MINUS bis 3, weil sonst bei der If bedingung über die Buttons zählt
		for (int s = 6; s >= 3; s--){
			// Zählt bis 2, weil sonst bei der If bedingung über die Buttons zählt
			for (int z = 0; z <= 2; z++){
				// Wenn alle vier Bausteine den gleichen activePlayer haben, dann soll return
				// true gegeben werden.
				if (gameBoard[z][s] == activePlayer
						&& gameBoard[z + 1][s - 1] == activePlayer
						&& gameBoard[z + 2][s - 2] == activePlayer
						&& gameBoard[z + 3][s - 3] == activePlayer){
							return true;
					}
			}
		}
		
		/*
		
		int upCounter = 0;
		for(int abstand = -3; abstand < 3; abstand++) {
			int row = pRow-abstand;
			int col = pCol+abstand;
			//Indizes die außerhalbb des Spielfeldes liegen mit if(...) überspringen
			if(row >= 0 
				&& row <= 5 
				&& col >= 0 
				&& col <= 6){
			// zählen wie viele in EinerReihe sind (s.o)
			// Wenn ein Feld vom selben aktiven Spieler belegt wurde, dann soll der counter sich um eins erhöhen. 
			if (gameBoard[row][col] == activePlayer) { 
				upCounter++;
			// Wenn nicht der Fall eintrifft, dann soll der zähler wieder auf Null gesetzt werden 
			  } else { upCounter = 0; 
			// Wenn der counter auf vier ist, soller einen true Wert abgeben. 
			  } if (upCounter == 4){ 
				  return true; 
			  } 
			}
		}
		
		*/
		
		// Wenn nichts passiert, soll einfach false gegeben werden.
		return false;
	}

	public void reset(){
		activePlayer = 1;
		statusText = "Spieler " + activePlayer + " ist dran.";
		for (int s = 0; s < 7; s++) {
			for (int z = 0; z < 6; z++) {
				gameBoard[z][s] = 0;
			}
		}
		notifyObserver();
	}
}
