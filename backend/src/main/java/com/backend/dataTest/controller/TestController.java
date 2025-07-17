package com.backend.dataTest.controller;

import com.backend.dataTest.dto.ItemDto;
import com.backend.dataTest.dto.ReqDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dataTest")
@Log4j2
public class TestController {
    @PostMapping
    public void getData(@RequestBody ReqDto reqDto) {
        log.info("email -> " + reqDto.getEmail());
        log.info("address -> " + reqDto.getAddress());
        log.info("zipCode -> " + reqDto.getZipCode());
        log.info("totalQuantity -> " + reqDto.getTotalQuantity());
        log.info("totalPrice -> " + reqDto.getTotalPrice());

        for(ItemDto item : reqDto.getItems()) {
            log.info("itemName -> " + item.getId());
            log.info("itemQuantity -> " + item.getQuantity());
        }
    }
}
