package DSCoinPackage;
import java.util.*;
import HelperClasses.*;

public class BlockChain_Malicious {

	public int tr_count;
	public static final String start_string = "DSCoin";
	public TransactionBlock[] lastBlocksList;

	public static boolean checkTransactionBlock (TransactionBlock tB) {
    CRF n = new CRF(64);
    MerkleTree m  = new MerkleTree();
    String f = m.Build(tB.trarray);
    if (tB.previous != null){
      String s = n.Fn(tB.previous.dgst+"#"+ tB.trsummary+ "#" + tB.nonce);
      if (tB.dgst.equals(s) && tB.trsummary.equals(f) && s.substring(0,4).equals("0000") && tB.dgst.substring(0,4).equals("0000")){
        for (int i = 0 ; i< tB.trarray.length ; i++){
          if (!tB.previous.checkTransaction(tB.trarray[i])){
            return false;
          }
        }
        
        {
          return true;
        }
      }
      else{
      return false;
      } 
    }
    else {
      String s = n.Fn(start_string+"#"+ tB.trsummary+ "#" + tB.nonce);
      if (tB.dgst.equals(s) && tB.trsummary.equals(f) && s.substring(0,4).equals("0000") && tB.dgst.substring(0,4).equals("0000")){
        return true; 
      }
      else{
        return false;
      }
    }
  }

	public TransactionBlock FindLongestValidChain () {
    int count_1 = 0;
    TransactionBlock b_2 = null;
    for( int i =0 ;i < lastBlocksList.length ; i ++){
      TransactionBlock b = this.lastBlocksList[i];
      int count =0; 
      TransactionBlock b_1 = b;
      while (b!= null){
        if (!this.checkTransactionBlock(b)){
          count = 0;
          b_1 = b.previous;
        }
        else {
          count ++;
        }
        b = b.previous;
      }
      if (count_1< count) {
        count_1 = count;
        b_2 = b_1; 
      }   
      }
      return b_2;
    }
	
	public void InsertBlock_Malicious (TransactionBlock newBlock) {
    CRF n = new CRF(64);
    TransactionBlock t = this.FindLongestValidChain();
    long s  = 1000000001L;
    String base;
    if (t==null){
       base = n.Fn(start_string+"#"+newBlock.trsummary+"#"+ String.valueOf(s));
      while (!base.substring(0,4).equals("0000")){
        base = n.Fn(start_string+"#"+newBlock.trsummary+"#"+ String.valueOf(s));
        s++;
    }
    }
    else{
    base = n.Fn(t.dgst+"#"+newBlock.trsummary+"#"+ String.valueOf(s));
    while (!base.substring(0,4).equals("0000")){
        base = n.Fn(t.dgst+"#"+newBlock.trsummary+"#"+ String.valueOf(s));
        s++;
    }
    }
      newBlock.nonce = String.valueOf(s-1);
      newBlock.dgst = base;
      newBlock.previous = t;
      boolean h = true;

      for (int i = 0 ;i <this.lastBlocksList.length;i++){
        if (t == this.lastBlocksList[i]){
          this.lastBlocksList[i] = newBlock;
          h = false;
          break;
        }
      }
      if (h){
        int q = 0;
        while(q<lastBlocksList.length){
				if(lastBlocksList[q]==null){
					lastBlocksList[q]=newBlock;
					break;
				}
        q++;
			}
}
}
}