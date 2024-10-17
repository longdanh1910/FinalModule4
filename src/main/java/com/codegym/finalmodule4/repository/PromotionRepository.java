package com.codegym.finalmodule4.repository;

import com.codegym.finalmodule4.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    List<Promotion> findByDiscountAmountGreaterThanEqual(double discountAmount);
    List<Promotion> findByStartDateGreaterThanEqual(LocalDate startDate);
    List<Promotion> findByEndDateLessThanEqual(LocalDate endDate);

    // Sửa lại tên phương thức sử dụng đúng thuộc tính trong entity
    List<Promotion> findByDiscountAmountAndStartDateGreaterThanEqualAndEndDateLessThanEqual(double discountAmount, LocalDate startDate, LocalDate endDate);
}



