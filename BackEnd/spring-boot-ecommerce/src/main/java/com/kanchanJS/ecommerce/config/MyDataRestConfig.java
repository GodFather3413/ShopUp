package com.kanchanJS.ecommerce.config;

import com.kanchanJS.ecommerce.entity.Country;
import com.kanchanJS.ecommerce.entity.Product;
import com.kanchanJS.ecommerce.entity.ProductCategory;
import com.kanchanJS.ecommerce.entity.State;

import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private EntityManager entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE};

        //disable HTTP methods for Product : PUT, POST and DELETE
        disableHttpMethods(Product.class, config, theUnsupportedActions);

        //disable HTTP methods for ProductCategory : PUT, POST and DELETE
        disableHttpMethods(ProductCategory.class, config, theUnsupportedActions);

        //disable HTTP methods for Country : PUT, POST and DELETE
        disableHttpMethods(Country.class, config, theUnsupportedActions);

        //disable HTTP methods for State : PUT, POST and DELETE
        disableHttpMethods(State.class, config, theUnsupportedActions);

        // call an internal helper method
        exposeIds(config);
    }


    private void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metadata, httpMethod) -> httpMethod.disable(theUnsupportedActions))
                .withCollectionExposure((metadata, httpMethod) -> httpMethod.disable(theUnsupportedActions));
    }

    private void exposeIds(RepositoryRestConfiguration config){
        // expose entity ids
//        "productCategory" : [ {
//            "id" : 1,                     --->  Like this

        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        List<Class> entityClasses = new ArrayList<>();

        for(EntityType tempEntityType : entities){
            entityClasses.add(tempEntityType.getJavaType());
        }

        Class[] domainType = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainType);
    }
}