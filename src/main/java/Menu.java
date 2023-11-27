import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
  private final Bank bank;
  private final Scanner scanner;
  private int choice;

  public Menu(Bank bank) {
    this.bank = bank;
    this.scanner = new Scanner(System.in);
  }

  public void displayMenu() throws IOException {
    boolean exit = false;

    while (!exit) {
      System.out.println("\nWelcome to A.L.D.O.!");
      System.out.println("1. Create Account");
      System.out.println("2. Deposit Into Account");
      System.out.println("3. Withdraw From Account");
      System.out.println("4. Process Transaction");
      System.out.println("5. Show All Accounts");
      System.out.println("6. Show Transaction History");
      System.out.println("7. Exit");
      System.out.print("Enter your choice: ");
      try {
      choice = scanner.nextInt();
      scanner.nextLine();
      } catch(InputMismatchException e) {
        scanner.nextLine();
      }

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
          System.out.println("Thank you for using A.L.D.O.!");
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
    double amount = amountWithExceptionHandling("Enter amount to deposit: ");

    bank.deposit(accountNumber, amount);
  }

  private void withdrawFromAccount() {
    System.out.println("Enter account number: ");
    String accountNumber = scanner.nextLine();
    double amount = amountWithExceptionHandling("Enter amount to withdraw> ");

    bank.withdraw(accountNumber, amount);
  }

  private void processTransaction() throws IOException {
    System.out.print("Enter sender's account number: ");
    String sender = scanner.nextLine();
    System.out.print("Enter receiver's account number: ");
    String receiver = scanner.nextLine();

    double amount = amountWithExceptionHandling("Enter amount to transfer: ");

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

  private double amountWithExceptionHandling(String message) {
    System.out.print(message);
    double amount;
    try {
      amount = scanner.nextDouble();
    } catch (InputMismatchException e) {
      System.out.println("Invalid value. Please input a numerical value.");
      scanner.nextLine();
      return 0;
    }
    return amount;
  }
}
