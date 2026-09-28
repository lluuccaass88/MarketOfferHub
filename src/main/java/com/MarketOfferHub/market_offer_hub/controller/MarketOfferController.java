package com.MarketOfferHub.market_offer_hub.controller;

import com.marketofferhub.api.OfertasApi;
import com.marketofferhub.api.dto.OfertasResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MarketOfferController implements OfertasApi {

  @Override
  public ResponseEntity<OfertasResponse> findDayOffers() {
    return ResponseEntity.ok(new OfertasResponse());
  }
}