package com.ecommerce.project.repositories;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Page<Product> findByCategoryOrderByPriceAsc(Category category, Pageable pageDetails);

    Page<Product> findByProductNameLikeIgnoreCase(String keyword, Pageable pageDetails);

    Page<Product> findByUser(User user, Pageable pageDetails);

    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Product> findByKeywordInNameOrDescription(@Param("keyword") String keyword, Pageable pageDetails);

    @Query(value = "SELECT * FROM products WHERE LOWER(product_name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(description) LIKE LOWER(CONCAT('%', :keyword, '%')) ORDER BY special_price ASC LIMIT :limit OFFSET :offset",
            nativeQuery = true)
    List<Product> findByKeywordNative(@Param("keyword") String keyword,
                                      @Param("limit") int limit,
                                      @Param("offset") int offset);

    @Query(value = "SELECT COUNT(*) FROM products WHERE LOWER(product_name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(description) LIKE LOWER(CONCAT('%', :keyword, '%'))",
            nativeQuery = true)
    long countByKeywordNative(@Param("keyword") String keyword);

}
