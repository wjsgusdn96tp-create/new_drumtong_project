package kr.co.iei.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.product.dao.ProductDao;

@Service
public class ProductService {
	@Autowired
	private ProductDao productDao;
}
