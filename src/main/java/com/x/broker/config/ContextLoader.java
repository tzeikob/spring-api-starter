package com.x.broker.config;

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
        Class<?>[] classes = {};

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
        return new String[]{"/"};
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

        servletContext.setInitParameter("spring.profiles.default", "develop");
    }
}
