package org.wallet.dto;

public class BalanceResponseDTO {

    private String username;
    private double balance;

    public BalanceResponseDTO(String username, double balance) {
        this.username = username;
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public double getBalance() {
        return balance;
    }
}
