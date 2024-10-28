package com.microservicios.batch.config;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;

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

/**
 * Configuración del Batch para el procesamiento de compras.
 * 
 * Esta clase configura los componentes necesarios para el procesamiento de
 * compras utilizando Spring Batch, incluyendo el lector, procesador y
 * escritor de items, así como los listeners y el job.
 */
@Configuration
@AllArgsConstructor
@Slf4j
public class BatchConfig {
	
	@Autowired
    private JpaTransactionManager transactionManager;
	
    /**
     * Crea un bean del lector de compras.
     * 
     * @return un objeto de tipo CompraItemReader.
     */
    @Bean
    public CompraItemReader reader() {
        return new CompraItemReader();
    }

    /**
     * Crea un bean del procesador de compras.
     * 
     * @return un objeto de tipo CompraItemProcessor.
     */
    @Bean
    public CompraItemProcessor processor() {
        return new CompraItemProcessor();
    }

    /**
     * Crea un bean del escritor de compras.
     * 
     * @return un objeto de tipo CompraItemWriter.
     */
    @Bean
    public CompraItemWriter writer() {
        return new CompraItemWriter();
    }

    /**
     * Crea un bean del listener para la ejecución del job.
     * 
     * @return un objeto de tipo CompraJobExecutionListener.
     */
    @Bean
    public CompraJobExecutionListener jobExecutionListener() {
        return new CompraJobExecutionListener();
    }
    
    /**
     * Crea un bean del listener para la lectura de items.
     * 
     * @return un objeto de tipo CompraItemReaderListener.
     */
    @Bean
    public CompraItemReaderListener readerListener() {
        return new CompraItemReaderListener();
    }

    /**
     * Crea un bean del listener para el procesamiento de items.
     * 
     * @return un objeto de tipo CompraItemProcessorListener.
     */
    @Bean
    public CompraItemProcessorListener itemProcessListener() {
        return new CompraItemProcessorListener();
    }

    /**
     * Crea un bean del listener para la escritura de items.
     * 
     * @return un objeto de tipo CompraItemWriterListener.
     */
    @Bean
    public CompraItemWriterListener writerListener() {
        return new CompraItemWriterListener();
    }

    /**
     * Crea un job de procesamiento de compras.
     * 
     * @param step el paso del job que se ejecutará.
     * @param jobExecutionListener el listener que se ejecutará durante la
     *                             ejecución del job.
     * @param jobRepository el repositorio del job.
     * @return un objeto de tipo Job configurado para procesar compras.
     */
    @Bean
    public Job processCompraJob(Step step, CompraJobExecutionListener jobExecutionListener, JobRepository jobRepository) {
        log.info("---- >>> JOB: processCompraJob");
        return new JobBuilder("processCompraJob", jobRepository)
                .listener(jobExecutionListener)
                .incrementer(new RunIdIncrementer())
                .flow(step)
                .end()
                .build();
    }

    /**
     * Crea un paso para el procesamiento de compras.
     * 
     * @param reader el lector de items.
     * @param processor el procesador de items.
     * @param writer el escritor de items.
     * @param readerListener el listener para la lectura de items.
     * @param itemProcessListener el listener para el procesamiento de items.
     * @param writerListener el listener para la escritura de items.
     * @param jobRepository el repositorio del job.
     * @return un objeto de tipo Step configurado para procesar compras.
     */
	@Bean
    public Step step(CompraItemReader reader,
                     CompraItemProcessor processor,
                     CompraItemWriter writer,
                     CompraItemReaderListener readerListener,
                     CompraItemProcessorListener itemProcessListener,
                     CompraItemWriterListener writerListener, JobRepository jobRepository) {
        log.info("---- >>> STEP: processCompraStep");
        return new StepBuilder("processCompraStep", jobRepository)
                .<List<CompraMedia>, List<Historico>>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(readerListener)
                .listener(itemProcessListener)
                .listener(writerListener)
                .build();
    }

}
