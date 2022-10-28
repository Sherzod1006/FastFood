package uz.pdp.appproduct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appproduct.entity.Category;

import java.util.Collection;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    Boolean existsByNameUzIgnoreCase(String nameUz);
    Boolean existsByNameRuIgnoreCase(String nameRu);

    Boolean existsByNameUzIgnoreCaseAndIdNot(String nameUz, Integer id);

    Boolean existsByNameRuIgnoreCaseAndIdNot(String nameRu, Integer id);
    List<Category> findAllByNameUzContainingIgnoreCaseAndIdIn(String nameUz, Collection<Integer> id);
    List<Category> findAllByNameRuContainingIgnoreCaseAndIdIn(String nameRu, Collection<Integer> id);

    List<Category>  findAllByIdInAndNameRuOrderByNameRuAsc(Collection<Integer> id, String nameRu);
    List<Category> findAllByIdInAndNameUzOrderByNameUzAsc(Collection<Integer> id, String nameUz);
    List<Category>  findAllByIdInAndNameRuOrderByNameRuDesc(Collection<Integer> id, String nameRu);
    List<Category> findAllByIdInAndNameUzOrderByNameUzDesc(Collection<Integer> id, String nameUz);
}
