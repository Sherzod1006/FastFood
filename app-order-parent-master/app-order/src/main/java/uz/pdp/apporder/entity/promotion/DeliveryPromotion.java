package uz.pdp.apporder.entity.promotion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.apporder.entity.template.AbsIntegerEntity;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryPromotion extends AbsIntegerEntity {

    @Column
    private Float moreThan;


    private Long startTime;

    private Long endTime;

    public DeliveryPromotion(Integer id, Float moreThan, Long startTime, Long endTime) {
        super(id);
        this.moreThan = moreThan;
        this.startTime = startTime;
        this.endTime = endTime;
    }

}
