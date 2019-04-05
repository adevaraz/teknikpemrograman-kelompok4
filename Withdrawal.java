package atm;

// Withdrawal.java
// Represents a withdrawal ATM transaction

public class Withdrawal extends Transaction {
   private int amount; // amount to withdraw
   private Keypad keypad; // reference to keypad
   private CashDispenser cashDispenser; // reference to cash dispenser

   // constant corresponding to menu option to cancel
   private final static int OTHER = 6;
   private final static int CANCELED = 7;

   // Withdrawal constructor
   public Withdrawal(int userAccountNumber, Screen atmScreen, 
      BankDatabase atmBankDatabase, Keypad atmKeypad, 
      CashDispenser atmCashDispenser) {

      // initialize superclass variables
      super(userAccountNumber, atmScreen, atmBankDatabase, atmCashDispenser);
      
   }

   // perform transaction
   @Override
   public void execute() {
      BankDatabase bankDatabase = getBankDatabase();
      Screen screen = getScreen();
      cashDispenser = getCashDispenser();
      
      amount = displayMenuOfAmounts();
      
      if(amount == CANCELED) {
        screen.displayMessageLine("Canceling transaction...");
      }
      else {
        if(cashDispenser.isSufficientCashAvailable(amount)) {
            cashDispenser.dispenseCash(amount);
            bankDatabase.debit(getAccountNumber(), amount); 
        } else {
            screen.displayMessageLine("This ATM has not enough money." +
                    "\nTransaction cancelled...");
        }
      }
   } 

   // display a menu of withdrawal amounts and the option to cancel;
   // return the chosen amount or 0 if the user chooses to cancel
   private int displayMenuOfAmounts() {
      int userChoice = 0; // local variable to store return value
      keypad = new Keypad();
      
      Screen screen = getScreen(); // get screen reference
      
      // array of amounts to correspond to menu numbers
      int[] amounts = {0, 20, 40, 60, 100, 200};

      // loop while no valid choice has been made
      while (userChoice == 0) {
         // display the withdrawal menu
         screen.displayMessageLine("\nWithdrawal Menu:");
         screen.displayMessageLine("1 - $20");
         screen.displayMessageLine("2 - $40");
         screen.displayMessageLine("3 - $60");
         screen.displayMessageLine("4 - $100");
         screen.displayMessageLine("5 - $200");
         screen.displayMessageLine("6 - Other");
         screen.displayMessageLine("7 - Cancel transaction");
         screen.displayMessage("Choose a withdrawal amount: ");

         int input = keypad.getInput(); // get user input through keypad

         // determine how to proceed based on the input value
         switch (input) {
            case 1: // if the user chose a withdrawal amount 
            case 2: // (i.e., chose option 1, 2, 3, 4 or 5), return the
            case 3: // corresponding amount from amounts array
            case 4:
            case 5:
                userChoice = amounts[input]; // save user's choice
               break; 
            case 6: 
                userChoice = otherAmounts();
               break;
            case CANCELED: // the user chose to cancel
               userChoice = CANCELED; // save user's choice
               break;
            default: // the user did not enter a value from 1-6
               screen.displayMessageLine(
                  "\nInvalid selection. Try again.");
         } 
      } 

      return userChoice; // return withdrawal amount or CANCELED
   }

    private int otherAmounts() {
        Screen screen = getScreen();
        keypad = new Keypad();
        boolean end = false;
        int input = 0;
        
        while(!end) {
            screen.displayMessage("Enter amount of withdrawal : ");
            input = keypad.getInput();
            if(input < 0) {
                screen.displayMessageLine("\nPlease enter amount above 0");
            }
            else if (input > 200) {
                screen.displayMessageLine("\nPlease enter amount under $200.0");
            } else {
                end = true;
            }
        }
       
        return input;
    }
} 