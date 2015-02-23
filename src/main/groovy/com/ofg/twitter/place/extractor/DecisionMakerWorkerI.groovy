package com.ofg.twitter.place.extractor

import com.ofg.twitter.place.model.Tweet

interface DecisionMakerWorkerI {
    void collectAndPropagate(long pairId, List<Tweet> tweets)
}