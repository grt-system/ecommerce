package com.imd.ecommerce.repository;

import com.imd.ecommerce.model.Sell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellRepository extends JpaRepository<Sell, Integer> {

}
