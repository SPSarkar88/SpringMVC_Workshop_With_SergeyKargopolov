package com.matteo.restfulwebservices.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OperationStatusModel {

  private String operationResult;
  private String operationName;

  public OperationStatusModel(String operationResult, String operationName) {
    this.operationResult = operationResult;
    this.operationName = operationName;
  }

  public OperationStatusModel() {}
}
