package br.com.bottinocode.cdpmirante.util.jpa;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ConversorLocalDate implements AttributeConverter<LocalDate, Date> {
    
   @Override
   public Date convertToDatabaseColumn(LocalDate locDate) {
       return locDate == null ? null : Date.valueOf(locDate);
   }

   @Override
   public LocalDate convertToEntityAttribute(Date sqlDate) {
       return sqlDate == null ? null : sqlDate.toLocalDate();
   }

}
