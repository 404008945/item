package com.xishan.store.item.server;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.interceptor.BeanFactoryTransactionAttributeSourceAdvisor;
import java.util.Properties;

@Configuration
public class ItemServerConfiguration  {

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

//    @Bean
//    public TransactionAdivisor transactionAdivisor(TransactionAttributeSource transactionAttributeSource,ModifyTransactionManager transactionManager){
//        TransactionAdivisor transactionAdivisor = new TransactionAdivisor();
//        transactionAdivisor.setTransactionAttributeSource(transactionAttributeSource);
//        transactionAdivisor.setTransactionManager(transactionManager);
//        return transactionAdivisor;
//    }

    @Autowired
    public void setOrder(BeanFactoryTransactionAttributeSourceAdvisor beanFactoryTransactionAttributeSourceAdvisor){
        beanFactoryTransactionAttributeSourceAdvisor.setOrder(4);
     //   beanFactoryTransactionAttributeSourceAdvisor.setAdvice(transactionAdivisor);
    }
//    @Bean
//    ModifyTransactionManager transactionManager(DataSource dataSource,
//                                                ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
//        ModifyTransactionManager transactionManager = new ModifyTransactionManager();
//        transactionManager.setDataSource(dataSource);
//        transactionManagerCustomizers.ifAvailable((customizers) -> customizers.customize(transactionManager));
//        return transactionManager;
//    }

}
