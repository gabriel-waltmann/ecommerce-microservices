package com.waltmann.inventory_sesrvice.controller;

import com.waltmann.inventory_sesrvice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    private InventoryRepository inventoryRepository;

    @GetMapping("/{sku-code}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable("sku-code") String skuCode) {
        return inventoryRepository.findBySkuCode(skuCode).isPresent();
    }
}
