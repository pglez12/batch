package com.microservicios.batch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CompraJobExecutionListener  implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("Iniciando el Job: {}", jobExecution.getJobInstance().getJobName());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus().isUnsuccessful()) {
            log.error("Job {} falló con los siguientes errores: {}", jobExecution.getJobInstance().getJobName(), jobExecution.getAllFailureExceptions());
        } else {
            log.info("Job {} finalizó con éxito.", jobExecution.getJobInstance().getJobName());
        }
    }
}

