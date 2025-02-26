package cgi.log.watcher.controller;

import cgi.log.watcher.controller.dto.LogsInputDTO;
import cgi.log.watcher.entity.Log;
import cgi.log.watcher.service.LogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogController {

    private LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @PostMapping("/enviar")
    public ResponseEntity<Log> salvarLog(@RequestBody LogsInputDTO dto) {

        try {
            var log = logService.salvarLog(dto);
            return new ResponseEntity<>(log, HttpStatus.CREATED);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<Log>> buscarLogs(Pageable pageable) {
        Page<Log> logs = logService.buscarLogs(pageable);
        return ResponseEntity.ok(logs);

    }
}
