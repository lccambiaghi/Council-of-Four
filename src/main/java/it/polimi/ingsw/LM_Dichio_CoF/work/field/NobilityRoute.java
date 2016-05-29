package it.polimi.ingsw.LM_Dichio_CoF.work.field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import it.polimi.ingsw.LM_Dichio_CoF.work.Constant;
import it.polimi.ingsw.LM_Dichio_CoF.work.Player;

public class NobilityRoute implements Route{

	private Map <Player,Integer> nobilityMap = new HashMap<>();
	private NobilityRouteCell [] arrayNobilityRouteCell = new NobilityRouteCell[Constant.NOBILITY_MAX+1];

	/* The constructor (1) assigns creates nobilityMap with every player
	   starting at 0. Then it creates arrayNobilityRouteCell */
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
	
	/* The constructor (2) assigns creates nobilityMap with every player
	   starting at 0. Then it creates arrayNobilityRouteCell.
	   This last process is different from the first constructor.
	   It takes nobilityBonusNumber bonuses from the Constant randomly
	   and puts them in a random order in arrayNobilityRouteCell.
	   Actually it uses the temporary arrayList arrayListNobilityRouteCell */
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
	

	/* This method increases/decreases the specified player's nobilityMap of the increment specified */
	public void movePlayer(int increment, Player player){
		int oldValue = nobilityMap.get(player);
		if (oldValue+increment < Constant.NOBILITY_MAX)
			nobilityMap.replace(player, oldValue+increment);
		else
			nobilityMap.replace(player, Constant.NOBILITY_MAX);
	}
	
	public int getPosition(Player player){
		return nobilityMap.get(player);
	}
	
	public NobilityRouteCell[] getArrayNobilityRouteCell(){
		return arrayNobilityRouteCell;
	}

}
