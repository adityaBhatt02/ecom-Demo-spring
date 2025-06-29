package com.aditya.ecom_proj.controller;


import com.aditya.ecom_proj.model.Product;
import com.aditya.ecom_proj.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/")
    public String greet() {
        return "Hello World";
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return new ResponseEntity<>(service.getAllProducts() , HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
//        return new ResponseEntity<>(service.getProductById(id) , HttpStatus.OK);

        Product product = service.getProductById(id);

        if(product != null)
            return new ResponseEntity<>(product , HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product ,                             // ? means i dont know for now what will i return the object or the error(https status i mean)
                           @RequestPart MultipartFile imageFile) {
        try {
        Product product1 = service.addProduct(product , imageFile);
        return new ResponseEntity<>(product1 , HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId) {
        Product product = service.getProductById(productId);       // product mill gya of that particular id.
        byte[] imageFile = product.getImageData();                 // uss product ke image ka data bhi mil gya.

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(product.getImageType()))       // Sets the Content-Type header of the response using the value returned by product.getImageType().        Example: "image/png", "image/jpeg"
                .body(imageFile);                                 // 	Sets the response body to be imageFile, which is likely a byte[], an InputStreamResource, or some binary data representing the image.
    }


    @PutMapping("product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id , @RequestPart Product product , @RequestPart MultipartFile imageFile) {
        Product product1 = null;
        try {
            product1 = service.updateProduct(id , product , imageFile);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed To Update",HttpStatus.BAD_REQUEST);
        }
        if (product1 != null)
            return new ResponseEntity<>("Updated Successfully" , HttpStatus.OK);
        else
            return new ResponseEntity<>("Failed To Update",HttpStatus.BAD_REQUEST);
}

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        Product product = service.getProductById(id);

        if(product != null) {
            service.deleteProduct(id);
            return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
        }else
            return new ResponseEntity<>("Failed To Delete" , HttpStatus.NOT_FOUND);
    }


    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String keyword) {
        List<Product> products = service.searchProducts(keyword);

        return new ResponseEntity<>(products , HttpStatus.OK);
    }

}

