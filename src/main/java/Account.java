import java.util.UUID;

public class Account {
  private final String accountNumber;
  private double balance;
  private final String ownerName;
  private int uniqueID;

  public Account(String ownerName, int uniqueID) {
    this.accountNumber = generateAccountNumber(uniqueID);
    this.ownerName = ownerName;
    this.balance = 0.0; // Initializing balance to zero
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public double getBalance() {
    return balance;
  }

  public String getOwnerName() {
    return ownerName;
  }

  public int getUniqueID() {
    return uniqueID;
  }

  public void deposit(double amount) {
    if (amount > 0) {
      balance += amount;
      System.out.println("Deposited: $" + amount + " into " + accountNumber + ". Current balance is: $" + balance);
    }
  }

  private String generateAccountNumber(int uniqueID) {
    String prefix = "ACC"; // Prefix for the account number
    this.uniqueID = uniqueID;
    return prefix + "-" + uniqueID;
  }

  public void withdraw(double amount) {
    if (amount > 0) {
      if (balance >= amount) {
      balance -= amount;
      System.out.println("Withdrawn: $" + amount + " from " + accountNumber + ". Current balance is: $" + balance);
      }
      else {
      System.out.println("Insufficient funds for withdrawal!");
      }
    }
    else {
      System.out.println("Invalid amount for withdrawal.");
    }
  }

  public void checkBalance() {
    System.out.println("Account Balance: $" + balance);
  }
}
