package com.ofg.twitter.place.extractor

import com.ofg.infrastructure.web.resttemplate.fluent.ServiceRestClient
import com.ofg.twitter.config.Collaborators
import groovy.transform.CompileStatic

@CompileStatic
class ColleratorLoanAppService {

    private final ServiceRestClient serviceRestClient

    ColleratorLoanAppService(ServiceRestClient serviceRestClient) {
        this.serviceRestClient = serviceRestClient
    }

    void populatePlaces(long pairId, String places) {
        serviceRestClient.forService(Collaborators.LOAN_APP_SERVICE_DEPENDENCY_NAME)
                .post()
                .onUrlFromTemplate("/{pairId}").withVariables(pairId)
                .body(places)
                .anObject()
                .ofType(String)
    }

}
