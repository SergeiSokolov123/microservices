package com.bank.publicinfo.controller;

import com.bank.publicinfo.service.CertificateService;
import com.bank.publicinfo.dto.CertificateDto;
import com.bank.publicinfo.entity.CertificateEntity;
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

import java.util.List;

/**
 * Контроллер для {@link CertificateDto}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/certificate")
@Tag(name = "Контролер для сертификатов")
public class CertificateController {

    private final CertificateService service;

    /**
     * @param id технический идентификатор {@link CertificateEntity}
     * @return {@link ResponseEntity}, {@link CertificateDto} и HttpStatus.OK
     */
    @GetMapping("/{id}")
    @Operation(summary = "Поиск по ID и вывод информации о сертификате по банкомату")
    private ResponseEntity<CertificateDto> readById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    /**
     * @param ids лист технических идентификаторов {@link CertificateEntity}
     * @return {@link ResponseEntity}, лист {@link CertificateDto} и HttpStatus.OK
     */
    @GetMapping("/read/all")
    @Operation(summary = "Поиск и вывод информации о нескольких сертификатах по ID")
    private ResponseEntity<List<CertificateDto>> readAllById(@RequestParam List<Long> ids) {
        return ResponseEntity.ok().body(service.findAllById(ids));
    }

    /**
     * @param certificate {@link CertificateDto}
     * @return {@link ResponseEntity}, {@link CertificateDto} и HttpStatus.OK
     */
    @PostMapping("/create")
    @Operation(summary = "Создание и вывод сертификата")
    private ResponseEntity<CertificateDto> create(@RequestBody CertificateDto certificate) {
        return ResponseEntity.ok().body(service.create(certificate));
    }

    /**
     * @param id          технический идентификатор {@link CertificateEntity}
     * @param certificate {@link CertificateDto}
     * @return {@link ResponseEntity}, {@link CertificateDto} и HttpStatus.OK
     */
    @PutMapping("/update/{id}")
    @Operation(summary = "Обновление и вывод информации о сертификате по ID")
    private ResponseEntity<CertificateDto> update(@PathVariable("id") Long id,
                                                  @RequestBody CertificateDto certificate) {
        return ResponseEntity.ok().body(service.update(id, certificate));
    }
}
