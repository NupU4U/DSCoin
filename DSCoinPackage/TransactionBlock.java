package DSCoinPackage;

import HelperClasses.MerkleTree;
import HelperClasses.CRF;

public class TransactionBlock {

  public Transaction[] trarray;
  public TransactionBlock previous;
  public MerkleTree Tree;
  public String trsummary;
  public String nonce;
  public String dgst;

 public TransactionBlock(Transaction[] t) {
    Transaction[] temp = new Transaction[t.length];
    for (int i =0 ; i<t.length; i++){
      temp[i] = t[i];
    }
    this.trarray = temp;
    MerkleTree temp_1 = new MerkleTree();
    this.Tree = temp_1;
    this.previous = null;
    this.trsummary = temp_1.Build(trarray);
    this.dgst = null;
  }

  public boolean checkTransaction (Transaction t) {
    int b = 0;
    if (t.coinsrc_block == null){
      return true;
    }
    else {
    for (int i =0 ; i < t.coinsrc_block.trarray.length;i++){
      if (t.coinsrc_block.trarray[i].coinID.equals(t.coinID) && t.coinsrc_block.trarray[i].Destination.UID.equals(t.Source.UID)){
        b = 1;
      }
    }
    TransactionBlock k = this ;
    while (k!= t.coinsrc_block){
      for (int i =0 ; i<k.trarray.length; i++){
        if (k.trarray[i].coinID.equals(t.coinID)){
          return false;
        }
      }
      k = k.previous;
    }
    }
   if (b==1){return true;}
    else {return false;}
  }
}
