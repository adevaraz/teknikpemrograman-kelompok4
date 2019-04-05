package atm;

/**
 *
 * @author Zara Veda
 */
public class ViewDispenser_Adm extends Transaction {
    private CashDispenser cashDispenser;
    
    public ViewDispenser_Adm(Screen atmScreen, CashDispenser atmCashDispenser) {
        
      super(atmScreen, atmCashDispenser);
    }
    
    @Override
    public void execute() {
        CashDispenser cashDispenser = getCashDispenser();
        Screen screen = getScreen();
        
        screen.displayMessageLine("Getting information...\n");
        
        int availableMoney = cashDispenser.getAmountofDispenser();
        screen.displayMessageLine("Dispenser Information:");
        screen.displayMessage(" - Amount of money : "); 
        screen.displayDollarAmount((double)availableMoney);
        int count = availableMoney/20;
        
        System.out.println("\n" + count + " bills remaining..");
    }
}
