package pl.pjatk.jaz_s30576_nbp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pjatk.jaz_s30576_nbp.model.QueryLog;

@Repository
public interface QueryLogRepository extends JpaRepository<QueryLog,Long> {
}