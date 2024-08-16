package DSCoinPackage;

import HelperClasses.CRF;

public class BlockChain_Honest {

  public int tr_count;
  public static final String start_string = "DSCoin";
  public TransactionBlock lastBlock;

  public void InsertBlock_Honest (TransactionBlock newBlock) {
    CRF n = new CRF (64);
    long s = 1000000001L;
    if (this.lastBlock == null){
      String base = n.Fn(start_string+"#"+newBlock.trsummary+"#"+ String.valueOf(s));
      while (!base.substring(0,4).equals("0000")){
        base = n.Fn(start_string+"#"+newBlock.trsummary+"#"+ String.valueOf(s));
        s++;
      }
      newBlock.dgst = base;
    } 
    else {
      String base = n.Fn(this.lastBlock.dgst+"#"+newBlock.trsummary+"#"+ String.valueOf(s));
      while (!base.substring(0,4).equals("0000")){
        base = n.Fn(this.lastBlock.dgst+"#"+newBlock.trsummary+"#"+ String.valueOf(s));
        s++;
      }
      newBlock.dgst = base;
    } 
    newBlock.previous = this.lastBlock;
    newBlock.nonce = String.valueOf(s-1);
    this.lastBlock = newBlock;
    }
}

