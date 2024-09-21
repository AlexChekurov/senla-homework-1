package com.alex.homework4example.entity;

import com.alex.homework4example.enumeration.RoleName;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Role.class)
public abstract class Role_ {

	public static volatile SingularAttribute<Role, RoleName> name;
	public static volatile SingularAttribute<Role, Long> id;

	public static final String NAME = "name";
	public static final String ID = "id";

}

