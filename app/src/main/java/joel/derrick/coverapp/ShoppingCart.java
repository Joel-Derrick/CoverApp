package joel.derrick.coverapp;

import java.util.ArrayList;

/**
 * Created by JD on 08/10/17.
 * Cart to hold items and their quantities
 * A singleton since a user should only have one cart
 */

public class ShoppingCart {
    private ArrayList<ItemModel> item_list;
    private ArrayList<Integer> quantity_list;
    private int selected_Province = 0;
    private String user_name;

    private static final ShoppingCart ourInstance = new ShoppingCart();

    static ShoppingCart getInstance() {
        return ourInstance;
    }

    public ShoppingCart(){
        item_list = new ArrayList<ItemModel>();
        quantity_list = new ArrayList<>();
    }

    //add a specific quantity of items to the cart
    public void addItem(ItemModel item, int quantity){
        if (quantity>0){
            //if item is already in the cart just update the quantity
            int pos = item_list.indexOf(item);
            if(pos>=0){
                quantity_list.set(pos, quantity_list.get(pos)+quantity);
            }
            else {
                item_list.add(item);
                quantity_list.add(quantity);
            }
        }
    }
    //add a single item to the cart
    public void addItem(ItemModel item){
        addItem(item, 1);
    }

    public ArrayList<ItemModel> getItems() {
        return item_list;
    }

    //get the number of unique items in the cart
    public int uniqueItems(){
        return item_list.size();
    }

    //get the quantity of a specific item
    public Integer getQuantityOfItem(int pos) {
        if (pos >= 0 && pos < quantity_list.size()){
            return quantity_list.get(pos);
        }
        else{
            return new Integer(-1);
        }
    }

    //update the quantity of an item
    public void setQuantityOfItem(int index, int new_quantity){
        if (index >= 0 && index < item_list.size() ){
            if (new_quantity<0){
                item_list.remove(index);
                quantity_list.remove(index);
            }
            else {
                quantity_list.set(index, new Integer(new_quantity));
            }
        }
    }

    //province getter
    public int getSelectedProvince() {
        return selected_Province;
    }
    //province setter
    public void setSelectedProvince(int selected_Province) {
        this.selected_Province = selected_Province;
    }

    //remove item from cart by index
    public void removeItem(int index) {
        if (index >= 0 && index < item_list.size()) {
            item_list.remove(index);
            quantity_list.remove(index);
        }
    }

    //return whether the cart is empty or not
    public boolean isEmpty(){
        return item_list.isEmpty();
    }

    //remove all items from the cart
    public void emptyCart(){
        item_list.clear();
        quantity_list.clear();
    }

    //calculate the base cost of the items in the cart
    public double calcSubtotal(){
        double price = 0;
        for (int i =0;i<item_list.size();++i) {
            price += item_list.get(i).getPrice()* quantity_list.get(i);
        }
        return price;
    }

    //calculate the tax on the items in the cart
    public double calcTax(){
        return ProvinceTaxRates.calcTax(selected_Province, calcSubtotal());
    }

    //calculate the shipping cost of the items in the cart
    public double calcShippingPrice(){
        double weight = 0;
        for (int i =0;i<item_list.size();++i) {
            weight += item_list.get(i).getWeight()* quantity_list.get(i);
        }
        return ShippingCalculator.calculateShippingToProvince(selected_Province, weight);
    }

    //calculate the total cost of the items in the cart
    public double calcTotal(){
        double sub = calcSubtotal();

        return sub+ProvinceTaxRates.calcTax(selected_Province,sub)+calcShippingPrice();
    }

    //user name getter
    public String getUserName() {
        return user_name;
    }

    //user name setter
    public void setUserName(String user_name) {
        this.user_name = user_name;
    }
}
