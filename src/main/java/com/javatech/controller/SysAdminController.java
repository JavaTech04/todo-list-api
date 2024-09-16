package com.javatech.controller;

import com.javatech.config.Translator;
import com.javatech.dto.response.ResponseData;
import com.javatech.service.SysAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/${api.version}/admin")
@Tag(name = "System Administration", description = "Manages system-level operations including user management for system administrators.")
@RequiredArgsConstructor
public class SysAdminController {

    private final SysAdminService sysAdminService;

    @Operation(
            summary = "Delete a User",
            description = "This endpoint allows a system administrator to delete a user account by specifying the user ID in the URL path. Upon successful deletion, a confirmation message is returned."
    )
    @DeleteMapping("/delete-user/{userId}")
    public ResponseData<?> deleteUser(@PathVariable("userId") long userId) {
        log.info("Attempting to delete user with id {}", userId);
        this.sysAdminService.deleteUser(userId);
        return new ResponseData<>(HttpStatus.NO_CONTENT.value(), Translator.toLocale("common.success.delete.user"));
    }
}
