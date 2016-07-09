package it.polimi.ingsw.server.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import it.polimi.ingsw.server.model.Color;

public class TestColor {

	@Test
	public void getRandomColor() {
		// This test verify if the method getRandomColor gives back all the colors 
		// defined as enum in the class Colors (6 colors)
		
		Color color;
		ArrayList <Color> ColorsArrayList = new ArrayList <Color> ();  
		
		// I create an ArrayList of Colors and I will assign an object
		// to it if it isn't present in the same arraylist
		// At the end of the test the size of ArrayList should be equals to the number of colors
		// We've chosen 50 numbers because it's probably that will appear all the colors
		// and in addict there is the print that confirm this opinion
		 for (int i = 0; i < 50; i++) {
			 color = Color.getRandomColor();
			 if (!(ColorsArrayList.contains(color))){
				 ColorsArrayList.add(color);
				 System.out.printf("color[%d] = %s%n", i, color);
				 }
		 }
		 assertEquals(7,ColorsArrayList.size());
}
}
