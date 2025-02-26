package cgi.log.watcher.service;

import cgi.log.watcher.controller.dto.LogsInputDTO;
import cgi.log.watcher.entity.Log;
import cgi.log.watcher.repository.LogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LogService {

    private LogRepository logRepository;

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public Log salvarLog(LogsInputDTO dto) {

        var entity = new Log(dto.tipo(), dto.mensagem(), dto.url(), dto.caminho(), dto.stack(), dto.userAgent());

        return logRepository.save(entity);
    }

    public Page<Log> buscarLogs(Pageable pageable) {
        return logRepository.findAll(pageable);
    }
}
