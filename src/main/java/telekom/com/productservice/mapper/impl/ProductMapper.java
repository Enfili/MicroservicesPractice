package telekom.com.productservice.mapper.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import telekom.com.productservice.DTO.ProductDTO;
import telekom.com.productservice.entity.Product;
import telekom.com.productservice.mapper.Mapper;

@Component
public class ProductMapper implements Mapper<Product, ProductDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Product mapFrom(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

    @Override
    public ProductDTO mapTo(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }
}
