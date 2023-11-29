import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import org.mockito.Mock;
import org.mockito.Mockito;

public class BankTest {

  private Bank bank;
  @Mock
  private JsonFileManager jsonFileManager;

  @BeforeEach
  public void setUp() throws IOException {
    jsonFileManager = Mockito.mock(JsonFileManager.class);
    bank = new Bank(jsonFileManager);

    LinkedHashSet<Account> storedAccounts = new LinkedHashSet<>(); //Simulating initial accounts
    when(jsonFileManager.loadAccounts()).thenReturn(storedAccounts);
    doAnswer(invocation -> {
      storedAccounts.addAll(bank.getAllAccounts());
      return null;
    }).when(jsonFileManager).saveAccounts(Mockito.anySet());

    LinkedHashSet<Transaction> storedTransactions = new LinkedHashSet<>();
    when(jsonFileManager.loadTransactions()).thenReturn(storedTransactions);
    doAnswer(invocation -> {
      storedTransactions.addAll(bank.getTransactionHistory());
          return null;
    }).when(jsonFileManager).saveTransactions(Mockito.anySet());
  }

  @Test
  public void testCreateAccount() throws IOException {
    bank.createAccount("Aldo Oporto");
    List<Account> accounts = new ArrayList<>(bank.getAllAccounts());

    Assertions.assertEquals("ACC-1000", accounts.get(0).getAccountNumber());
    Assertions.assertEquals("Aldo Oporto", accounts.get(0).getOwnerName());
  }

  @Test
  public void testCreateMultipleAccounts() throws IOException {
    bank.createAccount("Aldo Oporto");
    bank.createAccount("John Doe");
    List<Account> accounts = new ArrayList<>(bank.getAllAccounts());

    Assertions.assertEquals("Aldo Oporto", accounts.get(0).getOwnerName());
    Assertions.assertEquals("ACC-1000", accounts.get(0).getAccountNumber());

    Assertions.assertEquals("ACC-1001", accounts.get(1).getAccountNumber());
    Assertions.assertEquals("John Doe", accounts.get(1).getOwnerName());
  }

  @Test
  public void testDepositIntoAccount() throws IOException {
    bank.createAccount("Aldo Oporto");
    bank.deposit("ACC-1000", 100);
    Assertions.assertEquals(100, new ArrayList<>(bank.getAllAccounts()).get(0).getBalance());
  }

  @Test
  public void testDepositInvalidAmount() throws IOException {
    bank.createAccount("Aldo Oporto");
    bank.deposit("ACC-1000", -100);
    Assertions.assertNotEquals(-100, new ArrayList<>(bank.getAllAccounts()).get(0).getBalance());
  }

  @Test
  public void testWithdrawFromAccount() throws IOException {
    bank.createAccount("Aldo Oporto");
    bank.deposit("ACC-1000", 100);
    bank.withdraw("ACC-1000", 30);

    Assertions.assertEquals(70, new ArrayList<>(bank.getAllAccounts()).get(0).getBalance());
  }

  @Test
  public void testWithdrawInvalidAmount() throws IOException {
    bank.createAccount("Aldo Oporto");
    bank.deposit("ACC-1000", 50);
    bank.withdraw("ACC-1000", -10); //Attempt to withdraw invalid amount
    bank.withdraw("ACC-1000", 100); //Attempt to withdraw more than balance

    Assertions.assertNotEquals(-50, new ArrayList<>(bank.getAllAccounts()).get(0).getBalance());
  }

  @Test
  public void testProcessTransaction() throws IOException {
    bank.createAccount("Sender");
    bank.createAccount("Receiver");
    bank.deposit("ACC-1000", 350); //Depositing into Sender account

    bank.processTransaction("ACC-1000", "ACC-1001", 50.0);
    List<Transaction> transactions = new ArrayList<>(bank.getTransactionHistory());

    Assertions.assertEquals(1, transactions.size());
    Assertions.assertEquals("ACC-1000", transactions.get(0).getSender());
    Assertions.assertEquals("ACC-1001", transactions.get(0).getReceiver());
    Assertions.assertEquals(50.0, transactions.get(0).getAmount());
  }

  @Test
  public void testProcessNegativeTransaction() throws IOException {
    bank.createAccount("Sender");
    bank.createAccount("Receiver");
    bank.deposit("ACC-1000", 350); //Depositing into Sender account

    bank.processTransaction("ACC-1000", "ACC-1001", -50.0);
    List<Transaction> transactions = new ArrayList<>(bank.getTransactionHistory());

    Assertions.assertEquals(0, transactions.size());
  }

  @Test
  public void testProcessTransactionInvalidAccounts() throws IOException {
    bank.createAccount("Sender");
    bank.createAccount("Receiver");
    bank.deposit("ACC-1000", 100);

    bank.processTransaction("ACC-1000", "InvalidAccount", 50.0);
    Assertions.assertEquals(0, bank.getTransactionHistory().size());
  }

  @Test
  public void testProcessTransactionInsufficientBalance() throws IOException {
    bank.createAccount("Sender");
    bank.createAccount("Receiver");
    bank.deposit("ACC-1001", 150); //Depositing into receiver account

    bank.processTransaction("ACC-1000", "ACC-1001", 100.0);
    Assertions.assertEquals(0, bank.getTransactionHistory().size());
  }
}
