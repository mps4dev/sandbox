package pl.mps.tokenio.sandbox.model.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressInfo {
    private String city;
    private String conscriptionNumber;
    private String country;
    private String district;
    private String flats;
    private String full;
    private String hamlet;
    private String houseName;
    private String houseNumber;
    private String place;
    private String postCode;
    private String province;
    private String state;
    private String street;
    private String subdistrict;
    private String suburb;

    @Override
    public String toString() {
        return  "city='" + city + '\'' +
                ", conscriptionNumber='" + conscriptionNumber + '\'' +
                ", country='" + country + '\'' +
                ", district='" + district + '\'' +
                ", flats='" + flats + '\'' +
                ", full='" + full + '\'' +
                ", hamlet='" + hamlet + '\'' +
                ", houseName='" + houseName + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", place='" + place + '\'' +
                ", postCode='" + postCode + '\'' +
                ", province='" + province + '\'' +
                ", state='" + state + '\'' +
                ", street='" + street + '\'' +
                ", subdistrict='" + subdistrict + '\'' +
                ", suburb='" + suburb + '\'';
    }
}
