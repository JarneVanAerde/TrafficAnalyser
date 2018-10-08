package be.kdg.processor.config;

import be.kdg.processor.models.fines.Fine;
import be.kdg.processor.persistence.FineRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

@Configuration
public class RepositoryConfig {
}
