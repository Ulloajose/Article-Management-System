package com.backend.api.application.config.task;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import com.backend.api.domain.port.in.SynchronizeArticleUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ScheduledTasks.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ScheduledTasksTest {
    @Autowired
    private ScheduledTasks scheduledTasks;

    @MockBean
    private SynchronizeArticleUseCase synchronizeArticleUseCase;

    @Test
    void testStartProcessJira() {
        doNothing().when(synchronizeArticleUseCase).synchronize();
        scheduledTasks.startProcessJira();
        verify(synchronizeArticleUseCase).synchronize();
    }
}
