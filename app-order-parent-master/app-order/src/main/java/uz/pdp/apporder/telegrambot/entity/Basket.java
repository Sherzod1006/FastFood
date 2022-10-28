package uz.pdp.apporder.telegrambot.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.apporder.entity.ClientAddress;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private BotUser user;

    private boolean ordered;

    @OneToMany(mappedBy = "basket")
    private List<BasketProduct> basketProducts;

    @ManyToOne
    private ClientAddress address;

}
