package com.ofg.twitter.place.extractor

import com.ofg.twitter.place.model.Tweet
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired

@TypeChecked
@Slf4j
class OfferGeneratorWorker implements OfferGeneratorWorkerI {
    
    private final PlacesExtractor placesExtractor
    private final PlacesJsonBuilder placesJsonBuilder
    private final ColleratorOfferGenerator colleratorOfferGenerator

    @Autowired
    OfferGeneratorWorker(PlacesExtractor placesExtractor, 
                           PlacesJsonBuilder placesJsonBuilder,
                           ColleratorClient colleratorClient) {
        this.placesExtractor = placesExtractor
        this.placesJsonBuilder = placesJsonBuilder
        this.colleratorOfferGenerator = colleratorOfferGenerator
    }

    @Override
    void collectAndPropagate(long pairId, List<Tweet> tweets) {
        Map<String, Optional<Place>> extractedPlaces = placesExtractor.extractPlacesFrom(tweets)
        String jsonToPropagate = placesJsonBuilder.buildPlacesJson(pairId, extractedPlaces)
        colleratorOfferGenerator.populatePlaces(pairId, jsonToPropagate)
        log.debug("Sent json [$jsonToPropagate] to collerator")
    }
}
