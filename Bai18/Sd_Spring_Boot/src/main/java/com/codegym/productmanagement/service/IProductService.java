package com.codegym.productmanagement.service;

import com.codegym.productmanagement.model.Product;

import java.util.List;

public interface IProductService extends IGeneralService<Product> {
    List<Product> searchByName(String keyword);
}
