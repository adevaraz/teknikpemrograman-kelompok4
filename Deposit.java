package atm;

public class Deposit extends Transaction {
   private double amount; // amount to deposit
   private Keypad keypad; // reference to keypad
   private DepositSlot depositSlot; // reference to deposit slot
   private final static int CANCELED = 0; // constant for cancel option

   // Deposit constructor
   public Deposit(int userAccountNumber, Screen atmScreen, 
      BankDatabase atmBankDatabase, Keypad atmKeypad, 
      DepositSlot atmDepositSlot) {

      // initialize superclass variables
      super(userAccountNumber, atmScreen, atmBankDatabase);

   } 

   // perform transaction
   @Override
   public void execute() {
      BankDatabase bankDatabase = getBankDatabase();
      Screen screen = getScreen();
      depositSlot = new DepositSlot();
      boolean received;
      
      amount = promptForDepositAmount();
      if(amount==0) {
          screen.displayMessageLine("Cancelling transaction...");
      } else {
          if(amount>0) {
            bankDatabase.credit(getAccountNumber(), amount);
            screen.displayMessage("Please insert a deposit envelope containing ");
            screen.displayDollarAmount(amount);
            received = depositSlot.isEnvelopeReceived();
          
            if(received) {
              screen.displayMessageLine("\nYour envelope has been received.");
              screen.displayMessageLine("NOTE: The money just deposited will"
                      + " not be available until we verify the amount of any "
                      + "enclosed cash and your checks clear.");
            }
            
          } else {
              screen.displayMessageLine("Your transaction failed.");
          }
      }
   }

   // prompt user to enter a deposit amount in cents 
   private double promptForDepositAmount() {
      keypad = new Keypad();
      Screen screen = getScreen(); // get reference to screen

      // display the prompt
      screen.displayMessage("\nPlease enter a deposit amount in " + 
         "CENTS (or 0 to cancel): ");
      int input = keypad.getInput(); // receive input of deposit amount
      
      // check whether the user canceled or entered a valid amount
      if (input == CANCELED) {
         return CANCELED;
      }
      else {
         return (double) input / 100; // return dollar amount
      }
   }
} 
