package com.evelin.cars.service;

import com.evelin.cars.model.Brand;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

public interface BrandService {
    Brand createBrand(Brand newBrand);
Collection<Brand> getBrands();
Brand getBrandById(Long id);
Brand updateBrand(Brand brand);
Brand deleteBrand(Long id);
long getBrandsCount();
List<Brand> createBrandsBatch(List<Brand>brands);
}
