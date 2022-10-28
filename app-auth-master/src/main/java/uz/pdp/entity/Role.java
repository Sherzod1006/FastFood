package uz.pdp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.entity.enums.PermissionEnum;
import uz.pdp.entity.enums.RoleTypeEnum;

import javax.persistence.*;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//10

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "role", cascade = CascadeType.PERSIST)
    private Set<Page> pages;

    @ElementCollection
    @Enumerated(value = EnumType.STRING)
    private Set<PermissionEnum> permissions;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleTypeEnum roleType;


}
