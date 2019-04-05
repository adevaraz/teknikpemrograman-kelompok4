package atm;

/**
 *
 * @author Zara Veda
 */
public class DepositDispenser_Adm extends Transaction {
    private Keypad keypad;
    private CashDispenser cashDispenser;
    private final static int CANCELED = 0;
    
    DepositDispenser_Adm(Screen atmScreen, Keypad atmKeypad,
            CashDispenser atmCashDispenser) {
        super(atmScreen, atmCashDispenser);
    }
    
    @Override
    public void execute() {
        CashDispenser cashDispenser = getCashDispenser();
        Screen screen = getScreen();
        boolean end = false;
        
        while(!end) {
            int amount = enterAmount();
        
            if(amount == CANCELED) {
                screen.displayMessageLine("Canceling transaction...");
                end = true;
            } else {
                boolean positive = isAmountPositive(amount);
            
                if(positive) {
                   screen.displayMessageLine("Processing...");
                   cashDispenser.addtoDispenser(amount);
                   screen.displayMessageLine("\nSucceed");
                   end = true;
                } else {
                   screen.displayMessageLine("Please enter positive number!");
                }
            }
        }
    }
    
    public boolean isAmountPositive(int amount) {
        if(amount > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    public int enterAmount() {
        keypad = new Keypad();
        Screen screen = getScreen();
        
        screen.displayMessage("Please enter amount of money in bills " +
            "or 0 to Cancel : " );
        int amount = keypad.getInput();
                
        return amount;
    }
}
