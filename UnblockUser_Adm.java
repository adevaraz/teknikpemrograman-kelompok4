package atm;

/**
 *
 * @author Zara Veda
 */
public class UnblockUser_Adm extends Transaction {
    private Keypad keypad;
    
    public UnblockUser_Adm(int userAccountNumber, Screen atmScreen, 
      BankDatabase atmBankDatabase, Keypad atmKeypad) {
     
      super(userAccountNumber, atmScreen, atmBankDatabase);
    }
    
    @Override
    public void execute() {
        BankDatabase bankDatabase = getBankDatabase();
        Screen screen = getScreen();
        boolean end = false;
        
        while(!end) {
            int user2Unblock = theUserNumber();
        
            boolean exist = bankDatabase.validateBlockedUser(user2Unblock);
            if(exist) {
                boolean state = bankDatabase.getStatus(user2Unblock);
                if(state) {
                    screen.displayMessageLine("This account was unblocked before..");
                } else {
                    unblockingUser(user2Unblock);
                }
                
                end = true;
            } else {
                screen.displayMessageLine("Please enter the right number!");
            }
        }
    }
    
    private int theUserNumber () {
        keypad = new Keypad();
        Screen screen = getScreen();

        screen.displayMessage("Please enter user account number to be unblock : ");
        int theNumber2Unblock = keypad.getInput();
        
        return theNumber2Unblock;
    }
    
    private void unblockingUser(int userNumber) {
        BankDatabase bankDatabase = getBankDatabase();
        Screen screen = getScreen();
        
        screen.displayMessageLine("Unblocking...");
        bankDatabase.setStatus(userNumber, true);
        screen.displayMessageLine("\nFinish unblocking");
    }
}
