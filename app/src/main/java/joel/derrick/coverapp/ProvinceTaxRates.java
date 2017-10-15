package joel.derrick.coverapp;

/**
 * Created by JD on 12/10/17.
 * Provinces AND Territories are as follows:
 * ON,QC,NS,NB,MB,BC,PE,SK,AB,NL,NT,YT,NU
 * just dealing with combined sales tax values for simplicity's sake
 */

public final class ProvinceTaxRates {
    private final static double[] taxes = {.13,.14975,.15,.15,.13,.12,.15,.11,.05,.15,.05,.05,.05};
    private final static int number_of_provinces = 13;

    private ProvinceTaxRates(){}

    static double getTaxRate(int province_pos){
        if (province_pos>=0&&province_pos<number_of_provinces) {
            return taxes[province_pos];
        }
        else
            return -1;
    }

    static double calcTax(int province_pos, double cash_value){
        return getTaxRate(province_pos)*cash_value;
    }
}

