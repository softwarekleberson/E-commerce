package com.cleancode.ecommerce.product.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cleancode.ecommerce.event.product.EventPublisher;
import com.cleancode.ecommerce.product.application.service.ProductActivationServiceImpl;
import com.cleancode.ecommerce.product.application.service.ProductPriceServiceImpl;
import com.cleancode.ecommerce.product.application.service.contract.ProductActivationService;
import com.cleancode.ecommerce.product.application.service.contract.ProductPriceService;
import com.cleancode.ecommerce.product.application.usecase.ActiveProductImpl;
import com.cleancode.ecommerce.product.application.usecase.CreateProductImpl;
import com.cleancode.ecommerce.product.application.usecase.IncreaseSellingPriceAboveProfitMarginImpl;
import com.cleancode.ecommerce.product.application.usecase.ListProductActivetByIdImpl;
import com.cleancode.ecommerce.product.application.usecase.ListAllProductActiveImpl;
import com.cleancode.ecommerce.product.application.usecase.ListAllProductsInactiveImpl;
import com.cleancode.ecommerce.product.application.usecase.ManualProductActivationImpl;
import com.cleancode.ecommerce.product.application.usecase.ManualProductDeactivationImpl;
import com.cleancode.ecommerce.product.application.usecase.ReviseDetailsImpl;
import com.cleancode.ecommerce.product.application.usecase.SellingPriceRelativeToInventory;
import com.cleancode.ecommerce.product.application.usecase.contract.ActiveProduct;
import com.cleancode.ecommerce.product.application.usecase.contract.CreateProduct;
import com.cleancode.ecommerce.product.application.usecase.contract.IncreaseSellingPriceAboveProfitMargin;
import com.cleancode.ecommerce.product.application.usecase.contract.ListActiveProduct;
import com.cleancode.ecommerce.product.application.usecase.contract.ListAllProductActive;
import com.cleancode.ecommerce.product.application.usecase.contract.ListAllProductsInactive;
import com.cleancode.ecommerce.product.application.usecase.contract.ManualProductActivation;
import com.cleancode.ecommerce.product.application.usecase.contract.ManualProductDeactivation;
import com.cleancode.ecommerce.product.application.usecase.contract.ReviseDetails;
import com.cleancode.ecommerce.product.application.usecase.contract.SellingPriceToInventory;
import com.cleancode.ecommerce.product.domain.repository.ProductRepository;
import com.cleancode.ecommerce.stock.domain.repository.StockRepository;

@Configuration
public class ProductConfig {

	@Bean
	public CreateProduct createProduct(ProductRepository productRepository, EventPublisher eventPublisher) {
		return new CreateProductImpl(productRepository, eventPublisher);
	}
	
	@Bean
	public ReviseDetails reviseDetails(ProductRepository productRepository) {
		return new ReviseDetailsImpl(productRepository);
	}
	
	@Bean
	public IncreaseSellingPriceAboveProfitMargin increaseSelling (ProductRepository productRepository) {
		return new IncreaseSellingPriceAboveProfitMarginImpl(productRepository);
	}

	@Bean
	public ListAllProductActive listAllProduct(ProductRepository productRepository) {
		return new ListAllProductActiveImpl(productRepository);
	}
	
	@Bean
	public ListAllProductsInactive listAllProductsInactive(ProductRepository productRepository) {
		return new ListAllProductsInactiveImpl(productRepository);
	}
	
	@Bean
	public ListActiveProduct listProduct(ProductRepository productRepository) {
		return new ListProductActivetByIdImpl(productRepository);
	}
	
	@Bean
	public ManualProductDeactivation deactivateProduct (ProductRepository productRepository) {
		return new ManualProductDeactivationImpl(productRepository);
	}
	
	@Bean
	public ManualProductActivation activateManually (ProductRepository productRepository) {
		return new ManualProductActivationImpl(productRepository);
	}
	
	@Bean
	public ActiveProduct activeProduct (ProductRepository productRepository, ProductActivationService productActivationService) {
		return new ActiveProductImpl(productRepository, productActivationService);
	}
	
	@Bean
	public ProductActivationService productActivationService(ProductRepository productRepository, StockRepository stockRepository) {
		return new ProductActivationServiceImpl(productRepository,stockRepository);
	}

	@Bean
	public ProductPriceService productPriceService(
			StockRepository repository, ProductRepository productRepository) {
		return new ProductPriceServiceImpl(repository, productRepository);
	}	
	
	@Bean
    public SellingPriceToInventory sellingPriceToInventory(
            ProductRepository productRepository, 
            ProductPriceService productPriceService) {
        
        return new SellingPriceRelativeToInventory(productRepository, productPriceService);
    }
}