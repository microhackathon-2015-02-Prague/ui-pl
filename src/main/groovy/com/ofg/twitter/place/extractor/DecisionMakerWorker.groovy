package com.ofg.twitter.place.extractor

import com.ofg.twitter.place.model.Tweet
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired

@TypeChecked
@Slf4j
class DecisionMakerWorker implements DecisionMakerWorkerI {
    
    private final PlacesExtractor placesExtractor
    private final PlacesJsonBuilder placesJsonBuilder
    private final ColleratorDecisionMaker colleratorDecisionMaker

    @Autowired
    DecisionMakerWorker(PlacesExtractor placesExtractor, 
                           PlacesJsonBuilder placesJsonBuilder,
                           ColleratorDecisionMaker colleratorDecisionMaker) {
        this.placesExtractor = placesExtractor
        this.placesJsonBuilder = placesJsonBuilder
        this.colleratorDecisionMaker = colleratorDecisionMaker
    }

    @Override
    void collectAndPropagate(long pairId, List<Tweet> tweets) {
        Map<String, Optional<Place>> extractedPlaces = placesExtractor.extractPlacesFrom(tweets)
        String jsonToPropagate = placesJsonBuilder.buildPlacesJson(pairId, extractedPlaces)
        colleratorDecisionMaker.populatePlaces(pairId, jsonToPropagate)
        log.debug("Sent json [$jsonToPropagate] to collerator")
    }
}
