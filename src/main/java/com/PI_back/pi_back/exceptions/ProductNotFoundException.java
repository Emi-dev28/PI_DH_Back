package com.PI_back.pi_back.exceptions;

import java.util.function.Supplier;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String s) {
        super(s);
    }


}
