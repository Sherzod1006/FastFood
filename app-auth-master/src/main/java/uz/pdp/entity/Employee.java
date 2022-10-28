package uz.pdp.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import uz.pdp.entity.template.AbsUUIDEntity;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
public class Employee extends AbsUUIDEntity {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @JoinColumn(unique = true)
    @OneToOne(optional = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Role role;

    public Employee(String firstName, String lastName, User user, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }
}
