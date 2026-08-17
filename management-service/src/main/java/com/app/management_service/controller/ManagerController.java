package com.app.management_service.controller;

import com.app.management_service.dto.ManagerRequestDTO;
import com.app.management_service.dto.ManagerResponseDTO;
import com.app.management_service.service.ManagerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/management/managers")
@RequiredArgsConstructor
@Slf4j
public class ManagerController {

    private final ManagerService managerService;

    @PostMapping
    public ManagerResponseDTO create(@Valid @RequestBody ManagerRequestDTO dto) {
        log.info(dto.toString());
        return managerService.save(dto);
    }

    @PostMapping("/bulk")
    public List<ManagerResponseDTO> createBulk(@Valid @RequestBody List<@Valid ManagerRequestDTO> dtoList) {
        return managerService.saveAll(dtoList);
    }

    @PutMapping("/{id}")
    public ManagerResponseDTO update(
            @PathVariable Long id,
            @RequestBody ManagerRequestDTO dto) {
        return managerService.update(id, dto);
    }
}