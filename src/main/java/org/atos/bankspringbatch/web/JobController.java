package org.atos.bankspringbatch.web;

import org.atos.bankspringbatch.BankTransactionItemAnalyticsProcessor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class JobController {
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;
    @Autowired
    private BankTransactionItemAnalyticsProcessor bankTransactionItemAnalyticsProcessor;
    @GetMapping("/startJob")
    public BatchStatus load() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        Map<String, JobParameter> jobParam = new HashMap<>();
        jobParam.put("time",new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(jobParam);
        JobExecution jobExecution = jobLauncher.run(job , jobParameters);
        while (jobExecution.isRunning()){
            System.out.println("...");
        }

        return jobExecution.getStatus();
    }

    @GetMapping("/analytics")
    public Map<String,Double> analytics(){
        Map<String,Double> map=new HashMap<>();
        map.put("totalCredit" , bankTransactionItemAnalyticsProcessor.getTotalCredit());
        map.put("totalDebit" , bankTransactionItemAnalyticsProcessor.getTotalDebit());

        return map;
    }
}
