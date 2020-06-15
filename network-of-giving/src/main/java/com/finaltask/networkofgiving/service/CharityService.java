package com.finaltask.networkofgiving.service;

import com.finaltask.networkofgiving.dto.CharityDto;
import com.finaltask.networkofgiving.exception.CharityNotFoundException;
import com.finaltask.networkofgiving.model.Charity;
import com.finaltask.networkofgiving.repository.CharityRepository;
import com.finaltask.networkofgiving.service.interfaces.ICharityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.RollbackException;
import javax.ws.rs.NotAuthorizedException;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@Transactional
public class CharityService implements ICharityService {

    @Autowired
    private AuthService authService;

    @Autowired
    private CharityRepository charityRepository;

    @Override
    public void createCharity(CharityDto charityDto) throws RollbackException {
        if(isEmpty(charityDto.getRequiredBudget()) && isEmpty(charityDto.getRequiredVolunteers())){
            throw new RollbackException("Transaction can not be commit.");
        }

        Charity charity = mapFromDtoToCharity(charityDto);
        charityRepository.save(charity);
    }

    @Override
    public List<CharityDto> showAllCharities() {
        List<Charity> charities = charityRepository.findAll();
        return charities.stream()
                .map(this::mapFromCharityToDto)
                .collect(toList());
    }

    @Override
    public CharityDto readSingleCharity(Long id) {
        Charity charity = charityRepository.findById(id).orElseThrow(
                () -> new CharityNotFoundException("Charity not found"));
        return mapFromCharityToDto(charity);
    }

    @Override
    public List<CharityDto> getUsersCharity() {
        User loggedUser = authService.getCurrentUser().orElseThrow(
                () -> new NotAuthorizedException("User not found.")
        );

        String username = loggedUser.getUsername();
        List<Charity> charities = charityRepository.findAllByOwner(username);
        return charities.stream().map(this::mapFromCharityToDto).collect(toList());

    }

    @Override
    public void deleteCharity(Long id) {
        User loggedUser = authService.getCurrentUser().orElseThrow(
                () -> new NotAuthorizedException("User not found.")
        );

        charityRepository.findById(id).orElseThrow(
                () -> new CharityNotFoundException("Charity not found"));

        charityRepository.deleteByIdAndOwner(id, loggedUser.getUsername());
    }

    @Override
    public CharityDto editCharity(Long id, CharityDto charityDto) {

        User loggedUser = authService.getCurrentUser().orElseThrow(
                () -> new NotAuthorizedException("User not found.")
        );

        if(!charityRepository.existsById(id)){
            throw new IllegalArgumentException("Charity doesn't exist.");
        }
        Charity charity = charityRepository.findById(id).get();

        charity.setTitle(charityDto.getTitle());
        charity.setDescription(charityDto.getDescription());
        charity.setImage(charityDto.getImage());
        charity.setCurrentVolunteers(charityDto.getCurrentVolunteers());
        charity.setCurrentBudget(charityDto.getCurrentBudget());
        charity.setRequiredVolunteers(charityDto.getRequiredVolunteers());
        charity.setRequiredBudget(charityDto.getRequiredBudget());
        charityRepository.save(charity);
        return charityDto;
    }

    @Override
    public CharityDto mapFromCharityToDto(Charity charity){

        CharityDto charityDto = new CharityDto();

        charityDto.setId(charity.getId());
        charityDto.setOwner(charity.getOwner());
        charityDto.setTitle(charity.getTitle());
        charityDto.setDescription(charity.getDescription());
        charityDto.setImage(charity.getImage());
        charityDto.setRequiredBudget(charity.getRequiredBudget());
        charityDto.setRequiredVolunteers(charity.getRequiredVolunteers());
        charityDto.setCurrentVolunteers(charity.getCurrentVolunteers());
        charityDto.setCurrentBudget(charity.getCurrentBudget());
        return charityDto;
    }

    @Override
    public Charity mapFromDtoToCharity(CharityDto charityDto) {
        User loggedUser = authService.getCurrentUser().orElseThrow(
                () -> new IllegalArgumentException("User not found.")
        );

        Charity charity = new Charity();
        charity.setOwner(loggedUser.getUsername());
        charity.setId(charityDto.getId());
        charity.setTitle(charityDto.getTitle());
        charity.setImage(charityDto.getImage());
        charity.setDescription(charityDto.getDescription());
        charity.setRequiredBudget(charityDto.getRequiredBudget());
        charity.setRequiredVolunteers(charityDto.getRequiredVolunteers());
        charity.setCurrentBudget(charityDto.getCurrentBudget());
        charity.setCurrentVolunteers(charityDto.getCurrentVolunteers());
        return charity;
    }
}
