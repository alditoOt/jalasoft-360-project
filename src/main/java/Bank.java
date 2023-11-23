import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Bank {
  private LinkedHashSet<Account> accounts;
  private LinkedHashSet<Transaction> transactionHistory;
  private final JsonFileManager fileManager;
  private static final int INITIAL_UNIQUE_ID = 1000;
  public Bank(JsonFileManager fileManager) throws IOException {
    this.fileManager = fileManager;
    loadFromFile();
  }

  public void createAccount(String ownerName) throws IOException {
    Set<Account> storedAccounts = fileManager.loadAccounts();
    if (storedAccounts != null) {
    accounts.clear();
    accounts.addAll(storedAccounts);
    }
    Account newAccount;
    if(accounts.size() != 0) {
      newAccount = new Account(ownerName, new ArrayList<>(accounts).get(accounts.size() - 1).getUniqueID() + 1);
    }
    else {
      newAccount = new Account(ownerName, INITIAL_UNIQUE_ID);
    }
    accounts.add(newAccount);
    System.out.println("New account created for " + ownerName + ": " + newAccount.getAccountNumber());
    fileManager.saveAccounts(accounts);
  }

  public void deposit(String accountNumber, double amount) {
    Account foundAccount = getAccountByNumber(accountNumber);
    foundAccount.deposit(amount);
    fileManager.saveAccounts(accounts);
  }

  public void withdraw(String accountNumber, double amount) {
    Account account = getAccountByNumber(accountNumber);
    account.withdraw(amount);
    fileManager.saveAccounts(accounts);
  }

  public void processTransaction(String senderAccountNumber, String receiverAccountNumber, double amount)
      throws IOException {
    Account sender = getAccountByNumber(senderAccountNumber);
    Account receiver = getAccountByNumber(receiverAccountNumber);

    Set<Transaction> storedTransactions = fileManager.loadTransactions();
    if (storedTransactions != null) {
      transactionHistory.clear();
      transactionHistory.addAll(storedTransactions);
    }

    if (sender != null && receiver != null) {
      if (sender.getBalance() >= amount) {
        sender.withdraw(amount);
        receiver.deposit(amount);

        Transaction transaction = new Transaction();
       if(transaction.makeTransaction(transactionHistory.size() + 1, senderAccountNumber, receiverAccountNumber, amount)) {
        transactionHistory.add(transaction);
        fileManager.saveAccounts(accounts);
        fileManager.saveTransactions(transactionHistory);
        System.out.println("Transaction processed: " + senderAccountNumber + " sent $" + amount + " to " + receiverAccountNumber);
       }
      } else {
        System.out.println("Sender has insufficient funds for this transaction.");
      }
    } else {
      System.out.println("Invalid sender or receiver account.");
    }
  }

  private void loadFromFile() throws IOException {
    if (fileManager.loadAccounts() != null) {
      accounts = fileManager.loadAccounts();
    } else {
      accounts = new LinkedHashSet<>();
    }
    if (fileManager.loadTransactions() != null) {
      transactionHistory = fileManager.loadTransactions();
    } else {
      transactionHistory = new LinkedHashSet<>();
    }
  }

  public Account getAccountByNumber(String accountNumber) {
    for (Account account : accounts) {
      if (account.getAccountNumber().equals(accountNumber)) {
        return account;
      }
    }
    System.out.println("No account found with account number " + accountNumber);
    return null;
  }

  public Account getAccountByOwnerName(String ownerName) {
    for (Account account : accounts) {
      if (account.getOwnerName().equals(ownerName)) {
        return account;
      }
    }
    System.out.println("No account found for " + ownerName);
    return null;
  }

  public LinkedHashSet<Account> getAllAccounts() {
    return accounts;
  }

  public LinkedHashSet<Transaction> getTransactionHistory() {
    return transactionHistory;
  }
}
