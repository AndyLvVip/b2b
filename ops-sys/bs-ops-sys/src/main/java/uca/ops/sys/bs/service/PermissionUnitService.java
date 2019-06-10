package uca.ops.sys.bs.service;

import org.springframework.stereotype.Service;
import uca.ops.sys.bs.repository.PermissionUnitRepository;
import uca.ops.sys.domain.PermissionUnit;

import java.util.List;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/6/6 14:05
 */
@Service
public class PermissionUnitService {

    private final PermissionUnitRepository permissionUnitRepository;

    public PermissionUnitService(PermissionUnitRepository permissionUnitRepository) {
        this.permissionUnitRepository = permissionUnitRepository;
    }

    public List<PermissionUnit> findAll() {
        return this.permissionUnitRepository.findAll();
    }
}
