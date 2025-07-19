package com.backend.api.v1.orders.controller;

import com.backend.api.v1.orders.dto.OrderDto;
import com.backend.api.v1.orders.entity.ErrorResponse;
import com.backend.api.v1.orders.entity.Orders;
import com.backend.api.v1.orders.service.OrdersService;
import com.backend.api.v1.wishiList.entity.WishList;
import com.backend.api.v1.wishiList.repository.WishListRepository;
import com.backend.api.v1.wishiList.service.WishListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDto request) {
        int wishListId = request.getWishListId();
        Optional<WishList> wishList = wishListRepository.findById(wishListId);

        if (wishList.isPresent()) {
            Orders orders = ordersService.createOrders(wishList.get());
            return ResponseEntity.ok(orders);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("해당 id의 wishList가 존재하지 않습니다.", 404));
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
