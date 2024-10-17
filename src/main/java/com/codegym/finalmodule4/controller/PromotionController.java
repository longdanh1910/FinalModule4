package com.codegym.finalmodule4.controller;

import com.codegym.finalmodule4.model.Promotion;
import com.codegym.finalmodule4.service.PromotionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/promotions")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    // Hiển thị danh sách khuyến mãi
    @GetMapping
    public String listPromotions(Model model) {
        List<Promotion> promotions = promotionService.findAll();
        model.addAttribute("promotions", promotions);
        return "promotion/list";
    }

    // Hiển thị form thêm mới khuyến mãi
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("promotion", new Promotion());
        return "promotion/create";
    }

    // Xử lý form thêm mới khuyến mãi
    @PostMapping
    public String createPromotion(@Valid @ModelAttribute Promotion promotion, BindingResult result, Model model) {
        // Validate: Ngày kết thúc phải lớn hơn ngày bắt đầu ít nhất 1 ngày
        if (promotion.getEndDate().isBefore(promotion.getStartDate().plusDays(1))) {
            result.rejectValue("endDate", "error.promotion", "Ngày kết thúc phải lớn hơn ngày bắt đầu ít nhất 1 ngày.");
        }

        // Nếu có lỗi, trả về form thêm mới
        if (result.hasErrors()) {
            return "promotion/create";
        }

        // Lưu chương trình khuyến mãi nếu không có lỗi
        promotionService.save(promotion);
        return "redirect:/promotions";
    }

    // Xóa chương trình khuyến mãi với popup xác nhận
    @GetMapping("/delete/{id}")
    public String deletePromotion(@PathVariable Long id) {
        promotionService.delete(id);
        return "redirect:/promotions";
    }
    // Phương thức để hiển thị form chỉnh sửa
    @GetMapping("/edit/{id}")
    public String editPromotion(@PathVariable Long id, Model model) {
        Promotion promotion = promotionService.findById(id);
        if (promotion == null) {
            return "redirect:/promotions"; // Nếu không tìm thấy, quay lại danh sách
        }
        model.addAttribute("promotion", promotion);
        return "promotion/edit"; // Trả về view edit
    }

    // Phương thức để xử lý cập nhật
    @PostMapping("/update")
    public String updatePromotion(@Valid @ModelAttribute Promotion promotion,
                                  BindingResult result) {
        if (result.hasErrors()) {
            return "promotion/edit"; // Nếu có lỗi, quay lại form
        }

        // Lưu thông tin đã cập nhật
        promotionService.save(promotion);

        // Chuyển hướng về danh sách
        return "redirect:/promotions";
    }

    // Tìm kiếm chương trình khuyến mãi theo các điều kiện
    @GetMapping("/search")
    public String searchPromotions(@RequestParam(required = false) Double discount,
                                   @RequestParam(required = false) LocalDate startDate,
                                   @RequestParam(required = false) LocalDate endDate,
                                   Model model) {

        List<Promotion> promotions;

        if (discount != null && startDate != null && endDate != null) {
            promotions = promotionService.searchByDiscountAmountAndDateRange(discount, startDate, endDate);
        } else if (discount != null) {
            promotions = promotionService.searchByDiscount(discount);
        } else if (startDate != null) {
            promotions = promotionService.searchByStartDate(startDate);
        } else if (endDate != null) {
            promotions = promotionService.searchByEndDate(endDate);
        } else {
            promotions = promotionService.findAll();
        }

        model.addAttribute("promotions", promotions);
        return "promotion/list";
    }
}
