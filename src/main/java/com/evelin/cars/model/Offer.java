package com.evelin.cars.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "offers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @NonNull
    private String description;
    @NonNull
    private Engine engine;
    @NonNull
    private String imageUrl;
    private Integer mileage;
    private Double price;
    private Transmission transmission;
    private Integer year;
    private Date created = new Date();
    private Date modified = new Date();
    @ManyToOne
    @NonNull
    private Model model;
    @ManyToOne(optional = true)
    @ToString.Exclude
    private User seller;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    private Long sellerId;

}
