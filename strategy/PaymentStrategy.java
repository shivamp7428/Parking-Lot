package strategy;

public class PaymentStrategy {

    public interface Payment {
        boolean pay(int amount);
    }

    public class UPI implements Payment{
        public boolean  pay(int amount) {
            System.out.println(amount + " AMOUNT PAID BY UPI PAYMENT");
            return true;
        }
    }

    public class NetBanking implements Payment{
        public boolean pay(int amount) {
            System.out.println(amount + " AMOUNT PAID BY Net Banking Payment");
            return true;
        }
    }
    public class CreditCard implements Payment{
        public boolean pay(int amount) {
            System.out.println(amount + " AMOUNT PAID BY Credit Card PAYMENT");
            return true;
        }
    }

}
