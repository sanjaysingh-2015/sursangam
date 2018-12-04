package com.p3s.sursangam.service;

import com.p3s.sursangam.model.*;
import com.p3s.sursangam.payload.*;
import com.p3s.sursangam.repository.*;
import com.p3s.sursangam.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class GroupProfileService {
    @Autowired
    PersonRepository personRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    GroupRegistrationRepository groupRegistrationRepository;

    @Autowired
    GroupTypeRepository groupTypeRepository;

    @Autowired
    TransactionRepository transactionRepository;

    public Groups createGroupProfile(User user, GroupProfileRequest profileRequest) {
        Groups group= new Groups();
        group.setName(profileRequest.getName());
        group.setEmail(profileRequest.getEmail());
        group.setPhoneNo(profileRequest.getPhoneNo());
        group.setProfileImageSize(profileRequest.getProfileImageSize());
        group.setProfileImageType(profileRequest.getProfileImageType());
        group.setProfileImageUri(profileRequest.getProfileImageUri());

        group.setUserId(user);

        Groups result = groupRepository.save(group);

        return result;
    }

    public GroupRegistration registerGroup(UserPrincipal currentUser, GroupRegistrationRequest groupRegistrationRequest) {
        User user = userRepository.findByEmail(currentUser.getEmail()).orElseGet(User::new);
        GroupRegistration groupRegistration = new GroupRegistration();
        groupRegistration.setRegistredOn(new Date());
        groupRegistration.setUserId(user);
        GroupType groupType = groupTypeRepository.findByName(groupRegistrationRequest.getGroupType());
        groupRegistration.setGroupTypeId(groupType);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, groupType.getDuration());
        java.util.Date dt = cal.getTime();
        groupRegistration.setValidUpto(dt);

        GroupRegistration result = groupRegistrationRepository.save(groupRegistration);
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
