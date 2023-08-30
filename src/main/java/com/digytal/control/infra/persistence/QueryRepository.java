package com.digytal.control.infra.persistence;

import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;

import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class QueryRepository {
    protected  <T> List<T> convertCollection(List<Tuple> result, Class dto) {
        List list = new ArrayList();
        result.forEach(tuple -> {
            list.add(convertElement(tuple, dto));
        });
        return list;

    }

    private <T> T convertElement(Tuple record, Class dto) {
        Object item = null;
        String field = null;
        try {
            item = dto.newInstance();
            for (TupleElement te : record.getElements()) {
                field = te.getAlias();
                Object value = record.get(field);
                set(item, field, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (T) item;
    }

    private void set(Object item, String field, Object value) throws Exception {
        if (value != null) {
            Class typeValue = value.getClass();
            if (typeValue.equals(Integer.class))
                value = new BigDecimal(value.toString()).intValue();
            else if (typeValue.equals(Long.class))
                value = new BigDecimal(value.toString()).longValue();
            else if (typeValue.equals(java.sql.Timestamp.class))
                value = ((java.sql.Timestamp) value).toLocalDateTime();
            else if (typeValue.equals(java.sql.Date.class))
                value = ((java.sql.Date) value).toLocalDate();

            /**
             * Usando o spring, depois ver outras formas como BeansUtils
             * https://stackoverflow.com/questions/10009052/invoking-setter-method-using-java-reflection
             */
            PropertyAccessor myAccessor = PropertyAccessorFactory.forDirectFieldAccess(item);
            String atttibute = field.replaceAll("\\_",".");
            myAccessor.setPropertyValue(atttibute, value);
        }
    }
}
