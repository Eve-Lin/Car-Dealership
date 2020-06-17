package com.evelin.cars.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "models")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private VehicleCategory category;
    @NonNull
    private Integer startYear;
    private Integer endYear;
    private Date created = new Date() ;
    private Date modified = new Date();
    @ManyToOne
    @ToString.Exclude
    private Brand brand;
    @NonNull
    private String imageUrl;

    public Model(String name, VehicleCategory category, Integer startYear, Integer endYear, String imageUrl) {
        this.name = name;
        this.category = category;
        this.startYear = startYear;
        this.imageUrl = imageUrl;
        this.endYear = endYear;
    }
}
