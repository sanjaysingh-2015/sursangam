package com.p3s.sursangam.service;

import com.p3s.sursangam.model.*;
import com.p3s.sursangam.payload.ProfileRequest;
import com.p3s.sursangam.payload.RegistrationFeeRequest;
import com.p3s.sursangam.payload.UserRegistrationRequest;
import com.p3s.sursangam.repository.*;
import com.p3s.sursangam.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class UserProfileService {
    @Autowired
    PersonRepository personRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRegistrationRepository userRegistrationRepository;

    @Autowired
    UserTypeRepository userTypeRepository;

    @Autowired
    TransactionRepository transactionRepository;

    public Person createPersonProfile(User user, ProfileRequest profileRequest) {
        Person person= personRepository.findByUserId(user);
        person.setFirstName(profileRequest.getFirstName());
        person.setMiddleName(profileRequest.getMiddleName());
        person.setLastName(profileRequest.getLastName());
        person.setDateOfBirth(profileRequest.getDateOfBirth());
        person.setGender(profileRequest.getGender());
        person.setEmail(profileRequest.getEmail());
        person.setPhoneNo(profileRequest.getPhoneNo());
        person.setProfileImageSize(profileRequest.getProfileImageSize());
        person.setProfileImageType(profileRequest.getProfileImageType());
        person.setProfileImageUri(profileRequest.getProfileImageUri());

        person.setUserId(user);

        Person savedPerson = personRepository.save(person);

        return savedPerson;
    }

    public UserRegistration registerUser(UserPrincipal currentUser, UserRegistrationRequest userRegistrationRequest) {
        User user = userRepository.findByEmail(currentUser.getEmail()).orElseGet(User::new);
        UserRegistration userRegistration = new UserRegistration();
        userRegistration.setRegistredOn(new Date());
        userRegistration.setUserId(user);
        UserType userType = userTypeRepository.findByName(userRegistrationRequest.getUserType());
        userRegistration.setUserTypeId(userType);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, userType.getDuration());
        java.util.Date dt = cal.getTime();
        userRegistration.setValidUpto(dt);

        UserRegistration result = userRegistrationRepository.save(userRegistration);
        return result;
    }

    public Transaction payRegistrationFee(UserPrincipal currentUser, RegistrationFeeRequest registrationFeeRequest) {
        User user = userRepository.findByEmail(currentUser.getEmail()).orElseGet(User::new);
        Transaction transaction = new Transaction();
        transaction.setAmount(registrationFeeRequest.getAmount());
        transaction.setPaymentGatewayTransactionNo(registrationFeeRequest.getPaymentGatewayTransactionNo());
        transaction.setRansactionStatus(registrationFeeRequest.getRansactionStatus());
        transaction.setRemarks(registrationFeeRequest.getRemarks());
        transaction.setTransactionDate(registrationFeeRequest.getTransactionDate());
        transaction.setTransactionId(registrationFeeRequest.getTransactionId());
        transaction.setTransactionStatusCode(registrationFeeRequest.getTransactionStatusCode());
        transaction.setUserId(user);

        Transaction result = transactionRepository.save(transaction);
        return result;
    }
}
