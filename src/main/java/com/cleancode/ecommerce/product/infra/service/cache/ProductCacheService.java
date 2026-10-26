package com.cleancode.ecommerce.product.infra.service.cache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cleancode.ecommerce.product.application.dto.input.CreateProductDto;
import com.cleancode.ecommerce.product.application.dto.input.ModifySellingPriceDto;
import com.cleancode.ecommerce.product.application.dto.input.ProductStatusChangeDto;
import com.cleancode.ecommerce.product.application.dto.input.ReviseDetailsDto;
import com.cleancode.ecommerce.product.application.dto.output.ListProductDto;
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
import com.cleancode.ecommerce.shared.util.pagination.controller.PageRequests;
import com.cleancode.ecommerce.shared.util.pagination.controller.PageResponse;

@Service
public class ProductCacheService {

	private final CreateProduct createProduct;
	private final ReviseDetails reviseDetails;
	private final ManualProductDeactivation manualProductDeactivation;
	private final ManualProductActivation manualProductActivation;
	private final IncreaseSellingPriceAboveProfitMargin increaseSellingPrice;
	private final ListAllProductsInactive listAllProductsInactive;
	private final ListAllProductActive listAllProductsActive;
	private final ListActiveProduct listProduct;
	private final ActiveProduct activeProduct;
	private final SellingPriceToInventory sellingPriceToInventory;

	public ProductCacheService(
			CreateProduct createProduct,
			ReviseDetails reviseDetails,
			ManualProductDeactivation manualProductDeactivation,
			ManualProductActivation manualProductActivation,
			IncreaseSellingPriceAboveProfitMargin increaseSellingPrice,
			ListAllProductsInactive listAllProductsInactive,
			ListAllProductActive listAllProductsActive,
			ListActiveProduct listProduct,
			ActiveProduct activeProduct,
			SellingPriceToInventory sellingPriceToInventory) {
		this.createProduct = createProduct;
		this.reviseDetails = reviseDetails;
		this.manualProductDeactivation = manualProductDeactivation;
		this.manualProductActivation = manualProductActivation;
		this.increaseSellingPrice = increaseSellingPrice;
		this.listAllProductsInactive = listAllProductsInactive;
		this.listAllProductsActive = listAllProductsActive;
		this.listProduct = listProduct;
		this.activeProduct = activeProduct;
		this.sellingPriceToInventory = sellingPriceToInventory;
	}

	@Cacheable(cacheNames = ProductCacheNames.BY_ID, key = "#p0")
	public ListProductDto getActiveProductById(String productId) {
		return listProduct.execute(productId);
	}

	@Cacheable(cacheNames = ProductCacheNames.ACTIVE_PAGES,
			key = "#p0 + ':' + T(com.cleancode.ecommerce.shared.util.pagination.controller.PageRequests).boundedSize(#p1)")
	public PageResponse<ListProductDto> getActiveProducts(int page, int size) {
		Pageable pageable = PageRequests.of(page, size);
		return PageResponse.from(listAllProductsActive.execute(pageable));
	}

	@Cacheable(cacheNames = ProductCacheNames.INACTIVE_PAGES,
			key = "#p0 + ':' + T(com.cleancode.ecommerce.shared.util.pagination.controller.PageRequests).boundedSize(#p1)")
	public PageResponse<ListProductDto> getInactiveProducts(int page, int size) {
		Pageable pageable = PageRequests.of(page, size);
		return PageResponse.from(listAllProductsInactive.execute(pageable));
	}

	@CacheEvict(cacheNames = { ProductCacheNames.BY_ID, ProductCacheNames.ACTIVE_PAGES,
			ProductCacheNames.INACTIVE_PAGES }, allEntries = true, beforeInvocation = true)
	public void create(CreateProductDto dto) {
		createProduct.execute(dto);
	}

	@CacheEvict(cacheNames = { ProductCacheNames.BY_ID, ProductCacheNames.ACTIVE_PAGES,
			ProductCacheNames.INACTIVE_PAGES }, allEntries = true, beforeInvocation = true)
	public void revise(String productId, ReviseDetailsDto dto) {
		reviseDetails.execute(productId, dto);
	}

	@CacheEvict(cacheNames = { ProductCacheNames.BY_ID, ProductCacheNames.ACTIVE_PAGES,
			ProductCacheNames.INACTIVE_PAGES }, allEntries = true, beforeInvocation = true)
	public void manuallyDeactivate(ProductStatusChangeDto dto) {
		manualProductDeactivation.execute(dto);
	}

	@CacheEvict(cacheNames = { ProductCacheNames.BY_ID, ProductCacheNames.ACTIVE_PAGES,
			ProductCacheNames.INACTIVE_PAGES }, allEntries = true, beforeInvocation = true)
	public void manuallyActivate(ProductStatusChangeDto dto) {
		manualProductActivation.execute(dto);
	}

	@CacheEvict(cacheNames = { ProductCacheNames.BY_ID, ProductCacheNames.ACTIVE_PAGES,
			ProductCacheNames.INACTIVE_PAGES }, allEntries = true, beforeInvocation = true)
	public void changePrice(ModifySellingPriceDto dto) {
		increaseSellingPrice.execute(dto);
	}

	@CacheEvict(cacheNames = { ProductCacheNames.BY_ID, ProductCacheNames.ACTIVE_PAGES,
			ProductCacheNames.INACTIVE_PAGES }, allEntries = true, beforeInvocation = true)
	public void activateProduct(String productId, String stockId) {
		activeProduct.execute(productId, stockId);
	}

	@CacheEvict(cacheNames = { ProductCacheNames.BY_ID, ProductCacheNames.ACTIVE_PAGES,
			ProductCacheNames.INACTIVE_PAGES }, allEntries = true, beforeInvocation = true)
	public void recalculatePrice(String productId, String stockId) {
		sellingPriceToInventory.execute(productId, stockId);
	}
}
