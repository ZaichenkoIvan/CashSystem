package com.shop.controller;

import com.shop.model.domain.Goods;
import com.shop.model.service.GoodService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GoodsController {

    private final GoodService goodsService;

    @GetMapping("/goods")
    public String viewGoods(Model model, @RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size) {
        addPagination(model, page, size);
        model.addAttribute("good", new Goods());

        return "/goods";
    }

    @PostMapping("/goods")
    public String addGoods(Model model, @ModelAttribute Goods good,
                           @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {

        goodsService.addGoods(good);
        model.addAttribute("addedGood", good.getName());

        addPagination(model, page, size);

        return "redirect:/goods";
    }

    @GetMapping("/goods/edit/{code}")
    public String editGoods(Model model, @PathVariable Integer code, @RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size) {
        model.addAttribute("editCode", code);
        addPagination(model, page, size);

        return "/goods";
    }

    @PostMapping("/goods/edit/{code}")
    public ModelAndView updateGoods(Model model, @PathVariable("code") Integer code,
                                    @RequestParam("changequant") Double changequant,
                                    @RequestParam("changeprice") Double changeprice,
                                    @RequestParam("page") Optional<Integer> page,
                                    @RequestParam("size") Optional<Integer> size) {
        goodsService.changeGoods(code, changequant, changeprice);
        addPagination(model, page, size);

        return new ModelAndView("redirect:/goods");
    }

    private void addPagination(Model model, Optional<Integer> current, Optional<Integer> size) {
        int currentPage = current.orElse(1);
        int pageSize = size.orElse(10);

        Page<Goods> goods = goodsService.getPageGoods(currentPage, pageSize);
        model.addAttribute("goods", goods);
        model.addAttribute("currentPage", currentPage);
        int totalPages = goods.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
    }
}
