package com.akhil.uber_backend.uber_ride.services;

import org.locationtech.jts.geom.Point;

public interface DistanceService {

    double calculateDistance(Point source, Point destination);
}
