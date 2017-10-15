package joel.derrick.coverapp;

import android.content.ClipData;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JD on 07/10/17.
 * Data storage for items, implements Parcelable to send via Intent
 */

public class ItemModel implements Parcelable {

    private int id;
    private String name;
    private String desc;
    private double price;
    private double weight;

    public ItemModel(int id, String name, String desc, double price, double weight) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.weight = weight;
    }

    public ItemModel() {
        this.id = -1;
        this.name = "No name";
        this.desc = "No desc";
        this.price = -1.d;
        this.weight = -1.d;
    }

    //getters
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDesc() {
        return desc;
    }
    //setters
    public double getPrice() {
        return price;
    }
    public double getWeight() {
        return weight;
    }

    //Parcelable methods implemented
    public static final Parcelable.Creator<ItemModel> CREATOR =
    new Parcelable.Creator<ItemModel>() {

        @Override
        public ItemModel createFromParcel(Parcel source){

            return new ItemModel(source.readInt(), source.readString(), source.readString(),
                            source.readDouble(), source.readDouble());
        }

        @Override
        public ItemModel[] newArray(int size) {
            return new ItemModel[size];
        }

    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(desc);
        dest.writeDouble(price);
        dest.writeDouble(weight);
    }

    //compare method
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof ItemModel)) return false;
        ItemModel casted_obj = (ItemModel)obj;
        //since items are obtained from a data base, if their id's correspond they're the same item
        return this.id == casted_obj.id;
    }
}