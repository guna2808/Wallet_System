package org.wallet.dto;

public class TransactionResponseDTO {

    private double amount;
    private String type;
    private String createdAt;

    public TransactionResponseDTO(double amount, String type, String createdAt) {
        this.amount = amount;
        this.type = type;
        this.createdAt = createdAt;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
