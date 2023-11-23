import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.google.gson.reflect.TypeToken;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class JsonFileManager {
  private static final String ACCOUNTS_FILE = "D:\\Personal\\banking-project\\data\\accounts.json";
  private static final String TRANSACTIONS_FILE = "D:\\Personal\\banking-project\\data\\transactions.json";

  private final Gson gson;
  private final Path accountsPath;
  private final Path transactionsPath;

  public JsonFileManager() {
    gson = new GsonBuilder().setPrettyPrinting().create();
    accountsPath = Path.of(ACCOUNTS_FILE);
    transactionsPath = Path.of(TRANSACTIONS_FILE);

    // Create files if they don't exist
    try {
      Files.createFile(accountsPath);
      Files.createFile(transactionsPath);
    } catch (IOException ignored) {
      // Files already exist
    }
  }

  public void saveAccounts(Set<Account> accounts) {
    try (FileWriter writer = new FileWriter(accountsPath.toString())) {
      gson.toJson(accounts, writer);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public LinkedHashSet<Account> loadAccounts() throws IOException {
    Type accountListType = new TypeToken<LinkedHashSet<Account>>() {}.getType();
    try (Reader reader = Files.newBufferedReader(accountsPath)) {
      return gson.fromJson(reader, accountListType);
    }
  }

  public void saveTransactions(Set<Transaction> transactions) {
    try (FileWriter writer = new FileWriter(transactionsPath.toString())) {
      gson.toJson(transactions, writer);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public LinkedHashSet<Transaction> loadTransactions() throws IOException {
    Type accountListType = new TypeToken<LinkedHashSet<Transaction>>() {}.getType();
    try (Reader reader = Files.newBufferedReader(transactionsPath)) {
      return gson.fromJson(reader, accountListType);
    }
  }
}
