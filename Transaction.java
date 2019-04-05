package atm;

public abstract class Transaction {
   private int accountNumber; // indicates account involved
   private Screen screen; // ATM screen
   private BankDatabase bankDatabase; // account database
   private CashDispenser cashDispenser;
   
   public Transaction(int userAccountNumber, Screen atmScreen, 
      BankDatabase atmBankDatabase) {

      accountNumber = userAccountNumber;
      screen = atmScreen;
      bankDatabase = atmBankDatabase;
   }
   
   public Transaction(Screen atmScreen, CashDispenser atmCashDispenser) {
       screen = atmScreen;
       cashDispenser = atmCashDispenser;
   }
   
   public Transaction(int userAccountNumber, Screen atmScreen, 
      BankDatabase atmBankDatabase, CashDispenser atmCashDispenser) {
       
      accountNumber = userAccountNumber;
      screen = atmScreen;
      bankDatabase = atmBankDatabase;
      cashDispenser = atmCashDispenser;
   }

   // return account number 
   public int getAccountNumber() {
      return accountNumber; 
   } 

   // return reference to screen
   public Screen getScreen() {
      return screen;
   } 

   // return reference to bank database
   public BankDatabase getBankDatabase() {
      return bankDatabase;
   } 
   
   public CashDispenser getCashDispenser() {
       return cashDispenser;
   }

   // perform the transaction (overridden by each subclass)
   abstract public void execute();
} 