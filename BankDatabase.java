package atm;

public class BankDatabase {
   private Account[] accounts; // array of Accounts
   
   public BankDatabase() {
      accounts = new Account[3]; // just 2 accounts for testing
      accounts[0] = new Account(1234, 4321, true, 1000.0, 1200.0, 1); //1 is admin
      accounts[1] = new Account(8765, 5678, true, 1000.0, 1200.0, 0); //0 is user
      accounts[2] = new Account(1111, 1111, true, 1000.0, 200.0, 0);
   }
   
   private Account getAccount(int accountNumber) {
       int i = 0;
       
       while(i<3) {
           if(accountNumber != this.accounts[i].getAccountNumber()) {
               i++;
           } else {
              return accounts[i];
           }
       }
       return null; // if no matching account was found, return null
   } 

   public boolean authenticateUser(int userAccountNumber, int userPIN) {
      // attempt to retrieve the account with the account number
      Account userAccount = getAccount(userAccountNumber);

      // if account exists, return result of Account method validatePIN
      if (userAccount != null) {
         return userAccount.validatePIN(userPIN);
      }
      else {
         return false; // account number not found, so return false
      }
   }
   
   public boolean validateRecipient(int userAccountNumber) {
      Account userAccount = getAccount(userAccountNumber);
      
       return userAccount != null;
   }
   
   public boolean validateBlockedUser(int userAccountNumber) {
       Account userAccount = getAccount(userAccountNumber);
       
       return userAccount != null;
   }
   
   public int getPinNumber(int userAccountNumber) {
      return getAccount(userAccountNumber).getPinNumber();
   }
   
   public void setPinNumber(int userAccountNumber, int newPin) {
       getAccount(userAccountNumber).setPinNumber(newPin);
   }

   public double getAvailableBalance(int userAccountNumber) {
      return getAccount(userAccountNumber).getAvailableBalance();
   } 

   public double getTotalBalance(int userAccountNumber) {
      return getAccount(userAccountNumber).getTotalBalance();
   } 

   public void credit(int userAccountNumber, double amount) {
      getAccount(userAccountNumber).credit(amount);
   }

   public void debit(int userAccountNumber, double amount) {
      getAccount(userAccountNumber).debit(amount);
   }
   
   public boolean getStatus(int userAccountNumber) {
       return getAccount(userAccountNumber).getStatus();
   }
   
   public void setStatus(int userAccountNumber, boolean newStatus) {
       getAccount(userAccountNumber).setStatus(newStatus);
   }
   
   public void moneyTransfer(int sender, int recipient, double amount) {
       getAccount(sender).minusTransfer(amount);
       getAccount(recipient).plusTransfer(amount);
   }
   
   public int getRole(int userAccountNumber) {
       return getAccount(userAccountNumber).getRole();
   }
} 