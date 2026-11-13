package com.cleancode.ecommerce.stock.infra.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cleancode.ecommerce.stock.application.dto.CreateInputStockDto;
import com.cleancode.ecommerce.stock.application.dto.ListStockDto;
import com.cleancode.ecommerce.stock.application.usecase.contract.AddProductStock;
import com.cleancode.ecommerce.stock.application.usecase.contract.ListStocks;

import jakarta.validation.Valid;

@RestController
@RequestMapping("adm/stock")
@CrossOrigin(origins = "*")
public class StockController {

	private final AddProductStock addProductStock;
	private final ListStocks listStocks;
	
	public StockController(AddProductStock addProductStock, ListStocks listStocks) {
		this.addProductStock = addProductStock;
		this.listStocks = listStocks;
	}

	@GetMapping
	public ResponseEntity<java.util.List<ListStockDto>> getAllStocks() {
		return ResponseEntity.ok(listStocks.execute());
	}
	
	@PostMapping("/input")
	public ResponseEntity<ListStockDto> productInput (@Valid @RequestBody CreateInputStockDto dto){
		var created = addProductStock.execute(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}
}
