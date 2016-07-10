package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.control.Player;
import it.polimi.ingsw.utils.Constant;

import java.util.*;

/**
 * Nobility Route class manages the position of the player on the route. First of all it saves all the 
 * players and the position of them on it. The route is made by cells, so the constructors create 
 * all the Nobility Route Cell and stores them into an Array. 
 * The differences between the two constructor is that the first is used when the player don't uses
 * custom configurations, the second when them are customized, so he can choose how many nobility route cells
 * wants with bonuses.
 *
 */

public class NobilityRoute{

	private Map <Player,Integer> nobilityMap = new HashMap<>();

	private NobilityRouteCell [] arrayNobilityRouteCell = new NobilityRouteCell[Constant.NOBILITY_MAX+1];

	private Field field;
	
	/**
	 * The constructor (1) assigns creates nobilityMap with every player
	 * starting at 0. Then it creates arrayNobilityRouteCell 
	 * @param arrayListPlayer
	 */
	public NobilityRoute(ArrayList<Player> arrayListPlayer){

		for (Player anArrayListPlayer : arrayListPlayer) {
			nobilityMap.put(anArrayListPlayer, 0);
		}
		
		for (int indexNobility = 0, indexBonuses = 0; indexNobility<Constant.NOBILITY_MAX+1; indexNobility++){
			
			Bonus[] arrayBonus=null;
			if(((indexNobility%2==0)&&(indexNobility!=0)) || (indexNobility==19)){
				arrayBonus=Constant.NOBILITY_CELL_BONUSES[indexBonuses];
				indexBonuses++;
			}
		
			arrayNobilityRouteCell[indexNobility] = new NobilityRouteCell(indexNobility, arrayBonus); 
		}
	
	}
	
	/**
	 * The constructor (2) assigns creates nobilityMap with every player
	 * starting at 0. Then it creates arrayNobilityRouteCell.
	 * This last process is different from the first constructor.
	 * It takes nobilityBonusNumber bonuses from the Constant randomly
	 * and puts them in a random order in arrayNobilityRouteCell.
	 * @param arrayListPlayer
	 * @param nobilityBonusNumber: the number of cells with bonuses
	 * 
	 */
	public NobilityRoute(ArrayList<Player> arrayListPlayer, int nobilityBonusNumber){
		
		for (Player anArrayListPlayer : arrayListPlayer) {
			nobilityMap.put(anArrayListPlayer, 0);
		}
		
		
		Bonus[][] matrixNobilityRouteCellBonus = Constant.NOBILITY_CELL_BONUSES.clone();
		Collections.shuffle(Arrays.asList(matrixNobilityRouteCellBonus));
		
		ArrayList<NobilityRouteCell> arrayListNobilityRouteCell = new ArrayList<>();
		
		for (int indexNobility = 0, indexBonuses = 0; indexNobility<Constant.NOBILITY_MAX; indexNobility++){
			
			Bonus[] arrayBonus=null;
			if(indexBonuses<nobilityBonusNumber){
				arrayBonus=matrixNobilityRouteCellBonus[indexBonuses];
				indexBonuses++;
			}
			arrayListNobilityRouteCell.add( new NobilityRouteCell(0, arrayBonus)); 
		}
		Collections.shuffle(arrayListNobilityRouteCell);
		
		/*	The first cell cannot (in any way) have bonuses */
		arrayListNobilityRouteCell.add(0, new NobilityRouteCell(0, null));
		
		arrayNobilityRouteCell = arrayListNobilityRouteCell.toArray(arrayNobilityRouteCell);
		
		for (int indexNobility = 0;  indexNobility<Constant.NOBILITY_MAX+1; indexNobility++){
			arrayNobilityRouteCell[indexNobility].setIndex(indexNobility);
		}
		
	}
	

	/**
	 * This method increases/decreases the specified player's nobilityMap of the increment specified.
	 * In addict it verify that the destination cell contains some bonuses and if yes, call applyBonus method
	 * from Bonus Class.
	 * @param increment
	 * @param player: the player that needs to increase it points
	 */
	public void movePlayer(int increment, Player player){
		int oldValue = nobilityMap.get(player);
		
		for(int i=1; i<=increment && (oldValue+i < Constant.NOBILITY_MAX+1); i++){
			nobilityMap.replace(player, oldValue+i);
			if(arrayNobilityRouteCell[oldValue+i].hasBonus()){
				for(Bonus bonus : arrayNobilityRouteCell[oldValue+i].getArrayBonus()){
					bonus.applyBonus(player, field);
				}
			}	
		}
	}
	
	public int getPosition(Player player){
		return nobilityMap.get(player);
	}
	
	public NobilityRouteCell[] getArrayNobilityRouteCell(){
		return arrayNobilityRouteCell;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}
	
	
}
