package com.microservicios.batch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Listener para el ciclo de vida de la ejecución de un job.
 * 
 * Este componente se encarga de manejar eventos que ocurren antes y después
 * de la ejecución de un job en el contexto de procesamiento por lotes.
 * Proporciona una forma de realizar acciones o registrar información
 * en función del estado de la ejecución del job.
 * 
 * @author grupo1
 */
@Slf4j
@Component
public class CompraJobExecutionListener  implements JobExecutionListener {

    /**
     * Método que se invoca antes de que se inicie la ejecución del job.
     * 
     * @param jobExecution la información sobre la ejecución del job que se va a iniciar
     */
    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("Iniciando el Job: {}", jobExecution.getJobInstance().getJobName());
    }


    /**
     * Método que se invoca después de que se completa la ejecución del job.
     * 
     * @param jobExecution la información sobre la ejecución del job que se completó
     */
    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus().isUnsuccessful()) {
            log.error("Job {} falló con los siguientes errores: {}", jobExecution.getJobInstance().getJobName(), jobExecution.getAllFailureExceptions());
        } else {
            log.info("Job {} finalizó con éxito.", jobExecution.getJobInstance().getJobName());
        }
    }
}

