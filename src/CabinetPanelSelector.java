/* Copyright (c) <2014> <Marco Benavides>

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files 
(the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, 
publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, 
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */

import java.util.ArrayList;
import java.util.List;

public class CabinetPanelSelector {
	
	public static void main(String[] args) 
	{
		//******************************************************Inputs*****************************************************
		ArrayList<Double> PanelSizes = new ArrayList<Double>(); //List Panel sizes available
		PanelSizes.add((double) 11.75);
		PanelSizes.add((double) 14.75);
		PanelSizes.add((double) 16.625);
		PanelSizes.add((double) 17.75);
		PanelSizes.add((double) 20.75);
		PanelSizes.add((double) 23.875);
		//PanelSizes.add((double) 24.625);
		//PanelSizes.add((double) 12.875);
		
		//measurements are in inches
		double cabinet1 = 35.875;
		double cabinet2 = 17.875;
		double sidePanelWidth = 0.5;
		double desiredPanel_TotalLength =  cabinet1 + cabinet2 + sidePanelWidth*2;
		
		double acceptedError_eachSide = 0.5;
		double minL = desiredPanel_TotalLength - (acceptedError_eachSide*2);
		double maxL = desiredPanel_TotalLength + (acceptedError_eachSide*2);
		
		List<List<Double>> FoundCombinations = new ArrayList<List<Double>>();
		
		Permutation_forTargetSumCalculator permuationCalc = new Permutation_forTargetSumCalculator(PanelSizes, desiredPanel_TotalLength, minL, maxL, true);
		permuationCalc.calculateSets();
		FoundCombinations = permuationCalc.AllFoundSets;
		
		System.out.println("Your desired length is of: " + desiredPanel_TotalLength + " in.");
		System.out.println("Working sets within the range " + minL + " - " + maxL + ", inclusive:");
		System.out.println();
		
		for (List<Double> list : FoundCombinations) 
		{
			double setSize = list.size();
			
			System.out.print(list.get((int) 0) + " ");
					
			for(double i = 1; i < setSize - 1; i++)
			{
				System.out.print("+ " + list.get((int) i) + " ");
			}
			
			System.out.println("= " + list.get((int) setSize-1) + " in.");
			
			System.out.println("Which gives us an offset of " + Math.abs(desiredPanel_TotalLength - list.get((int) setSize-1))/2 + " in. on each side");
			System.out.println();
		}
	}
}
