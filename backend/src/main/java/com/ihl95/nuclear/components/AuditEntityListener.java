package com.ihl95.nuclear.components;

import java.time.LocalDateTime;

import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuditEntityListener {

  private static final Logger auditLogger = LoggerFactory.getLogger("AuditLogger");

  @PrePersist
  public void onPrePersist(Object entity) {
      auditLogger.info("Entity Created: {} at {}", entity.getClass().getSimpleName(), LocalDateTime.now());
  }

  @PreUpdate
  public void onPreUpdate(Object entity) {
      auditLogger.info("Entity Updated: {} at {}", entity.getClass().getSimpleName(), LocalDateTime.now());
  }

  @PreRemove
  public void onPreRemove(Object entity) {
      auditLogger.info("Entity Deleted: {} at {}", entity.getClass().getSimpleName(), LocalDateTime.now());
  }
}
