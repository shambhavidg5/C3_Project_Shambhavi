import java.util.List;

public class Item {
    private String name;
    private int price;

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString(){
        return  name + ":"
                + price
                + "\n"
                ;
    }


    public int getOrderTotalValue(List<Item> menu) throws itemNotFoundException {
        int orderTotal = 0;
        for(Item item : menu) {
            orderTotal += orderTotal + item.getPrice();
        }
        return orderTotal ;
    }
}
