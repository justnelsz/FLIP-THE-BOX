import java.io.*;
import java.util.ArrayList; 
 
class UserAccounts{ 
    private static ArrayList<Account> accounts = new ArrayList<>();
    private static final String FILE = "useraccounts.txt";

    public static void add(String username, String password) {
        if (username.matches(".*[a-zA-Z].*") && password.matches(".*[a-zA-Z1-9]")){
            if (!contains(username, password)){ //prevents duplicates
                Account newAccount = new Account(username, password);
                accounts.add(newAccount);
                saveToFile();
            } 
        } else {
            System.out.println("Invalid username or password format. Please try again!");
        }
    }
    public static void loadFromFile() {
        accounts.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE))){
            String line;
            while((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 2){
                    String username = parts[0].trim();
                    String password = parts[1].trim();
                    Account account = new Account(username, password);
                    accounts.add(account);
                }
            }
        } catch (IOException e){
            System.out.println("An error occurred while loading the file: " + e.getMessage());
        }
    }

    public static void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE))){
            for (Account account : accounts){
                writer.write(account.getUsername() + ", " + account.getPassword());
                writer.newLine();
            }
        } catch (IOException e){
            System.out.println("An error occurred while saving to the file: " + e.getMessage());
        }
    }

    public static boolean login(String username, String password){
        for (Account account : accounts){
            if (account.getUsername().equals(username) && account.getPassword().equals(password)){
                return true;
            }
        }
        return false;       
    }
   
    public static void deleteContact(String username, String password) {
        Account accountToRemove = new Account(username, password);
        if (accounts.contains(accountToRemove)){
            accounts.remove(accountToRemove);
            saveToFile();
        } else {
            System.out.println("Account not found!");
        }
    
    }
    public static boolean contains(String username, String password){
        for (Account account : accounts) {
            if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public static void updatePassword(String username, String newPassword){
        boolean updated = false;
        for (Account account : accounts){
            if (account.getUsername().equals(username)){
                account.setPassword(newPassword);
                updated = true;
                break;
            }
        }
        if (updated){
            saveToFile();
            System.out.println("Password updated successfully!");
        } else {
            System.out.println("Acoount not found!");
        }  
    }
}


