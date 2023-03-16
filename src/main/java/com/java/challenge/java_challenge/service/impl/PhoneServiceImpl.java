package com.java.challenge.java_challenge.service.impl;

import com.java.challenge.java_challenge.entity.Phone;
import com.java.challenge.java_challenge.repository.PhoneRepository;
import com.java.challenge.java_challenge.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;

public class PhoneServiceImpl implements PhoneService {

    @Autowired
    PhoneRepository phoneRepository;

    @Override
    public Phone createPhone(Phone phone) {
        return phoneRepository.save(phone);
    }
}
