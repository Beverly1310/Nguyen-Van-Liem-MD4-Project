package com.ra.project.service.impl;

import com.ra.project.model.entity.Product;
import com.ra.project.repository.ProductRepository;
import com.ra.project.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    @Override
    public List<Product> getProductsByNameOrDescription(String nameOrDescription) {
        return productRepository.findProductByProductNameOrDescription(nameOrDescription);
    }

    @Override
    public Page<Product> getProducts(Integer page, Integer size, String orderBy,String direction) {
        Pageable pageable = null;

        if(orderBy!=null && !orderBy.isEmpty()){
            // co sap xep
            Sort sort = null;
            switch (direction){
                case "ASC":
                    sort = Sort.by(orderBy).ascending();
                    break;
                case "DESC":
                    sort = Sort.by(orderBy).descending();
                    break;
            }
            pageable = PageRequest.of(page-1, size, sort);
        }else{
            //khong sap xep
            pageable = PageRequest.of(page-1, size);
        }
        return productRepository.getAllSale(pageable);
    }

    @Override
    public List<Product> getFeaturedProducts(Integer limit) {
        return productRepository.getFeaturedProduct().stream().limit(limit).toList();
    }

    @Override
    public List<Product> getNewProducts(Integer limit) {
        return productRepository.getNewProduct().stream().limit(limit).toList();
    }

    @Override
    public List<Product> getBestSellerProducts(Integer limit) {
        return productRepository.getBestSellerProducts().stream().limit(limit).toList();
    }

    @Override
    public List<Product> getProductByCategory(Long categoryId) {
        return productRepository.getProductByCategory_Id(categoryId);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.getProductById(id).orElseThrow(() ->new NoSuchElementException("Product Not Found"));
    }
}
