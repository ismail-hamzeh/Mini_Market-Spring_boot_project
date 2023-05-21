package com.example.market.controller;

import com.example.market.dto.UserSignupDto;
import com.example.market.model.Items;
import com.example.market.model.Orders;
import com.example.market.model.Users;
import com.example.market.service.ItemService;
import com.example.market.service.OrderService;
import com.example.market.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    UserService userService;

    @Autowired
    ItemService itemService;

    @Autowired
    OrderService orderService;

    @Autowired
    AuthenticationManager authenticationManager;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @GetMapping("/login")
    public String loginForm() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }

        return "home";
    }

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new Users());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "signup";
        }

        return "home";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("user") UserSignupDto user, RedirectAttributes redirectAttributes) {

        if (!userService.isEmailExist(user.getEmail())) {
            userService.save(user);
            return "redirect:/login";
        } else {
            redirectAttributes.addAttribute("emailExists", true);
            return "redirect:/signup";
        }
    }


    @GetMapping(value = "/home")
    public String homeForm(Model model) {
        List<Items> items = itemService.findAllItems();
        model.addAttribute("items", items);
        return "home";
    }

    @PostMapping("/home")
    public String home(@RequestParam("paymentButton") String paymentButton,
                       @RequestParam("totalQuantityInput") int quantity,
                       @RequestParam("totalPriceInput") float totalPrice,
                       Model model) {
        if (paymentButton != null) {
            model.addAttribute("orderTotalPrice", totalPrice);
            model.addAttribute("orderQuantity", quantity);
            return "payment";
        }
        return "home";

    }

    @GetMapping(value = "/profile")
    public String profileForm(Model model) {

        model.addAttribute("users", userService.getUserById(getUserId()));

        return "profile";
    }

    @PostMapping(value = "/profile")
    public String updateProfile(@ModelAttribute("users") Users users) {


        Users existingUser = userService.getUserById(getUserId());
        existingUser.setId(getUserId());
        existingUser.setFirstName(users.getFirstName());
        existingUser.setLastName(users.getLastName());
        existingUser.setCountry(users.getCountry());

        userService.updateUser(existingUser);

        return "redirect:/profile?edited";
    }

    @GetMapping(value = "/resetPassword")
    public String resetPasswordForm() {
        return "resetPassword";
    }

    @PostMapping(value = "/resetPassword")
    public String resetPassword(@RequestParam("currentPassword") String currentPassword,
                                @RequestParam("newPassword") String newPassword,
                                @RequestParam("repeatNewPassword") String repeatNewPassword,
                                RedirectAttributes redirectAttributes) {

        Users users = userService.getUserById(getUserId());

        if (!passwordEncoder.matches(currentPassword, users.getPassword())) {
            redirectAttributes.addAttribute("currentPasswordError", true);
            return "redirect:/resetPassword";
        } else if (!newPassword.equals(repeatNewPassword)) {
            redirectAttributes.addAttribute("repeatPasswordError", true);
            return "redirect:/resetPassword";
        } else {
            userService.changePassword(getUserId(), newPassword);
            redirectAttributes.addAttribute("resetPasswordSucceeded", true);
            return "redirect:/profile";
        }

    }

    @GetMapping("/payment")
    public String paymentForm() {
        return "payment";
    }

    @PostMapping("/payment")
    public String payment(@RequestParam("orderQuantity") int orderQuantity,
                          @RequestParam("orderTotalPrice") float orderTotalPrice,
                          @RequestParam("orderNowButton") String orderNowButton,
                          RedirectAttributes redirectAttributes) {

        if (orderNowButton != null) {
            Date currentDate = new Date();
            Orders orders = new Orders(getUserId(), orderQuantity, orderTotalPrice, currentDate);
            Orders savedOrders = orderService.save(orders);

            redirectAttributes.addAttribute("orderId", savedOrders.getOrderId());

            return "redirect:/confirmation";
        }

        return "redirect:/home?paymentWrong";
    }

    @GetMapping("/orders")
    public String ordersForm(Model model) {

        List<Orders> orders = orderService.findAllByUserId(getUserId());

        model.addAttribute("orders", orders);
        model.addAttribute("ordersCount", orderService.countOrdersByUserId(getUserId()));

        return "orders";
    }

    @GetMapping("/orders/{id}")
    public String deleteOneOrder(@PathVariable int id) {

        orderService.deleteOrderByOrderId(id);

        return "redirect:/orders";
    }

    @GetMapping("/orders/empty-list")
    public String deleteAllOrders() {
        orderService.deleteAllByUserId(getUserId());
        return "redirect:/orders";
    }


    @GetMapping("/confirmation")
    public String confirmationForm(@RequestParam("orderId") int orderId, Model model) {
        model.addAttribute("orderId", orderId);
        return "confirmation";
    }
    private int getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Users users = userService.findUserByEmail(userDetails.getUsername());
        return (int) users.getId();
    }
}
