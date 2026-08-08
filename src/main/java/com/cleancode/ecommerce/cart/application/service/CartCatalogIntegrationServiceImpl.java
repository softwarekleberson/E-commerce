package com.cleancode.ecommerce.cart.application.service;

import com.cleancode.ecommerce.cart.application.service.contract.CartCatalogIntegrationService;
import com.cleancode.ecommerce.cart.application.service.contract.ValidateProductHasStock;
import com.cleancode.ecommerce.cart.application.service.dto.ContextDetailsDto;
import com.cleancode.ecommerce.cart.application.service.dto.ProductReservationResult;
import com.cleancode.ecommerce.customer.domain.customer.Customer;
import com.cleancode.ecommerce.customer.domain.customer.repository.CustomerRepository;
import com.cleancode.ecommerce.product.domain.Product;
import com.cleancode.ecommerce.product.domain.repository.ProductRepository;
import com.cleancode.ecommerce.shared.exception.IllegalDomainException;
import com.cleancode.ecommerce.stock.domain.Stock;
import com.cleancode.ecommerce.stock.domain.repository.StockRepository;

public class CartCatalogIntegrationServiceImpl implements CartCatalogIntegrationService {

	private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;
    private final ValidateProductHasStock validateProductHasStock; 

    public CartCatalogIntegrationServiceImpl(
            CustomerRepository customerRepository, 
            ProductRepository productRepository,
            StockRepository stockRepository, 
            ValidateProductHasStock validateProductHasStock) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.stockRepository = stockRepository;
        this.validateProductHasStock = validateProductHasStock;
    }

    @Override
    public ContextDetailsDto resolveContextDetails(String email, String productId) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Customer with email: " + email + " not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product with id: " + productId + " not found"));

        return new ContextDetailsDto(
                customer.getId().getValue(),
                product.getProductId().getProductId(),
                product.getName().getName(),
                product.getMidia().get(0).getUrl(), 
                product.getPrice().getPrice(),
                product.getPrice().getCoin()
        );
    }

    @Override
    public ProductReservationResult reserveStock(String productId, int quantity, String customerId, String cartId) {
        Stock stock = stockRepository.getStock(productId)
                .orElseThrow(() -> new IllegalDomainException("Stock for product id: " + productId + " not found"));

        var stockAfterReservation = validateProductHasStock.reserve(stock, quantity, customerId, cartId);
        
        stockRepository.save(stockAfterReservation.stock());

        return new ProductReservationResult(stockAfterReservation.reservationId());
    }
}