package uz.pdp.apporder.entity;

import lombok.*;
import uz.pdp.appproduct.entity.Product;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    private Product product;

    @Column(nullable = false)
    private Float discount;

    @Column(nullable = false)
    private Long startDate;

    @Column(nullable = false)
    private Long endDate;

}
