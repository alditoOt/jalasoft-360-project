import java.time.LocalDateTime;

public class Transaction {
  private int transactionId;
  private LocalDateTime timestamp;
  private String sender;
  private String receiver;
  private double amount;

  public Transaction() {
  }

  public int getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(int transactionId) {
    this.transactionId = transactionId;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public String getSender() {
    return sender;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

  public String getReceiver() {
    return receiver;
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public boolean makeTransaction(int transactionId, String sender, String receiver, double amount) {
    if (amount > 0) {
      this.amount = amount;
    }
    else {
      //Negative amounts for transactions aren't allowed
      System.out.println("Invalid amount for transaction.");
      return false;
    }
   this.transactionId = transactionId;
    this.sender = sender;
    this.receiver = receiver;
    this.timestamp = LocalDateTime.now();
    return true;
  }

  //Unused - doesn't consider negative/invalid amounts
//  public void makeTransaction(int transactionId, String sender, String receiver, double amount) {
//    this.amount = amount;
//    this.transactionId = transactionId;
//    this.sender = sender;
//    this.receiver = receiver;
//    this.timestamp = LocalDateTime.now();
//  }
}
