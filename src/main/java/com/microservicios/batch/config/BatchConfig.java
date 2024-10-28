package com.microservicios.batch.config;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;

import com.microservicios.batch.domain.Compra;
import com.microservicios.batch.domain.CompraMedia;
import com.microservicios.batch.domain.Historico;
import com.microservicios.batch.job.CompraItemProcessor;
import com.microservicios.batch.job.CompraItemReader;
import com.microservicios.batch.job.CompraItemWriter;
import com.microservicios.batch.listener.CompraItemProcessorListener;
import com.microservicios.batch.listener.CompraItemReaderListener;
import com.microservicios.batch.listener.CompraItemWriterListener;
import com.microservicios.batch.listener.CompraJobExecutionListener;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@AllArgsConstructor
//@EnableBatchProcessing
@Slf4j
public class BatchConfig {
	
	@Autowired
    private JpaTransactionManager transactionManager;

    // -----------------------
    // Beans para el job de Compra
    // -----------------------
	
	
    @Bean
    public CompraItemReader reader() {
        return new CompraItemReader();
    }

    @Bean
    public CompraItemProcessor processor() {
        return new CompraItemProcessor();
    }

    @Bean
    public CompraItemWriter writer() {
        return new CompraItemWriter();
    }

    // -----------------------
    // Beans para levantar los listeners
    // -----------------------    
    @Bean
    public CompraJobExecutionListener jobExecutionListener() {
        return new CompraJobExecutionListener();
    }

    @Bean
    public CompraItemReaderListener readerListener() {
        return new CompraItemReaderListener();
    }

    @Bean
    public CompraItemProcessorListener itemProcessListener() {
        return new CompraItemProcessorListener();
    }

    @Bean
    public CompraItemWriterListener writerListener() {
        return new CompraItemWriterListener();
    }

    @Bean
    public Job processCompraJob(Step step, CompraJobExecutionListener jobExecutionListener, JobRepository jobRepository) {
        log.info("---- >>> JOB: processCompraJob");
        return new JobBuilder("processCompraJob", jobRepository)
                .listener(jobExecutionListener)
                .flow(step)
                .end()
                .build();
    }

	@Bean
    public Step step(CompraItemReader reader,
                     CompraItemProcessor processor,
                     CompraItemWriter writer,
                     CompraItemReaderListener readerListener,
                     CompraItemProcessorListener itemProcessListener,
                     CompraItemWriterListener writerListener, JobRepository jobRepository) {
        log.info("---- >>> STEP: processCompraStep");
        return new StepBuilder("processCompraStep", jobRepository)
                .<List<CompraMedia>, List<Historico>>chunk(100, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(readerListener)
                .listener(itemProcessListener)
                .listener(writerListener)
                .transactionManager(transactionManager)
                .build();
    }

}
