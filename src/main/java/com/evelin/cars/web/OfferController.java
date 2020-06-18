package com.evelin.cars.web;

import com.evelin.cars.model.Offer;
import com.evelin.cars.service.BrandService;
import com.evelin.cars.service.OfferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/offers")
@Slf4j
public class OfferController {

    private OfferService offerService;
    private BrandService brandService;

    @Autowired
    public OfferController(OfferService offerService, BrandService brandService) {
        this.offerService = offerService;
        this.brandService = brandService;
    }

    @GetMapping
    public String getOffers(Model offers){
            offers.addAttribute("offers", offerService.getOffers());
     return "offers";
    }

    @GetMapping("/add")
    public String getOfferForm(Model model){
        if(model.getAttribute("offer") == null) {
            model.addAttribute("offer", new Offer());
        }
        model.addAttribute("brands", brandService.getBrands());
        return "offer-add";
    }

    @PostMapping("/add")
    public String createNewOffer(@ModelAttribute("offer") Offer offer, Errors errors){
        try {
            offerService.createOffer(offer);
        }catch(Exception e){
            log.error("Error creating offer", e);
            return "redirect:/offers/add";
        }
        if(errors.hasErrors()){
            log.error("There are errors", errors.getAllErrors());
            return "redirect:/offers/add";
        }
        return "redirect:/offers";
    }

}
