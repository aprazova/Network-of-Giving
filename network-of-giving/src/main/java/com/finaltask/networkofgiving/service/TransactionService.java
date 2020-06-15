package com.finaltask.networkofgiving.service;

import com.finaltask.networkofgiving.dto.CharityDto;
import com.finaltask.networkofgiving.dto.TransactionDto;
import com.finaltask.networkofgiving.dto.UserDto;
import com.finaltask.networkofgiving.exception.IsAlreadyVolunteer;
import com.finaltask.networkofgiving.model.Charity;
import com.finaltask.networkofgiving.model.User;
import com.finaltask.networkofgiving.model.UsersTransaction;
import com.finaltask.networkofgiving.repository.CharityRepository;
import com.finaltask.networkofgiving.repository.TransactionRepository;
import com.finaltask.networkofgiving.service.interfaces.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.UserTransaction;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.NotAuthorizedException;

import java.util.Date;
import java.util.Set;

import static org.springframework.util.ObjectUtils.containsConstant;
import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class TransactionService  implements ITransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CharityService charityService;

    @Autowired
    private CustomUserServices customUserServices;

    @Autowired
    private AuthService authService;


    @Override
    @Transactional
    public CharityDto addTransaction(TransactionDto transactionDto) {
        org.springframework.security.core.userdetails.User loggedUser = authService.getCurrentUser().orElseThrow(
                () -> new NotAuthorizedException("User not found.")
        );


        if(!validateCharityUpdate(transactionDto)){
            throw new NotAcceptableException("Invalid Transaction");
        }

        Set<UsersTransaction> allUserTransaction = this.customUserServices.getUserInfo().getUsersTransactions();

        if(!allUserTransaction.isEmpty()){
            allUserTransaction.forEach(transaction -> {
                if(transaction.getCharity().getId() == transactionDto.getCharity().getId() &&
                        transaction.getIsVolunteer() &&
                        transactionDto.getIsVolunteer()){
                    throw new IsAlreadyVolunteer("Is already volunteer!");
                }
            });
        }


        UsersTransaction usersTransaction = mapFromDtoToTransaction(transactionDto);
        transactionRepository.save(usersTransaction);
        return updateCharity(transactionDto);
    }

    @Override
    public TransactionDto mapFromTransactionToDto(UsersTransaction usersTransaction) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(usersTransaction.getId());
        transactionDto.setUser(usersTransaction.getUser());
        transactionDto.setCharity(usersTransaction.getCharity());
        transactionDto.setDonation(usersTransaction.getDonation());
        transactionDto.setIsVolunteer(usersTransaction.getIsVolunteer());
        transactionDto.setRegisteredAt(usersTransaction.getRegisteredAt());
        return transactionDto;
    }

    @Override
    public UsersTransaction mapFromDtoToTransaction(TransactionDto transactionDto) {
        UserDto userDto = this.customUserServices.getUserInfo();
        User user = this.customUserServices.mapFromDtoToUser(userDto);
        UsersTransaction usersTransaction = new UsersTransaction();
        usersTransaction.setId(transactionDto.getId());
        usersTransaction.setUser(user);
        usersTransaction.setCharity(transactionDto.getCharity());
        usersTransaction.setDonation(transactionDto.getDonation());
        usersTransaction.setIsVolunteer(transactionDto.getIsVolunteer());
        usersTransaction.setRegisteredAt(new Date());
        return usersTransaction;
    }

    private CharityDto updateCharity(TransactionDto transactionDto){
        CharityDto charityDto = this.charityService.mapFromCharityToDto(transactionDto.getCharity());
        if(transactionDto.getIsVolunteer()){
            charityDto.setCurrentVolunteers(charityDto.getCurrentVolunteers()+1);
        }
        if (!isEmpty(transactionDto.getDonation())){
            charityDto.setCurrentBudget(charityDto.getCurrentBudget() + transactionDto.getDonation());
        } else {
        }
        return this.charityService.editCharity(charityDto.getId(), charityDto);
    }

    private boolean validateCharityUpdate(TransactionDto transactionDto){
        Charity charity = transactionDto.getCharity();
        if(transactionDto.getIsVolunteer() &&
                charity.getCurrentVolunteers() + 1  > charity.getRequiredVolunteers()){
            return false;
        }
        if(!isEmpty(transactionDto.getDonation()) &&
                transactionDto.getCharity().getCurrentBudget() + transactionDto.getDonation() > transactionDto.getCharity().getRequiredBudget()){
            return false;
        }
        return true;
    }
}
