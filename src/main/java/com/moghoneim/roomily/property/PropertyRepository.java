package com.moghoneim.roomily.property;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PropertyRepository extends JpaRepository<PropertyEntity,Long>, JpaSpecificationExecutor<PropertyEntity> {
}
