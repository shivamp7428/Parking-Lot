package enums;

import strategy.PaymentStrategy;

public class Payment {

    public enum PaymentType {
        UPI,
        NET_BANKING,
        CREDIT_CARD;
    }

    public synchronized  PaymentStrategy.Payment getPaymentType(String paymentType) {
        PaymentType type;
        try {
            type = PaymentType.valueOf(paymentType.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid payment type: " + paymentType);
        }
        PaymentStrategy strategy = new PaymentStrategy();
        switch (type) {
            case UPI:
                return strategy.new UPI();
            case NET_BANKING:
                return strategy.new NetBanking();
            case CREDIT_CARD:
                return strategy.new CreditCard();
            default:
                throw new IllegalArgumentException("Unsupported payment type");
        }
    }
}
