package com.app.forecast.repository;

import com.app.forecast.entity.EndpointCall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EndpointCallsLoggerRepository extends JpaRepository<EndpointCall, Long> {
}
