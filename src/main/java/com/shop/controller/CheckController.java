package com.shop.controller;

import com.shop.model.domain.Activation;
import com.shop.model.domain.Order;
import com.shop.model.service.ActivationService;
import com.shop.model.service.CheckService;
import com.shop.model.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CheckController {
    private final ActivationService activationService;
    private final CheckService checkService;
    private final UserService userService;

    @GetMapping("/check")
    public String viewCheck(Model model) {
        model.addAttribute("order", new Order());

        return "/check";
    }

    @PostMapping(value = "/check", params = "btnAddOrder")
    public String addOrder(HttpSession session, @ModelAttribute Order order) {
        @SuppressWarnings("unchecked")
        List<Order> orders = (List<Order>) session.getAttribute("addOrders");
        Activation activation = activationService.getActivation();
        if(Activation.ACTIVE.equals(activation)) {
            if (orders == null) {
                orders = new ArrayList<>();
                session.setAttribute("addOrders", orders);
            }
            Order orderResult = checkService.addOrder(order);
            orders.add(orderResult);
        }
        return "/check";
    }

    @PostMapping(value = "/check", params = "btnCreateCheck")
    public String createCheck(HttpSession session, HttpServletRequest request) {
        @SuppressWarnings("unchecked")
        List<Order> orders = (List<Order>) session.getAttribute("addOrders");

        if (!orders.isEmpty()) {
            checkService.addCheck(userService.getCurrentUser(), orders);
            request.setAttribute("addedCheck", true);
        } else {
            request.setAttribute("addedCheck", false);
        }
        orders.clear();

        return "redirect:/check";
    }

    @PostMapping(value = "/check", params = "btnCancelCheck")
    public String clearCheck(HttpSession session) {
        @SuppressWarnings("unchecked")
        List<Order> orders = (List<Order>) session.getAttribute("addOrders");
        if (!orders.isEmpty()) {
            orders.clear();
        }

        return "/check";
    }

    @GetMapping("/check/del/{count}")
    public ModelAndView editGoods(HttpSession session, @PathVariable Integer count) {
        @SuppressWarnings("unchecked")
        List<Order> orders = (List<Order>) session.getAttribute("addOrders");
        if (!orders.isEmpty()) {
            orders.remove(count - 1);
        }
        return new ModelAndView("redirect:/check");
    }
}
