package com.backend.api.v1.orders.controller;

import com.backend.api.v1.orders.dto.OrderDto;
import com.backend.api.v1.orders.dto.ErrorResponse;
import com.backend.api.v1.orders.dto.OrderResponseDto;
import com.backend.api.v1.orders.entity.Orders;
import com.backend.api.v1.orders.service.OrdersService;
import com.backend.api.v1.wishiList.entity.WishList;
import com.backend.api.v1.wishiList.repository.WishListRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@Tag(name = "OrdersController", description = "주문 목록 컨트롤러")
public class OrderController {

    private final OrdersService ordersService;
    private final WishListRepository wishListRepository;

    @PostMapping("")
    @Operation(summary = "위시리스트 기반 주문 생성 및 주문 추가")
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody OrderDto request) {
        int wishListId = request.getWishListId();
        Optional<WishList> wishList = wishListRepository.findById(wishListId);

        Map<String, Object> response = new HashMap<>();

        if (wishList.isPresent()) {
            String result = String.valueOf(ordersService.createOrders(wishList.get()));
            response.put("message", result);
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "해당 id의 withList가 존재하지 않음");
            response.put("status", "error");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/shipping")
    @Operation(summary = "전날 오후 2시~금일 오후 2시 사이 배송 시작된 주문 목록 리스트")
    public List<Orders> orders() {
        // 현재 시간 무시하고 전날 오후 2시~금일 오후 2시 사이 미배송 객체들을 배송 시작
//         ordersService.deliverOrder();

        //배송되고 있는 주문목록만 보여주는 페이지
        return ordersService.getDeliveredOrders();
    }
}
