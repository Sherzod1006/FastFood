package uz.pdp.apporder.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClientAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Float lat;

    private Float lng;

    private String address;

    private String extraAddress;

    public ClientAddress(Float lat, Float lng, String address, String extraAddress) {
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        this.extraAddress = extraAddress;
    }
}
