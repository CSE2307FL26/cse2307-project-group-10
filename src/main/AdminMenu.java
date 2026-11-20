package main;

public class AdminMenu {
    public void displayOptions(String accountName) {
        System.out.println("Admin Menu");
        System.out.println("Account: " + accountName);
        System.out.println("1. Collect a fee");
        System.out.println("2. Apply interest");
        System.out.println("3. Switch account");
        System.out.println("4. Back to role selection");
    }
}
