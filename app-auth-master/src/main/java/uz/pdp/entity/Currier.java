package uz.pdp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import uz.pdp.entity.template.AbsUUIDEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class Currier extends AbsUUIDEntity {

    @Column(nullable = false)
    private Long birthDate;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String carNumber;

    private String driverLicense;

    @NotNull
    private boolean online;

    @JoinColumn(unique = true)
    @OneToOne(optional = false)
    private User user;;


    public Currier(User user, Long birthDate, String firstName, String lastName, String carNumber, String driverLicense) {
        this.user = user;
        this.birthDate = birthDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.carNumber = carNumber;
        this.driverLicense = driverLicense;
    }
}
