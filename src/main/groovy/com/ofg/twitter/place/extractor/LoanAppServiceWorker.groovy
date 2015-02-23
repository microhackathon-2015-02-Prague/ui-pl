package com.ofg.twitter.place.extractor

import com.ofg.twitter.place.model.Tweet
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired

@TypeChecked
@Slf4j
class LoanAppServiceWorker implements LoanAppServiceWorkerI {
    
    private final PlacesExtractor placesExtractor
    private final PlacesJsonBuilder placesJsonBuilder
    private final ColleratorLoanAppService colleratorLoanAppService

    @Autowired
    LoanAppServiceWorker(PlacesExtractor placesExtractor, 
                           PlacesJsonBuilder placesJsonBuilder,
                           ColleratorClient colleratorClient) {
        this.placesExtractor = placesExtractor
        this.placesJsonBuilder = placesJsonBuilder
        this.colleratorLoanAppService = colleratorLoanAppService
    }

    @Override
    void collectAndPropagate(long pairId, List<Tweet> tweets) {
        Map<String, Optional<Place>> extractedPlaces = placesExtractor.extractPlacesFrom(tweets)
        String jsonToPropagate = placesJsonBuilder.buildPlacesJson(pairId, extractedPlaces)
        colleratorLoanAppService.populatePlaces(pairId, jsonToPropagate)
        log.debug("Sent json [$jsonToPropagate] to collerator")
    }
}
