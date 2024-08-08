package edu.miu.cs.cs425.controller;

import java.util.Date;
import java.util.List;


import edu.miu.cs.cs425.dto.OrdersDto;
import edu.miu.cs.cs425.model.Orders;
import edu.miu.cs.cs425.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/ecom/orders")
public class OrderController {
    private  OrdersService ordersService;


    @PostMapping("/placed/{userid}")
    public ResponseEntity<?> addOrderToCart(@PathVariable("userid") Integer userid) {
        OrdersDto placeOrder = ordersService.placeOrder(userid);
        return ResponseEntity.ok(placeOrder);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Orders> getOrdersDetails(@PathVariable("orderId") Integer orderId) {
        Orders order = ordersService.getOrdersDetails(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/orders/{userId}")
    public ResponseEntity<List<Orders>> getAllUserOrder(@PathVariable Integer userId) {
        List<Orders> orders = ordersService.getAllUserOrder(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Orders>> viewAllOrders() {
        List<Orders> orders = ordersService.viewAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Orders>> viewAllOrderByDate(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        List<Orders> orders = ordersService.viewAllOrderByDate(date);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}/{orderId}")
    public ResponseEntity<String> deleteOrders(@PathVariable Integer userId, @PathVariable Integer orderId) {
        ordersService.deleteOrders(userId, orderId);
        return new ResponseEntity<>("Order successfully deleted.", HttpStatus.OK);
    }

}
