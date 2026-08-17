package com.app.management_service.service;

import com.app.management_service.dto.ManagerRequestDTO;
import com.app.management_service.dto.ManagerResponseDTO;
import com.app.management_service.mapper.ManagerMapper;
import com.app.management_service.model.ManagerDetails;
import com.app.management_service.doa.ManagerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ManagerService  {

    private final ManagerRepository managerRepository;
    private final ManagerMapper managerMapper;

    public ManagerResponseDTO save(ManagerRequestDTO dto) {

        if (managerRepository.existsByManagerEmail(dto.getManagerEmail())) {
            throw new RuntimeException("Manager email already exists");
        }

        ManagerDetails entity = managerMapper.toEntity(dto);

        // default handling
        if (entity.getActive() == null) {
            entity.setActive(true);
        }
        log.info(dto.getManagerEmail());
        log.info(entity.getManagerEmail());

        ManagerDetails saved = managerRepository.save(entity);
        return managerMapper.toResponse(saved);
    }

    public List<ManagerResponseDTO> saveAll(List<ManagerRequestDTO> dtoList) {

        ManagerDetails entity = managerMapper.toEntity(dtoList.get(0));

        List<ManagerDetails> entities = dtoList.stream()
                .map(managerMapper::toEntity)
                .peek(e -> {
                    if (e.getActive() == null) e.setActive(true);
                })
                .toList();

        return managerRepository.saveAll(entities)
                .stream()
                .map(managerMapper::toResponse)
                .toList();
    }

    public ManagerResponseDTO update(Long id, ManagerRequestDTO dto) {

        ManagerDetails entity = managerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manager not found"));

        // MapStruct update (null-safe)
        managerMapper.updateEntityFromDto(dto, entity);

        ManagerDetails updated = managerRepository.save(entity);
        return managerMapper.toResponse(updated);
    }
}