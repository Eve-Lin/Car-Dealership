package com.evelin.cars.service;

import com.evelin.cars.model.Offer;

import java.util.Collection;
import java.util.List;

public interface OfferService {
    Collection<Offer> getOffers();

    Offer getOfferById(Long id);

    Offer createOffer(Offer offer);

    Offer updateOffer(Offer offer);

    Offer deleteOffer(Long id);

    long getOffersCount();
    List<Offer> createOfferBatch(List<Offer> offers);
}
