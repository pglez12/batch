package com.microservicios.batch.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BatchServiceImp {

    private static final Logger logger = LoggerFactory.getLogger(BatchServiceImp.class);

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job processCompraJob;

    @Scheduled(cron = "0 17 23 * * ?")
    public void executeJob() {
        try {
            logger.info("Iniciando el job de procesamiento de compras.");
            jobLauncher.run(processCompraJob, new JobParameters());
        } catch (Exception e) {
            logger.error("Error al ejecutar el job de procesamiento de compras", e);
        }
    }
}
