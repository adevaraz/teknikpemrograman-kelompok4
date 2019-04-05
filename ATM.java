package atm;

public class ATM {
   private boolean userAuthenticated; // whether user is authenticated
   private int currentAccountNumber; // current user's account number
   private boolean currentStatus;
   private int currentRole;
   private Screen screen; // ATM's screen
   private Keypad keypad; // ATM's keypad
   private CashDispenser cashDispenser; // ATM's cash dispenser
   private DepositSlot depositSlot;

   private BankDatabase bankDatabase; // account information database

   // constants corresponding to main menu options
   private static final int BALANCE_INQUIRY = 1;
   private static final int WITHDRAWAL = 2;
   private static final int DEPOSIT = 3;
   private static final int CHANGEPIN = 4;
   private static final int TRANSFER = 5;
   private static final int EXIT = 6;

   // no-argument ATM constructor initializes instance variables
   public ATM() {
      userAuthenticated = false; // user is not authenticated to start
      currentAccountNumber = 0; // no current account number to start
      screen = new Screen(); // create screen
      keypad = new Keypad(); // create keypad 
      cashDispenser = new CashDispenser(); // create cash dispenser
      bankDatabase = new BankDatabase(); // create acct info database
   }

   // start ATM 
   public void run() {
      // welcome and authenticate user; perform transactions
      while (true) {
         // loop while user is not yet authenticated
         while (!userAuthenticated) {
            screen.displayMessageLine("\nWelcome!");       
            authenticateUser(); // authenticate user
         }
         
         if(currentRole == 1) {
             adminTransactions();
         } else {
             if(currentStatus) {
             performTransactions(); // user is now authenticated
             screen.displayMessageLine("\nThank you! Goodbye!");
             }
         }
         userAuthenticated = false; // reset before next ATM session
         currentAccountNumber = 0; // reset before next ATM session
      }
   }

   // attempts to authenticate user against database
   private void authenticateUser() {
      screen.displayMessage("\nPlease enter your account number: ");
      int accountNumber = keypad.getInput(); // input account number
      
      int trial = 0;
      while(trial!=3) {
        screen.displayMessage("\nEnter your PIN: "); // prompt for PIN
        int pin = keypad.getInput(); // input PIN
      
      // set userAuthenticated to boolean value returned by database
        userAuthenticated = 
         bankDatabase.authenticateUser(accountNumber, pin);
         currentStatus = bankDatabase.getStatus(accountNumber);
      
      // check whether authentication succeeded
        if (userAuthenticated && currentStatus) {
          currentAccountNumber = accountNumber; // save user's account # 
          currentRole = bankDatabase.getRole(currentAccountNumber);
          break;
        } 
        else if(userAuthenticated && !currentStatus){
           screen.displayMessageLine(
            "Sorry, your account has been blocked.");
           break;
        } else {
            screen.displayMessageLine("Invalid account number or PIN. Please try again.");
            trial++;
        }
        
        if(trial == 3) {
          bankDatabase.setStatus(accountNumber, false);
          screen.displayMessageLine("Your account has been blocked.");
        }
      }
   } 

   // display the main menu and perform transactions
   private void performTransactions() {
      // local variable to store transaction currently being processed
      Transaction currentTransaction = null;
      
      boolean userExited = false; // user has not chosen to exit

      // loop while user has not chosen option to exit system
      while (!userExited) {
         // show main menu and get user selection
         int mainMenuSelection = displayMainMenu();

         // decide how to proceed based on user's menu selection
         switch (mainMenuSelection) {
            // user chose to perform one of three transaction types
            case BALANCE_INQUIRY:
                // initialize as new object of chosen type
               currentTransaction = 
                  createTransaction(mainMenuSelection);
               currentTransaction.execute(); // execute transaction
               break; 
            
            case WITHDRAWAL:
               currentTransaction = 
                  createTransaction(mainMenuSelection);
               currentTransaction.execute();
               break;
                
            case DEPOSIT:
               currentTransaction = 
                  createTransaction(mainMenuSelection);
               currentTransaction.execute();
               break;
               
            case CHANGEPIN:
               currentTransaction = 
                  createTransaction(mainMenuSelection);
               currentTransaction.execute();
               break;
               
            case TRANSFER:
               currentTransaction =
                   createTransaction(mainMenuSelection);
               currentTransaction.execute();
               break;
                
            case EXIT: // user chose to terminate session
               screen.displayMessageLine("\nExiting the system...");
               userExited = true; // this ATM session should end
               break;
               
            default: // 
               screen.displayMessageLine(
                  "\nYou did not enter a valid selection. Try again.");
               break;
         }
      } 
   } 
   
   private void adminTransactions() {
      Transaction currentTransaction = null;
      
      boolean userExited = false; // user has not chosen to exit

      // loop while user has not chosen option to exit system
      while (!userExited) {
         // show main menu and get user selection
         int adminSelection = displayAdminMenu();

         // decide how to proceed based on user's menu selection
         switch (adminSelection) {
            // user chose to perform one of three transaction types
            case 1: //VIEW DISPENSER
                // initialize as new object of chosen type
               currentTransaction = 
                  createTransaction(adminSelection);
               currentTransaction.execute(); // execute transaction
               break; 
            
            case 2: //DEPOSIT DISPENSER
               currentTransaction = 
                  createTransaction(adminSelection);
               currentTransaction.execute();
               break;
                
            case 3: //UNBLOCK USER
               currentTransaction = 
                  createTransaction(adminSelection);
               currentTransaction.execute();
               break;
                
            case 4: // user chose to terminate session //EXIT
               screen.displayMessageLine("\nExiting the system...");
               userExited = true; // this ATM session should end
               break;
               
            default:
               screen.displayMessageLine(
                  "\nYou did not enter a valid selection. Try again.");
               break;
         }
      }
   }

   // display the main menu and return an input selection
   private int displayMainMenu() {
      screen.displayMessageLine("\nMain menu:");
      screen.displayMessageLine("1 - View my balance");
      screen.displayMessageLine("2 - Withdraw cash");
      screen.displayMessageLine("3 - Deposit funds");
      screen.displayMessageLine("4 - Change Pin");
      screen.displayMessageLine("5 - Transfer");
      screen.displayMessageLine("6 - Exit\n");
      screen.displayMessage("Enter a choice: ");
      return keypad.getInput(); // return user's selection
   } 
   
   private int displayAdminMenu() {
       screen.displayMessageLine("\n\nLog in as Admin");
       screen.displayMessageLine("\nAdmin menu :");
       screen.displayMessageLine("1 - View dispenser");
       screen.displayMessageLine("2 - Deposit dispenser");
       screen.displayMessageLine("3 - Unblock user");
       screen.displayMessageLine("4 - Exit Admin mode\n");
       screen.displayMessage("Enter a choice: ");
       return keypad.getInput();
   }
         
   private Transaction createTransaction(int type) {
      Transaction temp = null; 
      
      if(currentRole == 0) {
         switch (type) {
            case BALANCE_INQUIRY: 
                temp = new BalanceInquiry(
                currentAccountNumber, screen, bankDatabase);
            break;
            
            case WITHDRAWAL:
                temp = new Withdrawal(
                currentAccountNumber, screen, bankDatabase, keypad, cashDispenser);
            break;
            
            case DEPOSIT:
                temp = new Deposit(
                currentAccountNumber, screen, bankDatabase, keypad, depositSlot);
            break;
            
            case CHANGEPIN:
                temp = new ChangePin (
                currentAccountNumber, screen, bankDatabase, keypad);
            break;
             
            case TRANSFER:
                temp = new Transfer(
                currentAccountNumber, screen, bankDatabase, keypad);
            break;
        }
      } else {
        switch (type) {
            case 1 : //VIEW DISPENSER
               temp = new ViewDispenser_Adm(screen, cashDispenser);
            break;
            
            case 2 : //DEPOSIT DISPENSER
                temp = new DepositDispenser_Adm(screen, keypad, cashDispenser);
            break;
            
            case 3 : //UNBLOCK USER
                temp = new UnblockUser_Adm(
                currentAccountNumber, screen, bankDatabase, keypad);
            break;
        }
      }

      return temp;
   } 
}