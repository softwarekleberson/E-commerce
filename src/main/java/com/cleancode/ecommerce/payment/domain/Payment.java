package com.cleancode.ecommerce.payment.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.cleancode.ecommerce.customer.domain.customer.CustomerId;
import com.cleancode.ecommerce.order.domain.OrderId;
import com.cleancode.ecommerce.payment.application.dto.PaymentDetails;
import com.cleancode.ecommerce.payment.application.dto.PaymentExecutionResult;
import com.cleancode.ecommerce.payment.application.usecase.contract.PaymentMethod;
import com.cleancode.ecommerce.payment.domain.exceptions.IllegalDomainPayment;

public final class Payment {

	private static final int MAX_DESCRIPTIONS_REQUIRED = 2;
	
	private final PaymentId paymentId;
	private final CustomerId customerId;
    private final LocalDateTime paymentDate;
    private Total total;
    private List<DescriptionPayment> description = new ArrayList<>();
    private StatusPayment statusPayment;
    private final OrderId orderId;
    
    public Payment(String customerId ,Total total, String orderId) {
		
    	 if (customerId == null || customerId.isBlank()) {
             throw new IllegalDomainPayment("Customer id is required");
         }
    	 
    	 if(orderId == null || orderId.isBlank()) {
             throw new IllegalDomainPayment("Order id is required");
    	 }
    	 
         if (total == null) {
             throw new IllegalDomainPayment("Total is required");
         }
    	
    	this.paymentId = new PaymentId();
		this.customerId = new CustomerId(customerId);
		this.orderId = new OrderId(orderId);
		this.paymentDate = LocalDateTime.now();
		this.total = total;
		this.statusPayment = StatusPayment.UNDER_REVIEW;
	}
    
    public Payment(String customerId, String paymentId, LocalDateTime paymentDate, StatusPayment statusPayment, String orderId) {
    	this.paymentId = new PaymentId(paymentId);
    	this.customerId = new CustomerId(customerId);
    	this.paymentDate = paymentDate;
    	this.statusPayment = statusPayment;
		this.orderId = new OrderId(orderId);
    }
    
    public void approvetPayment() {
    	this.statusPayment = StatusPayment.APPROVED;
    }
    
    public void rejectedPayment() {
    	this.statusPayment = StatusPayment.REJECTED;
    }
    
	public void addDescriptionPayment(TypePayment typePayment) {
    	if(this.description.size() >= MAX_DESCRIPTIONS_REQUIRED) {
    		throw new IllegalDomainPayment("The number of payment methods cannot exceed 2");
    	}
    	
    	this.description.add(new DescriptionPayment(typePayment));
    }
    
    public PaymentExecutionResult process(PaymentMethod paymentMethod, PaymentDetails details) {
        if (this.total == null || this.total.getTotalValue().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalDomainPayment("Cannot complete an order with zero total value.");
        }
        
        return paymentMethod.payment(this.total.getTotalValue(), details);
    }
    
    public OrderId getOrderId() {
		return orderId;
	}

	public PaymentId getPaymentId() {
		return paymentId;
	}

	public CustomerId getCustomerId() {
		return customerId;
	}

	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}

	public Total getTotal() {
		return total;
	}

	public List<DescriptionPayment> getDescription() {
		return Collections.unmodifiableList(this.description);
	}

	public StatusPayment getStatusPayment() {
		return statusPayment;
	}

	@Override
	public int hashCode() {
		return Objects.hash(paymentId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payment other = (Payment) obj;
		return Objects.equals(paymentId, other.paymentId);
	}
}