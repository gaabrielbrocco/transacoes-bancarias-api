package cgi.log.watcher.repository;

import cgi.log.watcher.entity.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository  extends JpaRepository<Log, Long> {

    Page<Log> findAll(Pageable pageable);
}
