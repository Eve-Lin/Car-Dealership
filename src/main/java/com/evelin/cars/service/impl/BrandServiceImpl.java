package com.evelin.cars.service.impl;

import com.evelin.cars.model.Brand;
import com.evelin.cars.repository.BrandRepository;
import com.evelin.cars.repository.UserRepository;
import com.evelin.cars.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private final UserRepository userRepository;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository, UserRepository userRepository) {
        this.brandRepository = brandRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Brand createBrand(Brand newBrand) {
        if(newBrand.getCreated() == null) {
            newBrand.setCreated(new Date());
        }
        newBrand.setModified(newBrand.getCreated());
        return brandRepository.save(newBrand);
    }

    @Override
    public Collection<Brand> getBrands() {
        return brandRepository.findAll();
    }

    @Override
    public Brand getBrandById(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("Brand with ID=%s not found.", id)));
    }

    @Override
    public Brand updateBrand(Brand post) {
        post.setModified(new Date());
        Brand old = getBrandById(post.getId());
        return brandRepository.save(post);
    }

    @Override
    public Brand deleteBrand(Long id) {
        Brand old = brandRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Brand with ID=%s not found.", id)));
        brandRepository.deleteById(id);
        return old;
    }

    @Override
    public long getBrandsCount() {
        return brandRepository.count();
    }

    @Override
    @Transactional
    public List<Brand> createBrandsBatch(List<Brand> posts) {
     List<Brand> created = posts.stream()
             .map(brand -> {
                 Brand resultBrand = createBrand(brand);
                 return  resultBrand;
             }).collect(Collectors.toList());
     return created;
    }
}
