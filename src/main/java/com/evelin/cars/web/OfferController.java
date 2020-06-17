package com.evelin.cars.web;

import com.evelin.cars.service.BrandService;
import com.evelin.cars.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/offers")
public class OfferController {

    private OfferService offerService;
    private BrandService brandService;

    @Autowired
    public OfferController(OfferService offerService, BrandService brandService) {
        this.offerService = offerService;
        this.brandService = brandService;
    }

    @GetMapping("/all")
    public String getOffers(Model offers){
     offers.addAttribute("offers", offerService.getOffers());
     return "all";
    }
}
