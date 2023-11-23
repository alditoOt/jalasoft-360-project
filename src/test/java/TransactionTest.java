import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class TransactionTest {
  private Transaction transaction;

  @BeforeEach
  public void setUp() {
    transaction = new Transaction();
  }

  @Test
  public void testTransactionDetails() {
    transaction.makeTransaction(1, "Sender", "Receiver", 100);
    Assertions.assertEquals(1, transaction.getTransactionId());
    Assertions.assertEquals("Sender", transaction.getSender());
    Assertions.assertEquals("Receiver", transaction.getReceiver());
    Assertions.assertEquals(100.0, transaction.getAmount());
  }

  @Test
  public void testTransactionTimestamp() {
    LocalDateTime currentTimestamp = LocalDateTime.now();
    transaction.makeTransaction(1, "Sender", "Receiver", 100);
    // Ensure the timestamp is within a reasonable range
    Assertions.assertTrue(transaction.getTimestamp().isBefore(currentTimestamp.plusSeconds(1)));
    Assertions.assertTrue(transaction.getTimestamp().isAfter(currentTimestamp.minusSeconds(1)));
  }

  @Test
  public void testNegativeAmountTransaction() {
    transaction.makeTransaction(1, "Sender", "Receiver", -50.0);
    Assertions.assertEquals(0.0, transaction.getAmount());
  }
}
