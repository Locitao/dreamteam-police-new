package com.dreamteam.police.service;

import com.dreamteam.police.remote.RemoteOwnershipData;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * Created by Loci on 8-5-2017.
 */
public class OwnershipService {

    @Autowired
    private RemoteOwnershipData ownershipData;

}
