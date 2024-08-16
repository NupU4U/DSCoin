package DSCoinPackage;

public class TransactionQueue {

  public Transaction firstTransaction;
  public Transaction lastTransaction;
  public int numTransactions;

  public void AddTransactions (Transaction transaction) {
    if (this.numTransactions ==0){
      this.firstTransaction = transaction;
      this.lastTransaction = transaction;
      this.numTransactions = 1;
    }
    else{
    Transaction temp = new Transaction();
    temp = this.lastTransaction;
    this.lastTransaction.next = transaction;
	transaction.previous = this.lastTransaction;
    this.lastTransaction = transaction;
    this.numTransactions +=1;
    }
  } 
  public Transaction RemoveTransaction () throws EmptyQueueException {
    Transaction temp = new Transaction();
    temp = this.firstTransaction;
    
    if (this.numTransactions == 0){
      throw new EmptyQueueException() ;
    }
    else if(this.numTransactions == 1){
      this.lastTransaction = null;
      this.firstTransaction = null;
      this.numTransactions = 0;
      return temp;
    }
    else {
      this.firstTransaction = this.firstTransaction.next;
      this.firstTransaction.previous = null;
      this.numTransactions -= 1;
      return temp; 
    }
  }

  public int size() {
    return this.numTransactions;
  }
}