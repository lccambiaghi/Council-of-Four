package it.polimi.ingsw.LM_Dichio_CoF.model.field;

import it.polimi.ingsw.LM_Dichio_CoF.TestCases;
import it.polimi.ingsw.LM_Dichio_CoF.control.Player;
import it.polimi.ingsw.LM_Dichio_CoF.model.field.RichnessRoute;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Luca on 22/05/16.
 */
public class TestRichnessRoute {

    TestCases testCases = new TestCases();
    ArrayList <Player> arrayListPlayer =testCases.arrayListPlayer();

    @Test
    public void initialRichness() throws Exception {

        RichnessRoute richnessRoute = new RichnessRoute(arrayListPlayer);

        for(Player player : arrayListPlayer)
            System.out.println(player.getNickname()+ " " + richnessRoute.getPosition(player));

        assertEquals(3, arrayListPlayer.size()); // da cambiare
    }

    @Test
    public void MovePlayer() {

        int[] arrayPosition = new int[arrayListPlayer.size()];

        RichnessRoute richnessRoute = new RichnessRoute(arrayListPlayer);

        int i=2;
        for(Player player : arrayListPlayer){
            richnessRoute.movePlayer(i, player);
            i+=5;
        }

        int j=0;
        for(Player player : arrayListPlayer){
            arrayPosition[j] = richnessRoute.getPosition(player);
            System.out.println(arrayPosition[j]);
            j++;
        }

        assertArrayEquals(arrayPosition, new int[]{12,18,20});
    }

    @Test
    public void getPosition() throws Exception {

    }

}