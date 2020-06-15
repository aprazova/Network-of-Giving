package com.finaltask.networkofgiving.service.interfaces;

import com.finaltask.networkofgiving.dto.CharityDto;
import com.finaltask.networkofgiving.dto.TransactionDto;
import com.finaltask.networkofgiving.model.UsersTransaction;

public interface ITransactionService {

    CharityDto addTransaction(TransactionDto transactionDto);

    TransactionDto mapFromTransactionToDto(UsersTransaction usersTransaction);
    UsersTransaction mapFromDtoToTransaction(TransactionDto transactionDto);
}
