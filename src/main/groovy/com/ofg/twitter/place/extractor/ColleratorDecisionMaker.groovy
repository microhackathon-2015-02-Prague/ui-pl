package com.ofg.twitter.place.extractor

import com.ofg.infrastructure.web.resttemplate.fluent.ServiceRestClient
import com.ofg.twitter.config.Collaborators
import groovy.transform.CompileStatic

@CompileStatic
class ColleratorDecisionMaker {

    private final ServiceRestClient serviceRestClient

    ColleratorDecisionMaker(ServiceRestClient serviceRestClient) {
        this.serviceRestClient = serviceRestClient
    }

    void populatePlaces(long pairId, String places) {
        serviceRestClient.forService(Collaborators.DECISION_MAKER_DEPENDENCY_NAME)
                .post()
                .onUrlFromTemplate("/{pairId}").withVariables(pairId)
                .body(places)
                .anObject()
                .ofType(String)
    }

}
