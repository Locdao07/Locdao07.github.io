package test.service;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import test.entity.Registration;
import test.entity.RegistrationDTO;

@Mapper(componentModel = "spring")
public interface RegistrationMapper {
    @Mapping(target = "subjectName", source = "subject.subjectName") // Example mapping if needed
    RegistrationDTO toDTO(Registration registration);
}
