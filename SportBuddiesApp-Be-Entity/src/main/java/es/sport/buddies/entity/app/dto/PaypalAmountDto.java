package es.sport.buddies.entity.app.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class PaypalAmountDto implements Serializable {

  private double total;
  
  private String currency;
  
  private static final long serialVersionUID = -3902760622759163981L;

}
