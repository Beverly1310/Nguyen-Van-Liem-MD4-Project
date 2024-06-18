package com.ra.project.repository;


import com.ra.project.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {
    @Query("select p from Product p where p.productName like concat('%',:productNameOrDescription,'%') or p.description like concat('%',:productNameOrDescription,'%')")
    List<Product> findProductByProductNameOrDescription(String productNameOrDescription);

    Optional<Product> findProductByProductName(String productName);

    @Query("select p from Product p where p.id in(SELECT DISTINCT od.id.product.id FROM OrderDetail od)")
    Page<Product> getAllSale(Pageable pageable);

    //    @Query("select p from Product p join (select  od.id.product,count(od.orderQuantity)  total_view from OrderDetail od group by od.id.product )  order by total_view desc ")
    @Query("SELECT p FROM Product p JOIN (SELECT od.id.product.id AS product_id, COUNT(od.orderQuantity) AS total_view FROM OrderDetail od GROUP BY od.id.product.id ) v ON p.id = v.product_id ORDER BY v.total_view DESC")
    List<Product> getFeaturedProduct();

    @Query("select p from Product p order by p.createdAt desc")
    List<Product> getNewProduct();

    //    @Query("select p from Product p join (select  od.id.product,sum (od.orderQuantity)  total_view from OrderDetail od group by od.id.product )  order by total_view desc ")
    @Query("SELECT p FROM Product p JOIN (SELECT od.id.product.id AS product_id, Sum(od.orderQuantity) AS total_buy FROM OrderDetail od GROUP BY od.id.product.id ) v ON p.id = v.product_id ORDER BY v.total_buy DESC")
    List<Product> getBestSellerProducts();

    List<Product> getProductByCategory_Id(Long category_id);

    Optional<Product> getProductById(Long id);

    @Query("select p from Product p ")
    Page<Product> getAll(Pageable pageable);
}
