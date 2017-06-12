package com.dreamteam.police.service;

import com.dreamteam.police.model.Ownership;
import com.dreamteam.police.remote.RemoteOwnershipData;
import com.vaadin.data.provider.ListDataProvider;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * Created by Loci on 8-5-2017.
 */
public class OwnershipService {

    @Autowired
    private RemoteOwnershipData ownershipData;
}
