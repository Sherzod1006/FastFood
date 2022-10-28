package uz.pdp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import uz.pdp.entity.template.AbsUUIDEntity;
import uz.pdp.payload.add_DTO.AddClientDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@DynamicInsert
public class Client extends AbsUUIDEntity {

    private Long birthDate;

    @Column(nullable = false)
    private String name;

    @JoinColumn(unique = true)
    @OneToOne(optional = false)
    private User user;

    public Client(User user, Long birthDate, String name) {
        this.user = user;
        this.birthDate = birthDate;
        this.name = name;
    }

    public Client(User user, AddClientDTO addClientDTO) {
        this.user = user;
        this.birthDate = addClientDTO.getBirthDate();
        this.name = addClientDTO.getName();
    }
}
