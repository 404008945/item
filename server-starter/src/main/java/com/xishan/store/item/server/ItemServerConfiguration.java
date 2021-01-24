package com.xishan.store.item.server;

import com.github.pagehelper.PageHelper;
import com.xishan.store.item.server.aop.TransactionAdivisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.interceptor.BeanFactoryTransactionAttributeSourceAdvisor;
import org.springframework.transaction.interceptor.TransactionAttributeSource;

import java.util.Properties;

@Configuration
public class ItemServerConfiguration {

    @Bean
    public PageHelper PageHelperpageHelper(){
        PageHelper pageHelper =new PageHelper();
        Properties properties =new Properties();
        properties.setProperty("offsetAsPageNum","true");
        properties.setProperty("rowBoundsWithCount","true");
        properties.setProperty("reasonable","true");
        properties.setProperty("dialect","mysql");
        pageHelper.setProperties(properties);
        return pageHelper;
    }
    @Autowired
    public void setOrder(BeanFactoryTransactionAttributeSourceAdvisor beanFactoryTransactionAttributeSourceAdvisor, TransactionAttributeSource transactionAttributeSource){
        beanFactoryTransactionAttributeSourceAdvisor.setOrder(4);
        TransactionAdivisor transactionAdivisor = new TransactionAdivisor();
        transactionAdivisor.setTransactionAttributeSource(transactionAttributeSource);
        beanFactoryTransactionAttributeSourceAdvisor.setAdvice(transactionAdivisor);
    }
}
