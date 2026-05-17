package com.appsdeveloperblog.ws.products.service;

import com.appsdeveloperblog.ws.products.rest.CreateProductModel;

public interface ProductService {
  String createProduct(CreateProductModel productModel) throws Exception;
}
