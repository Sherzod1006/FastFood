package uz.pdp.appproduct.entity;

import lombok.*;

import javax.persistence.*;

@Entity(name = "category")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Category {
    /*
    * this is primary key for category table
    * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /*this is category's name in uzbek language*/
    @Column(nullable = false)
    private String nameUz;

    /*this is category's name in russian language*/
    @Column(nullable = false)
    private String nameRu;

    /*category may have parent category. this is category's parent*/
    @ManyToOne
    private Category parentCategory;

}
