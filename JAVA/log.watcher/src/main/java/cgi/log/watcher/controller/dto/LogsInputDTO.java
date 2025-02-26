package cgi.log.watcher.controller.dto;

import java.time.LocalDateTime;

public record LogsInputDTO(String tipo, String mensagem, String url, String caminho, String stack, LocalDateTime dataOcorrencia, String userAgent) {
}
