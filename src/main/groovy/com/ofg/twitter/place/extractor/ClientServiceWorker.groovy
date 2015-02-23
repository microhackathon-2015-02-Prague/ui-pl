package com.ofg.twitter.place.extractor

import com.ofg.twitter.place.model.Tweet
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired

@TypeChecked
@Slf4j
class ClientServiceWorker implements ClientServiceWorkerI {
    
    private final PlacesExtractor placesExtractor
    private final PlacesJsonBuilder placesJsonBuilder
    private final ColleratorClientService colleratorClientService

    @Autowired
    ClientServiceWorker(PlacesExtractor placesExtractor, 
                           PlacesJsonBuilder placesJsonBuilder,
                           ColleratorClientService colleratorClientService) {
        this.placesExtractor = placesExtractor
        this.placesJsonBuilder = placesJsonBuilder
        this.colleratorClientService = colleratorClientService
    }

    @Override
    void collectAndPropagate(long pairId, List<Tweet> tweets) {
        Map<String, Optional<Place>> extractedPlaces = placesExtractor.extractPlacesFrom(tweets)
        String jsonToPropagate = placesJsonBuilder.buildPlacesJson(pairId, extractedPlaces)
        colleratorClientService.populatePlaces(pairId, jsonToPropagate)
        log.debug("Sent json [$jsonToPropagate] to collerator")
    }
}
