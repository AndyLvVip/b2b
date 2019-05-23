package uca.corporate.fs.service;

import org.springframework.stereotype.Service;
import uca.corporate.domain.Corporate;
import uca.corporate.fs.repository.CorporateRepository;

import static uca.base.constant.Constants.SYSTEM;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/5/17 14:24
 */
@Service
public class CorporateService {

    private final CorporateRepository corporateRepository;

    public CorporateService(CorporateRepository corporateRepository) {
        this.corporateRepository = corporateRepository;
    }

    public Corporate create(String username) {
        Corporate corporate = Corporate.newInstance(username);
        corporateRepository.insert(corporate, SYSTEM);
        return corporate;
    }
}
