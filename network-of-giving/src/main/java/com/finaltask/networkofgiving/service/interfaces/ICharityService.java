package com.finaltask.networkofgiving.service.interfaces;

import com.finaltask.networkofgiving.dto.CharityDto;
import com.finaltask.networkofgiving.model.Charity;

import javax.persistence.RollbackException;
import java.util.List;

public interface ICharityService {

    void createCharity(CharityDto charityDto) throws RollbackException;

    List<CharityDto> showAllCharities();

    CharityDto readSingleCharity(Long id);

    CharityDto mapFromCharityToDto(Charity charity);

    Charity mapFromDtoToCharity(CharityDto charityDto);

    List<CharityDto> getUsersCharity();

    void deleteCharity(Long id);

    CharityDto editCharity(Long id, CharityDto charityDto);

}
