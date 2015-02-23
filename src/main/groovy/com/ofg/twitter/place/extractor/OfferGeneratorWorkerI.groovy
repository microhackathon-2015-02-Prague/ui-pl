package com.ofg.twitter.place.extractor

import com.ofg.twitter.place.model.Tweet

interface OfferGeneratorWorkerI {
    void collectAndPropagate(long pairId, List<Tweet> tweets)
}