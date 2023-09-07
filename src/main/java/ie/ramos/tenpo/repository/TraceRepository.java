package ie.ramos.tenpo.repository;

import ie.ramos.tenpo.dao.Trace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TraceRepository extends JpaRepository<Trace, UUID> {
}
