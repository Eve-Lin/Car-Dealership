package com.evelin.cars.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
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
    @Length(min = 1, max = 40)
    private String name;
    @NonNull
    private VehicleCategory category;
    @NonNull
    @Min(1990)
    private Integer startYear;
    @Min(1990)
    private Integer endYear;
    private Date created = new Date() ;
    private Date modified = new Date();
    @ManyToOne
    @ToString.Exclude
    private Brand brand;
    @NonNull
    @Length(min = 8, max = 512)
    private String imageUrl;

    public Model(String name, VehicleCategory category, Integer startYear, Integer endYear, String imageUrl) {
        this.name = name;
        this.category = category;
        this.startYear = startYear;
        this.imageUrl = imageUrl;
        this.endYear = endYear;
    }
}
