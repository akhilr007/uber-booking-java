package com.akhil.uber_backend.uber_ride.services.impl;

import com.akhil.uber_backend.uber_ride.services.DistanceService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@Slf4j
public class DistanceServiceOSRMImpl implements DistanceService {

    private static final String OSRM_API_BASE_URL = "http://router.project-osrm.org/route/v1/driving/";

    @Override
    public double calculateDistance(Point source, Point destination) {

        try {

            String uri = String
                    .format("%f,%f;%f,%f", source.getX(), source.getY(), destination.getX(), destination.getY());

            OSRMResponseDTO responseDTO = RestClient.builder()
                    .baseUrl(OSRM_API_BASE_URL)
                    .build()
                    .get()
                    .uri(uri)
                    .retrieve()
                    .body(OSRMResponseDTO.class);

            return responseDTO.getRoutes().get(0).getDistance() / 1000.0; // in kilometres

        } catch (Exception e){
            log.info(e.getLocalizedMessage());
            throw new RuntimeException("Error getting data from OSRM " + e.getLocalizedMessage());
        }
    }
}

@Data
class OSRMResponseDTO {
    private List<OSRMRoute> routes;
}

@Data
class OSRMRoute {
    private Double distance;
}
