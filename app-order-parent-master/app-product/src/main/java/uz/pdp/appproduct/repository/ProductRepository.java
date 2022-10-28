package uz.pdp.appproduct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appproduct.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    boolean existsByNameAndCategoryId(String name, Integer category_id);

    @Query(value = "SELECT * FROM get_result_of_query_section(:query)", nativeQuery = true)
    List<Product> getSectionsByStringQuery(String query);


    boolean existsByNameAndCategoryIdAndIdNot(String name, Integer category_id, Integer id);

}


