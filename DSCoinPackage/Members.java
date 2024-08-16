package DSCoinPackage;

import java.util.*;
import HelperClasses.Pair;
import HelperClasses.*;

public class Members
 {

  public String UID;
  public List<Pair<String, TransactionBlock>> mycoins;
  public Transaction[] in_process_trans;

  public void initiateCoinsend(String destUID, DSCoin_Honest DSObj) {
    Pair<String ,TransactionBlock> temp = this.mycoins.get(0);
    Transaction tobj = new Transaction();
    for (int i = 0 ; i<DSObj.memberlist.length;i++){
      if (destUID.equals( DSObj.memberlist[i].UID)){
        tobj.Destination = DSObj.memberlist[i];
      }
    }
    tobj.Source = this;
    tobj.coinID = temp.get_first();
    tobj.coinsrc_block = temp.get_second();
	this.mycoins.remove(0);
    if(this.in_process_trans == null){
      this.in_process_trans = new Transaction[100];
    }
    int k = 0;
    while(k<this.in_process_trans.length){
      if (this.in_process_trans[k] == null){
        this.in_process_trans[k] = tobj;
        break;
      }
      k++;
    }
    DSObj.pendingTransactions.AddTransactions(tobj);
  }

  public void initiateCoinsend(String destUID, DSCoin_Malicious DSObj) {
    Pair<String ,TransactionBlock> temp = this.mycoins.get(0);
    Transaction tobj = new Transaction();
    for (int i = 0 ; i<DSObj.memberlist.length;i++){
      if (destUID.equals( DSObj.memberlist[i].UID)){
        tobj.Destination = DSObj.memberlist[i];
      }
    }
    tobj.Source = this;
    tobj.coinID = temp.get_first();
    tobj.coinsrc_block = temp.get_second();
	this.mycoins.remove(0);
    if(this.in_process_trans == null){
      this.in_process_trans = new Transaction[100];
    }
    int k = 0;
    while(k<this.in_process_trans.length){
      if (this.in_process_trans[k] == null){
        this.in_process_trans[k] = tobj;
        break;
      }
      k++;
    }
    DSObj.pendingTransactions.AddTransactions(tobj);
  }
    public int ind(Transaction tobj, DSCoin_Honest DSObj)throws MissingTransactionException{
      TransactionBlock n = DSObj.bChain.lastBlock;
      int k = 0; 
      int index = 0;
      while (n!= null ){
        for (int i = 0 ; i<n.trarray.length;i++){
          if(n.trarray[i]== tobj){
            index =  i;
            k = 1;
            break;
          }
        }
        if (k ==1){
          break;
        }
        n = n.previous;
      }
      if (k ==0){
        throw new MissingTransactionException();
      }
      return index;
    }
  public TransactionBlock block(Transaction tobj, DSCoin_Honest DSObj)throws MissingTransactionException{
    TransactionBlock n = DSObj.bChain.lastBlock;
      int k = 0; 
      while (n!= null ){
        for (int i = 0 ; i<n.trarray.length;i++){
          if(n.trarray[i]== tobj){
            k = 1;
            break;
          }
        }
        if (k ==1){
          break;
        }
        n = n.previous;
      }
      if (k ==0){
        throw new MissingTransactionException();
      }
      return n;
    }
    public TreeNode get_node(Transaction tobj,TransactionBlock t, DSCoin_Honest DSObj)throws MissingTransactionException{
     TreeNode present_node = t.Tree.rootnode;
    int t_ind=ind(tobj,DSObj);
		int start = t.trarray.length;
		for(int j = start/2 ;j>=1 ; j/=2){
			if (t_ind+1 <= j){
        	present_node = present_node.left;
			}
			else{
				present_node = present_node.right;
				t_ind-=j;
			}
		}
    return present_node;
    }
  
	 public Pair<List<Pair<String, String>>, List<Pair<String, String>>> finalizeCoinsend (Transaction tobj, DSCoin_Honest DSObj) throws MissingTransactionException {
		TransactionBlock present = block(tobj,DSObj);
		int doc_idx=ind(tobj,DSObj);
		TreeNode present_node = get_node(tobj,present,DSObj);
		ArrayList<Pair<String, String>> out1 = new ArrayList<Pair<String, String>>();
		while (present_node.parent != null) {
      String first_1 = present_node.parent.left.val;
      String second_1 = present_node.parent.right.val;
			Pair<String, String> ins = new Pair<String, String>(first_1,second_1);
			out1.add(ins);
			present_node = present_node.parent;
		}
    String first_2 = present_node.val;
		Pair<String, String> f = new Pair<String, String>(first_2, null);
		out1.add(f);
		TransactionBlock present1 = DSObj.bChain.lastBlock;
    TransactionBlock present2 = present.previous;
		List<Pair<String, String>> out2 = new ArrayList<Pair<String, String>>();
		while(present1!=present2){
			String s;
			if(present1.previous==null){
        Pair<String, String> new_p = new Pair<String, String>(present1.dgst,DSObj.bChain.start_string + "#" + present1.trsummary + "#" + present1.nonce);
			  out2.add(0,new_p);
			  present1 = present1.previous;
			}
			else{
        Pair<String, String> new_p = new Pair<String, String>(present1.dgst,present1.previous.dgst + "#" + present1.trsummary + "#" + present1.nonce);
			out2.add(0,new_p);
			present1 = present1.previous;
			}
		}
		if(present1==null){
      String last =  DSObj.bChain.start_string;
      Pair<String, String> last_p = new Pair<String, String>(last,null);
			out2.add(0,last_p);
		} 
		else{
			out2.add(0,new Pair<String, String>(present1.dgst,null));
		}
		int en =0 ;
		while(en<this.in_process_trans.length){
			if(this.in_process_trans[en]==tobj){
				this.in_process_trans[en] = null;
			}
      en++;
		}
    int k_4 = 0;
    int z_1 = 0;
		while(z_1<tobj.Destination.mycoins.size()){
			if(tobj.Destination.mycoins.get(z_1).get_first().compareTo(tobj.coinID)>=0){
				tobj.Destination.mycoins.add(z_1,new Pair<String, TransactionBlock>(tobj.coinID,present));
				k_4 = 1;
				break;
			}
      z_1++;
		}
		if(k_4 == 0){
			tobj.Destination.mycoins.add(new Pair<String, TransactionBlock>(tobj.coinID,present));
		}
    Pair<List<Pair<String, String>>, List<Pair<String, String>>> out_final = new Pair<List<Pair<String, String>>, List<Pair<String, String>>>(out1,out2);
		return out_final;
	} 

	public void MineCoin(DSCoin_Malicious DSObj){
    int r = 0;
    Transaction t_new = new Transaction();
    int val = DSObj.bChain.tr_count;
    Transaction[] t_list = new Transaction[val];
		TransactionBlock get = DSObj.bChain.FindLongestValidChain();
		t_new.coinID = Integer.toString(Integer.parseInt(DSObj.latestCoinID)+1);
     //Using hash map for checking 
    HashMap<String, String> new_map = new HashMap<String, String>(); 
		
		while(r<(val-1)){
			try{
				Transaction t_temp = DSObj.pendingTransactions.RemoveTransaction();
				if(get.checkTransaction(t_temp)&&new_map.get(t_temp.coinID)==null){
					new_map.put(t_temp.coinID,t_temp.coinID);
					t_list[r]=t_temp;
					r+=1;
				}
      		}
     		catch(Exception EmptyQueueException){
				 return;
			}			
		}
		t_new.coinsrc_block = null;
		t_new.Destination = this;
    t_new.Source = null;
		t_list[val-1] = t_new;
		TransactionBlock t_fin = new TransactionBlock(t_list);
    this.mycoins.add(new Pair<String, TransactionBlock>(t_new.coinID,t_fin));
		DSObj.bChain.InsertBlock_Malicious(t_fin);
		DSObj.latestCoinID = t_new.coinID;
	}  

  public void MineCoin(DSCoin_Honest DSObj){
    int r = 0;
    Transaction t_new = new Transaction();
    int val = DSObj.bChain.tr_count;
		Transaction[] t_list = new Transaction[val];
		t_new.coinID = String.valueOf(Integer.parseInt(DSObj.latestCoinID)+1);
    //Using hash map for checking 
    HashMap<String, String> new_map = new HashMap<String, String>(); 
	
		while(r<(val-1)){
			try{
				Transaction t_temp = DSObj.pendingTransactions.RemoveTransaction();
				if(DSObj.bChain.lastBlock.checkTransaction(t_temp)&&new_map.get(t_temp.coinID)==null){
					new_map.put(t_temp.coinID,t_temp.coinID);
					t_list[r]=t_temp;
					r+=1;
				}        
      		}
     		catch(Exception EmptyQueueException){
				 return;
			}			
		}
    t_list[val-1] = t_new;
    t_new.Destination = this;
		t_new.coinsrc_block = null;
    t_new.Source = null;
		TransactionBlock t_fin = new TransactionBlock(t_list);
    this.mycoins.add(new Pair<String, TransactionBlock>(t_new.coinID,t_fin));
		DSObj.bChain.InsertBlock_Honest(t_fin);
		DSObj.latestCoinID = t_new.coinID;
	} 
}