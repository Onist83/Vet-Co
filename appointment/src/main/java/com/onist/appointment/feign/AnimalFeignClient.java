package com.onist.appointment.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.onist.appointment.dto.AnimalFeignResponse;

@FeignClient(name = "animal")
public interface AnimalFeignClient {

    @GetMapping("/api/v1/animal/{id}")
    AnimalFeignResponse getAnimalById(@PathVariable("id") Long id);

    @GetMapping("/api/v1/animal/search")
    List<AnimalFeignResponse> searchAnimalsByName(@RequestParam String name);
}
