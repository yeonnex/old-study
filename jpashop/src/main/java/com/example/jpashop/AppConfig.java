package com.example.jpashop;

import com.example.jpashop.controller.MemberForm;
import com.example.jpashop.domain.Address;
import com.example.jpashop.domain.Member;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.NoRepositoryBean;

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

    @Bean
    public Hibernate5Module hibernate5Module() {
        Hibernate5Module hibernate5Module = new Hibernate5Module();
        hibernate5Module.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, true);
        return hibernate5Module;
    }
}
