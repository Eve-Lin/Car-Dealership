package com.evelin.cars.events;

import com.evelin.cars.model.Offer;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class OfferCreationEvent {
    private final Offer offer;
}
