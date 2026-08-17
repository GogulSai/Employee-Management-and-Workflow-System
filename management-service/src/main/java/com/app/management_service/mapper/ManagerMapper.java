package com.app.management_service.mapper;

import com.app.management_service.dto.ManagerRequestDTO;
import com.app.management_service.dto.ManagerResponseDTO;
import com.app.management_service.model.ManagerDetails;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ManagerMapper {

    // Request → Entity
    // @Mapping(source = "email", target = "managerEmail") If field name mismatch
    ManagerDetails toEntity(ManagerRequestDTO dto);

    // Entity → Response

    //@Mapping(target = "password", ignore = true)
    ManagerResponseDTO toResponse(ManagerDetails entity);

    // ✅ For update (reuse same DTO)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ManagerRequestDTO dto, @MappingTarget ManagerDetails entity);
}