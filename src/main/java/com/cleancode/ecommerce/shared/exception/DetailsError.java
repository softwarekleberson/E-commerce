package com.cleancode.ecommerce.shared.exception;

import java.time.LocalDateTime;

public record DetailsError(LocalDateTime timestamp, int status, String error, String message) {

}
