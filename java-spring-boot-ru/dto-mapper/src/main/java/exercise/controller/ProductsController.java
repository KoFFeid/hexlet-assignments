package exercise.controller;

import exercise.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;
import java.util.stream.Collectors;

import exercise.repository.ProductRepository;
import exercise.dto.ProductDTO;
import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.ProductMapper;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    // BEGIN
    @GetMapping(path = "")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTO> getAll(){
        List<ProductDTO> allProduct = productRepository.findAll().stream().map(
                product -> productMapper.map(product)).collect(Collectors.toList());

        return allProduct;
    }

    // BEGIN
    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO getProduct(@PathVariable Long id){
        Product product = productRepository.findById(id).orElseThrow(
                () ->new ResourceNotFoundException("Not Found: " + id));

        return productMapper.map(product);
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO addProduct(@RequestBody ProductCreateDTO productCreateData){
        Product product = productRepository.save(productMapper.map(productCreateData));
        ProductDTO productDTO = productMapper.map(product);
        return productDTO;
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO updateProduct(@RequestBody ProductUpdateDTO productUpdateData, @PathVariable Long id){

        Product product = productRepository.findById(id).orElseThrow(
                () ->new ResourceNotFoundException("Not Found: " + id));
        productMapper.update(productUpdateData, product);
        productRepository.save(product);

        ProductDTO productDTO = productMapper.map(product);
        return productDTO;
    }
    
    // END

//    Создайте в контроллере обработчики, которые будут обрабатывать следующие запросы:
//
//    GET /products – вывод списка всех товаров
//    GET /products/{id} – просмотр конкретного товара
//    POST /products – создание нового товара
//    PUT /products/{id} – редактирование товара
}
