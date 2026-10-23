package com.codegym.validsong;

import com.codegym.validsong.configuration.AppConfiguration;
import org.apache.catalina.Context;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.File;

public class MainApp {

    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();

        String docBase = new File("src/main/webapp").getAbsolutePath();
        Context context = tomcat.addContext("", docBase);
        context.setParentClassLoader(MainApp.class.getClassLoader());

        // Spring MVC ApplicationContext
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(AppConfiguration.class);

        // DispatcherServlet
        DispatcherServlet dispatcherServlet = new DispatcherServlet(appContext);
        Wrapper wrapper = tomcat.addServlet("", "dispatcher", dispatcherServlet);
        wrapper.setLoadOnStartup(1);
        wrapper.addMapping("/");

        // UTF-8 Filter
        FilterDef filterDef = new FilterDef();
        filterDef.setFilterName("encodingFilter");
        filterDef.setFilterClass(CharacterEncodingFilter.class.getName());
        filterDef.addInitParameter("encoding", "UTF-8");
        filterDef.addInitParameter("forceEncoding", "true");
        context.addFilterDef(filterDef);

        FilterMap filterMap = new FilterMap();
        filterMap.setFilterName("encodingFilter");
        filterMap.addURLPattern("/*");
        context.addFilterMap(filterMap);

        tomcat.start();
        System.out.println("===============================================================");
        System.out.println("Spring MVC Web App started at: http://localhost:8080");
        System.out.println("===============================================================");
        tomcat.getServer().await();
    }
}
