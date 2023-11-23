import java.io.IOException;

public class Main {
  public static void main(String[] args) throws IOException {
    Bank bank = new Bank(new JsonFileManager());
    Menu menu = new Menu(bank);
    menu.displayMenu();
  }
}
