package uz.pdp.apporder.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PriceForDelivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Float priceForPerKilometre;

    @Column(nullable = false)
    private Float initialPrice;

    @Column(nullable = false)
    private Integer initialDistance;

    @OneToOne
    @NotNull
    private  Branch branch;

}
