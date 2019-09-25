package huffman;

import java.util.Comparator;

public class FrequenceComparator implements Comparator<HuffmanNode>
  {

	@Override
	public int compare(HuffmanNode first, HuffmanNode second) {
		// TODO Auto-generated method stub
		
		return first.frequence-second.frequence;
			
	}
   
} 
