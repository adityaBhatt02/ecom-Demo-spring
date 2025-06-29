package com.aditya.ecom_proj.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)                                        // for auto-increment of id.
    private int id;
    private String name;
    private String description;
    private String brand;
    private BigDecimal price;
    private String category;

//    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "dd-MM-yyyy")                      // in .sql file you have to write in format ("yyyy-mm-dd") becz it expects a standard date format but here after the creation and inserting of values we had changed it format to ("dd-mm-yyyy")
    private Date releaseDate;
    private boolean productAvailable;
    private int stockQuantity;


    private String imageName;
    private String imageType;
    @Lob                                          // Large object
    private byte[] imageData;
}


