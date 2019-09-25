package huffman;

import java.util.PriorityQueue;



public class HuffMan {
     final int CHAR_LIMITE =256;
     StringBuilder header  =new StringBuilder();
	 //creating frequence table
    public int[] createFrequenceTable(String text)
    {
    	int frequence [] =new int[CHAR_LIMITE];
    	
    	for(int i=0;i<text.length();i++)
    	{
    		frequence[text.charAt(i)]++;
    	}
    	
    return frequence;
    }
	 //creating  PriorityQueue
	public PriorityQueue<HuffmanNode> createPriorityQueue(int []frequence)
	{   
		header = new StringBuilder();
		header.append((char)1);
		PriorityQueue<HuffmanNode> pqueue = new PriorityQueue<>(1, new FrequenceComparator());
		for(int i=0;i<frequence.length;i++)
		{
			if(frequence[i]>0)
			{
				pqueue.add(new HuffmanNode((char) i, frequence[i]));
				header.append(":"+(char)i+frequence[i]);
			}
		}
		header.append((char)2);
		return pqueue;
	}
	

	//make Huffman Tree
	public HuffmanNode createHuffmanTree(PriorityQueue<HuffmanNode> huf)
	{
		HuffmanNode root =null;
		
		while(huf.isEmpty()==false)
		{
			root=joinTwoHuffmanNode(huf);
			if(huf.isEmpty()==false)
				huf.add(root);
		}
		return root;
	}
	// pull the two least from the queue
	public HuffmanNode joinTwoHuffmanNode(PriorityQueue<HuffmanNode> huf) {
		
		HuffmanNode Node1 = (huf.isEmpty())?null:huf.poll();
		HuffmanNode Node2 = (huf.isEmpty())?null:huf.poll();
		
		HuffmanNode Node = new HuffmanNode('-', Node1.frequence+Node2.frequence);
		Node.left=Node1;
		Node.right=Node2;
		
		return Node;
	}
	
  public String compress(String text)
  {
	  int frequence[] = createFrequenceTable(text);
	  PriorityQueue<HuffmanNode>pqueue = createPriorityQueue(frequence);
	  HuffmanNode root = createHuffmanTree(pqueue);
	  String Compress= header.toString()+ stringCompress(text, root);
	  
	  return Compress;
  }
  /**
   *   Decodeing
   * @param Array
   * @param root
   * @param str
   */

  public String decode(String encodetext)
  {
  	if(encodetext.charAt(0)!=(char)1)
  		return null;
  	int frequence[] =parseHeadeAsFrequence(encodetext);
  	  PriorityQueue<HuffmanNode>pqueue = createPriorityQueue(frequence);
	  HuffmanNode root = createHuffmanTree(pqueue);
	  String Decodingstr = decodingString(encodetext,root);
  	return Decodingstr;
  }
 
   public String decodingString(String encodetext, HuffmanNode root) {
	// TODO Auto-generated method stub
	   StringBuilder s =new StringBuilder();
	   HuffmanNode currnode =root;
	   for(int i=header.length();i<encodetext.length();i++ )
	   {
		   if(encodetext.charAt(i)=='0') currnode=currnode.left;
		   else if(encodetext.charAt(i)=='1') currnode=currnode.right;
		   if(isleaf(currnode))
		   {
			   s.append(currnode.c);
			   currnode=root;
		   }
	   }
	   
	return s.toString();
}
private boolean isleaf(HuffmanNode currnode) {
	// TODO Auto-generated method stub
	if(currnode==null)
		return false;
	return currnode.left==null && currnode.right==null;
}
public String stringCompress(String text,HuffmanNode root)
   {
	   String Array []= new String[CHAR_LIMITE];
	   StringBuilder str  =new StringBuilder();
		  generateBites(Array,root,new StringBuilder());
		  
		  for(int i=0;i<text.length();i++)
		  {
			  str.append(Array[text.charAt(i)]);
		  }
		return str.toString();  
	  	  
   }
 
//generateBites
public void generateBites(String[] Array, HuffmanNode root, StringBuilder str) {
	// TODO Auto-generated method stub
	  if(root.c=='-')
	  {
		  str.append("0");
		  generateBites(Array, root.left, str);
		  str.append("1");
		  generateBites(Array, root.right, str);
		  if (str.length() > 0) 
			  str.deleteCharAt(str.length() - 1);
	  }else{
		  
		  System.out.println(root.c+" - "+str.toString());
		  Array[root.c]=str.toString();
		  if (str.length() > 0) 
		  str.deleteCharAt(str.length()-1);
	  }
	
}
//parseHead
private int[] parseHeadeAsFrequence(String encodetext) {
	
	int frquence[] =new int[CHAR_LIMITE];
	int i=0;
	for(; i<encodetext.length() && encodetext.charAt(i)!=(char)2;i++)
	{
		header.append(encodetext.charAt(i));
		if(encodetext.charAt(i) ==':')
		{
			i++;
			header.append(encodetext.charAt(i));
			int f=0;
			int m=1;
			int j=i+1;
			for(;j<encodetext.length() && encodetext.charAt(j)!=(char)2 && encodetext.charAt(j)!=':';j++)
			{
				f= (f*m)+(int)(encodetext.charAt(j)-'0');
				if(f!=0)
					m=10;
				header.append((int)(encodetext.charAt(j)-'0'));
			}
			frquence[encodetext.charAt(i)]=f;
			System.out.println(f);
			i=j-1;
		}
	}
	return frquence;
}
public static void main (String [] arg)
{   HuffMan obj =new HuffMan();
    String encode =obj.compress("We can use these comparators to pass an argument to sort function of Arrays and Collections classes.");
	System.out.println(encode);
	System.out.println(obj.header);
	System.out.println(obj.decode(encode));
}
	
}
