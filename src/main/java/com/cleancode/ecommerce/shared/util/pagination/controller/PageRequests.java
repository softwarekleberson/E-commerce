package com.cleancode.ecommerce.shared.util.pagination.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public final class PageRequests {

	public static final int MAX_PAGE_SIZE = 100;

	private PageRequests() {
	}

	public static Pageable of(int page, int size) {
		return PageRequest.of(page, boundedSize(size));
	}

	public static int boundedSize(int size) {
		return Math.min(size, MAX_PAGE_SIZE);
	}
}
