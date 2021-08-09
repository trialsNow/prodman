package com.galvanize.prodman.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;

@Repository
public class BaseDataAccessor {

    private static final String SELECT_SYSDATE = "SELECT SYSTIMESTAMP FROM DUAL";
    protected JdbcTemplate jdbcTemplate;
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    protected JdbcTemplate highFetchJdbcTemplate;
    protected JdbcTemplate midFetchJdbcTemplate;

    @Autowired
    public void createTemplate(@Qualifier("dataSource") DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        midFetchJdbcTemplate = new JdbcTemplate(dataSource);
        midFetchJdbcTemplate.setFetchSize(1000);
        highFetchJdbcTemplate = new JdbcTemplate(dataSource);
        highFetchJdbcTemplate.setFetchSize(10000);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate mjdbcTemplate) {
        jdbcTemplate = mjdbcTemplate;
    }

    public JdbcTemplate getMidFetchJdbcTemplate() {
        return midFetchJdbcTemplate;
    }

    public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return namedParameterJdbcTemplate;
    }

    public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public JdbcTemplate getHighFetchJdbcTemplate() {
        return this.highFetchJdbcTemplate;
    }

    public Timestamp getSysdate() {
        return jdbcTemplate.queryForObject(SELECT_SYSDATE, Timestamp.class);
    }
}
