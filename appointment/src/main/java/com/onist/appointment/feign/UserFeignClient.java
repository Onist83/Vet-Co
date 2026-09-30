package com.onist.appointment.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.onist.appointment.dto.UserFeignResponse;

@FeignClient(name = "user")
public interface UserFeignClient {

    @GetMapping("/api/v1/user/{id}")
    UserFeignResponse getUserById(@PathVariable("id") Long id);

    // NOUVELLE MÉTHODE : Chercher par nom et prénom
    @GetMapping("/api/v1/user/search")
    List<UserFeignResponse> searchUsersByNames(
            @RequestParam String firstName,
            @RequestParam String lastName);
}
