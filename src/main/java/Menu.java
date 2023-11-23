import java.io.IOException;
import java.util.Scanner;

public class Menu {
  private final Bank bank;
  private final Scanner scanner;

  public Menu(Bank bank) {
    this.bank = bank;
    this.scanner = new Scanner(System.in);
  }

  public void displayMenu() throws IOException {
    boolean exit = false;

    while (!exit) {
      System.out.println("\nWelcome to the Simple Banking System!");
      System.out.println("1. Create Account");
      System.out.println("2. Deposit Into Account");
      System.out.println("3. Withdraw From Account");
      System.out.println("4. Process Transaction");
      System.out.println("5. Show All Accounts");
      System.out.println("6. Show Transaction History");
      System.out.println("7. Exit");
      System.out.print("Enter your choice: ");
      int choice = scanner.nextInt();
      scanner.nextLine(); // Consume newline

      switch (choice) {
        case 1:
          createAccount();
          break;
        case 2:
          depositIntoAccount();
          break;
        case 3:
          withdrawFromAccount();
          break;
        case 4:
          processTransaction();
          break;
        case 5:
          showAllAccounts();
          break;
        case 6:
          showTransactionHistory();
          break;
        case 7:
          exit = true;
          System.out.println("Thank you for using the Banking System!");
          break;
        default:
          System.out.println("Invalid choice. Please enter a valid option.");
      }
    }
    scanner.close();
  }

  private void createAccount() throws IOException {
    System.out.print("Enter owner name: ");
    String ownerName = scanner.nextLine();

    bank.createAccount(ownerName);
  }

  private void depositIntoAccount() {
    System.out.println("Enter account number: ");
    String accountNumber = scanner.nextLine();

    System.out.println("Enter amount to deposit: ");
    double amount = scanner.nextDouble();
    bank.deposit(accountNumber, amount);
  }

  private void withdrawFromAccount() {
    System.out.println("Enter account number: ");
    String accountNumber = scanner.nextLine();

    System.out.println("Enter amount to withdraw: ");
    double amount = scanner.nextDouble();
    bank.withdraw(accountNumber, amount);
  }

  private void processTransaction() throws IOException {
    System.out.print("Enter sender's account number: ");
    String sender = scanner.nextLine();
    System.out.print("Enter receiver's account number: ");
    String receiver = scanner.nextLine();
    System.out.print("Enter amount to transfer: ");
    double amount = scanner.nextDouble();
    scanner.nextLine(); // Consume newline

    bank.processTransaction(sender, receiver, amount);
  }

  private void showAllAccounts() {
    System.out.println("All Accounts:");
    for (Account account : bank.getAllAccounts()) {
      System.out.println("Account Number: " + account.getAccountNumber() + ", Owner: " + account.getOwnerName() + ", Balance: $" + account.getBalance());
    }
  }

  private void showTransactionHistory() {
    System.out.println("Transaction History:");
    for (Transaction transaction : bank.getTransactionHistory()) {
      System.out.println("Transaction ID: " + transaction.getTransactionId() + ", Sender: " + transaction.getSender() +
          ", Receiver: " + transaction.getReceiver() + ", Amount: $" + transaction.getAmount() + ", Timestamp: " + transaction.getTimestamp());
    }
  }
}
