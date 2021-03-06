package com.app.config;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * An application context loader.
 *
 * @author Akis Papadopoulos
 */
public class ContextLoader extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        Class<?>[] classes = {
            DataSourceConfiguration.class
        };

        return classes;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        Class<?>[] classes = {
            WebConfiguration.class
        };

        return classes;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"api/v1/*"};
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{
            new HiddenHttpMethodFilter()
        };
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);

        servletContext.setInitParameter("spring.profiles.default", "development");
    }
}
