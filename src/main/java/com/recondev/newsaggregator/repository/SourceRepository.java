package com.recondev.newsaggregator.repository;

import com.recondev.newsaggregator.model.Source;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SourceRepository extends JpaRepository<Source, String> {


}
