package DSCoinPackage;
import java.util.Arrays;
import HelperClasses.*;
public class Moderator
 {
	   public void initializeDSCoin(DSCoin_Malicious DSObj, int coinCount) {
		int c = 100000;
		int dup = coinCount;
		Members m = new Members();
		m.UID = "Moderator";
		int mem_count = DSObj.memberlist.length;
		while(dup!=0){
			Transaction[] t_itter = new Transaction[DSObj.bChain.tr_count];
			int j = 0;
			while( j<DSObj.bChain.tr_count){
				if(dup==0){
					return;
				}
				Transaction temp = new Transaction();
				
				temp.coinsrc_block = null;
				temp.coinID = String.valueOf(c);
				temp.Source = m;
				int mem = (c-100000) % mem_count;
				temp.Destination = DSObj.memberlist[mem];
				t_itter[j]=temp;
				DSObj.latestCoinID = String.valueOf(c);
				c+=1;
				dup-=1;
				j++;
			}
			TransactionBlock new_Block = new TransactionBlock(t_itter);
			int i = 0;
			while( i<t_itter.length) {
				Pair<String, TransactionBlock> temp_1 = new Pair<String, TransactionBlock>(t_itter[i].coinID,new_Block);
				t_itter[i].Destination.mycoins.add(temp_1);
				i+=1;
			}
			DSObj.bChain.InsertBlock_Malicious(new_Block);
		}
	}

  public void initializeDSCoin(DSCoin_Honest DSObj, int coinCount) {
		int c = 100000;
		int dup = coinCount;
		Members m = new Members();
		m.UID = "Moderator";
		int mem_count = DSObj.memberlist.length;
		while(dup!=0){
			Transaction[] t_itter = new Transaction[DSObj.bChain.tr_count];
			for(int j=0;j<DSObj.bChain.tr_count;j++){
				if(dup==0){
					return;
				}
				Transaction temp = new Transaction();
				temp.coinsrc_block = null;
				temp.coinID = String.valueOf(c);
				temp.Source = m;
				int mem = (c-100000) % mem_count;
				temp.Destination = DSObj.memberlist[mem];
				t_itter[j]=temp;
				DSObj.latestCoinID = String.valueOf(c);
				c+= 1;
				dup-=1;
			}
			TransactionBlock new_Block = new TransactionBlock(t_itter);
			int i =0;
			while( i<t_itter.length) {
				Pair<String, TransactionBlock> temp_1 = new Pair<String, TransactionBlock>(t_itter[i].coinID,new_Block);
				t_itter[i].Destination.mycoins.add(temp_1);
				i+=1;
			}
			DSObj.bChain.InsertBlock_Honest(new_Block);
		}
	}
  

}
