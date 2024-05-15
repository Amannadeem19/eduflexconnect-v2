package com.fyp.eduflexconnect.Generators;

import com.fyp.eduflexconnect.Models.Semester;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serial;
import java.io.Serializable;

public class SemesterIdGenerator implements IdentifierGenerator
{
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException
    {
         String semester_name = ((Semester) object).getName();
         int semester_year = ((Semester) object).getYear();

         return semester_name.toLowerCase()+String.valueOf(semester_year);

    }
}
