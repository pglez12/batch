package com.microservicios.batch.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * Servicio que se encarga de lanzar el job de procesamiento de compras a una hora específica
 * cada día, utilizando el JobLauncher de Spring Batch.
 * 
 * @author grupo1
 */
@Service
@Slf4j
public class BatchServiceImp {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job processCompraJob;
    
    /**
     * Método que se ejecuta de forma programada a las 23:30 todos los días.
     * 
     * Inicia el job de procesamiento de compras y maneja cualquier excepción que
     * pueda ocurrir durante la ejecución del job.
     */
    @Scheduled(cron = "0 52 23 * * ?")
    public void executeJob() {
        try {
            log.info("Iniciando el job de procesamiento de compras.");
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(processCompraJob, jobParameters);
        } catch (Exception e) {
            log.error("Error al ejecutar el job de procesamiento de compras", e);
        }
    }

}
