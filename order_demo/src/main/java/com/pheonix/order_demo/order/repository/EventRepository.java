package com.pheonix.order_demo.order.repository;

import com.pheonix.order_demo.order.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

}
