package com.thomazllr.producer;

import com.thomazllr.domain.Producer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProducerRepository extends JpaRepository<Producer, Long> {

    List<Producer> findProducerByName(String name);
}
