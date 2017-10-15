package joel.derrick.coverapp;

/**
 * Created by JD on 12/10/17.
 * ON,QC,NS,NB,MB,BC,PE,SK,AB,NL,NT,YT,NU
 * A fairly arbitrary algorithm for determining shipping costs
 */

public final class ShippingCalculator {

    private static final double base_shipping_fee[] =
            {5.00,6.00,6.20,6.20,7.00,11.00,6.25,8.25,10.00,8.20,13.00,14.00,13.00};
    private static final double weight_multiplier[] =
            {3,3.15,3.3,3.3,4.5,5.5,3.38,4.6,3.8,4.41,6.1,6.3,6.1};
    private static final int number_of_provinces = 13;

    private ShippingCalculator(){}

    public static double calculateShippingToProvince(int province_pos, double shipment_weight){

        if (province_pos>=0&&province_pos<number_of_provinces) {
            return base_shipping_fee[province_pos]+shipment_weight*weight_multiplier[province_pos];
        }
        return -1;
    }
}
