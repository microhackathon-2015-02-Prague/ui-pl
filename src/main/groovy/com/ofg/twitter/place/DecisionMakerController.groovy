package com.ofg.twitter.place

import com.ofg.twitter.place.extractor.DecisionMakerWorker
import com.ofg.twitter.place.extractor.PropagationWorker
import com.ofg.twitter.place.model.Tweet
import com.wordnik.swagger.annotations.Api
import com.wordnik.swagger.annotations.ApiOperation
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.validation.constraints.NotNull
import java.util.concurrent.Callable

import static com.ofg.twitter.config.Versions.TWITTER_PLACES_ANALYZER_JSON_VERSION_1
import static org.springframework.web.bind.annotation.RequestMethod.PUT

@Slf4j
@RestController
@RequestMapping('/decisionmaker')
@TypeChecked
@Api(value = "pairId", description = "Collects places from tweets and propagates them to Collerators")
class DecisionMakerController {

    private final DecisionMakerWorker decisionMakerWorker

    @Autowired
    DecisionMakerController(DecisionMakerWorker decisionMakerWorker) {
        this.decisionMakerWorker = decisionMakerWorker
    }

    @RequestMapping(
            value = '{pairId}',
            method = PUT,
            consumes = TWITTER_PLACES_ANALYZER_JSON_VERSION_1,
            produces = TWITTER_PLACES_ANALYZER_JSON_VERSION_1)
    @ApiOperation(value = "Async collecting and propagating of tweets for a given pairId",
            notes = "This will asynchronously call tweet collecting, place extracting and their propagation to Collerators")
    Callable<Void> getPlacesFromTweets(@PathVariable @NotNull long pairId, @RequestBody @NotNull List<Tweet> tweets) {
        return {
            decisionMakerWorker.collectAndPropagate(pairId, tweets)
        }
    }

}
