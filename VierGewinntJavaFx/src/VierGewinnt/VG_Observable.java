package VierGewinnt;
public interface VG_Observable {
	/** Adds an observer to be notified to the list. 
	 * @param pObs the Observer to add
	 */
	public void registerObserver(VG_Observer pObs);
	/** Calls update() on every registered observer
	 * 
	 */
	public void notifyObserver();
	/** Removes an observer from the list.
	 * @param pObs the observer to be removed
	 */
	public void removeObserver(VG_Observer pObs);
	public int getCurrentPlayer();
	public int[][] getGameBoard();
	//public VG_GameState getCurrentState();
	public String getStatusText();

}
