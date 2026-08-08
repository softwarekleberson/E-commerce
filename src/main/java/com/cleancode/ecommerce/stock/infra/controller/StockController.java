package com.cleancode.ecommerce.stock.infra.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cleancode.ecommerce.stock.application.dto.CreateInputStockDto;
import com.cleancode.ecommerce.stock.application.dto.ListStockDto;
import com.cleancode.ecommerce.stock.application.usecase.contract.AddProductStock;

import jakarta.validation.Valid;

@RestController
@RequestMapping("adm/stock")
@CrossOrigin(origins = "*")
public class StockController {

	private final AddProductStock addProductStock;
	
	public StockController(AddProductStock addProductStock) {
		this.addProductStock = addProductStock;
	}
	
	@PostMapping("/input")
	public ResponseEntity<ListStockDto> productInput (@Valid @RequestBody CreateInputStockDto dto){
		var created = addProductStock.execute(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}
}