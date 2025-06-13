package models.user;

public class Order {
    protected String orderid;
    protected Buyer buyer;
    protected double totalPrice;
    protected String status;

    public Order(String orderid, Buyer buyer, double totalPrice, String status) {
        this.orderid = orderid;
        this.buyer = buyer;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public String getOrderId() {
        return orderid;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String newStatus) {
        this.status = newStatus;
    }

    /*public double calculateTotalPrice(CartItem[] items) {
        double total = 0;
        for (CartItem item : items) {
            total += item.getSubTotal();
        }
        return total;
    }*/

    public void placeOrder() {
        this.status = "Placed";
        System.out.println("Order " + orderid + " telah ditempatkan.");
    }

    public void cancelOrder() {
        this.status = "Cancelled";
        System.out.println("Order " + orderid + " dibatalkan.");
    }
}

