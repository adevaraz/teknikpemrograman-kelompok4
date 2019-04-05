package atm;

/**
 *
 * @author Zara Veda
 */
public class ChangePin extends Transaction {
    private int newPin;
    private Keypad keypad;
    
    public ChangePin(int userAccountNumber, Screen atmScreen, 
      BankDatabase atmBankDatabase, Keypad atmKeypad) {
      
      super(userAccountNumber, atmScreen, atmBankDatabase);
    }
    
    @Override
    public void execute() {
      BankDatabase bankDatabase = getBankDatabase();
      Screen screen = getScreen();
      boolean done = false;
      boolean valid;
      int validPin;
      
      //newPin = insertNewPin();
      int oldPin = bankDatabase.getPinNumber(getAccountNumber());
      
      while(!done) {
          newPin = insertNewPin();
          
         if(newPin == 0) {
             done = true;
             screen.displayMessageLine("Canceling transaction...");
         } else {
             while(newPin == oldPin) {
              screen.displayMessageLine("Please enter different pin!");
              newPin = insertNewPin();
             }
          
             if(newPin != oldPin) {
                 bankDatabase.setPinNumber(getAccountNumber(), newPin);
                 validPin = validateNewPin();
                 valid = bankDatabase.authenticateUser(
                         getAccountNumber(), validPin);
              
                 while(!valid) {
                     screen.displayMessageLine("The pin is not match!");
                     validPin = validateNewPin();
                     valid = bankDatabase.authenticateUser(
                             getAccountNumber(), validPin);
                 }
            
                 if(valid) {
                     done = valid;
                 }
             }
            
             screen.displayMessageLine("Your new pin has been setted");
         }
      }
    }
    
    private int insertNewPin() {
      keypad = new Keypad();
      Screen screen = getScreen();

      screen.displayMessage("Please enter new pin or 0 to Cancel : ");
      int theNewPin = keypad.getInput();
      
      return theNewPin;
    }
    
    private int validateNewPin() {
      keypad = new Keypad();
      Screen screen = getScreen();
      
      screen.displayMessage("Please enter new pin one more time : ");
      int validatePin = keypad.getInput();
      
      return validatePin;
    }
}
