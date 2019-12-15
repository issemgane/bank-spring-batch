package com.appstude.config;
import org.springframework.batch.core.Job;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;


import com.appstude.model.BankTransaction;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfiguration {

	@Bean
	public Job bankJob(JobBuilderFactory jobBuilderFactory,StepBuilderFactory stepBuilderFactory,ItemReader<BankTransaction> itemReader,
			      ItemProcessor<BankTransaction,BankTransaction> itemProcessor,ItemWriter<BankTransaction> itemWriter) {
		

					Step step = stepBuilderFactory.get("ETL-transaction-file-load")
							.<BankTransaction,BankTransaction>chunk(100)
							.reader(itemReader)
							.processor(itemProcessor)
							.writer(itemWriter)
							.build();
					
					return jobBuilderFactory.get("ETL-JOB-LOAD")
					.incrementer(new RunIdIncrementer())
					.start(step)
//multiple steps    .flow(step)
//multiple steps	.next(step)
					.build();
		
	}
	
	
    @Bean
	public FlatFileItemReader<BankTransaction> itemReader(@Value("${filePath}")Resource resource) {
		
		FlatFileItemReader<BankTransaction> flatFileItemReader = new FlatFileItemReader<>();
		flatFileItemReader.setResource(resource);
		flatFileItemReader.setName("CSV-READER");
		flatFileItemReader.setLinesToSkip(1); // nbr ligne to skip if there is an error
		flatFileItemReader.setLineMapper(lineMapper()); // map columns with pojo
		
		return flatFileItemReader;
		
	}


	@Bean
	public LineMapper<BankTransaction> lineMapper() {
		DefaultLineMapper<BankTransaction> defaultLineMapper = new DefaultLineMapper<>();
		
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		//lineTokenizer.setNames(new String[] {"transaction_id","account_number","transaction_date","transaction_type","transaction_amount"});
        lineTokenizer.setNames(new String[] {"id","accountId","strTransactionDate","transactionType","transactionAmount"});

        BeanWrapperFieldSetMapper<BankTransaction> fieldSetMapper = new BeanWrapperFieldSetMapper<BankTransaction>();
		
		fieldSetMapper.setTargetType(BankTransaction.class);
		
		defaultLineMapper.setLineTokenizer(lineTokenizer);
		defaultLineMapper.setFieldSetMapper(fieldSetMapper);
		
		return defaultLineMapper;
	}
}
