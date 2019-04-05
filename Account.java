package atm;

public class Account {
   private int accountNumber; // account number
   private int pin; // PIN for authentication
   private boolean status;
   private double availableBalance; // funds available for withdrawal
   private double totalBalance; // funds available & pending deposits
   private int role;

   // Account constructor initializes attributes
   public Account(int theAccountNumber, int thePIN, boolean theStatus,
      double theAvailableBalance, double theTotalBalance, int theRole) {
      accountNumber = theAccountNumber;
      pin = thePIN;
      status = theStatus;
      availableBalance = theAvailableBalance;
      totalBalance = theTotalBalance;
      role = theRole;
   }

   // determines whether a user-specified PIN matches PIN in Account
   public boolean validatePIN(int userPIN) {
      if (userPIN == pin) {
         return true;
      }
      else {
         return false;
      }
   } 

   // returns available balance
   public double getAvailableBalance() {
      return availableBalance;
   } 

   // returns the total balance
   public double getTotalBalance() {
      return totalBalance;
   } 

   public void credit(double amount) {
       if(amount>0) {
           totalBalance += amount;
           availableBalance += amount;
       }
   }

   public void debit(double amount) {
       if(amount<=availableBalance && amount<totalBalance) {
           totalBalance -= amount;
           availableBalance -= amount;
           System.out.println("Your cash has been dispensed.\nPlease take your cash now.\n");
       } else {
           System.out.println("Insufficient funds in your account.\n\nPlease choose a smaller amount.");
       }
   }
   
   public boolean getStatus() {
       return status;
   }
   
   public void setStatus(boolean newStatus) {
       status = newStatus;
   }

   public int getAccountNumber() {
      return accountNumber; 
   }
   
   public int getPinNumber() {
      return pin;
   }
   
   public void setPinNumber(int newPin) {
       pin = newPin;
   }
   
   public void plusTransfer(double amount) {
       if(amount>0) {
           totalBalance += amount;
           availableBalance += amount;
       }
   }
   
   public void minusTransfer(double amount) {
       if(amount<=availableBalance) {
           totalBalance -= amount;
           availableBalance -= amount;
       }
   }
   
   public int getRole() {
       return role;
   }
} 