package com.cleancode.ecommerce.shared.kernel.exception;

import java.time.LocalDateTime;

public record DetailsError(LocalDateTime timestamp, int status, String error, String message) {

}
