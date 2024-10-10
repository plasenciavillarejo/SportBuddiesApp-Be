package es.sport.buddies.entity.app.json.converter;

import jakarta.persistence.AttributeConverter;

public class BooleanToIntegerConverter implements AttributeConverter<Boolean, Integer> {

  @Override
  public Integer convertToDatabaseColumn(Boolean attribute) {
      if (attribute == null) {
          return null;
      }
      return attribute ? 1 : 0; // true -> 1, false -> 0
  }

  @Override
  public Boolean convertToEntityAttribute(Integer dbData) {
      if (dbData == null) {
          return null;
      }
      return dbData.equals(1); // 1 -> true, 0 -> false
  }
}
