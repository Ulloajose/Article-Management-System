package com.backend.api.application.config.task;

import com.backend.api.domain.port.in.SynchronizeArticleUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ScheduledTasks {

    private final SynchronizeArticleUseCase synchronizeArticleUseCase;

    @Scheduled(cron = "${cron.hourly-scheduled}")
    public void startProcessJira() {
        synchronizeArticleUseCase.synchronize();
    }
}
