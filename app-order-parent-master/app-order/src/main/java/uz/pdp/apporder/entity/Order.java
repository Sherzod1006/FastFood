package uz.pdp.apporder.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.apporder.entity.enums.OrderStatusEnum;
import uz.pdp.apporder.entity.enums.PaymentType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID clientId;

    private UUID operatorId;

    private UUID currierId;

    @ManyToOne
    private Branch branch;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OrderStatusEnum statusEnum;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PaymentType paymentType;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<OrderProduct> orderProducts;

    private Float deliverySum;

    private Float overAllSum;

    private Integer number;

    @ManyToOne
    private ClientAddress address;

    @Column(columnDefinition = "text")
    private String comment;

    private Short tasteRate;

    private Short serviceRate;

    private LocalDateTime orderedAt = LocalDateTime.now();

    private LocalDateTime acceptedAt;

    private LocalDateTime cookingAt;

    private LocalDateTime readyAt;

    private LocalDateTime sentAt;

    private LocalDateTime closedAt;

    private LocalDateTime cancelledAt;


}
