package com.example.jpashop;

import com.example.jpashop.controller.MemberForm;
import com.example.jpashop.domain.Address;
import com.example.jpashop.domain.Member;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        modelMapper.typeMap(MemberForm.class, Address.class)
                .addMappings(mapper -> {
                    mapper.map(MemberForm::getCity, Address::setCity);
                    mapper.map(MemberForm::getStreet, Address::setStreet);
                    mapper.map(MemberForm::getZipcode, Address::setZipcode);
                });
        return modelMapper;
    }
}
