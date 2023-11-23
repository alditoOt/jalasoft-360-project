import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountTest {
  private Account account;

  @BeforeEach
  public void setUp() {
    account = new Account("John Doe", 1000);
  }

  @Test
  public void testInitialAccountNumber() {
    Assertions.assertEquals("ACC-1000", account.getAccountNumber());
  }

  @Test
  public void testInitialBalance() {
    Assertions.assertEquals(0.0, account.getBalance());
  }

  @Test
  public void testDeposit() {
    account.deposit(100.0);
    Assertions.assertEquals(100.0, account.getBalance());
  }

  @Test
  public void testWithdrawSufficientBalance() {
    account.deposit(200.0);
    account.withdraw(100.0);
    Assertions.assertEquals(100.0, account.getBalance());
  }

  @Test
  public void testWithdrawInsufficientBalance() {
    account.deposit(50.0);
    account.withdraw(100.0);
    Assertions.assertEquals(50.0, account.getBalance());
  }

  @Test
  public void testWithdrawNegativeAmount() {
    account.deposit(100.0);
    account.withdraw(-50.0);
    Assertions.assertEquals(100.0, account.getBalance());
  }

  @Test
  public void testWithdrawZeroAmount() {
    account.deposit(100.0);
    account.withdraw(0.0);
    Assertions.assertEquals(100.0, account.getBalance());
  }
}
