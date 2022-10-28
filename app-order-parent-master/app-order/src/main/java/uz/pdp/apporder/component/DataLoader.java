package uz.pdp.apporder.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.apporder.repository.BranchRepository;

import java.util.Objects;

@RequiredArgsConstructor
@Configuration
public class DataLoader implements CommandLineRunner {

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlMode;
    private final BranchRepository branchRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (Objects.nonNull(ddlMode) && ddlMode.startsWith("create")) {
            branchRepository.executeInitialFunction();
        }
    }
}
