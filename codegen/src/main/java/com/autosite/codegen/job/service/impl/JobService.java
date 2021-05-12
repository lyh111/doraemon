package com.autosite.codegen.job.service.impl;

import com.autosite.codegen.job.entity.Job;
import com.autosite.codegen.job.dao.JobDao;
import com.autosite.codegen.job.service.IJobService;
import com.autosite.codegen.common.base.BaseService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 作业调度表 服务实现类
 * </p>
 *
 * @author lyh
 * @since 2021-05-12
 */
@Service
public class JobService extends BaseService<JobDao, Job> implements IJobService {

}
