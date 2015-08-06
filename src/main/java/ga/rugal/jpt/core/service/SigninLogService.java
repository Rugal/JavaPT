/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ga.rugal.jpt.core.service;

import ga.rugal.jpt.core.entity.SigninLog;
import ml.rugal.sshcommon.page.Pagination;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rugal Bernstein
 */
public interface SigninLogService
{

    SigninLog deleteById(Integer id);

    @Transactional(readOnly = true)
    SigninLog getByID(Integer id);

    @Transactional(readOnly = true)
    Pagination getPage(int pageNo, int pageSize);

    SigninLog save(SigninLog bean);

    SigninLog update(SigninLog bean);

}
