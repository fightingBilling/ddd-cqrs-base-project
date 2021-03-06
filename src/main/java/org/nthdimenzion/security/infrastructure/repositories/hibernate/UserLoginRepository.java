package org.nthdimenzion.security.infrastructure.repositories.hibernate;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.nthdimenzion.ddd.domain.annotations.DomainRepositoryImpl;
import org.nthdimenzion.ddd.infrastructure.hibernate.GenericHibernateRepository;
import org.nthdimenzion.ddd.infrastructure.hibernate.IHibernateDaoOperations;
import org.nthdimenzion.security.domain.IUserLoginRepository;
import org.nthdimenzion.security.domain.SecurityGroup;
import org.nthdimenzion.security.domain.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@DomainRepositoryImpl
@Transactional
public class UserLoginRepository extends GenericHibernateRepository<UserLogin, Long> implements IUserLoginRepository {

    protected UserLoginRepository(){

    }

    @Autowired
    public UserLoginRepository(IHibernateDaoOperations hibernateDaoOperations) {
        super(hibernateDaoOperations);
    }

    @Override
    public UserLogin findUserLoginWithUserName(String username) {
        DetachedCriteria userLoginWithUserNameCriteria = DetachedCriteria.forClass(UserLogin.class);
        userLoginWithUserNameCriteria.add(Restrictions.eq("credentials.username", username));
        return (UserLogin)hibernateDaoOperations.findByCriteria(userLoginWithUserNameCriteria).get(0);
    }

    public List<UserLogin> findAllUserLoginsWithGivenSecurityGroup(SecurityGroup securityGroup){
        DetachedCriteria userLoginWithUserNameCriteria = DetachedCriteria.forClass(UserLogin.class);
        userLoginWithUserNameCriteria.createCriteria("securityGroups").add(Restrictions.eq("name",
                securityGroup.getName()));
        return hibernateDaoOperations.findByCriteria(userLoginWithUserNameCriteria);
    }


}
