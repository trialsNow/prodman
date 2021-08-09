package com.galvanize.prodman.service;

import com.galvanize.prodman.config.DomainConfig;
import com.galvanize.prodman.config.JacksonConfig;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DomainConfig.class, JacksonConfig.class})
@WebAppConfiguration
@ActiveProfiles("test")
@Transactional
public class ProductServiceIntegrationTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    ProductService productService;

    //TODO Tests was optional we can write more integration tests here which will test with real db

}
