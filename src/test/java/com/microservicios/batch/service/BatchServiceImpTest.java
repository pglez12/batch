package com.microservicios.batch.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;


@ExtendWith(MockitoExtension.class)
public class BatchServiceImpTest {
	@InjectMocks
    private BatchServiceImp batchService;

    @Mock
    private JobLauncher jobLauncher;

    @Mock
    private Job processCompraJob;

    @Mock
    private JobExecution jobExecution;

    @Test
    void testExecuteJob_Success() throws Exception {
        when(jobLauncher.run(any(Job.class), any(JobParameters.class))).thenReturn(jobExecution);

        batchService.executeJob();

        verify(jobLauncher, times(1)).run(processCompraJob, new JobParameters());
    }

    @Test
    void testExecuteJob_Error() throws Exception {
        doThrow(new RuntimeException("Job execution failed")).when(jobLauncher).run(any(Job.class), any(JobParameters.class));

        batchService.executeJob();

        verify(jobLauncher, times(1)).run(processCompraJob, new JobParameters());
    }

}
