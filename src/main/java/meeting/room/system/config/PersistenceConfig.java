//package meeting.room.system.config;
//import com.querydsl.jpa.JPQLTemplates;
//import com.querydsl.jpa.impl.JPAPathBuilder;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import jakarta.persistence.EntityManager;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
//@Configuration
//public class PersistenceConfig {
//
//    @Bean
//    public JPAQueryFactory queryFactory(EntityManager em) {
//        JPQLTemplates templates = JPQLTemplates.DEFAULT; // You may need to adjust this based on your requirements
//        return new JPAQueryFactory(templates, new JPAPathBuilder<>(em.getMetamodel(), templates));
//
//    }
//}
