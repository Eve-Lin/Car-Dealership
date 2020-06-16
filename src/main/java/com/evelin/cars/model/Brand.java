package com.evelin.cars.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "brands")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @NonNull
    private String name;
    private LocalDateTime created;
    private LocalDateTime modified;
    @OneToMany(mappedBy = "brand", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Model> models = new ArrayList<>();

    public static Brand create(String name, Set<Model> models) {
        Brand brand = new Brand(name);
        models.stream().forEach(model -> {
            model.setBrand(brand);
            brand.getModels().add(model);
        });
        return brand;
    }

    ;

}
