package com.codegym.finalmodule4.service;

import com.codegym.finalmodule4.model.Promotion;
import com.codegym.finalmodule4.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;
    public Promotion findById(Long id) {
        return promotionRepository.findById(id).orElse(null);
    }


    public List<Promotion> findAll() {
        return promotionRepository.findAll();
    }

    public Promotion save(Promotion promotion) {
        return promotionRepository.save(promotion);
    }

    public void delete(Long id) {
        promotionRepository.deleteById(id);
    }

    public List<Promotion> searchByDiscount(double discountAmount) {
        return promotionRepository.findByDiscountAmountGreaterThanEqual(discountAmount);
    }

    public List<Promotion> searchByStartDate(LocalDate startDate) {
        return promotionRepository.findByStartDateGreaterThanEqual(startDate);
    }

    public List<Promotion> searchByEndDate(LocalDate endDate) {
        return promotionRepository.findByEndDateLessThanEqual(endDate);
    }

    public List<Promotion> searchByDiscountAmountAndDateRange(double discountAmount, LocalDate startDate, LocalDate endDate) {
        return promotionRepository.findByDiscountAmountAndStartDateGreaterThanEqualAndEndDateLessThanEqual(discountAmount, startDate, endDate);
    }
}


