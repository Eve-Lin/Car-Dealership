package com.evelin.cars.service.impl;

import com.evelin.cars.events.OfferCreationEvent;
import com.evelin.cars.model.Offer;
import com.evelin.cars.repository.OfferRepository;
import com.evelin.cars.repository.UserRepository;
import com.evelin.cars.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private ApplicationEventPublisher applicationEventPublisher;


    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, UserRepository userRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Collection<Offer> getOffers() {
        return offerRepository.findAll();
    }

    @Override
    public Offer getOfferById(Long id) {
        return offerRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Offer with id %d could not be found",id)));
    }

    @Override
    public Offer createOffer(Offer offer) {
        return offerRepository.save(offer);
    }

    @Override
    public Offer updateOffer(Offer offer) {

        Offer updateOffer = offerRepository.findById(offer.getId()).orElseThrow(() ->
                new EntityNotFoundException(String.format("Offer with id %d could not be found",offer.getId())));
        return offerRepository.save(updateOffer);
    }

    @Override
    public Offer deleteOffer(Long id) {
        Offer deletedOffer = offerRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Offer with id %d could not be found",id)));
        offerRepository.deleteById(id);
        return deletedOffer;
    }

    @Override
    public long getOffersCount() {
        return offerRepository.count();
    }

    @Transactional
    public List<Offer> createOfferBatch(List<Offer> offers) {
      List<Offer> created = offers.stream()
              .map(offer -> {
                  Offer resultOffer = createOffer(offer);
                  applicationEventPublisher.publishEvent(new OfferCreationEvent(resultOffer));
                  return resultOffer;
              }).collect(Collectors.toList());
      return created;
    }
}
