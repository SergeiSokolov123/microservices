package com.bank.publicinfo.controller;

import com.bank.publicinfo.dto.BankDetailsDto;
import com.bank.publicinfo.entity.BankDetailsEntity;
import com.bank.publicinfo.service.BankDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Контроллер для {@link BankDetailsDto}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/bank/details")
@Tag(name = "Контролер для реквизитов банка")
public class BankDetailsController {

    private final BankDetailsService service;

    /**
     * @param id технический идентификатор {@link BankDetailsEntity}
     * @return {@link ResponseEntity}, {@link BankDetailsDto} и HttpStatus.OK
     */
    @GetMapping("/{id}")
    @Operation(summary = "Поиск по ID и вывод информации по реквизитам банка")
    private ResponseEntity<BankDetailsDto> readById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    /**
     * @param ids лист технических идентификаторов {@link BankDetailsEntity}
     * @return {@link ResponseEntity}, лист {@link BankDetailsDto} и HttpStatus.OK
     */
    @GetMapping("/read/all")
    @Operation(summary = "Поиск и вывод информации о нескольких банковских реквизитах по ID")
    private ResponseEntity<List<BankDetailsDto>> readAllById(@RequestParam List<Long> ids) {
        return ResponseEntity.ok().body(service.findAllById(ids));
    }

    /**
     * @param bankDetails {@link BankDetailsDto}
     * @return {@link ResponseEntity}, {@link BankDetailsDto} и HttpStatus.OK
     */
    @PostMapping("/create")
    @Operation(summary = "Создание и вывод банковских реквизитов")
    private ResponseEntity<BankDetailsDto> create(@Valid @RequestBody BankDetailsDto bankDetails) {
        return ResponseEntity.ok().body(service.create(bankDetails));
    }

    /**
     * @param id          технический идентификатор {@link BankDetailsEntity}
     * @param bankDetails {@link BankDetailsDto}
     * @return {@link ResponseEntity}, {@link BankDetailsDto} и HttpStatus.OK
     */
    @PutMapping("/update/{id}")
    @Operation(summary = "Обновление и вывод банковских реквизитов по ID")
    private ResponseEntity<BankDetailsDto> update(@PathVariable("id") Long id,
                                                  @Valid @RequestBody BankDetailsDto bankDetails) {
        return ResponseEntity.ok().body(service.update(id, bankDetails));
    }
}
