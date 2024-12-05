package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Sort;

import java.util.List;

import exercise.model.Product;
import exercise.repository.ProductRepository;
import exercise.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    // BEGIN
    @GetMapping(path = "")
    public List<Product> showAll(@RequestParam(required = false, name="min") Integer minPrice,
                                 @RequestParam(required = false, name="max") Integer maxPrice){
        System.out.println(minPrice);System.out.println(maxPrice);
        minPrice = minPrice == null ? Integer.MIN_VALUE : minPrice;
        maxPrice = maxPrice == null ? Integer.MAX_VALUE : maxPrice;
        return productRepository.findByPriceBetweenOrderByPrice(minPrice, maxPrice);
        }
    
    // END

    @GetMapping(path = "/{id}")
    public Product show(@PathVariable long id) {

        var product =  productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        return product;
    }
}
