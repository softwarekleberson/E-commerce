package com.cleancode.ecommerce.cart.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cleancode.ecommerce.cart.application.service.CancelProductStockReservationImpl;
import com.cleancode.ecommerce.cart.application.service.CartCatalogIntegrationServiceImpl;
import com.cleancode.ecommerce.cart.application.service.CartQueryIntegrationServiceImpl;
import com.cleancode.ecommerce.cart.application.service.CartRemovalIntegrationServiceImpl;
import com.cleancode.ecommerce.cart.application.service.CartReservationExpirationScheduler;
import com.cleancode.ecommerce.cart.application.service.CartUpdateIntegrationServiceImpl;
import com.cleancode.ecommerce.cart.application.service.UpdateNewReservationImpl;
import com.cleancode.ecommerce.cart.application.service.ValidateProductHasStockServiceImpl;
import com.cleancode.ecommerce.cart.application.service.contract.CancelProductStockReservation;
import com.cleancode.ecommerce.cart.application.service.contract.CartCatalogIntegrationService;
import com.cleancode.ecommerce.cart.application.service.contract.CartQueryIntegrationService;
import com.cleancode.ecommerce.cart.application.service.contract.CartRemovalIntegrationService;
import com.cleancode.ecommerce.cart.application.service.contract.CartShippingCostCalculator;
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
import com.cleancode.ecommerce.event.cart.ProductAddedToCartEventPublisher;
import com.cleancode.ecommerce.event.cart.ProductRemovedFromCartEventPublisher;
import com.cleancode.ecommerce.order.domain.shipping.ShippingCostStrategyFactory;
import com.cleancode.ecommerce.product.domain.repository.ProductRepository;
import com.cleancode.ecommerce.stock.domain.repository.StockRepository;
import com.cleancode.ecommerce.cart.application.service.CartReservationTimeoutImpl;
import com.cleancode.ecommerce.cart.application.service.ExpireCartReservationsImpl;
import com.cleancode.ecommerce.cart.application.service.contract.CartReservationTimeout;
import com.cleancode.ecommerce.cart.application.service.contract.ExpireCartReservations;
import com.cleancode.ecommerce.cart.domain.repository.CartReservationSettingsRepository;
import com.cleancode.ecommerce.cart.infra.gateway.CartShippingCostCalculatorAdapter;
import com.cleancode.ecommerce.wishlist.domain.WishlistRepository;

@Configuration
public class CartConfig {

	@Bean
	public AddProductToCart addProductToCart(

			CartRepository cartRepository, CartCatalogIntegrationService integrationService,
			CartShippingCostCalculator shippingCostCalculator,
			ProductAddedToCartEventPublisher productAddedToCartEventPublisher) {

		return new AddProductToCartImpl(cartRepository, integrationService, shippingCostCalculator,
				productAddedToCartEventPublisher);
	}

	@Bean
	public CartShippingCostCalculator cartShippingCostCalculator(
			ShippingCostStrategyFactory shippingStrategyFactory) {
		return new CartShippingCostCalculatorAdapter(shippingStrategyFactory);
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
	public DeleteUniqueProductCart deleteUniqueProductCart (CartRepository cartRepository,
			CartRemovalIntegrationService removalService,
			ProductRemovedFromCartEventPublisher productRemovedFromCartEventPublisher) {
		return new DeleteUniqueProductCartImpl(cartRepository, removalService,
				productRemovedFromCartEventPublisher);
	}

	@Bean
	public CartReservationTimeout cartReservationTimeout(CartReservationSettingsRepository repository) {
		return new CartReservationTimeoutImpl(repository);
	}

	@Bean
	public ExpireCartReservations expireCartReservations(CartRepository cartRepository,
			CartRemovalIntegrationService removalService, WishlistRepository wishlistRepository,
			CartReservationTimeout cartReservationTimeout) {
		return new ExpireCartReservationsImpl(cartRepository, removalService, wishlistRepository,
				cartReservationTimeout);
	}

	@Bean
	public CartReservationExpirationScheduler cartReservationExpirationScheduler(
			ExpireCartReservations expireCartReservations) {
		return new CartReservationExpirationScheduler(expireCartReservations);
	}
	
	@Bean
	public CartQueryIntegrationService cartQueryIntegrationService(CustomerRepository customerRepository) {
		return new CartQueryIntegrationServiceImpl(customerRepository);
	}
	
	@Bean
	public ListCart listCart(CartRepository cartRepository, CartQueryIntegrationService queryService,
			CartShippingCostCalculator shippingCostCalculator) {
		return new ListCartImpl(cartRepository, queryService, shippingCostCalculator);
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
	public UpdateCart updateCart(CartRepository cartRepository, CartUpdateIntegrationService updateService,
			CartShippingCostCalculator shippingCostCalculator) {
		return new UpdateCartImpl(cartRepository, updateService, shippingCostCalculator);
	}
}