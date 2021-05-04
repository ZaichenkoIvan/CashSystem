package com.shop.model.service;

import com.shop.model.domain.Activation;

public interface ActivationService {

    Activation getActivation();

    void setActivation(Activation activation);

    void createFirstData();

    void destroy();
}
