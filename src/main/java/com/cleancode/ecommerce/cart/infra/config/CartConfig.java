package com.cleancode.ecommerce.cart.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cleancode.ecommerce.cart.application.service.CancelProductStockReservationImpl;
import com.cleancode.ecommerce.cart.application.service.CartCatalogIntegrationServiceImpl;
import com.cleancode.ecommerce.cart.application.service.CartQueryIntegrationServiceImpl;
import com.cleancode.ecommerce.cart.application.service.CartRemovalIntegrationServiceImpl;
import com.cleancode.ecommerce.cart.application.service.CartUpdateIntegrationServiceImpl;
import com.cleancode.ecommerce.cart.application.service.UpdateNewReservationImpl;
import com.cleancode.ecommerce.cart.application.service.ValidateProductHasStockServiceImpl;
import com.cleancode.ecommerce.cart.application.service.contract.CancelProductStockReservation;
import com.cleancode.ecommerce.cart.application.service.contract.CartCatalogIntegrationService;
import com.cleancode.ecommerce.cart.application.service.contract.CartQueryIntegrationService;
import com.cleancode.ecommerce.cart.application.service.contract.CartRemovalIntegrationService;
import com.cleancode.ecommerce.cart.application.service.contract.CartUpdateIntegrationService;
import com.cleancode.ecommerce.cart.application.service.contract.UpdateNewReservation;
import com.cleancode.ecommerce.cart.application.service.contract.ValidateProductHasStock;
import com.cleancode.ecommerce.cart.application.usecase.AddProductToCartImpl;
import com.cleancode.ecommerce.cart.application.usecase.DeleteAllCartImpl;
import com.cleancode.ecommerce.cart.application.usecase.DeleteUniqueProductCartImpl;
import com.cleancode.ecommerce.cart.application.usecase.ListCartImpl;
import com.cleancode.ecommerce.cart.application.usecase.UpdateCartImpl;
import com.cleancode.ecommerce.cart.application.usecase.contract.AddProductToCart;
import com.cleancode.ecommerce.cart.application.usecase.contract.DeleteAllCart;
import com.cleancode.ecommerce.cart.application.usecase.contract.DeleteUniqueProductCart;
import com.cleancode.ecommerce.cart.application.usecase.contract.ListCart;
import com.cleancode.ecommerce.cart.application.usecase.contract.UpdateCart;
import com.cleancode.ecommerce.cart.domain.repository.CartRepository;
import com.cleancode.ecommerce.customer.domain.customer.repository.CustomerRepository;
import com.cleancode.ecommerce.product.domain.repository.ProductRepository;
import com.cleancode.ecommerce.stock.domain.repository.StockRepository;

@Configuration
public class CartConfig {

	@Bean
	public AddProductToCart addProductToCart(

			CartRepository cartRepository, CartCatalogIntegrationService integrationService) {

		return new AddProductToCartImpl(cartRepository, integrationService);
	}
	
	@Bean
	public CartCatalogIntegrationService cartCatalogIntegrationService (
			CustomerRepository customerRepository,
			ProductRepository productRepository,
			StockRepository stockRepository,
			ValidateProductHasStock validateProductHasStock
			) {
		
		return new CartCatalogIntegrationServiceImpl(customerRepository, productRepository, stockRepository, validateProductHasStock);
	}
	
	@Bean
	public CartRemovalIntegrationService cartRemovalIntegrationService (
			CustomerRepository customerRepository, StockRepository stockRepository
			) {
		
		return new CartRemovalIntegrationServiceImpl(customerRepository, stockRepository);
	}
	
	@Bean
	public DeleteUniqueProductCart deleteUniqueProductCart (CartRepository cartRepository, CartRemovalIntegrationService removalService) {
		return new DeleteUniqueProductCartImpl(cartRepository, removalService);
	}
	
	@Bean
	public CartQueryIntegrationService cartQueryIntegrationService(CustomerRepository customerRepository) {
		return new CartQueryIntegrationServiceImpl(customerRepository);
	}
	
	@Bean
	public ListCart listCart(CartRepository cartRepository, CartQueryIntegrationService queryService) {
		return new ListCartImpl(cartRepository, queryService);
	}
	
	@Bean
	public UpdateNewReservation updateNewReservation () {
		return new UpdateNewReservationImpl();
	}
	
	@Bean
	public DeleteAllCart deleteAllCart (CartRepository cartRepository) {
		return new DeleteAllCartImpl(cartRepository);
	}
	
	@Bean
	public ValidateProductHasStock validateProductHasStock() {
		return new ValidateProductHasStockServiceImpl();
	}
	
	@Bean
	public CartUpdateIntegrationService cartUpdateIntegrationService(
			CustomerRepository customerRepository, 
            StockRepository stockRepository,
            CancelProductStockReservation cancelService, 
            UpdateNewReservation updateNewReservation) {
		return new CartUpdateIntegrationServiceImpl(customerRepository, stockRepository, cancelService, updateNewReservation);
	}
	
	@Bean
	public CancelProductStockReservation cancelProductStockReservation() {
		return new CancelProductStockReservationImpl();
	}
	
	@Bean
	public UpdateCart updateCart(CartRepository cartRepository, CartUpdateIntegrationService updateService) {
		return new UpdateCartImpl(cartRepository, updateService);
	}
}