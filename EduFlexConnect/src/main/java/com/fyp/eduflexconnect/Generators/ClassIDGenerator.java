package com.fyp.eduflexconnect.Generators;

import com.fyp.eduflexconnect.Models.Classroom;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

public class ClassIDGenerator implements IdentifierGenerator
{

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException
    {
        String semester_id = ((Classroom) object).getSemester().getSemester_id();
        String section_id = ((Classroom) object).getSection().getId();
        int course_id = ((Classroom) object).getCourse().getCourse_id();

        return course_id+semester_id+section_id;
    }
}
