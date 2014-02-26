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

public class Permutation_forTargetSumCalculator {
	
	private static final double NaN = 0;
	
	public ArrayList<Double> DataSet = new ArrayList<Double>();
	public double DataSet_Size = 0;
	public double targetSum = 0;
	public boolean rangeEn = false;
	public double minSum = 0;
	public double maxSum = 0;
	
	public double element_considered;
	public double element_toAdd;
	public double currentSetSum = 0;
	
	public double AdderElem_addedCounter = 0;
	public double DataSet_currElement_index = 0;
	public double DataSet_currAdder_index = 0;
	public double DataSet_global_index = 0;
	
	public ArrayList<Double> foundSet = new ArrayList<Double>();
	public List<List<Double>> AllFoundSets = new ArrayList<List<Double>>();
	
	public Permutation_forTargetSumCalculator(ArrayList<Double> _dataSet, double _targetSum, double _minSum, double _maxSum, boolean _rangeEn)
	{
		//record data set we will be working with
		DataSet = _dataSet;
		DataSet_Size = DataSet.size();
		
		targetSum = _targetSum;
		rangeEn = _rangeEn; //determine whether we will accepting sums within a range or absolute sums
		minSum = _minSum;
		maxSum = _maxSum;
	}
	
	public void calculateSets()
	{
		element_considered = DataSet.get((int)DataSet_global_index); //extract element to be tested
		element_toAdd = DataSet.get((int)DataSet_global_index); //extract element we will adding to our element_considered
		currentSetSum = element_considered; //current sum is simply set to the value of the element being tested, element_considered
		
		determineIfSet(element_considered, element_toAdd, currentSetSum);
	}
	
	public void determineIfSet(double elemConsidered, double AdderElem, double SetSum)
	{
		if(rangeEn)
		{
			if(SetSum >= minSum && SetSum <= maxSum)//if the sum lies within our accepted range
			{
				recordSet(elemConsidered, AdderElem, SetSum);
			}
			
			else if(SetSum > maxSum)
			{
				recordSet(NaN, NaN, NaN); //let the method in charge or recording the set that the element tested is not an acceptable element
			}
			
			else
			{
				SetSum += AdderElem; //increase sum
				AdderElem_addedCounter++; //note that we have added our element_toAdd once more to our element_considered
				determineIfSet(elemConsidered, AdderElem, SetSum); //use recursion to see if we have matched out sum target once more
			}
		}
		
		else
		{
			if(SetSum == targetSum)//if the sum matches our target sum
			{
				recordSet(elemConsidered, AdderElem, SetSum);
			}
			
			else if(SetSum > targetSum)
			{
				recordSet(NaN, NaN, NaN); 
			}
			
			else
			{
				SetSum += AdderElem;
				AdderElem_addedCounter++;
				determineIfSet(elemConsidered, AdderElem, SetSum);
			}
		}
	}
	
	public void recordSet(double element, double elementAdded, double elemSum)
	{
		if(element != NaN)//if we have found an element that can be used in sum
		{
			foundSet.add((Double) element); //record element
			
			for(double i = 0; i < AdderElem_addedCounter; i++)
			{
				foundSet.add((Double) elementAdded); //record element that was added to our our element, the number of times we added it
			}
			
			foundSet.add((Double) elemSum); //record total sum
			
			AllFoundSets.add((List<Double>) foundSet); //add compiled subset to array holding list of acceptable subsets.
		}
		
		//reset
		foundSet = new ArrayList<Double>();
		AdderElem_addedCounter = 0;
		
		//increase index element being checked
		DataSet_currElement_index++;
		
		if(DataSet_currElement_index < DataSet_Size) //if the element being tested is still within the data set size
		{
			element_considered = DataSet.get((int)DataSet_currElement_index); //update the element being tested to the next possible element
			element_toAdd = DataSet.get((int)DataSet_currAdder_index); //still use the same element to increase our set sum
			currentSetSum = element_considered;
			
			determineIfSet(element_considered, element_toAdd, currentSetSum);
		}
		
		else//if we have tested all elements in the array with the current index element
		{
			DataSet_global_index++; //advance to the next element
			DataSet_currAdder_index = DataSet_global_index; //set the adder element
			DataSet_currElement_index = DataSet_global_index; //begin with the element set by DataSet_global_index
			
			if(DataSet_global_index < DataSet_Size) //if the index of the element to be tested exists
			{
				element_considered = DataSet.get((int)DataSet_global_index);
				element_toAdd = DataSet.get((int)DataSet_global_index);
				currentSetSum = element_considered;
				determineIfSet(element_considered, element_toAdd, currentSetSum);
			}
			
			else //otherwise we have finished testing the entire set
			{
				//System.out.println(AllFoundSets);
			}
		}
	}
}
